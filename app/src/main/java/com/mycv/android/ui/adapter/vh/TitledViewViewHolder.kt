package com.mycv.android.ui.adapter.vh

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mycv.android.R
import com.mycv.android.ui.adapter.entry.TitledEntry
import kotlinx.android.synthetic.main.label_value_line.view.*

open class TitledViewViewHolder(view: View): RecyclerView.ViewHolder(view) {

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