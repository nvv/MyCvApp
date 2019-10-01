package com.mycv.android.di

import android.content.Context
import android.net.ConnectivityManager
import com.mycv.android.core.NetworkManager
import com.mycv.android.core.Storage
import com.mycv.android.data.facade.ResumeFacade
import com.mycv.android.data.service.ResumeService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FacadaModule {

    @Provides
    @Singleton
    internal fun provideResumeFacade(context: Context): ResumeFacade {
        return ResumeFacade(
            ResumeService.create(context),
            NetworkManager(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager),
            Storage(context)
        )
    }

}