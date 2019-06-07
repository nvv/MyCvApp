package com.mycv.android.ui.adapter.vh

import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.mycv.android.R
import com.mycv.android.ui.adapter.ExperienceExpandListener
import com.mycv.android.ui.adapter.entry.ExperienceEntry
import com.mycv.android.ui.adapter.entry.TitledEntry
import kotlinx.android.synthetic.main.experience_row_compact.view.*

class ExperienceViewHolder(view: View, private val listener: ExperienceExpandListener? = null) : TitledViewViewHolder(view) {

    override fun bind(entry: TitledEntry) {

        details?.let { details ->
            details.removeAllViews()
            (entry as? ExperienceEntry)?.let {
                it.experienceEntries.forEach { item ->
                    val row = LayoutInflater.from(details.context).inflate(R.layout.experience_row_compact, details, false)

                    row.company.text = item.companyName
                    Glide.with(row.logo).load(item.logo).into(row.logo)

                    details.addView(row)

                    row.setOnClickListener {
                        listener?.onSelected(item)
                    }
                }
            }
        }
    }
}