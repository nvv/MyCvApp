package com.mycv.android.data.facade

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import com.mycv.android.core.Storage
import com.mycv.android.data.model.Resume
import com.mycv.android.data.service.ResumeService
import java.io.File

class ResumeFacade constructor(private val service: ResumeService,
                               private val manager: ConnectivityManager,
                               private val storage: Storage) {

    fun getResume() : Resume? {
        val json: String?
        if (isNetworkAvailable() && isTimeToUpdate()) {
            json = service.getResumeRaw()
            json?.let {
                cacheFile().writeText(it)
                storage.resumeUpdateTimestap = System.currentTimeMillis()
            }
        } else {
            json = cacheFile().readText()
        }

        return Gson().fromJson(json, Resume::class.java)
    }

    private fun cacheFile() = File(storage.cacheDir, RESUME_CACHE_FILE)

    private fun isTimeToUpdate() = System.currentTimeMillis() - storage.resumeUpdateTimestap > RELOAD_RESUME_PERIOD

    private fun isNetworkAvailable(): Boolean {
        var activeNetworkInfo: NetworkInfo? = manager.activeNetworkInfo
        return activeNetworkInfo?.isConnected == true
    }

    companion object {
        private const val RELOAD_RESUME_PERIOD = 7200000 // 2 hours in millis
        private const val RESUME_CACHE_FILE = "resume_cache"
    }
}