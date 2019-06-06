package com.mycv.android.data.model

import com.google.gson.annotations.SerializedName

data class EducationInstitution(
    @SerializedName("title") var title: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("diploma") var diploma: String? = null,
    @SerializedName("dates") var dates: Dates? = null
)