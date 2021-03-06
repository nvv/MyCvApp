package com.mycv.android.ui.adapter

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycv.android.R
import com.mycv.android.data.model.WorkExperience
import com.mycv.android.ui.adapter.vh.*
import com.mycv.android.ui.adapter.viewitem.*

class ResumeAdapter(
    private var data: List<ViewItem>? = null,
    private val listener: ExperienceExpandListener? = null
) : RecyclerView.Adapter<TitledViewViewHolder>() {

    fun setData(resumeViewItem: ResumeViewItem) {
        this.data = resumeViewItem.data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        when (data?.get(position)) {
            is ContactsViewItem -> TYPE_CONTACTS
            is SkillsViewItem -> TYPE_SKILLS
            is ResumeObjectiveViewItem -> TYPE_RESUME_OBJECTIVE
            is ExpirienceViewItem -> TYPE_EXPERIENCE
            is EducationViewItem -> TYPE_EDUCATION
            else -> TYPE_DEFAULT
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TitledViewViewHolder {

        return when (type) {
            TYPE_CONTACTS -> ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
            TYPE_SKILLS -> SkillsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
            TYPE_RESUME_OBJECTIVE -> ResumeObjectiveViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
            TYPE_EXPERIENCE -> ExperienceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false), listener)
            TYPE_EDUCATION -> EducationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
            else -> TitledViewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
        }
    }

    override fun onBindViewHolder(holder: TitledViewViewHolder, position: Int) {
        val entry = data?.get(position)

        entry?.let {
            holder.title?.text = data?.get(position)?.title

            holder.details?.visibility = data?.get(position)?.isExpanded ?: View.VISIBLE

            holder.bind(entry)

            holder.itemView.setOnClickListener {
                click(holder, entry)
            }

        }
    }

    private fun click(holder: TitledViewViewHolder, viewItem: ViewItem) {
        val visibility = if (viewItem.isExpanded == View.VISIBLE) View.GONE else View.VISIBLE

        // animate layout change only when showing
        // because hide looks ugly and unstable on old phones like Nexus 5
        holder.detailsCard?.layoutTransition = if (visibility == View.VISIBLE) LayoutTransition() else null

        viewItem.isExpanded = visibility
        holder.details?.visibility = visibility

        val showAnimation = visibility == View.GONE
        ObjectAnimator.ofFloat(
            holder.expand,
            "rotation",
            if (showAnimation) 0F else 180F,
            if (showAnimation) 180F else 0F
        ).setDuration(ROTATION_ANIM_DURATION).start()
    }

    override fun getItemCount() = data?.size ?: 0

    companion object {
        private const val ROTATION_ANIM_DURATION = 200L

        private const val TYPE_DEFAULT = 0
        private const val TYPE_CONTACTS = 2
        private const val TYPE_SKILLS = 3
        private const val TYPE_RESUME_OBJECTIVE = 4
        private const val TYPE_EXPERIENCE = 5
        private const val TYPE_EDUCATION = 6

    }
}

/**
 * DUMMY APPROACH BUT I DON'T WANT TO ADD EVENT BUS OR RX
 */
interface ExperienceExpandListener {
    fun onSelected(workExperienceEntry: WorkExperience, sharedViews: List<View>)
}

