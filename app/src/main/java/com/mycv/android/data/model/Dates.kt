package com.mycv.android.data.model

import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("from") var from: String? = null,
    @SerializedName("to") var to: String? = null
)