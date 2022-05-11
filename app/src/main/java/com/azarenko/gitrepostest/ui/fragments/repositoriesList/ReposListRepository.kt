package com.azarenko.gitrepostest.ui.fragments.repositoriesList

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.azarenko.gitrepostest.data.RepositoriesData
import com.azarenko.gitrepostest.data.RepositoriesDataSource
import com.azarenko.gitrepostest.network.ApiService
import com.azarenko.gitrepostest.utils.Constants.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReposListRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getRepositories(searchValue: String): Flow<PagingData<RepositoriesData>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                RepositoriesDataSource(searchValue, apiService)
            }
        ).flow
    }
}