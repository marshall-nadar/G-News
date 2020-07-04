package com.adam.gnews.headlines.paginaion

import androidx.paging.DataSource
import com.adam.gnews.headlines.model.HeadLinesC
import com.adam.gnews.headlines.uimodels.ArticleUiModel

class ArticlePageSourceFactory(
    private val repo: HeadLinesC.Repository
) : DataSource.Factory<Int, ArticleUiModel>() {

    override fun create(): DataSource<Int, ArticleUiModel> = ArticlePageKeyDataSource(repo = repo)

}