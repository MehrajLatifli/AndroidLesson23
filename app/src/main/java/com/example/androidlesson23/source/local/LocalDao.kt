package com.example.androidlesson23.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidlesson23.models.entities.ProductEntity


@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addProducts(productEntity: ProductEntity)

    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<ProductEntity>

}