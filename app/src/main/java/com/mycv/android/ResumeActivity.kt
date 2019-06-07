package com.mycv.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.DropBoxManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mycv.android.data.model.Resume
import com.mycv.android.ui.adapter.ResumeAdapter
import com.mycv.android.ui.adapter.entry.ContactsEntry
import com.mycv.android.ui.adapter.entry.ProfileEntry
import com.mycv.android.ui.adapter.entry.TitledEntry
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
        val viewAdapter = ResumeAdapter()

        resumeData.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        viewModel.resume.observe(this, Observer<Resume> {resume ->

            val array = mutableListOf<TitledEntry>()

            resume?.let {
                resume.profile?.let {
                    array.add(ProfileEntry(it))
                }
                resume.contacts?.let {
                    array.add(ContactsEntry(it))
                }
                resume.objectiveNotes?.let {
                    array.add(TitledEntry("Ojective Notes"))
                }
                resume.skillMap?.let {
                    array.add(TitledEntry("Skills"))
                }
                resume.experience?.let {
                    array.add(TitledEntry("Experience"))
                }
                resume.education?.let {
                    array.add(TitledEntry("Education"))
                }

            }


            viewAdapter.setDataSet(array)
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
