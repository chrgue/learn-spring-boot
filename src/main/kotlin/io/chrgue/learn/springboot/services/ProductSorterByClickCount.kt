package io.chrgue.learn.springboot.services

import io.chrgue.learn.springboot.ABTestVariant
import io.chrgue.learn.springboot.domain.Product
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class ProductSorterByClickCount : ProductSorter {

    override fun sort(items: Flux<Product>) =
        items.sort(compareBy { it.clickCount })

    override fun supports(delimiter: ABTestVariant) =
        delimiter == ABTestVariant.A
}