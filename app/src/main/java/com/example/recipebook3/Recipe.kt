package com.example.recipebook3

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val imageUrl: List<String>,
    val ingredients: List<String>,
    val cuisine: String
)




