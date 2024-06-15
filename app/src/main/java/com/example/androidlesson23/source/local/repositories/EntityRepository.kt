package com.example.androidlesson23.source.local.repositories

import com.example.androidlesson23.models.entities.ProductEntity
import com.example.androidlesson23.source.local.LocalDao
import javax.inject.Inject

class EntityRepository @Inject constructor(
    val localDao: LocalDao
){
    fun addProductEntity(productEntity: ProductEntity) = localDao.addProducts(productEntity)

    suspend fun getProductEntity() = localDao.getProducts()
}