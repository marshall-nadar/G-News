package com.adam.gnews.headlines.paginaion

import androidx.paging.PageKeyedDataSource
import com.adam.gnews.headlines.model.HeadLinesC
import com.adam.gnews.headlines.uimodels.ArticleUiModel
import com.adam.gnews.utils.processingstates.State

class ArticlePageKeyDataSource(
    private val repo: HeadLinesC.Repository
) : PageKeyedDataSource<Int, ArticleUiModel>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ArticleUiModel>
    ) {
        repo.headLineStream.onNext(State.publishLoading())

        val currentPage = 0
        val nextPage = 1
        runCatching {
            repo.getHeadLineList(
                requestedLoadSize = params.requestedLoadSize,
                key = currentPage
            ).blockingGet()
        }.onSuccess {
            callback.onResult(
                it.page,
                null,
                if (it.hasNext)
                    nextPage
                else
                    null
            )
            repo.headLineStream.onNext(State.publishLoading(isLoading = false))
        }.onFailure {
            repo.headLineStream.onNext(State.publishError(it))
        }.getOrNull()

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ArticleUiModel>
    ) {
        repo.headLineStream.onNext(State.publishLoading())
        val currentPage = params.key
        val nextPage = params.key.plus(1)
        runCatching {
            repo.getHeadLineList(
                requestedLoadSize = params.requestedLoadSize,
                key = currentPage
            ).blockingGet()
        }.onSuccess {
            callback.onResult(
                it.page,
                if (it.hasNext)
                    nextPage
                else
                    null
            )
            repo.headLineStream.onNext(State.publishLoading(isLoading = false))
        }.onFailure {
            repo.headLineStream.onNext(State.publishError(it))
        }.getOrNull()
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ArticleUiModel>
    ) {
    }
}