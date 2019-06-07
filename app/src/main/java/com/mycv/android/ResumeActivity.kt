package com.mycv.android

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.mycv.android.data.model.Resume
import com.mycv.android.data.model.WorkExperience
import com.mycv.android.ui.adapter.ExperienceExpandListener
import com.mycv.android.ui.adapter.ResumeAdapter
import com.mycv.android.ui.adapter.ResumeEntryBuilder
import com.mycv.android.vm.ResumeViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class ResumeActivity : AppCompatActivity() {

    private val viewModel: ResumeViewModel
        get() = ViewModelProviders
            .of(this@ResumeActivity)
            .get(ResumeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = ResumeAdapter(listener = object : ExperienceExpandListener {
            override fun onSelected(workExperienceEntry: WorkExperience) {
                startActivity(
                    WorkItemActivity.createIntent(this@ResumeActivity, workExperienceEntry),
                    ActivityOptions.makeSceneTransitionAnimation(this@ResumeActivity).toBundle()
                )
            }
        })

        resumeData.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel.isLoading.observe(this, Observer<Boolean> { loading ->
            val isLoading = loading == true /* fight with nullable r*/

            progress.visibility = if (isLoading) View.VISIBLE else View.GONE
            resumeData.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        viewModel.resume.observe(this, Observer<Resume> { resume ->
            setupContactButton(resume)
            resume?.let {
                viewAdapter.setData(ResumeEntryBuilder.build(applicationContext, resume))

                title = resume.profile?.fullName
            }
        })

        viewModel.loadResume()
    }

    @SuppressLint("RestrictedApi")
    private fun setupContactButton(resume: Resume?) {
        val to = resume?.contacts?.get("Email")

        inviteViaEmail.visibility = if (!to.isNullOrEmpty()) View.VISIBLE else View.GONE
        to?.let {
            inviteViaEmail.setOnClickListener { view ->
                try {
                    startActivity(
                        Intent.createChooser(
                            viewModel.contactViaEmail(to, getString(R.string.email_invite)),
                            getString(R.string.send_using)
                        )
                    );
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(this@ResumeActivity, getString(R.string.no_client_installed), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reload_resume -> {
                viewModel.loadResume(force = true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
