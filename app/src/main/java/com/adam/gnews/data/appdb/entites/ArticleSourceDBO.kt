package com.adam.gnews.data.appdb.entites

import com.adam.gnews.data.webservice.remotemodels.ArticleSourceDTO

data class ArticleSourceDBO(
    val id: String,
    val name: String
) {
    constructor(remoteModel: ArticleSourceDTO) :
            this(
                id = remoteModel.id.orEmpty(),
                name = remoteModel.name
            )
}