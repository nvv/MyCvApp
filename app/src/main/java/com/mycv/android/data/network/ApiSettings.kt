package com.mycv.android.data.network

import com.mycv.android.BuildConfig


object ApiSettings {

    private const val SCHEME = BuildConfig.SCHEME

    private const val HOSTNAME = BuildConfig.HOSTNAME

    const val DEFAULT_TIMEOUT_MIN = 1L

    const val SERVER = SCHEME + HOSTNAME

}