package com.mycv.android.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import com.mycv.android.NavigatableActivity
import com.mycv.android.R
import com.mycv.android.ResumeActivity
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

class DashboardFragment : androidx.fragment.app.Fragment(), BaseFragment {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: ResumeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.content_main, container, false)

        viewModel.isLoading.observe(this, Observer<Boolean> { loading ->
            val isLoading = loading == true /* fight with nullable r*/

            progress.visibility = if (isLoading) View.VISIBLE else View.GONE
            resumeData.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        viewModel.resume.observe(this, Observer<Resume> { resume ->
            resume?.let {
                noData.visibility = View.GONE
                (resumeData.adapter as ResumeAdapter).setData(ResumeEntryBuilder.build(requireContext(), resume))

                (activity as ResumeActivity).setPhoto(resume.profile?.photo!!)

            } ?: run {
                noData.visibility = View.VISIBLE
            }
        })


        val viewManager = LinearLayoutManager(requireContext())
        val viewAdapter = ResumeAdapter(listener = object : ExperienceExpandListener {

            override fun onSelected(workExperienceEntry: WorkExperience, sharedViews: List<View>) {

                val fragment = WorkPlaceFragment.newInstance(workExperienceEntry)
                fragment.sharedElementEnterTransition = DetailsTransition()
                fragment.enterTransition = Fade()
                fragment.sharedElementReturnTransition = DetailsTransition()

                (activity as? NavigatableActivity)?.navigate(fragment, sharedViews)
            }
        })

        view.resumeData.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }


        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it, viewModelFactory).get(ResumeViewModel::class.java)
            viewModel.loadResume()
        }
    }

    override fun getTitle() = context?.getString(R.string.landing) ?: ""

    override fun isMenuSupported() = true

    override fun hasBackNavigation() = false

    companion object {
        fun newInstance() = DashboardFragment()
    }
}