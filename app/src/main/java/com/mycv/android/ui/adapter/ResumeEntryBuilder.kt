package com.mycv.android.ui.adapter

import android.content.Context
import com.mycv.android.R
import com.mycv.android.data.model.Resume
import com.mycv.android.ui.adapter.entry.*

object ResumeEntryBuilder {

    fun build(context: Context, resume: Resume): List<TitledEntry> {
        val array = mutableListOf<TitledEntry>()

        resume.let {
            resume.profile?.let {
                array.add(ProfileEntry(it))
            }
            resume.contacts?.let {
                array.add(ContactsEntry(context.getString(R.string.contacts), it))
            }
            resume.objectiveNotes?.let {
                array.add(ResumeObjectiveEntry(context.getString(R.string.resume_objective), it))
            }
            resume.skillMap?.let {
                array.add(SkillsEntry(context.getString(R.string.skills), it))
            }
            resume.experience?.let {
                array.add(TitledEntry("Experience"))
            }
            resume.education?.let {
                array.add(TitledEntry("Education"))
            }

        }

        return array
    }

}