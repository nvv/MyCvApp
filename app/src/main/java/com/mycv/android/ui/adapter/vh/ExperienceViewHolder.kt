package com.mycv.android.ui.adapter.vh

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.mycv.android.R
import com.mycv.android.ui.adapter.ExperienceExpandListener
import com.mycv.android.ui.adapter.viewitem.ExpirienceViewItem
import com.mycv.android.ui.adapter.viewitem.ViewItem
import kotlinx.android.synthetic.main.experience_row_compact.view.*

class ExperienceViewHolder(view: View, private val listener: ExperienceExpandListener? = null) : TitledViewViewHolder(view) {

    override fun bind(entry: ViewItem) {

        details?.let { details ->
            details.removeAllViews()
            (entry as? ExpirienceViewItem)?.let {
                it.experienceEntries.forEach { item ->
                    val row = LayoutInflater.from(details.context).inflate(R.layout.experience_row_compact, details, false)

                    row.company.text = item.companyName
                    Glide.with(row.logo).load(item.logo).into(row.logo)

                    details.addView(row)

                    ViewCompat.setTransitionName(row.logo, item.companyName + "_logo")
                    ViewCompat.setTransitionName(row.company, item.companyName + "_title")

                    row.logo.setTag(R.id.transition_name, "logo")
                    row.company.setTag(R.id.transition_name, "company")

                    row.setOnClickListener {
                        listener?.onSelected(item, listOf(row.logo, row.company))
                    }
                }
            }
        }
    }
}