package com.mycv.android.data.network.retrofitservice

import com.mycv.android.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path

interface ResumeService {

    @GET("cv_references")
    suspend fun loadResume(): ResumeReferences?

    @GET("cv")
    suspend fun loadResumeFull(): Resume?

    @GET("{id}")
    suspend fun loadContacts(@Path(value="id") id: String?): Contacts?

    @GET("{id}")
    suspend fun loadObjectiveNotes(@Path(value="id") id: String?): ObjectiveNotes?

    @GET("{id}")
    suspend fun loadSkills(@Path(value="id") id: String?): Skills?

    @GET("{id}")
    suspend fun loadWorkExperienceItem(@Path(value="id") id: String): WorkExperience?

    @GET("{id}")
    suspend fun loadEducationItem(@Path(value="id") id: String): EducationInstitution?

}