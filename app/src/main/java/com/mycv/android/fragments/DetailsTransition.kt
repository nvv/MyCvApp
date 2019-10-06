package com.mycv.android.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.transition.*

/**
 * @author Vlad Namashko
 */
@RequiresApi(Build.VERSION_CODES.KITKAT)
class DetailsTransition : TransitionSet() {

    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds()).
                addTransition(ChangeTransform()).
                addTransition(ChangeImageTransform())
    }
}