package com.adambulette.di

import com.adambulette.repository.ApiRepository
import org.koin.dsl.module

val RepoModule= module {
    single{ ApiRepository(get()) }
}