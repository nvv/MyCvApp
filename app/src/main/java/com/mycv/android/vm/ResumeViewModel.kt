package com.mycv.android.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mycv.android.core.DependencyLocator
import com.mycv.android.data.model.Resume

class ResumeViewModel : ViewModel() {

    val resume = MutableLiveData<Resume>()

    val isLoading = MutableLiveData<Boolean>()

    fun loadResume() {
        isLoading.value = true
        Thread(Runnable {
            resume.postValue(DependencyLocator.getInstance().resumeFacade.getResume())
            isLoading.postValue(false)
        }).start()
    }

}