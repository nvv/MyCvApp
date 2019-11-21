package com.mycv.android.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Intent
import android.net.Uri
import com.mycv.android.data.facade.ResumeFacade
import com.mycv.android.data.model.Resume
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResumeViewModel @Inject constructor (private val resumeFacade: ResumeFacade) : ViewModel() {

    val resume = MutableLiveData<Resume>()

    val isLoading = MutableLiveData<Boolean>()

    fun loadResume(force: Boolean = false) {
        isLoading.value = true
        GlobalScope.launch {
            resume.postValue(resumeFacade.getResume(force))
            isLoading.postValue(false)
        }
    }

    fun contactViaEmail(to: String, subj: String) =
        Intent(Intent.ACTION_SENDTO)
            .setData(Uri.parse("mailto:$to"))
            .putExtra(Intent.EXTRA_SUBJECT, subj)

}