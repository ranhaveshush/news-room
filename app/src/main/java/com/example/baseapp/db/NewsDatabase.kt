package com.example.baseapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.baseapp.vo.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getArticlesDao(): ArticlesDao

    companion object {
        @Volatile
        private var instance: NewsDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: createInstance(context).also {
                instance = it
            }
        }

        private fun createInstance(context: Context) = Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news.db"
        ).build()
    }
}
