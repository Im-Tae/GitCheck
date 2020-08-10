package com.imtae.gitcheck.di.modules

import com.imtae.gitcheck.retrofit.repository.ContributionRepository
import com.imtae.gitcheck.retrofit.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory { UserRepository() }

    factory { ContributionRepository() }
}