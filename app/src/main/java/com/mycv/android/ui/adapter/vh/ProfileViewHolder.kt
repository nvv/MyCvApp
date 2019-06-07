package com.mycv.android.ui.adapter.vh

import android.view.View
import android.widget.TextView
import com.mycv.android.ui.adapter.entry.ProfileEntry
import com.mycv.android.ui.adapter.entry.TitledEntry
import kotlinx.android.synthetic.main.profile_resume_row.view.*

class ProfileViewHolder(view: View): TitledViewViewHolder(view) {

    private val fullName: TextView? = view.fullName
    private val profession: TextView? = view.profession

    override fun bind(entry: TitledEntry) {
        (entry as? ProfileEntry)?.let {
            fullName?.text = entry.fullName
            profession?.text = entry.profession
        }
    }
}