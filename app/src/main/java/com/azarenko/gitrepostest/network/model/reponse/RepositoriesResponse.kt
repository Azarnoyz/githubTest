package com.azarenko.gitrepostest.network.model.reponse

import com.azarenko.gitrepostest.data.RepositoriesData
import com.google.gson.annotations.SerializedName

data class RepositoriesResponse(
    @SerializedName("items") val items: List<RepositoriesData> = emptyList()
)
