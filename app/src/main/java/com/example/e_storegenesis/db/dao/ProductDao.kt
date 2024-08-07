package com.example.e_storegenesis.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_storegenesis.db.models.Products

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: Products): Long

    @Query("SELECT * FROM Products ORDER BY id DESC")
    fun getAllProducts(): LiveData<MutableList<Products>>

    @Query("UPDATE Products SET addedToCart = :isAdded WHERE id = :productId")
    fun addToCart(productId: Int, isAdded: Boolean)

    @Query("SELECT * FROM Products WHERE id = :productId")
    fun getProductById(productId: Int): Products

    @Query("SELECT * FROM Products WHERE addedToCart = 1")
    fun getProductsInCart(): LiveData<MutableList<Products>>

    @Query("SELECT SUM(price) FROM Products WHERE addedToCart = 1")
    fun getSumOfPricesInCart(): LiveData<Double>

}