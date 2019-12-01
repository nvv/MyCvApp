package com.mycv.android.data.model

import com.google.gson.annotations.SerializedName

data class ResumeReferences(
    @SerializedName("profile") var profile: Profile? = null,
    @SerializedName("contacts") var contacts: String? = null,
    @SerializedName("objective") var objective: String? = null,
    @SerializedName("skills_full") var skillsFull: String? = null,
    @SerializedName("experience") var experience: List<String>? = null,
    @SerializedName("education") var education: List<String>? = null
)