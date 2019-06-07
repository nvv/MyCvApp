package com.mycv.android.ui.adapter.entry

import com.mycv.android.data.model.Profile

class ProfileEntry(profile: Profile) : TitledEntry(title = "Profile") {

    val fullName = profile.fullName
    val profession = profile.profession

}