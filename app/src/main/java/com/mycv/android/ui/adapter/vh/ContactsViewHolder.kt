package com.mycv.android.ui.adapter.vh

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.mycv.android.R
import com.mycv.android.ui.adapter.viewitem.ContactViewItem
import com.mycv.android.ui.adapter.entry.ContactsEntry
import com.mycv.android.ui.adapter.entry.TitledEntry
import kotlinx.android.synthetic.main.contact_line.view.*

class ContactsViewHolder(view: View): TitledViewViewHolder(view) {

    override fun bind(entry: TitledEntry) {
        details?.let { details ->
            details.removeAllViews()
            (entry as? ContactsEntry)?.let {
                it.contacts?.forEach { item ->
                    addContactLine(details, item)
                }
            }
        }
    }

    private fun addContactLine(details: LinearLayout, item: ContactViewItem) {
        val line = LayoutInflater.from(details.context).inflate(R.layout.contact_line, details, false)

        line.icon.setImageResource(item.getIcon())
        line.value.text = item.value

        details.addView(line)
    }
}