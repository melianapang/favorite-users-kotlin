package com.example.meliana_kusuma_pangkasidhi.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.usecase.FetchFavoriteUsersUseCase
import com.example.core.usecase.FetchUsersUseCase
import com.example.meliana_kusuma_pangkasidhi.model.User
import com.example.meliana_kusuma_pangkasidhi.utilities.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val favoriteUsersUseCase: FetchFavoriteUsersUseCase
): ViewModel() {

    private var _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    private var _userState: MutableState<User?> = mutableStateOf(null)
    val userState get() = _userState.value

    private var _errorState: MutableState<Boolean> = mutableStateOf(false)
    val errorState get() = _errorState.value

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val data = fetchUsersUseCase.fetchAndSaveUserByUsername(username)

                if (data != null) {
                    val isFavorite = favoriteUsersUseCase.isFavoriteUser(data.id)
                    _userState.value = data.toUser(isFavorite)
                }
            } catch (e: Exception) {
                _errorState.value = true
                Log.e("DetailViewModel", e.localizedMessage ?: "Unknown Error")
            }
            finally {
                _isLoading.value = false
            }
        }
    }

    fun updateFavorite() {
        viewModelScope.launch {
            try {
                val isNowFavorite = _userState.value?.isFavorite ?: false
                _userState.value = _userState.value?.copy(isFavorite = !isNowFavorite)
                favoriteUsersUseCase.updateFavorite(
                    id = _userState.value?.id ?: 0,
                    isFavorite = !isNowFavorite
                )
            } catch (e: Exception) {
                _errorState.value = true
                Log.e("DetailViewModel", e.localizedMessage ?: "Unknown Error")
            }
        }
    }

    fun clearErrorState() {
        viewModelScope.launch {
            _errorState.value = false
        }
    }
}