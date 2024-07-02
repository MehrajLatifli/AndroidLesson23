package com.example.androidlesson23.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidlesson23.models.responses.get.product.Rating

@Entity("Baskets")
data class BasketEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("basket_id")
    val id: Int?,
    val category: String?,
    val image: String?,
    val price: Double?,
    val title: String?
)