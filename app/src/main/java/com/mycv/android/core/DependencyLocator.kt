package com.mycv.android.core

import android.content.Context
import android.net.ConnectivityManager
import com.mycv.android.data.facade.ResumeFacade
import com.mycv.android.data.service.ResumeService
import java.util.concurrent.atomic.AtomicBoolean

class DependencyLocator private constructor() {

    lateinit var resumeFacade: ResumeFacade
        private set

    fun initLocator(context: Context) {
        resumeFacade = ResumeFacade(ResumeService.create(context),
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
            Storage(context))
    }

    companion object {

        @Volatile private lateinit var INSTANCE: DependencyLocator
        private val initialized = AtomicBoolean()

        fun initInstance(context: Context) {
            if (!initialized.getAndSet(true)) {
                INSTANCE = buildDependencyLocator()
                INSTANCE.initLocator(context)
            }
        }

        fun getInstance() = INSTANCE

        private fun buildDependencyLocator() = DependencyLocator()

    }
}