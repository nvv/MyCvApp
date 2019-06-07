package com.mycv.android.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.net.Uri
import com.mycv.android.core.DependencyLocator
import com.mycv.android.data.model.Resume

class ResumeViewModel : ViewModel() {

    val resume = MutableLiveData<Resume>()

    val isLoading = MutableLiveData<Boolean>()

    fun loadResume(force: Boolean = false) {
        isLoading.value = true
        Thread(Runnable {
            resume.postValue(DependencyLocator.getInstance().resumeFacade.getResume(force))
            isLoading.postValue(false)
        }).start()
    }

    fun contactViaEmail(to: String, subj: String) =
        Intent(Intent.ACTION_SENDTO)
            .setData(Uri.parse("mailto:$to"))
            .putExtra(Intent.EXTRA_SUBJECT, subj)

}