package com.mycv.android.ui.adapter.viewitem

import com.mycv.android.R

data class ContactViewItem (val id: String, val value: String) {

    fun getIcon(): Int = when (id) {
        PHONE -> R.drawable.ic_phone
        LINKEDIN -> R.drawable.ic_linkedin
        EMAIL -> R.drawable.ic_email
        GITHUB -> R.drawable.ic_github
        GOOGLE_PLAY -> R.drawable.ic_google_play
        else -> R.drawable.ic_phone
    }

    companion object {
        private const val PHONE = "phone"
        private const val LINKEDIN = "linkedin"
        private const val EMAIL = "email"
        private const val GITHUB = "github"
        private const val GOOGLE_PLAY = "google_play"
    }
}
