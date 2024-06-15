package com.example.androidlesson23.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidlesson23.models.responses.get.product.Rating
import com.example.androidlesson23.utilities.CustomTypeConverters
import com.google.gson.annotations.SerializedName


@Entity("Products")
@TypeConverters(CustomTypeConverters::class)
data class ProductEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("product_id")
    val id: Int?,
    val category: String?,
    val description: String?,
    val image: String?,
    val price: Double?,
    val rating: Rating?,
    val title: String?
)