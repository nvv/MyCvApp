package com.mycv.android.data.model

import com.google.gson.annotations.SerializedName

data class Resume(
    @SerializedName("profile") var profile: Profile? = null,
    @SerializedName("contacts") var contacts: Contacts? = null,
    @SerializedName("objective") var objectiveNotes: ObjectiveNotes? = null,
    @SerializedName("skills") var skillMap: Skills? = null,
    @SerializedName("experience") var experience: List<WorkExperience>? = null,
    @SerializedName("education") var education: List<EducationInstitution>? = null
)