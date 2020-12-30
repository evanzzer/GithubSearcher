package com.leafy.githubsearcher.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.Detail
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.core.domain.usecase.GithubUseCase

class DetailViewModel @ViewModelInject constructor(private val githubUseCase: GithubUseCase) : ViewModel() {
    fun getDetails(user: String): LiveData<Status<Detail?>> = githubUseCase.getDetails(user).asLiveData()

    fun getFavorite(user: String): LiveData<User?> = githubUseCase.getFavoriteUser(user).asLiveData()

    fun setFavorite(user: User, state: Boolean) {
        githubUseCase.setFavorite(user, state)
    }
}