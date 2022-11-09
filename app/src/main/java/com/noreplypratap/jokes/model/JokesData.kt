package com.noreplypratap.jokes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "JokesData")
data class JokesData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val punchline: String,
    val setup: String,
    val type: String
)