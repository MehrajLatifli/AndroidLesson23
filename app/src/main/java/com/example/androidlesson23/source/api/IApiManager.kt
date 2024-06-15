package com.example.androidlesson23.source.api

import com.example.androidlesson23.models.responses.get.category.CategoryResponse
import com.example.androidlesson23.models.responses.get.product.Product
import com.example.androidlesson23.models.responses.get.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IApiManager {

    @GET("products")
    suspend fun getAllProduct(): Response<ProductResponse>


    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<Product>


    @GET("products/categories")
    suspend fun getAllCategory(): Response<CategoryResponse>


    @GET("products/category/{categoryname}")
    suspend fun getProductsbyCategory(
        @Path("categoryname") categoryname: String
    ): Response<ProductResponse>


}