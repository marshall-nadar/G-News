package com.adam.gnews.data.appdb.entites

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adam.gnews.data.webservice.remotemodels.ArticleDTO
import java.util.*

@Entity(tableName = "Article")
data class ArticleDBO(
    @PrimaryKey
    @Embedded
    val source: ArticleSourceDBO,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String
) {
    constructor(remoteModel: ArticleDTO) : this(
        source = ArticleSourceDBO(remoteModel = remoteModel.source),
        author = remoteModel.author.orEmpty(),
        content = remoteModel.content.orEmpty(),
        description = remoteModel.description.orEmpty(),
        publishedAt = remoteModel.publishedAt,
        title = remoteModel.title,
        url = remoteModel.url,
        urlToImage = remoteModel.urlToImage.orEmpty()
    )
}