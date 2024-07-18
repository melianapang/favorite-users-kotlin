package com.example.core.datastore

import com.example.core.api.ApiService
import com.example.core.api.model.UserResponse
import com.google.gson.stream.MalformedJsonException
import java.io.IOException
import javax.inject.Inject

class UserRemoteDataStore @Inject constructor(
    private val apiService: ApiService,
){
    suspend fun fetchUsers(cursor: Int): List<UserResponse>? {
        return try {
            val request = apiService.fetchUsers(cursor)
            val response = request.execute()
            response.body()
        } catch (e: MalformedJsonException) {
            throw e
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun searchUsers(keyword: String, cursor: Int, loadSize: Int): List<UserResponse>? {
        return try {
            val request = apiService.searchUsers(keyword, cursor, loadSize)
            val response = request.execute()
            response.body()?.items
        } catch (e: MalformedJsonException) {
            throw e
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun fetchUserByUsername(username: String): UserResponse? {
        return try {
            val request = apiService.fetchUserByUsername(username)
            val response = request.execute()
            response.body()
        } catch (e: MalformedJsonException) {
            throw e
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}