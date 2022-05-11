package com.azarenko.gitrepostest.db

import androidx.room.*
import com.azarenko.gitrepostest.data.RepositoriesData
import kotlinx.coroutines.flow.Flow

@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepository(repository: RepositoriesData)

    @Query("SELECT * FROM repositories LIMIT 20")
    fun getRepositories(): Flow<List<RepositoriesData>>

    @Update
    suspend fun updateNote(repository: RepositoriesData)

    @Delete
    suspend fun deleteNote(repository: RepositoriesData)
}