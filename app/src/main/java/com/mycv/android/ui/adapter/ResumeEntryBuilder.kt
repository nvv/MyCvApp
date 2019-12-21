package com.mycv.android.ui.adapter

import android.content.Context
import com.mycv.android.R
import com.mycv.android.data.model.Resume
import com.mycv.android.ui.adapter.entry.*

object ResumeEntryBuilder {

    fun build(context: Context, resume: Resume): List<TitledEntry> {
        val array = mutableListOf<TitledEntry>()

        resume.let {
            resume.contacts?.contacts?.let {
                array.add(ContactsEntry(context.getString(R.string.contacts), it))
            }
            resume.objectiveNotes?.let {
                array.add(ResumeObjectiveEntry(context.getString(R.string.resume_objective), it.notes))
            }
            resume.skillMap?.skills?.let {
                array.add(SkillsEntry(context.getString(R.string.skills), it))
            }
            resume.experience?.let {
                array.add(ExperienceEntry(context.getString(R.string.experience), it))
            }
            resume.education?.let {
                array.add(EducationEntry(context.getString(R.string.education), it))
            }

        }

        return array
    }

}