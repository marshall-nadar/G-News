package com.adam.gnews.data.webservice.remotemodels

import com.google.gson.annotations.SerializedName

data class ArticleSourceDTO(
    @SerializedName(value = "id")
    val id: String?,
    @SerializedName(value = "name")
    val name: String
)