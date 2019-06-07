package com.mycv.android.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mycv.android.R
import com.mycv.android.ui.adapter.entry.ProfileEntry
import com.mycv.android.ui.adapter.entry.TitledEntry
import kotlinx.android.synthetic.main.profile_resume_row.view.*
import kotlinx.android.synthetic.main.simple_resume_row.view.*

class ResumeAdapter(private var myDataset: List<TitledEntry>? = null) : RecyclerView.Adapter<ResumeAdapter.TitledViewVH>() {

    fun setDataSet(data: List<TitledEntry>) {
        myDataset = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =

        when (myDataset?.get(position)) {
            is ProfileEntry -> TYPE_PROFILE
            else -> TYPE_DEFAULT
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ResumeAdapter.TitledViewVH {

        return when (type) {
            TYPE_PROFILE -> ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_resume_row, parent, false))
            else ->TitledViewVH(LayoutInflater.from(parent.context).inflate(R.layout.simple_resume_row, parent, false))
        }
    }

    override fun onBindViewHolder(holder: TitledViewVH, position: Int) {
        val entry = myDataset?.get(position)

        entry?.let {
            holder.title?.text = myDataset?.get(position)?.title

            holder.details.visibility = myDataset?.get(position)?.isExpanded ?: View.VISIBLE

            holder.bind(entry)

            holder.itemView.setOnClickListener {
                click(holder, entry)
            }

        }
    }

    private fun click(holder: TitledViewVH, entry: TitledEntry) {
        val visibility = if (entry.isExpanded == View.VISIBLE) View.GONE else View.VISIBLE
        entry.isExpanded = visibility
        holder.details.visibility = visibility
    }

    override fun getItemCount() = myDataset?.size ?: 0

    open class TitledViewVH(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView? = view.findViewById(R.id.title)
        val details = view.findViewById<View>(R.id.details)

        open fun bind(entry: TitledEntry) {

        }
    }

    class ProfileViewHolder(view: View) : TitledViewVH(view) {

        val fullNameLabel: TextView? = view.fullNameLabel
        val fullNameValue: TextView? = view.fullNameValue
//        val profession: TextView? = view.details

        override fun bind(entry: TitledEntry) {
            fullNameLabel?.text = "Full Name: "
            fullNameValue?.text = (entry as ProfileEntry).fullName

        }
    }


    companion object {

        const val TYPE_DEFAULT = 0
        const val TYPE_PROFILE = 1
    }


}