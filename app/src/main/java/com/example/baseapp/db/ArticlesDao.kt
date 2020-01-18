package com.example.baseapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseapp.vo.Article

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM article WHERE source_id IN (:sources)")
    suspend fun getAll(sources: List<String>): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)
}
