package com.leafy.githubsearcher.ui.detail.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.Repository
import com.leafy.githubsearcher.core.domain.usecase.GithubUseCase

class RepositoryViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    fun getRepository(user: String): LiveData<Status<List<Repository>>> =
            githubUseCase.getRepositoryList(user).asLiveData()
}