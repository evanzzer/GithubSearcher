package com.leafy.githubsearcher.di

import com.leafy.githubsearcher.core.domain.usecase.GithubInteractor
import com.leafy.githubsearcher.core.domain.usecase.GithubUseCase
import com.leafy.githubsearcher.ui.detail.DetailViewModel
import com.leafy.githubsearcher.ui.detail.follower.FollowerViewModel
import com.leafy.githubsearcher.ui.detail.following.FollowingViewModel
import com.leafy.githubsearcher.ui.detail.repository.RepositoryViewModel
import com.leafy.githubsearcher.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val useCaseModule = module {
        factory<GithubUseCase> { GithubInteractor(get()) }
    }

    val viewModelModule = module {
        viewModel { HomeViewModel(get()) }
        viewModel { DetailViewModel(get()) }
        viewModel { RepositoryViewModel(get()) }
        viewModel { FollowerViewModel(get()) }
        viewModel { FollowingViewModel(get()) }
    }
}