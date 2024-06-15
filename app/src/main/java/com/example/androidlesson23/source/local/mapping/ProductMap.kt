package com.example.androidlesson23.source.local.mapping

import com.example.androidlesson23.models.entities.ProductEntity
import com.example.androidlesson23.models.responses.get.product.Product

fun ProductEntity.toProduct(): Product {
    return Product(
        id = this.id,
        category = this.category,
        description = this.description,
        price = this.price,
        rating = this.rating,
        image = this.image,
        title = this.title
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(

        id = this.id,
        category = this.category,
        description = this.description,
        price = this.price,
        rating = this.rating,
        image = this.image,
        title = this.title
    )
}