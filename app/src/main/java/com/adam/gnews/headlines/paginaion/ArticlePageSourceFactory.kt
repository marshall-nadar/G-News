package com.adam.gnews.headlines.paginaion

import androidx.paging.DataSource
import com.adam.gnews.headlines.model.HeadLinesC
import com.adam.gnews.headlines.uimodels.ArticleUiModel

class ArticlePageSourceFactory(
    private val repo: HeadLinesC.Repository
) : DataSource.Factory<Int, ArticleUiModel>() {

    private lateinit var dataSource: ArticlePageKeyDataSource


    override fun create(): DataSource<Int, ArticleUiModel> {
        dataSource = ArticlePageKeyDataSource(repo = repo)
        dataSource.addInvalidatedCallback(repo::invalidateRepo)
        return dataSource
    }

    fun invalidateDataSource() = dataSource.invalidate()

}