package io.chrgue.learn.springboot.services

import io.chrgue.learn.springboot.ABTestVariant
import io.chrgue.learn.springboot.domain.Product
import org.springframework.plugin.core.Plugin
import reactor.core.publisher.Flux

interface ProductSorter: Plugin<ABTestVariant>{
    fun sort(items:Flux<Product>):Flux<Product>
}