package com.adambulette.di

import com.adambulette.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        ForgotViewModel(get())
    }
}