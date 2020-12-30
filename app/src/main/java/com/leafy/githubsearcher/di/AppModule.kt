package com.leafy.githubsearcher.di

import com.leafy.githubsearcher.core.domain.usecase.GithubInteractor
import com.leafy.githubsearcher.core.domain.usecase.GithubUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideGithubUseCase(githubInteractor: GithubInteractor): GithubUseCase
}