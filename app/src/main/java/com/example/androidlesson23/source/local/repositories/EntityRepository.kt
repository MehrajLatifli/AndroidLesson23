package com.example.androidlesson23.source.local.repositories

import com.example.androidlesson23.models.entities.BasketEntity
import com.example.androidlesson23.models.entities.ProductEntity
import com.example.androidlesson23.source.local.LocalBasketDAO
import com.example.androidlesson23.source.local.LocalProductDAO
import javax.inject.Inject

class EntityRepository @Inject constructor(
    val localProductDAO: LocalProductDAO,
    val localBasketDAO: LocalBasketDAO
){
    fun addProductEntity(productEntity: ProductEntity) = localProductDAO.addProducts(productEntity)

    suspend fun getProductEntity() = localProductDAO.getProducts()


    fun addbasketEntity(basketEntity: BasketEntity) = localBasketDAO.addBaskets(basketEntity)

    suspend fun getBasketEntity() = localBasketDAO.getBaskets()
}