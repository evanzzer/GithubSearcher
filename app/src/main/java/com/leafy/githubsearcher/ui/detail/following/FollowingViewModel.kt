package com.leafy.githubsearcher.ui.detail.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.core.domain.usecase.GithubUseCase

class FollowingViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    fun getFollowingList(user: String): LiveData<Status<List<User>>> =
            githubUseCase.getFollowingList(user).asLiveData()
}