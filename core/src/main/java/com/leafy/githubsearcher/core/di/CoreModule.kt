package com.leafy.githubsearcher.core.di

import androidx.room.Room
import com.leafy.githubsearcher.core.data.GithubRepository
import com.leafy.githubsearcher.core.data.source.local.LocalDataSource
import com.leafy.githubsearcher.core.data.source.local.room.GithubDatabase
import com.leafy.githubsearcher.core.data.source.remote.RemoteDataSource
import com.leafy.githubsearcher.core.data.source.remote.network.ApiService
import com.leafy.githubsearcher.core.domain.repository.GithubDataSource
import com.leafy.githubsearcher.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreModule {
    val databaseModule = module {
        factory { get<GithubDatabase>().githubDao() }
        single {
            Room.databaseBuilder(
                androidContext(),
                GithubDatabase::class.java,
                "github.db"
            ).fallbackToDestructiveMigration().build()
        }
    }

    val networkModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
        }
        single {
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
                .create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<GithubDataSource> { GithubRepository(get(), get(), get()) }
    }
}