package com.mycv.android.data.model

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("profession") var profession: String? = null,
    @SerializedName("photo") var photo: String? = null
)