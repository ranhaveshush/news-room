package com.example.baseapp.vo

import androidx.room.Entity

@Entity
data class Source(
    val id: String,
    val name: String
)
