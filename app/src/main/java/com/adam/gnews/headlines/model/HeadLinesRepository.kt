package com.adam.gnews.headlines.model

import com.adam.gnews.data.appdb.entites.ArticleDBO
import com.adam.gnews.data.webservice.remotemodels.ArticlePageWrapper
import com.adam.gnews.headlines.model.HeadLinesC.Repository.Companion.KEY_ARTICLE_LOCAL_SIZE
import com.adam.gnews.headlines.uimodels.ArticleUiModel
import com.adam.gnews.headlines.uimodels.ArticleUiPageWrapper
import com.adam.gnews.headlines.utils.ArticleDateFormatHelper
import com.adam.gnews.headlines.utils.HeadLinesPreferencesHelper
import com.adam.gnews.utils.extensions.doOp
import com.adam.gnews.utils.processingstates.State
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class HeadLinesRepository(
    private val sharedPreferencesHelper: HeadLinesPreferencesHelper,
    private val dateFormatter: ArticleDateFormatHelper,
    private val local: HeadLinesC.Local,
    private val remote: HeadLinesC.Remote
) : HeadLinesC.Repository {

    private val cache: MutableList<ArticleUiModel> = mutableListOf()

    private var dataLimit: Int = sharedPreferencesHelper.getInt(KEY_ARTICLE_LOCAL_SIZE, 0)

    override val headLineStream: Subject<State<Boolean>> by lazy { PublishSubject.create<State<Boolean>>() }

    override fun showError(ex: Throwable) = headLineStream.onNext(State.publishError(ex))

    override fun getHeadLineList(
        key: Int, requestedLoadSize: Int
    ): Single<ArticleUiPageWrapper> {

        val nextPage = key.plus(1)

        val isNextPageAvailable: Boolean = key.times(requestedLoadSize) <= dataLimit

        if (isNextPageAvailable.not()) {
            return Single.just(ArticleUiPageWrapper(page = emptyList(), hasNext = false))
        }

        return local.getHeadLineList(
            offset = cache.count(),
            limit = requestedLoadSize
        ).map { dbModels ->
            dbModels.map { dbModel ->
                ArticleUiModel(
                    dbModel = dbModel,
                    dateFormatter = dateFormatter
                )
            }
        }.onErrorResumeNext {
            fetchFromRemoteAndUpdateCountCache(
                pageNo = nextPage,
                limit = requestedLoadSize
            ).map { wrapper ->
                local.cacheArticles(
                    articles = wrapper.articleList.map(::ArticleDBO)
                )
                return@map wrapper.articleList.map { remoteModel ->
                    ArticleUiModel(
                        remoteModel = remoteModel,
                        dateFormatter = dateFormatter
                    )
                }
            }
        }.map { uiModels ->
            cache.addAll(uiModels)
            ArticleUiPageWrapper(
                page = uiModels,
                hasNext = isNextPageAvailable
            )
        }
    }

    override fun invalidateRepo() {
        sharedPreferencesHelper.remove(KEY_ARTICLE_LOCAL_SIZE)
        cache.clear()
        dataLimit = 0
    }

    private fun fetchFromRemoteAndUpdateCountCache(
        pageNo: Int,
        limit: Int
    ): Single<ArticlePageWrapper> = remote.getHeadLineList(
        pageNo = pageNo,
        limit = limit
    ).doOp { wrapper ->
        with(wrapper.totalResults) {
            dataLimit = this
            sharedPreferencesHelper.putInt(KEY_ARTICLE_LOCAL_SIZE, this@with)
        }
    }

    companion object {
        private const val TAG = "HeadLinesRepo"
    }
}