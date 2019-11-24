package com.mycv.android.data.facade

import com.google.gson.Gson
import com.mycv.android.core.NetworkManager
import com.mycv.android.core.Storage
import com.mycv.android.data.model.Resume
import com.mycv.android.data.network.retrofitservice.ResumeService
import javax.inject.Singleton

@Singleton
class ResumeFacade constructor(private val service: ResumeService,
                               private val manager: NetworkManager,
                               private val storage: Storage) {

    suspend fun getResume(force: Boolean = false) : Resume? {
        val resume: Resume?

        val gson = Gson()
        if (manager.isNetworkAvailable() && (isTimeToUpdate() || force)) {
            resume = service.loadResume()
            resume?.let {
                storage.putResumeJsonToCache(gson.toJson(resume))
            }
        } else {
            resume = gson.fromJson(storage.getResumeJson(), Resume::class.java)
        }

        return resume
    }

    private fun isTimeToUpdate() = System.currentTimeMillis() - storage.resumeUpdateTimestap > RELOAD_RESUME_PERIOD

    companion object {
        const val RELOAD_RESUME_PERIOD = 7200000 // 2 hours in millis
    }
}