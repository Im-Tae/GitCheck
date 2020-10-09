package com.imtae.gitcheck.di

import com.imtae.gitcheck.data.repository.ContributionRepository
import com.imtae.gitcheck.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { UserRepository() }

    single { ContributionRepository() }
}