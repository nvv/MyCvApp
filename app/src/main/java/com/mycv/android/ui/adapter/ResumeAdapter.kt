package com.mycv.android.ui.adapter

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mycv.android.R
import com.mycv.android.ui.adapter.entry.*
import kotlinx.android.synthetic.main.label_value_line.view.*
import kotlinx.android.synthetic.main.profile_resume_row.view.*
import kotlinx.android.synthetic.main.simple_line.view.*

class ResumeAdapter(private var data: List<TitledEntry>? = null) : RecyclerView.Adapter<TitledViewVH>() {

    fun setData(entries: List<TitledEntry>) {
        this.data = entries
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        when (data?.get(position)) {
            is ProfileEntry -> TYPE_PROFILE
            is ContactsEntry -> TYPE_CONTACTS
            is SkillsEntry -> TYPE_SKILLS
            is ResumeObjectiveEntry -> TYPE_RESUME_OBJECTIVE
            else -> TYPE_DEFAULT
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TitledViewVH {

        return when (type) {
            TYPE_PROFILE -> ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_resume_row, parent, false))
            TYPE_CONTACTS -> ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
            TYPE_SKILLS -> SkillsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
            TYPE_RESUME_OBJECTIVE -> ResumeObjectiveViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
            else -> TitledViewVH(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
        }
    }

    override fun onBindViewHolder(holder: TitledViewVH, position: Int) {
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

    private fun click(holder: TitledViewVH, entry: TitledEntry) {
        val visibility = if (entry.isExpanded == View.VISIBLE) View.GONE else View.VISIBLE

        // animate layout change only when showing
        // because hide looks ugly and unstable on old phones like Nexus 5
        holder.detailsCard?.layoutTransition = if (visibility == View.VISIBLE) LayoutTransition() else null

        entry.isExpanded = visibility
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
        private const val TYPE_PROFILE = 1
        private const val TYPE_CONTACTS = 2
        private const val TYPE_SKILLS = 3
        private const val TYPE_RESUME_OBJECTIVE = 4
    }
}

open class TitledViewVH(view: View): RecyclerView.ViewHolder(view) {

    val title: TextView? = view.findViewById(R.id.title)
    val details: LinearLayout? = view.findViewById(R.id.details)
    val detailsCard: ViewGroup? = view.findViewById(R.id.detailsCard)
    val expand: View? = view.findViewById(R.id.expand)

    open fun bind(entry: TitledEntry) {

    }

    protected fun addKeyValueLine(details: LinearLayout, key: String, value: String) {
        val line = LayoutInflater.from(details.context).inflate(R.layout.label_value_line, details, false)

        line.label.text = key
        line.value.text = value

        details.addView(line)
    }
}

class ProfileViewHolder(view: View): TitledViewVH(view) {

    private val fullName: TextView? = view.fullName
    private val profession: TextView? = view.profession

    override fun bind(entry: TitledEntry) {
        (entry as? ProfileEntry)?.let {
            fullName?.text = entry.fullName
            profession?.text = entry.profession
        }
    }
}

class ContactsViewHolder(view: View): TitledViewVH(view) {

    override fun bind(entry: TitledEntry) {

        details?.let { details ->
            (entry as? ContactsEntry)?.let {
                it.contacts.entries.forEach { item ->
                    addKeyValueLine(details, item.key, item.value)
                }
            }
        }
    }
}

class SkillsViewHolder(view: View): TitledViewVH(view) {

    override fun bind(entry: TitledEntry) {

        details?.let { details ->
            (entry as? SkillsEntry)?.let {
                it.skills.entries.forEach { item ->
                    addKeyValueLine(details, item.key, item.value.joinToString())
                }
            }
        }
    }
}

class ResumeObjectiveViewHolder(view: View): TitledViewVH(view) {

    override fun bind(entry: TitledEntry) {

        details?.let { details ->
            (entry as? ResumeObjectiveEntry)?.let {
                it.resumeObjective.map { item -> "\u2022 $item" }.forEach { item ->
                    val line = LayoutInflater.from(details.context).inflate(R.layout.simple_line, details, false)

                    line.line.text = item

                    details.addView(line)
                }
            }
        }
    }
}

