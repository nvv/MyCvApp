package com.mycv.android.di

import com.mycv.android.ResumeActivity
import com.mycv.android.fragments.DashboardFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeResumeActivity(): ResumeActivity

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

}