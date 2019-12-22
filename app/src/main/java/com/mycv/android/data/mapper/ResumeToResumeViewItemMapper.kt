package com.mycv.android.data.mapper

import android.content.Context
import com.mycv.android.R
import com.mycv.android.data.model.Resume
import com.mycv.android.ui.adapter.viewitem.*

class ResumeToResumeViewItemMapper(private val context: Context) : Mapper<Resume, ResumeViewItem> {

    override fun map(item: Resume): ResumeViewItem {
        val data = mutableListOf<ViewItem>()

        item.contacts?.contacts?.let { contacts ->
            data.add(
                ContactsViewItem(context.getString(R.string.contacts),
                    contacts.filter { it.key != "address" }.map {
                        ContactViewItem(it.key, it.value)
                    })
            )
        }
        item.objectiveNotes?.let {
            data.add(ResumeObjectiveViewItem(context.getString(R.string.resume_objective), it.notes))
        }
        item.skillMap?.skills?.let {
            data.add(SkillsViewItem(context.getString(R.string.skills), it))
        }
        item.experience?.let {
            data.add(ExpirienceViewItem(context.getString(R.string.experience), it))
        }
        item.education?.let {
            data.add(EducationViewItem(context.getString(R.string.education), it))
        }

        return ResumeViewItem(data)
    }

}