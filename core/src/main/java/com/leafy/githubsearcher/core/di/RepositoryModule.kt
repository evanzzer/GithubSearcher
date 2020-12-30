package com.leafy.githubsearcher.core.di

import com.leafy.githubsearcher.core.data.GithubRepository
import com.leafy.githubsearcher.core.domain.repository.GithubDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(githubRepository: GithubRepository): GithubDataSource
}