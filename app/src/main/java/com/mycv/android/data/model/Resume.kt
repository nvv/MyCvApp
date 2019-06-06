package com.mycv.android.data.model

import com.google.gson.annotations.SerializedName

data class Resume(
    @SerializedName("profile") var profile: Profile? = null,
    @SerializedName("contacts") var contacts: Map<String, String>? = null,
    @SerializedName("objective") var objectiveNotes: List<String>? = null,
    @SerializedName("skills") var skillMap: Map<String,List<String>>? = null,
    @SerializedName("experience") var experience: List<WorkExperience>? = null,
    @SerializedName("education") var education: List<EducationInstitution>? = null
)