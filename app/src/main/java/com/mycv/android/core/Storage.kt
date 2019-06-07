package com.mycv.android.core

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.io.File

class Storage (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFERENCE, Activity.MODE_PRIVATE)

    private val cacheDir = context.cacheDir

    var resumeUpdateTimestap: Long
        get() = preferences.getLong(RESUME_UPDATE_TIMESTAMP, 0)
        set(value) = preferences.edit().putLong(RESUME_UPDATE_TIMESTAMP, value).apply()


    fun putResumeJsonToCache(json: String) {
        cacheFile().writeText(json)
        resumeUpdateTimestap = System.currentTimeMillis()
    }

    private fun cacheFile() = File(cacheDir, RESUME_CACHE_FILE)

    fun getResumeJson() = if (cacheFile().exists()) cacheFile().readText() else null

    companion object {
        private const val PREFERENCE = "PREFERENCE"
        private const val RESUME_UPDATE_TIMESTAMP = "PREFERENCE"

        private const val RESUME_CACHE_FILE = "resume_cache"
    }
}