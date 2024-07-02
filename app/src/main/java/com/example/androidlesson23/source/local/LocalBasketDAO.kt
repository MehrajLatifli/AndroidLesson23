package com.example.androidlesson23.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidlesson23.models.entities.BasketEntity
import com.example.androidlesson23.models.entities.ProductEntity

@Dao
interface LocalBasketDAO {


    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addBaskets(basketEntity: BasketEntity)

    @Query("SELECT * FROM baskets")
    suspend fun getBaskets(): List<BasketEntity>

}
