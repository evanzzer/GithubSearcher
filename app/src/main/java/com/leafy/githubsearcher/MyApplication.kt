package com.leafy.githubsearcher

import android.app.Application
import com.leafy.githubsearcher.core.di.CoreModule
import com.leafy.githubsearcher.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    CoreModule.databaseModule,
                    CoreModule.networkModule,
                    CoreModule.repositoryModule,
                    AppModule.useCaseModule,
                    AppModule.viewModelModule
                )
            )
        }
    }
}