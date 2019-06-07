package com.mycv.android.data.model

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        Dates(parcel.readString(), parcel.readString()),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(companyName)
        dest?.writeString(location)
        dest?.writeString(logo)
        dest?.writeStringList(projects)
        dest?.writeString(dates?.from)
        dest?.writeString(dates?.to)
        dest?.writeString(position)
        dest?.writeString(description)
        dest?.writeStringList(achievements)
        dest?.writeStringList(environment)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<WorkExperience> {
        override fun createFromParcel(parcel: Parcel): WorkExperience {
            return WorkExperience(parcel)
        }

        override fun newArray(size: Int): Array<WorkExperience?> {
            return arrayOfNulls(size)
        }
    }
}