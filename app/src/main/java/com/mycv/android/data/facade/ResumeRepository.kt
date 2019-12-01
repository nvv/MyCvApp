package com.mycv.android.data.facade

import com.google.gson.Gson
import com.mycv.android.core.NetworkManager
import com.mycv.android.core.Storage
import com.mycv.android.data.model.EducationInstitution
import com.mycv.android.data.model.Resume
import com.mycv.android.data.model.WorkExperience
import com.mycv.android.data.network.retrofitservice.ResumeService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Singleton

@Singleton
class ResumeRepository constructor(
    private val service: ResumeService,
    private val manager: NetworkManager,
    private val storage: Storage
) {

    suspend fun getResume(force: Boolean = false): Resume? {
        var resume: Resume? = null

        val gson = Gson()

        if (manager.isNetworkAvailable() && (isTimeToUpdate() || force)) {
            // Load resume references. Most parts are fetched with separate requests.
            service.loadResume()?.let { reference ->

                // load contacts, objective map and full skills set
                val contacts = async { service.loadContacts(reference.contacts) }
                val objective = async { service.loadObjectiveNotes(reference.objective) }
                val skillsMap = async { service.loadSkills(reference.skillsFull) }

                // each work item as well as educational institution must be requested separately
                val workItems = mutableListOf<WorkExperience>()
                val educationItems = mutableListOf<EducationInstitution>()

                val workItemDeferredList = mutableListOf<Deferred<WorkExperience?>>()
                val educationItemsDeferredList = mutableListOf<Deferred<EducationInstitution?>>()

                reference.experience?.forEach { work ->
                    workItemDeferredList.add(async { service.loadWorkExperienceItem(work) })
                }

                reference.education?.forEach { institutionItem ->
                    educationItemsDeferredList.add(async { service.loadEducationItem(institutionItem) })
                }

                // wait for work items
                workItemDeferredList.forEach { deferred ->
                    deferred.await()?.let {
                        workItems.add(it)
                    }
                }

                // wait for educational institution
                educationItemsDeferredList.forEach { deferred ->
                    deferred.await()?.let {
                        educationItems.add(it)
                    }
                }

                resume = Resume(
                    reference.profile,
                    contacts.await(),
                    objective.await(),
                    skillsMap.await(),
                    workItems,
                    educationItems
                )

                storage.putResumeJsonToCache(gson.toJson(resume))
            }
        } else {
            resume = gson.fromJson(storage.getResumeJson(), Resume::class.java)
        }

        return resume
    }

    private suspend fun <T> async(func: suspend () -> T): Deferred<T> {
        return GlobalScope.async (Dispatchers.IO) { func.invoke() }
    }

    private fun isTimeToUpdate() =
        System.currentTimeMillis() - storage.resumeUpdateTimestap > RELOAD_RESUME_PERIOD

    companion object {
        const val RELOAD_RESUME_PERIOD = 7200000 // 2 hours in millis
    }
}