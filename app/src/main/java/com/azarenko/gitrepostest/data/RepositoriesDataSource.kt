package com.azarenko.gitrepostest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azarenko.gitrepostest.network.ApiService
import com.azarenko.gitrepostest.network.IN_QUALIFIER
import com.azarenko.gitrepostest.utils.Constants.Companion.PAGE_SIZE
import com.azarenko.gitrepostest.utils.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class RepositoriesDataSource(
    private val searchValue: String,
    private val apiService: ApiService
) : PagingSource<Int, RepositoriesData>() {

    override fun getRefreshKey(state: PagingState<Int, RepositoriesData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoriesData> {
        val page = params.key ?: STARTING_PAGE_INDEX
        val apiSearchValue = searchValue + IN_QUALIFIER
        return try {
            val response = apiService.getRepositories(apiSearchValue, page, params.loadSize)
            val repos = response.items
            val nextKey =
                if (repos.isEmpty()) {
                    null
                } else {
                    page + (params.loadSize / PAGE_SIZE)
                }
            LoadResult.Page(
                data = repos,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}