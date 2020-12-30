package com.leafy.githubsearcher.core.di

import android.content.Context
import androidx.room.Room
import com.leafy.githubsearcher.core.data.source.local.room.GithubDao
import com.leafy.githubsearcher.core.data.source.local.room.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase = Room.databaseBuilder(
        context,
        GithubDatabase::class.java,
        "github.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGithubDao(database: GithubDatabase): GithubDao = database.githubDao()
}