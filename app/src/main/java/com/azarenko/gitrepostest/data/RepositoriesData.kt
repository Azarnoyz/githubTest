package com.azarenko.gitrepostest.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repositories")
data class RepositoriesData(
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val name: String,
    @SerializedName("stargazers_url") val starsOwnersUrl: String,
    @SerializedName("html_url") val url: String,
    @SerializedName("language") val language: String?,
    @SerializedName("stargazers_count") val stars: Int
)