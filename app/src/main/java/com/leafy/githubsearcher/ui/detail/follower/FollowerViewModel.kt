package com.leafy.githubsearcher.ui.detail.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.core.domain.usecase.GithubUseCase

class FollowerViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    fun getFollower(user: String): LiveData<Status<List<User>>> =
            githubUseCase.getFollowerList(user).asLiveData()
}