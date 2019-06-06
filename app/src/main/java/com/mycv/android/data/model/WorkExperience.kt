package com.mycv.android.data.model

import com.google.gson.annotations.SerializedName

data class WorkExperience(
    @SerializedName("company_name") var companyName: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("logo") var logo: String? = null,
    @SerializedName("projects") var projects: List<String>? = null,
    @SerializedName("dates") var dates: Dates? = null,
    @SerializedName("position") var position: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("achievements") var achievements: List<String>? = null,
    @SerializedName("tech_environment_grouped") var environment: List<String>? = null
)