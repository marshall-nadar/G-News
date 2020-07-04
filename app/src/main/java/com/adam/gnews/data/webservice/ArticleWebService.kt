package com.adam.gnews.data.webservice

import com.adam.gnews.data.webservice.remotemodels.ArticlePageWrapper
import com.adam.gnews.utils.Constants.QUERY_COUNTRY
import com.adam.gnews.utils.Constants.QUERY_COUNTRY_VALUE
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleWebService {

    @GET(value = "/v2/top-headlines")
    fun getHeadLines(
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") page: Int,
        @Query(value = QUERY_COUNTRY) country: String = QUERY_COUNTRY_VALUE
    ): Single<ArticlePageWrapper>

}