package com.mycv.android

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mycv.android.data.model.Resume
import com.mycv.android.fragments.BaseFragment
import com.mycv.android.fragments.DashboardFragment
import com.mycv.android.vm.ResumeViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ResumeActivity : DaggerAppCompatActivity(), NavigatableActivity {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: ResumeViewModel

    private var reloadMenuItem: MenuItem? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ResumeViewModel::class.java)

        viewModel.resume.observe(this, Observer<Resume> { resume ->
            resume.profile?.let {
                animatedHeader.setUp(
                    title = it.fullName,
                    subTitle = it.profession,
                    toolbarTitle = it.fullName
                )
                it.photo?.let { url ->
                    viewModel.load(animatedHeader.context, url)
                } ?: run {
                    // TODO
                }
            } ?: run {
                // TODO
            }
        })

        viewModel.isLoading.observe(this, Observer<Boolean> { loading ->
            if (loading) {
                inviteViaEmail.visibility = View.GONE
            }
        })

        viewModel.profileDrawable.observe(this, Observer<Drawable> {
            animatedHeader.setUp(imageDrawable = it)
        })

        supportFragmentManager.addOnBackStackChangedListener {
            val fragment =
                supportFragmentManager.fragments[this.supportFragmentManager.fragments.size - 1] as? BaseFragment

            fragment?.let {
                reloadMenuItem?.isVisible = it.isMenuSupported()
                animatedHeader.setBackButtonVisibility(it.hasBackNavigation())
            }
        }

        animatedHeader.backClickListener = View.OnClickListener {
            onBackPressed()
        }

        navigate(DashboardFragment.newInstance())
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments[this.supportFragmentManager.fragments.size - 1] as? BaseFragment

        if (fragment?.hasBackNavigation() == true) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupContactButton(resume: Resume?) {
        val to = resume?.contacts?.contacts?.get("Email")

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
                    Toast.makeText(
                        this@ResumeActivity,
                        getString(R.string.no_client_installed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun navigate(fragment: Fragment, sharedViews: List<View>?) {
        val transaction = supportFragmentManager.beginTransaction()

        if (sharedViews != null) {
            sharedViews.forEach {
                transaction.addSharedElement(it, it.getTag(R.id.transition_name)?.toString() ?: "")
            }

            transaction.replace(R.id.content, fragment, fragment.tag)
        } else {
            transaction.add(R.id.content, fragment, null)
        }

        transaction.addToBackStack(null).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        reloadMenuItem = menu.findItem(R.id.action_reload_resume)
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
