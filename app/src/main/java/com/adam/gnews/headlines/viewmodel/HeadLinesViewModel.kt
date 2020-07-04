package com.adam.gnews.headlines.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.adam.gnews.headlines.di.HeadLinesDependencyHolder
import com.adam.gnews.headlines.model.HeadLinesC
import com.adam.gnews.headlines.paginaion.ArticlePageSourceFactory
import com.adam.gnews.headlines.uimodels.ArticleUiModel
import com.adam.gnews.utils.Constants.HEAD_LINES_PAGE_SIZE
import io.reactivex.BackpressureStrategy

class HeadLinesViewModel(
    private val repo: HeadLinesC.Repository
) : ViewModel() {

    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(HEAD_LINES_PAGE_SIZE)
        .setPageSize(HEAD_LINES_PAGE_SIZE)
        .setPrefetchDistance(HEAD_LINES_PAGE_SIZE.div(2))
        .build()

    private val factory: ArticlePageSourceFactory by lazy { ArticlePageSourceFactory(repo) }

    val livePagedList: LiveData<PagedList<ArticleUiModel>> by lazy {
        LivePagedListBuilder(factory, config)
            .build()
    }

    val loadingStatusSubject by lazy {
        LiveDataReactiveStreams.fromPublisher(
            repo.headLineStream.toFlowable(
                BackpressureStrategy.LATEST
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        HeadLinesDependencyHolder.destroyHeadLinesComponent()
    }

    companion object {
        private const val TAG = "HeadLinesViewModel"
    }
}