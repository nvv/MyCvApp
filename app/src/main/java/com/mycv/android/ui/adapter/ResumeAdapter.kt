package com.mycv.android.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mycv.android.R
import com.mycv.android.ui.adapter.entry.ContactsEntry
import com.mycv.android.ui.adapter.entry.ProfileEntry
import com.mycv.android.ui.adapter.entry.TitledEntry
import kotlinx.android.synthetic.main.label_value_line.view.*
import kotlinx.android.synthetic.main.profile_resume_row.view.*

class ResumeAdapter(private var data: List<TitledEntry>? = null) : RecyclerView.Adapter<TitledViewVH>() {

    fun setDataSet(entries: List<TitledEntry>) {
        this.data = entries
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        when (data?.get(position)) {
            is ProfileEntry -> TYPE_PROFILE
            is ContactsEntry -> TYPE_CONTACTS
            else -> TYPE_DEFAULT
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TitledViewVH {

        return when (type) {
            TYPE_PROFILE -> ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_resume_row, parent, false))
            TYPE_CONTACTS -> ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contacts_resume_row, parent, false))
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
        entry.isExpanded = visibility
        holder.details?.visibility = visibility
    }

    override fun getItemCount() = data?.size ?: 0

    companion object {
        const val TYPE_DEFAULT = 0
        const val TYPE_PROFILE = 1
        const val TYPE_CONTACTS = 2
    }
}

open class TitledViewVH(view: View): RecyclerView.ViewHolder(view) {

    val title: TextView? = view.findViewById(R.id.title)
    val details: LinearLayout? = view.findViewById(R.id.details)

    open fun bind(entry: TitledEntry) {

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

//    private val fullName: TextView? = view.fullName
//    private val profession: TextView? = view.profession

    override fun bind(entry: TitledEntry) {

        details?.let { details ->
            (entry as? ContactsEntry)?.let {
                it.contacts.entries.forEach { item ->
                    val line = LayoutInflater.from(details.context).inflate(R.layout.label_value_line, details, false)

                    line.label.text = item.key
                    line.value.text = item.value

                    details.addView(line)
                }
            }
        }

//        (entry as? ProfileEntry)?.let {
//            fullName?.text = entry.fullName
//            profession?.text = entry.profession
//        }
    }
}