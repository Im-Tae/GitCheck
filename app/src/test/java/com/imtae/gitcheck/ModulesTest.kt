package com.imtae.gitcheck

import com.imtae.gitcheck.di.*
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class ModulesTest {

    @Test
    fun run() {
        startKoin {
            printLogger(Level.ERROR)
            modules(
                appModule,
                activityModule,
                fragmentModule,
                networkModule,
                repositoryModule
            )
        }
    }

    @After
    fun clean() {
        stopKoin()
    }
}