package io.chrgue.learn.springboot.services

import io.chrgue.learn.springboot.ABTestVariant
import io.chrgue.learn.springboot.domain.Product
import io.chrgue.learn.springboot.repositories.ProductsRepository
import org.springframework.plugin.core.PluginRegistry
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class ProductService(
    private val registry: PluginRegistry<ProductSorter, ABTestVariant>,
    private val repository: ProductsRepository
) {

    fun get(variant: ABTestVariant): Flux<Product> {
        val products = repository.getAll()
        return registry.getPluginFor(variant)
                .map { it.sort(products) }
                .orElse(products)
    }
}