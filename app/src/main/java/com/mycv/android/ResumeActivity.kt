package com.mycv.android

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.DropBoxManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mycv.android.data.model.Resume
import com.mycv.android.data.model.WorkExperience
import com.mycv.android.ui.adapter.ExperienceExpandListener
import com.mycv.android.ui.adapter.ResumeAdapter
import com.mycv.android.ui.adapter.ResumeEntryBuilder
import com.mycv.android.ui.adapter.entry.*
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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            viewModel.loadResume()
        }


        val viewManager = LinearLayoutManager(this)
        val viewAdapter = ResumeAdapter(listener = object : ExperienceExpandListener {
            override fun onSelected(workExperienceEntry: WorkExperience) {
                startActivity(
                    WorkItemActivity.createIntent(this@ResumeActivity, workExperienceEntry),
                    ActivityOptions.makeSceneTransitionAnimation(this@ResumeActivity).toBundle())
            }

        })

        resumeData.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel.resume.observe(this, Observer<Resume> {resume ->
            resume?.let {
                viewAdapter.setData(ResumeEntryBuilder.build(applicationContext, resume))
            }
        })

        viewModel.loadResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
