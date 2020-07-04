package com.adam.gnews.headlines.di

import android.content.Context
import android.content.SharedPreferences
import com.adam.gnews.data.appdb.AppDb
import com.adam.gnews.data.appdb.dao.ArticleDao
import com.adam.gnews.data.webservice.ArticleWebService
import com.adam.gnews.headlines.model.HeadLinesC
import com.adam.gnews.headlines.model.HeadLinesLocalDataSource
import com.adam.gnews.headlines.model.HeadLinesRemoteDataSource
import com.adam.gnews.headlines.model.HeadLinesRepository
import com.adam.gnews.headlines.viewmodel.HeadLinesVMF
import com.adam.gnews.utils.Constants.READ_DATE_FORMAT
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

@Module
class HeadLinesModule {

    @Provides
    @HeadLinesScope
    fun providesVMF(
        repo: HeadLinesC.Repository
    ): HeadLinesVMF = HeadLinesVMF(repo = repo)

    @Provides
    @HeadLinesScope
    fun providesRepo(
        dateFormatter: SimpleDateFormat,
        remote: HeadLinesC.Remote,
        local: HeadLinesC.Local,
        sharedPreferences: SharedPreferences
    ): HeadLinesC.Repository = HeadLinesRepository(
        local = local,
        remote = remote,
        dateFormatter = dateFormatter,
        sharedPreferences = sharedPreferences
    )

    @Provides
    @HeadLinesScope
    fun providesWs(retrofit: Retrofit): ArticleWebService =
        retrofit.create(ArticleWebService::class.java)

    @Provides
    @HeadLinesScope
    fun providesRDS(
        articleWebService: ArticleWebService
    ): HeadLinesC.Remote = HeadLinesRemoteDataSource(webService = articleWebService)

    @Provides
    @HeadLinesScope
    fun providesLDS(
        dao: ArticleDao
    ): HeadLinesC.Local = HeadLinesLocalDataSource(dao = dao)

    @Provides
    @HeadLinesScope
    fun providesSDF(): SimpleDateFormat = SimpleDateFormat(READ_DATE_FORMAT, Locale.getDefault())

    @Provides
    @HeadLinesScope
    fun providesDAO(
        context: Context
    ): ArticleDao = AppDb.getDatabase(context = context).articleDao()
}



