package com.mycv.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mycv.android.data.model.WorkExperience
import kotlinx.android.synthetic.main.activity_work_item.*
import kotlinx.android.synthetic.main.simple_line.view.*

// Since it's very simple activity just to show already ready static data - we don't need ViewModel for it
class WorkItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_work_item)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val item : WorkExperience? = intent?.extras?.getParcelable(EXTRA_WORK_ITEM)
        item?.let {
            title = it.companyName

            company.text = it.companyName
            Glide.with(logo).load(item.logo).into(logo)

            role.text = it.position
            item.dates?.let {date ->
                val dateRange = "${date.from} / ${date.to}"
                dates.text = dateRange
            }

            location.text = it.location

            val projectList = it.projects?.joinToString()
            projectList?.let {
                projects.text = projectList
            } ?: run {
                projects.visibility = View.GONE
            }

            buildDottedList(it.achievements, achievements)
            buildDottedList(it.environment, tech_env_list)
        }
    }

    private fun buildDottedList(data: List<String>?, container: ViewGroup) {
        data?.map { item -> "\u2022 $item" }?.forEach { item ->
            val line = LayoutInflater.from(container.context).inflate(R.layout.simple_line, container, false)
            line.line.text = item

            container.addView(line)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val EXTRA_WORK_ITEM = "EXTRA_WORK_ITEM"

        fun createIntent(context: Context, workExperienceEntry: WorkExperience) =
            Intent(context, WorkItemActivity::class.java).putExtra(EXTRA_WORK_ITEM, workExperienceEntry)

    }
}
