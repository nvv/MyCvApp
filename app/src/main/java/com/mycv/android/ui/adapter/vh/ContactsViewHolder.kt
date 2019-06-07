package com.mycv.android.ui.adapter.vh

import android.view.View
import com.mycv.android.ui.adapter.entry.ContactsEntry
import com.mycv.android.ui.adapter.entry.TitledEntry

class ContactsViewHolder(view: View): TitledViewViewHolder(view) {

    override fun bind(entry: TitledEntry) {
        details?.let { details ->
            details.removeAllViews()
            (entry as? ContactsEntry)?.let {
                it.contacts.entries.forEach { item ->
                    addKeyValueLine(details, item.key, item.value)
                }
            }
        }
    }
}