package com.mycv.android.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mycv.android.core.DependencyLocator
import com.mycv.android.data.model.Resume

class ResumeViewModel : ViewModel() {

    val resume = MutableLiveData<Resume>()

    fun loadResume() {
        Thread(Runnable {
            resume.postValue(DependencyLocator.getInstance().resumeFacade.getResume())
        }).start()
    }

}