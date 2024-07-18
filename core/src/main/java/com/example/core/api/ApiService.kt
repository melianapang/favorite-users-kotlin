package com.example.core.api

import com.example.core.api.model.SearchResultResponse
import com.example.core.api.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun fetchUsers(
        @Query("since") cursor: Int,
    ): Call<List<UserResponse>>

    @GET("users/{username}")
    fun fetchUserByUsername(
        @Path("username") username: String,
    ): Call<UserResponse>

    @GET("search/users")
    fun searchUsers(
        @Query("q") keyword: String?,
        @Query("page") cursor: Int,
        @Query("per_page") loadSize: Int,
    ): Call<SearchResultResponse>
}