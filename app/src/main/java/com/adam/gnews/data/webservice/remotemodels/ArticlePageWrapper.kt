package com.adam.gnews.data.webservice.remotemodels

import com.google.gson.annotations.SerializedName

data class ArticlePageWrapper(
    @SerializedName(value = "totalResults")
    val totalResults: Int,
    @SerializedName(value = "articles")
    val articleList: List<ArticleDTO>
)