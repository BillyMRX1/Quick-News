package com.mrx.quicknews.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrx.quicknews.core.domain.model.Article
import com.mrx.quicknews.core.domain.model.BaseResponse
import com.mrx.quicknews.core.domain.usecase.GetTopHeadlinesUseCase
import com.mrx.quicknews.core.util.getLocationCountryCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class HomeState {
    data object Loading : HomeState()
    data class TopHeadline(val articles: List<Article>) : HomeState()
    data class Error(val errorMessage: String) : HomeState()
    data object PermissionDenied : HomeState()
}


class HomeViewModel : ViewModel(),
    KoinComponent {

    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase by inject()
    private val context: Context by inject()

    private val _state = MutableStateFlow<HomeState>(HomeState.TopHeadline(emptyList()))
    val state: StateFlow<HomeState> = _state

    private var currentPage = 1
    private val newsList = mutableListOf<Article>()

    fun getLocalTopHeadline() {
        if (_state.value == HomeState.Loading) return

        _state.value = HomeState.Loading
        viewModelScope.launch {
            when (val result =
                getTopHeadlinesUseCase.invoke(getLocationCountryCode(context), currentPage)) {
                is BaseResponse.Success -> {
                    val newArticles = result.data.articles
                    newsList.addAll(newArticles)
                    _state.value = HomeState.TopHeadline(newsList)
                    currentPage++
                }

                is BaseResponse.Error -> {
                    _state.value = HomeState.Error(result.errorResponse.message)
                }
            }
        }
    }

    fun permissionDenied() {
        _state.value = HomeState.PermissionDenied
    }
}
