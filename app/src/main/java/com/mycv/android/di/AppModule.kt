package com.mycv.android.di

import android.content.Context
import com.mycv.android.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppContext(app: App): Context = app.applicationContext

}