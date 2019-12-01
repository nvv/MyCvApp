package com.mycv.android.di

import com.google.gson.GsonBuilder
import com.mycv.android.BuildConfig
import com.mycv.android.data.model.Contacts
import com.mycv.android.data.model.ObjectiveNotes
import com.mycv.android.data.model.Skills
import com.mycv.android.data.network.ApiSettings
import com.mycv.android.data.network.retrofitservice.ResumeService
import com.mycv.android.utils.ContactsTypeAdapter
import com.mycv.android.utils.FullSkillsTypeAdapter
import com.mycv.android.utils.ObjectiveNotesTypeAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    @Named("OkHttpClient")
    fun provideClient(): OkHttpClient {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(ApiSettings.DEFAULT_TIMEOUT_MIN, TimeUnit.MINUTES)
            .readTimeout(ApiSettings.DEFAULT_TIMEOUT_MIN, TimeUnit.MINUTES)
            .writeTimeout(ApiSettings.DEFAULT_TIMEOUT_MIN, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().setLevel(level))
            .build()
    }


    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideRetrofit(@Named("GsonConverter") gsonConverter: GsonConverterFactory,
                        @Named("OkHttpClient") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiSettings.SERVER)
            .addConverterFactory(gsonConverter)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("GsonConverter")
    fun createGsonConverter(): GsonConverterFactory {
        val builder = GsonBuilder()
        builder.setLenient()
        builder.registerTypeAdapter(Contacts::class.java, ContactsTypeAdapter())
        builder.registerTypeAdapter(ObjectiveNotes::class.java, ObjectiveNotesTypeAdapter())
        builder.registerTypeAdapter(Skills::class.java, FullSkillsTypeAdapter())
        return GsonConverterFactory.create(builder.create())
    }

    @Provides
    @Singleton
    fun provideResumeService(@Named("Retrofit") retrofit: Retrofit) =
        retrofit.create(ResumeService::class.java)

}