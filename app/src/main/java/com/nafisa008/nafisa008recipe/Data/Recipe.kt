package com.nafisa008.nafisa008recipe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val ingredients: String,
    val steps: String,
    val imageUri: String? = null,
    val tags: List<String> = emptyList(),
    val isFavorite: Boolean = false,
    val createdTime: Long = System.currentTimeMillis()
)
