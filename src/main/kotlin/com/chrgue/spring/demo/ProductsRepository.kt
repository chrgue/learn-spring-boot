package com.chrgue.spring.demo

import com.chrgue.spring.demo.models.Product
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ProductsRepository {
    private val products = listOf (
        Product("1", 10 , "product 1"),
        Product("2", 3 , "product 2"),
        Product("3", 7 , "product 3")
    )

    fun getAll() =
        Flux.fromIterable(products)

}