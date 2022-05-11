package com.azarenko.gitrepostest.db

import com.azarenko.gitrepostest.data.RepositoriesData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReposDbRepository @Inject constructor(
    private val dataBase: ReposDataBase
) {

    fun getAll(): Flow<List<RepositoriesData>> = dataBase.reposDao().getRepositories()

    suspend fun insert(repository: RepositoriesData) = dataBase.reposDao().addRepository(repository)
}