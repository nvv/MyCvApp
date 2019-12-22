package com.mycv.android.ui.adapter.vh

import android.view.View
import com.mycv.android.ui.adapter.viewitem.SkillsViewItem
import com.mycv.android.ui.adapter.viewitem.ViewItem

class SkillsViewHolder(view: View): TitledViewViewHolder(view) {

    override fun bind(entry: ViewItem) {

        details?.let { details ->
            details.removeAllViews()
            (entry as? SkillsViewItem)?.let {
                it.skills.entries.forEach { item ->
                    addKeyValueLine(details, item.key, item.value.joinToString())
                }
            }
        }
    }
}