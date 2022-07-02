package com.azarenko.gitrepostest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azarenko.gitrepostest.data.RepositoriesData

@Database(
    entities = [RepositoriesData::class],
    version = 1,
    exportSchema = false
)
abstract class ReposDataBase : RoomDatabase() {

    abstract fun reposDao(): ReposDao

}