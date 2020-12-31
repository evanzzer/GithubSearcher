package com.leafy.githubsearcher.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.leafy.githubsearcher.core.domain.usecase.GithubUseCase

class FavoriteViewModel(githubUseCase: GithubUseCase) : ViewModel() {
    val list = githubUseCase.getFavoriteList().asLiveData()
}