package com.mycv.android.data.network.retrofitservice

import com.mycv.android.data.model.Resume
import retrofit2.http.GET

interface ResumeService {

    @GET("cv")
    suspend fun loadResume(): Resume?

}