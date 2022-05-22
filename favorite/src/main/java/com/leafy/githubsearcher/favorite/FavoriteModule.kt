package com.leafy.githubsearcher.favorite

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FavoriteModule {
    val favoriteModule = module {
        viewModel { FavoriteViewModel(get()) }
    }
}