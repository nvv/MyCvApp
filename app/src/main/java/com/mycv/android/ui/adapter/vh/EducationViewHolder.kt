package com.mycv.android.ui.adapter.vh

import android.view.LayoutInflater
import android.view.View
import com.mycv.android.R
import com.mycv.android.ui.adapter.entry.EducationEntry
import com.mycv.android.ui.adapter.entry.TitledEntry
import kotlinx.android.synthetic.main.education_institution_row.view.*

class EducationViewHolder(view: View): TitledViewViewHolder(view) {

    override fun bind(entry: TitledEntry) {

        details?.let { details ->
            details.removeAllViews()
            (entry as? EducationEntry)?.let {
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