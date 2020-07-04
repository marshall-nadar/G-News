package com.adam.gnews.headlines.model

import androidx.room.EmptyResultSetException
import com.adam.gnews.data.appdb.dao.ArticleDao
import com.adam.gnews.data.appdb.entites.ArticleDBO
import com.adam.gnews.utils.extensions.doOp
import io.reactivex.Single

class HeadLinesLocalDataSource(
    private val dao: ArticleDao
) : HeadLinesC.Local {

    override fun getHeadLineList(offset: Int, limit: Int): Single<List<ArticleDBO>> =
        dao.getArticles(offset = offset, requestedLoadSize = limit)
            .doOp {
                if (it.isEmpty())
                    throw EmptyResultSetException("NoData available with offset:$offset and limit:$limit")
            }

    override fun cacheArticles(articles: List<ArticleDBO>) =
        dao.insertAllArticles(dataSet = articles)

    companion object {
        private const val TAG = "HeadLinesLDS"
    }
}