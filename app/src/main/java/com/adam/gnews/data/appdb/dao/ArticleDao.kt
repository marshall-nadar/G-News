package com.adam.gnews.data.appdb.dao

import androidx.room.*
import com.adam.gnews.data.appdb.entites.ArticleDBO
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class ArticleDao {

    @Query(value = "SELECT * FROM Article ORDER BY publishedAt ASC LIMIT :requestedLoadSize OFFSET :offset")
    abstract fun getArticles(offset: Int, requestedLoadSize: Int): Single<List<ArticleDBO>>

    @Query(value = "SELECT COUNT(*) FROM Article ORDER BY publishedAt ASC LIMIT :requestedLoadSize OFFSET :offset")
    abstract fun articlesCount(offset: Int, requestedLoadSize: Int): Single<Int>

    @Query(value = "DELETE FROM Article")
    abstract fun nukeArticles(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllArticles(dataSet: List<ArticleDBO>)

    @Transaction
    open fun deleteAndInsertAll(dataSet: List<ArticleDBO>) {
        nukeArticles()
        insertAllArticles(dataSet = dataSet)
    }
}