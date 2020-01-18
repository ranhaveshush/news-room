package com.example.baseapp.vo

import androidx.room.Embedded
import androidx.room.Entity

@Entity(
    primaryKeys = ["author", "title"]
)
data class Article(
    @Embedded(prefix = "source_")
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String?
)
