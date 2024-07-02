package com.example.androidlesson23.source.local.mapping

import com.example.androidlesson23.models.entities.BasketEntity
import com.example.androidlesson23.models.entities.ProductEntity
import com.example.androidlesson23.models.responses.get.basket.Basket
import com.example.androidlesson23.models.responses.get.product.Product

fun BasketEntity.toBasket(): Basket {
    return Basket(
        id = this.id,
        category = this.category,
        price = this.price,
        image = this.image,
        title = this.title
    )
}

fun Basket.toBasketEntity(): BasketEntity {
    return BasketEntity(

        id = this.id,
        category = this.category,
        price = this.price,
        image = this.image,
        title = this.title
    )
}