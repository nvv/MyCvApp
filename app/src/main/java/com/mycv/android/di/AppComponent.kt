package com.mycv.android.di

import com.mycv.android.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(
    modules = [
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        FacadaModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {


    fun inject(application: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

}