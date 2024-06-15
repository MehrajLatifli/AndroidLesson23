package com.example.androidlesson23.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidlesson23.models.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun createDAO(): LocalDao
}