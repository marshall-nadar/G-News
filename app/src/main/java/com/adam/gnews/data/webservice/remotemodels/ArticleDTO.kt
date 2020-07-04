package com.adam.gnews.data.webservice.remotemodels

import com.google.gson.annotations.SerializedName
import java.util.*

data class ArticleDTO(
    @SerializedName(value = "source")
    val source: ArticleSourceDTO,
    @SerializedName(value = "author")
    val author: String?,
    @SerializedName(value = "title")
    val title: String,
    @SerializedName(value = "description")
    val description: String?,
    @SerializedName(value = "url")
    val url: String,
    @SerializedName(value = "urlToImage")
    val urlToImage: String?,
    @SerializedName(value = "publishedAt")
    val publishedAt: Date,
    @SerializedName(value = "content")
    val content: String?
)