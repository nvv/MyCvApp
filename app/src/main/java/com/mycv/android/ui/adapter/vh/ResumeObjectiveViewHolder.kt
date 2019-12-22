package com.mycv.android.ui.adapter.vh

import android.view.LayoutInflater
import android.view.View
import com.mycv.android.R
import com.mycv.android.ui.adapter.viewitem.ResumeObjectiveViewItem
import com.mycv.android.ui.adapter.viewitem.ViewItem
import kotlinx.android.synthetic.main.simple_line.view.*

class ResumeObjectiveViewHolder(view: View): TitledViewViewHolder(view) {

    override fun bind(entry: ViewItem) {

        details?.let { details ->
            details.removeAllViews()
            (entry as? ResumeObjectiveViewItem)?.let {
                it.resumeObjective.map { item -> "\u2022 $item" }.forEach { item ->
                    val line = LayoutInflater.from(details.context).inflate(R.layout.simple_line, details, false)

                    line.line.text = item

                    details.addView(line)
                }
            }
        }
    }
}