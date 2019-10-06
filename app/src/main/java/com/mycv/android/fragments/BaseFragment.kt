package com.mycv.android.fragments

interface BaseFragment {

    fun getTitle(): String

    fun isMenuSupported(): Boolean

    fun hasBackNavigation(): Boolean

}