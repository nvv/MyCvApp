package com.mycv.android.data.mapper

import com.mycv.android.data.model.Resume
import com.mycv.android.ui.adapter.viewitem.ContactViewItem
import com.mycv.android.ui.adapter.viewitem.ResumeViewItem

class ResumeToResumeViewItemMapper : Mapper<Resume, ResumeViewItem> {

    override fun map(item: Resume): ResumeViewItem {
        return ResumeViewItem(
            profile = item.profile,
            contacts = item.contacts?.contacts?.filter { it.key != "address" }?.map {
                ContactViewItem(it.key, it.value)
            },
            objectiveNotes = item.objectiveNotes,
            skillMap = item.skillMap,
            experience = item.experience,
            education = item.education
        )
    }

}