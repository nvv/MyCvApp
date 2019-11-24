package com.mycv.android.di

import android.content.Context
import android.net.ConnectivityManager
import com.mycv.android.core.NetworkManager
import com.mycv.android.core.Storage
import com.mycv.android.data.facade.ResumeFacade
import com.mycv.android.data.network.retrofitservice.ResumeService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FacadaModule {

    @Provides
    @Singleton
    internal fun provideResumeFacade(context: Context, service: ResumeService): ResumeFacade {
        return ResumeFacade(
            service,
            NetworkManager(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager),
            Storage(context)
        )
    }

}