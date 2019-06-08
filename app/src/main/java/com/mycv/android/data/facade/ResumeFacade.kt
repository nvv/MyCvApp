package com.mycv.android.data.facade

import com.google.gson.Gson
import com.mycv.android.core.NetworkManager
import com.mycv.android.core.Storage
import com.mycv.android.data.model.Resume
import com.mycv.android.data.service.ResumeService
import javax.inject.Singleton

@Singleton
class ResumeFacade constructor(private val service: ResumeService,
                               private val manager: NetworkManager,
                               private val storage: Storage) {

    fun getResume(force: Boolean = false) : Resume? {
        val json: String?
        if (manager.isNetworkAvailable() && (isTimeToUpdate() || force)) {
            json = service.getResumeRaw()
            json?.let {
                storage.putResumeJsonToCache(it)
            }
        } else {
            json = storage.getResumeJson()
        }

        return Gson().fromJson(json, Resume::class.java)
    }

    private fun isTimeToUpdate() = System.currentTimeMillis() - storage.resumeUpdateTimestap > RELOAD_RESUME_PERIOD

    companion object {
        const val RELOAD_RESUME_PERIOD = 7200000 // 2 hours in millis
    }
}