package com.adam.gnews.headlines.model

import com.adam.gnews.data.webservice.remotemodels.ArticleDTO
import com.adam.gnews.data.webservice.remotemodels.ArticlePageWrapper
import com.adam.gnews.data.webservice.remotemodels.ArticleSourceDTO
import com.adam.gnews.headlines.utils.ArticleDateFormatHelper
import com.adam.gnews.headlines.utils.HeadLinesPreferencesHelper
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.internal.util.reflection.FieldReader
import java.lang.reflect.Field
import java.util.*

class HeadLinesRepositoryTest {

    private val sharedPreferencesHelper: HeadLinesPreferencesHelper by lazy {
        Mockito.mock(HeadLinesPreferencesHelper::class.java)
    }

    private val dateFormatter: ArticleDateFormatHelper by lazy {
        Mockito.mock(ArticleDateFormatHelper::class.java)
    }

    private val local: HeadLinesC.Local by lazy {
        Mockito.mock(HeadLinesC.Local::class.java)
    }

    private val remote: HeadLinesC.Remote by lazy {
        Mockito.mock(HeadLinesC.Remote::class.java)
    }

    private val cacheCountField: Field by lazy {
        HeadLinesRepository::class.java.getDeclaredField("dataLimit")
    }
    private val memoryCacheField: Field by lazy {
        HeadLinesRepository::class.java.getDeclaredField("cache")
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testEmptyCacheCount() {
        Mockito.`when`(local.getHeadLineList(0, 30))
            .thenReturn(Single.just(emptyList()))

        Mockito.`when`(remote.getHeadLineList(0, 30))
            .thenReturn(
                Single.just(
                    ArticlePageWrapper(
                        totalResults = 0,
                        articleList = emptyList()
                    )
                )
            )

        val classUnderTest = HeadLinesRepository(
            sharedPreferencesHelper = sharedPreferencesHelper,
            remote = remote,
            local = local,
            dateFormatter = dateFormatter
        )
        val response = classUnderTest.getHeadLineList(key = 0, requestedLoadSize = 30).blockingGet()
        val cacheCount = FieldReader(classUnderTest, cacheCountField).read() as Int
        val memoryCache = FieldReader(classUnderTest, memoryCacheField).read() as List<*>
        Assert.assertEquals(response.page.count(), cacheCount)
        Assert.assertEquals(response.page.count(), memoryCache.count())
    }

    @Test
    fun testLocalEmptyCacheCount() {

        Mockito.`when`(local.getHeadLineList(0, 30))
            .thenReturn(Single.just(emptyList()))
        Mockito.`when`(remote.getHeadLineList(0, 30))
            .thenReturn(
                Single.just(
                    ArticlePageWrapper(
                        totalResults = 34,
                        articleList = mutableListOf<ArticleDTO>().apply {
                            repeat(34) {
                                add(getMockedArticleDTO(it))
                            }
                        }
                    )
                )
            )

        val classUnderTest = HeadLinesRepository(
            sharedPreferencesHelper = sharedPreferencesHelper,
            remote = remote,
            local = local,
            dateFormatter = dateFormatter
        )
        val response = classUnderTest.getHeadLineList(key = 0, requestedLoadSize = 30).blockingGet()
        val cacheCount = FieldReader(classUnderTest, cacheCountField).read() as Int
        val memoryCache = FieldReader(classUnderTest, memoryCacheField).read() as List<*>
        Assert.assertEquals(response.page.count(), cacheCount)
        Assert.assertEquals(response.page.count(), memoryCache.count())
    }

    @Test
    fun invalidationTest() {
        val cacheCountField: Field = HeadLinesRepository::class.java.getDeclaredField("dataLimit")
        val memoryCacheField: Field = HeadLinesRepository::class.java.getDeclaredField("cache")

        val classUnderTest = HeadLinesRepository(
            sharedPreferencesHelper = sharedPreferencesHelper,
            remote = remote,
            local = local,
            dateFormatter = dateFormatter
        )
        classUnderTest.invalidateRepo()
        val cacheCount = FieldReader(classUnderTest, cacheCountField).read() as Int
        val memoryCache = FieldReader(classUnderTest, memoryCacheField).read() as List<*>
        Assert.assertEquals(cacheCount, 0)
        Assert.assertTrue(memoryCache.isEmpty())
    }

    @Test
    fun publishingBothSourceProduceError() {
        val throwable = RuntimeException()

        Mockito.`when`(local.getHeadLineList(0, 30))
            .thenReturn(Single.error(throwable))

        Mockito.`when`(remote.getHeadLineList(0, 30))
            .thenReturn(Single.error(throwable))

        val classUnderTest = HeadLinesRepository(
            sharedPreferencesHelper = sharedPreferencesHelper,
            remote = remote,
            local = local,
            dateFormatter = dateFormatter
        )

        classUnderTest.headLineStream
            .doOnSubscribe {
                classUnderTest.getHeadLineList(0, 30)
            }
            .test()
            .assertNoErrors()
    }

    private fun getMockedArticleDTO(position: Int): ArticleDTO = ArticleDTO(
        author = position.toString(),
        content = position.toString(),
        description = position.toString(),
        publishedAt = Date(),
        title = position.toString(),
        url = position.toString(),
        urlToImage = position.toString(),
        source = ArticleSourceDTO(
            id = position.toString(),
            name = position.toString()
        )
    )
}