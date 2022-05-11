package com.azarenko.gitrepostest.network

import com.azarenko.gitrepostest.network.model.reponse.RepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val IN_QUALIFIER = "in:name,description"

interface ApiService {
    @GET("search/repositories?sort=stars")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepositoriesResponse
}