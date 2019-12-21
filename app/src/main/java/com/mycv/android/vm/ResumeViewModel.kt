package com.mycv.android.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mycv.android.R
import com.mycv.android.data.facade.ResumeRepository
import com.mycv.android.data.model.Resume
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResumeViewModel @Inject constructor(private val resumeRepository: ResumeRepository) : ViewModel() {

    private val _resume: MutableLiveData<Resume> = MutableLiveData()
    val resume: LiveData<Resume> = _resume

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profileDrawable = MutableLiveData<Drawable>()
    val profileDrawable: LiveData<Drawable> = _profileDrawable


    fun loadResume(force: Boolean = false) {
        _isLoading.value = true
        GlobalScope.launch {
            _resume.postValue(resumeRepository.getResume(force))
            _isLoading.postValue(false)
        }
    }

    fun load(context: Context, url: String) {
        Glide.with(context)
            .asDrawable()
            .load(url)
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    _profileDrawable.postValue(resource)
                }

            })

    }

    fun contactViaEmail(to: String, subj: String) =
        Intent(Intent.ACTION_SENDTO)
            .setData(Uri.parse("mailto:$to"))
            .putExtra(Intent.EXTRA_SUBJECT, subj)

}