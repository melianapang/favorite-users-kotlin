package com.example.core.repository.user

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.api.model.UserResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchUserPagingSource @Inject constructor(
    private val repository: UserRepository,
) : PagingSource<Int, UserResponse>() {

    data class SearchUserParam (
        val keyword: String = ""
    )

    lateinit var param: SearchUserParam

    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {
        val page = params.key ?: 1
        val response = repository.searchUsers(
            keyword = param.keyword,
            cursor = page,
            loadSize = params.loadSize
        ) ?: listOf()

        return try {
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(
                e
            )
        } catch (e: HttpException) {
            LoadResult.Error(
                e
            )
        }
    }
}
