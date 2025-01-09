package com.adambulette

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.adambulette.di.ApiModule
import com.adambulette.di.RepoModule
import com.adambulette.di.ViewModelModule
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class Adambulette : Application() {

    companion object {
        lateinit var instance: Adambulette
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Hawk.init(this).build()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Adambulette)
            modules(getModule())
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    private fun getModule(): List<Module> {
        return listOf(ApiModule, RepoModule, ViewModelModule)
    }

}
