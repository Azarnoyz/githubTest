package com.azarenko.gitrepostest.ui.fragments.repositoriesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.azarenko.gitrepostest.data.RepositoriesData
import com.azarenko.gitrepostest.db.ReposDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ReposListViewModel @Inject constructor(
    private val reposListRepository: ReposListRepository,
    private val dataBaseRepository: ReposDbRepository
) : ViewModel() {


    fun getRepositories(searchValue: String): Flow<PagingData<RepositoriesData>> {
        return reposListRepository.getRepositories(searchValue).cachedIn(viewModelScope)
    }

    suspend fun addRepository(repository: RepositoriesData) {
        dataBaseRepository.insert(repository)
    }
}