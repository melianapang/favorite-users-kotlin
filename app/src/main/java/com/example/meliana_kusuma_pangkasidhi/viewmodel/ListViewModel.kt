package com.example.meliana_kusuma_pangkasidhi.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.database.entities.UserEntity
import com.example.core.usecase.FetchFavoriteUsersUseCase
import com.example.core.usecase.FetchUsersUseCase
import com.example.meliana_kusuma_pangkasidhi.model.User
import com.example.meliana_kusuma_pangkasidhi.utilities.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val fetchFavoriteUsersUseCase: FetchFavoriteUsersUseCase
): ViewModel() {

    private val _usersState: MutableState<Flow<PagingData<UserEntity>>?> = mutableStateOf(null)
    val usersState: MutableState<Flow<
            PagingData<UserEntity>>?> get() = _usersState

    private val _favoriteUsersState: MutableState<List<User>?> = mutableStateOf(null)
    val favoriteUsersState get() = _favoriteUsersState.value

    private var _errorState: MutableState<Boolean> = mutableStateOf(false)
    val errorState get() = _errorState.value

    init {
        fetchUsers()
        getFavoriteUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            try {
                _usersState.value = fetchUsersUseCase.fetchUsers().cachedIn(viewModelScope)
            } catch (e: Exception) {
                _errorState.value = true
                Log.e("ListViewModel", e.localizedMessage ?: "Unknown Error")
            }
        }
    }

    fun getFavoriteUsers() {
        viewModelScope.launch {
            try {
                val data = fetchFavoriteUsersUseCase.getFavoriteUsers()
                _favoriteUsersState.value = data.toUser()
            } catch (e: Exception) {
                _errorState.value = true
                Log.e("ListViewModel", e.localizedMessage ?: "Unknown Error")
            }
        }
    }

    fun clearErrorState() {
        viewModelScope.launch {
            _errorState.value = false
        }
    }
}