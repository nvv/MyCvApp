package com.mycv.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mycv.android.R
import com.mycv.android.data.model.WorkExperience
import kotlinx.android.synthetic.main.activity_work_item.*
import kotlinx.android.synthetic.main.simple_line.view.*

class WorkPlaceFragment : androidx.fragment.app.Fragment(), BaseFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_work_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item : WorkExperience? = arguments?.getParcelable(ARG_WORK_ITEM)
        item?.let {
            //            title = it.companyName

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

    override fun getTitle() = arguments?.getParcelable<WorkExperience>(ARG_WORK_ITEM)?.companyName ?: ""

    override fun isMenuSupported() = false

    companion object {
        const val ARG_WORK_ITEM = "ARG_WORK_ITEM"

        fun newInstance(workExperience: WorkExperience) = WorkPlaceFragment().apply {
            val arguments = Bundle()
            arguments.putParcelable(ARG_WORK_ITEM, workExperience)
            setArguments(arguments)
        }
    }

}