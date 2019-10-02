package com.mycv.android.fragments

import android.app.ActivityOptions
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycv.android.R
import com.mycv.android.WorkItemActivity
import com.mycv.android.data.model.Resume
import com.mycv.android.data.model.WorkExperience
import com.mycv.android.ui.adapter.ExperienceExpandListener
import com.mycv.android.ui.adapter.ResumeAdapter
import com.mycv.android.ui.adapter.ResumeEntryBuilder
import com.mycv.android.vm.ResumeViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import javax.inject.Inject

class DashboardFragment : androidx.fragment.app.Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: ResumeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.content_main, container, false)


        val viewManager = LinearLayoutManager(requireContext())
        val viewAdapter = ResumeAdapter(listener = object : ExperienceExpandListener {
            override fun onSelected(workExperienceEntry: WorkExperience) {
                startActivity(
                    WorkItemActivity.createIntent(requireActivity(), workExperienceEntry),
                    ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
                )
            }
        })

        view.resumeData.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }


        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidSupportInjection.inject(this)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(ResumeViewModel::class.java)

        viewModel.isLoading.observe(this, Observer<Boolean> { loading ->
            val isLoading = loading == true /* fight with nullable r*/

            progress.visibility = if (isLoading) View.VISIBLE else View.GONE
            resumeData.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        viewModel.resume.observe(this, Observer<Resume> { resume ->
            resume?.let {
                noData.visibility = View.GONE
                (resumeData.adapter as ResumeAdapter).setData(ResumeEntryBuilder.build(requireContext(), resume))

            } ?: run {
                noData.visibility = View.VISIBLE
            }
        })

        viewModel.loadResume()
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}