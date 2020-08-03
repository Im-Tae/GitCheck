package com.imtae.gitcheck

import com.imtae.gitcheck.di.modules.networkModule
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.network.UserApi
import org.junit.*
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.core.qualifier.named
import retrofit2.Response

class RetrofitTest : KoinComponent {

    private val module = listOf(networkModule)

    private val getUserInfo : UserApi by inject(named("UserApi"))

    @Before
    fun setUp() {
        startKoin {modules(module)}
    }

    @After
    fun clean() {
        stopKoin()
    }

    @Test
    fun getUserInfo() {
        val call = getUserInfo.getUserInfoTest("token b657e2c6c9eca394e23057d7f26c67fa500a132d")

        val res : Response<User> = call.execute()

        Assert.assertEquals(res.body()?.login, "Im-Tae")
    }
}