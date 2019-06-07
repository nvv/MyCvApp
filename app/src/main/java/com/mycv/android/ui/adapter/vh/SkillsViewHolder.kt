package com.mycv.android.ui.adapter.vh

import android.view.View
import com.mycv.android.ui.adapter.entry.SkillsEntry
import com.mycv.android.ui.adapter.entry.TitledEntry

class SkillsViewHolder(view: View): TitledViewViewHolder(view) {

    override fun bind(entry: TitledEntry) {

        details?.let { details ->
            (entry as? SkillsEntry)?.let {
                it.skills.entries.forEach { item ->
                    addKeyValueLine(details, item.key, item.value.joinToString())
                }
            }
        }
    }
}