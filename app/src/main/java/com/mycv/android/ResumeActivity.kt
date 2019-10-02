package com.mycv.android

import android.annotation.SuppressLint
import android.app.ActivityOptions
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycv.android.data.model.Resume
import com.mycv.android.data.model.WorkExperience
import com.mycv.android.fragments.DashboardFragment
import com.mycv.android.ui.adapter.ExperienceExpandListener
import com.mycv.android.ui.adapter.ResumeAdapter
import com.mycv.android.ui.adapter.ResumeEntryBuilder
import com.mycv.android.vm.ResumeViewModel
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class ResumeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: ResumeViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ResumeViewModel::class.java)

        setSupportActionBar(toolbar)

        viewModel.resume.observe(this, Observer<Resume> {resume ->
            setupContactButton(resume)
        })

        viewModel.isLoading.observe(this, Observer<Boolean> { loading ->
            if (loading) {
                inviteViaEmail.visibility = View.GONE
            }
        })

        supportFragmentManager
            .beginTransaction()
            .add(R.id.content, DashboardFragment.newInstance(), null)
            .disallowAddToBackStack()
            .commit()
    }

    @SuppressLint("RestrictedApi")
    private fun setupContactButton(resume: Resume?) {
        val to = resume?.contacts?.get("Email")

        inviteViaEmail.visibility = if (!to.isNullOrEmpty()) View.VISIBLE else View.GONE
        to?.let {
            inviteViaEmail.setOnClickListener {
                try {
                    startActivity(
                        Intent.createChooser(
                            viewModel.contactViaEmail(to, getString(R.string.email_invite)),
                            getString(R.string.send_using)
                        )
                    )
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
