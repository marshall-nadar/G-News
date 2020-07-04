package com.adam.gnews.data.appdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adam.gnews.data.appdb.dao.ArticleDao
import com.adam.gnews.data.appdb.entites.ArticleDBO
import com.adam.gnews.data.appdb.roomconverters.DateConverters

@Database(
    entities = [ArticleDBO::class],
    version = INITIAL_RELEASE,
    exportSchema = true
)
@TypeConverters(DateConverters::class)
abstract class AppDb : RoomDatabase() {
    companion object {
        const val TAG = "AppDb"
        private const val DATABASE_NAME = "AppDatabase"

        private var sInstance: AppDb? = null

        fun getDatabase(context: Context): AppDb {
            if (sInstance == null) {
                synchronized(this) {
                    if (sInstance == null)
                        sInstance = Room.databaseBuilder(
                            context,
                            AppDb::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return sInstance as AppDb
        }
    }

    abstract fun articleDao(): ArticleDao

}