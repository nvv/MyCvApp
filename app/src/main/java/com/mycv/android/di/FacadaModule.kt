package com.mycv.android.di

import android.content.Context
import android.net.ConnectivityManager
import com.mycv.android.core.NetworkManager
import com.mycv.android.core.Storage
import com.mycv.android.data.facade.ResumeRepository
import com.mycv.android.data.mapper.ResumeToResumeViewItemMapper
import com.mycv.android.data.network.retrofitservice.ResumeService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FacadaModule {

    @Provides
    @Singleton
    internal fun provideResumeFacade(context: Context, service: ResumeService): ResumeRepository {
        return ResumeRepository(
            service,
            NetworkManager(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager),
            Storage(context)
        )
    }

    @Provides
    @Singleton
    internal fun provideResumeToResumeViewItemMapper(context: Context): ResumeToResumeViewItemMapper {
        return ResumeToResumeViewItemMapper(context)
    }

}