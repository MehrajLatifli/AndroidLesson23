package com.example.androidlesson23.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidlesson23.models.entities.BasketEntity


@Database(entities = [BasketEntity::class], version = 1)
abstract class BasketDatabase : RoomDatabase() {

    abstract fun createBasketDAO(): LocalBasketDAO
}