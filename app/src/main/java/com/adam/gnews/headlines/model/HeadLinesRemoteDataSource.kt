package com.adam.gnews.headlines.model

import com.adam.gnews.data.webservice.ArticleWebService
import com.adam.gnews.data.webservice.remotemodels.ArticlePageWrapper
import io.reactivex.Single

class HeadLinesRemoteDataSource(
    private val webService: ArticleWebService
) : HeadLinesC.Remote {

    override fun getHeadLineList(pageNo: Int, limit: Int): Single<ArticlePageWrapper> =
        webService.getHeadLines(page = pageNo, pageSize = limit)

    companion object {
        private const val TAG = "HeadLinesRDS"
    }
}