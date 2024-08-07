package com.example.e_storegenesis.di

import android.app.Application
import androidx.room.Room
import com.example.e_storegenesis.db.DataBaseClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesAppDataBase(context: Application): DataBaseClass {
        return Room.databaseBuilder(
            context,
            DataBaseClass::class.java,
            "products_database"
        ).createFromAsset("databases/products_data.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}