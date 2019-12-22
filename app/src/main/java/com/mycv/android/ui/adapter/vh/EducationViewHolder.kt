package com.mycv.android.ui.adapter.vh

import android.view.LayoutInflater
import android.view.View
import com.mycv.android.R
import com.mycv.android.ui.adapter.viewitem.EducationViewItem
import com.mycv.android.ui.adapter.viewitem.ViewItem
import kotlinx.android.synthetic.main.education_institution_row.view.*

class EducationViewHolder(view: View): TitledViewViewHolder(view) {

    override fun bind(entry: ViewItem) {

        details?.let { details ->
            details.removeAllViews()
            (entry as? EducationViewItem)?.let {
                it.educationInstitutions.forEach { item ->
                    val line = LayoutInflater.from(itemView.context)
                        .inflate(R.layout.education_institution_row, details, false)

                    val dateRange = "${item.dates?.from} / ${item.dates?.to}"

                    line.title.text = item.title
                    line.location.text = item.location
                    line.dates.text = dateRange
                    line.diploma.text = item.diploma

                    details.addView(line)
                }
            }
        }
    }
}