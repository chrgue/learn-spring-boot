package io.chrgue.learn.springboot.services

import io.chrgue.learn.springboot.ABTestVariant
import io.chrgue.learn.springboot.domain.Product
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class ProductSorterByPrice : ProductSorter {

    override fun sort(items: Flux<Product>) =
        items.sort(compareBy { it.price })

    override fun supports(delimiter: ABTestVariant) =
        delimiter == ABTestVariant.B
}