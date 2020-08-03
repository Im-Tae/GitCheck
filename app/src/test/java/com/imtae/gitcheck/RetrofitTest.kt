package com.imtae.gitcheck

import com.imtae.gitcheck.di.modules.networkModule
import com.imtae.gitcheck.retrofit.domain.User
import com.imtae.gitcheck.retrofit.network.UserApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
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

    @Test
    fun getUserInfo() {
        val call = getUserInfo.getUserInfoTest("token 6b43bb8dec8fc0a2b0f04893d58dbb836feebcc7")

        val res : Response<User> = call.execute()

        Assert.assertEquals(res.body()?.login, "Im-Tae")
    }
}