package com.example.androidlesson23.models.responses.get.basket

import android.os.Parcelable
import com.example.androidlesson23.models.responses.get.product.Rating
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize



@Parcelize
data class Basket(
    @SerializedName("category")
    val category: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("title")
    val title: String?
): Parcelable