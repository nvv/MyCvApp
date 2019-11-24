package com.mycv.android.data.network.service

import android.content.Context
import com.mycv.android.R
import java.net.URL


class ResumeService private constructor(private val url: String) {

    fun getResumeRaw() : String? {
        URL(url).openStream().use { input ->
            return input.bufferedReader().use { it.readText() }
        }
    }

    companion object Factory {
        fun create(context: Context) = ResumeService(context.getString(R.string.resume_url))
    }
}