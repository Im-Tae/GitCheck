package com.imtae.gitcheck

import com.imtae.gitcheck.di.modules.activityModule
import com.imtae.gitcheck.di.modules.appModule
import com.imtae.gitcheck.di.modules.fragmentModule
import com.imtae.gitcheck.di.modules.networkModule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ModulesTest {

    @Test
    fun run() {
        startKoin {
            printLogger(Level.DEBUG)
            modules(
                appModule,
                activityModule,
                fragmentModule,
                networkModule
            )
        }
    }
}