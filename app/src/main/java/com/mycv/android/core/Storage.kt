package com.mycv.android.core

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Storage (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFERENCE, Activity.MODE_PRIVATE)

    val cacheDir = context.cacheDir

    var resumeUpdateTimestap: Long
        get() = preferences.getLong(RESUME_UPDATE_TIMESTAMP, 0)
        set(value) = preferences.edit().putLong(RESUME_UPDATE_TIMESTAMP, value).apply()


    companion object {
        private const val PREFERENCE = "PREFERENCE"
        private const val RESUME_UPDATE_TIMESTAMP = "PREFERENCE"
    }
}