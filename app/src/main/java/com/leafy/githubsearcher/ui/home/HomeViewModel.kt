package com.leafy.githubsearcher.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.core.domain.usecase.GithubUseCase

class HomeViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    var list: LiveData<Status<List<User>>> = MutableLiveData()

    fun getSearchList(query: String) {
        list = githubUseCase.getSearchList(query).asLiveData()
    }
}