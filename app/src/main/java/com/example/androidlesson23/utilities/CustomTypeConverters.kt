package com.example.androidlesson23.utilities

import androidx.room.TypeConverter
import com.example.androidlesson23.models.responses.get.product.Rating
import com.google.gson.Gson

class CustomTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromDimensions(dimensions: Rating?): String? {
        return gson.toJson(dimensions)
    }

    @TypeConverter
    fun toDimensions(dimensionsString: String?): Rating? {
        return gson.fromJson(dimensionsString, Rating::class.java)
    }
}