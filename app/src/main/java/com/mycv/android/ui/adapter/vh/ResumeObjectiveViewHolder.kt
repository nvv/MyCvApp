package com.mycv.android.ui.adapter.vh

import android.view.LayoutInflater
import android.view.View
import com.mycv.android.R
import com.mycv.android.ui.adapter.entry.ResumeObjectiveEntry
import com.mycv.android.ui.adapter.entry.TitledEntry
import kotlinx.android.synthetic.main.simple_line.view.*

class ResumeObjectiveViewHolder(view: View): TitledViewViewHolder(view) {

    override fun bind(entry: TitledEntry) {

        details?.let { details ->
            details.removeAllViews()
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