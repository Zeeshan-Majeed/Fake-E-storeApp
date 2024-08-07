package com.example.e_storegenesis.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_storegenesis.db.dao.ProductDao
import com.example.e_storegenesis.db.models.Products

@Database(
    entities = [Products::class],
    version = 1,
    exportSchema = false
)
abstract class DataBaseClass : RoomDatabase() {
    abstract fun getDao(): ProductDao

}