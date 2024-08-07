package com.example.e_storegenesis.db.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class Products(
    @PrimaryKey
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val rating: Double,
    val title: String,
    val sold:Int,
    var addedToCart: Boolean = false
)