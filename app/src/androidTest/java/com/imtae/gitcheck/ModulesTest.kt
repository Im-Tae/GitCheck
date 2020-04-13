package com.imtae.gitcheck

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.imtae.gitcheck.di.modules.activityModule
import com.imtae.gitcheck.di.modules.appModule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@RunWith(AndroidJUnit4::class)
class ModulesTest {
    @Test
    fun run() {
        startKoin {
            printLogger(Level.DEBUG)
            modules(
                appModule,
                activityModule
            )
        }
    }
}