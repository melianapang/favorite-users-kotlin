package com.example.meliana_kusuma_pangkasidhi.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.api.model.UserResponse
import com.example.core.usecase.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
) : ViewModel() {

    data class HistorySearchState(
        val historyData: List<String> = listOf(),
        val currentKeyword: String = ""
    )

    private var _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    private val _usersState: MutableState<Flow<PagingData<UserResponse>>?> = mutableStateOf(null)
    val usersState: MutableState<Flow<PagingData<UserResponse>>?> get() = _usersState

    private val _searchHistoryState: MutableState<HistorySearchState?> = mutableStateOf(HistorySearchState())
    val searchHistoryState get() = _searchHistoryState.value

    private var _errorState: MutableState<Boolean> = mutableStateOf(false)
    val errorState get() = _errorState.value

    init {
        getHistorySearch()
    }

    fun getHistorySearch() {
        viewModelScope.launch {
            try {
                val data = searchUsersUseCase.getHistorySearchKeywords()
                _searchHistoryState.value = _searchHistoryState.value?.copy(
                    historyData = data.map { it.keyword },
                )
            } catch (e: Exception) {
                _errorState.value = true
                Log.e("SearchViewModel", e.localizedMessage ?: "Unknown Error")
            }
        }
    }

    fun searchUsers(keyword: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _usersState.value = searchUsersUseCase.searchUsers(keyword).cachedIn(viewModelScope).cachedIn(viewModelScope)

                val savedData = searchUsersUseCase.saveAndGetHistorySearchKeyword(keyword)?.map { it.keyword }
                val allData = if (savedData.isNullOrEmpty()) {
                    _searchHistoryState.value?.historyData ?: listOf()
                } else savedData

                _searchHistoryState.value = _searchHistoryState.value?.copy(
                    historyData = allData,
                    currentKeyword = keyword
                )
            } catch (e: Exception) {
                _errorState.value = true
                Log.e("SearchViewModel", e.localizedMessage ?: "Unknown Error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorState() {
        viewModelScope.launch {
            _errorState.value = false
        }
    }
}