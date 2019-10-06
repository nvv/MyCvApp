package com.mycv.android

import android.view.View
import androidx.fragment.app.Fragment

interface NavigatableActivity {

    fun navigate(fragment: Fragment, sharedViews: List<View>? = null)

}