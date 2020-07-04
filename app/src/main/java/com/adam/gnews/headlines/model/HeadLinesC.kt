package com.adam.gnews.headlines.model

import com.adam.gnews.data.appdb.entites.ArticleDBO
import com.adam.gnews.data.webservice.remotemodels.ArticlePageWrapper
import com.adam.gnews.headlines.uimodels.ArticleUiPageWrapper
import com.adam.gnews.utils.processingstates.State
import io.reactivex.Single
import io.reactivex.subjects.Subject

interface HeadLinesC {

    interface Repository {
        companion object {
            const val KEY_ARTICLE_LOCAL_SIZE = "key-article-local-size"
        }

        val headLineStream: Subject<State<Boolean>>

        fun showError(ex: Throwable)

        fun getHeadLineList(key: Int, requestedLoadSize: Int): Single<ArticleUiPageWrapper>

        fun invalidateRepo()

    }

    interface Local {
        fun getHeadLineList(offset: Int, limit: Int): Single<List<ArticleDBO>>

        fun cacheArticles(articles: List<ArticleDBO>)
    }

    interface Remote {
        fun getHeadLineList(pageNo: Int, limit: Int): Single<ArticlePageWrapper>
    }
}