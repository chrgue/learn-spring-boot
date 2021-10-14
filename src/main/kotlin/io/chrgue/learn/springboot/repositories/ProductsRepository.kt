package io.chrgue.learn.springboot.repositories

import io.chrgue.learn.springboot.domain.Product
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Service
class ProductsRepository {
    private val products = listOf(
        Product("1", 10, "product 1", 9),
        Product("2", 3, "product 2", 100),
        Product("3", 7, "product 3", 0)
    )

    fun getAll() =
        products.toFlux()
}