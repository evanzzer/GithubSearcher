package com.leafy.githubsearcher.core.di

import androidx.room.Room
import com.leafy.githubsearcher.core.data.GithubRepository
import com.leafy.githubsearcher.core.data.source.local.LocalDataSource
import com.leafy.githubsearcher.core.data.source.local.room.GithubDatabase
import com.leafy.githubsearcher.core.data.source.remote.RemoteDataSource
import com.leafy.githubsearcher.core.data.source.remote.network.ApiService
import com.leafy.githubsearcher.core.domain.repository.GithubDataSource
import com.leafy.githubsearcher.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
            val passphrase = SQLiteDatabase.getBytes("githubsearcher".toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                androidContext(),
                GithubDatabase::class.java,
                "github.db"
            ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
        }
    }

    @Suppress("SpellCheckingInspection")
    val networkModule = module {
        single {
            val hostname = "api.github.com"
            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/uyPYgclc5Jt69vKu92vci6etcBDY8UNTyrHQZJpVoZY=")
                .add(hostname, "sha256/e0IRz5Tio3GA1Xs4fUVWmH1xHDiH2dMbVtCBSkOIdqM=")
                .add(hostname, "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
                .build()
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
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