package com.adam.gnews.headlines.uimodels

data class ArticleUiPageWrapper(
    val page: List<ArticleUiModel>,
    val hasNext: Boolean
)