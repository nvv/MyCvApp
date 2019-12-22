package com.mycv.android.ui.adapter.viewitem

import com.mycv.android.data.model.*

data class ResumeViewItem (
    var profile: Profile? = null,
    var contacts: List<ContactViewItem>? = null,
    var objectiveNotes: ObjectiveNotes? = null,
    var skillMap: Skills? = null,
    var experience: List<WorkExperience>? = null,
    var education: List<EducationInstitution>? = null
)