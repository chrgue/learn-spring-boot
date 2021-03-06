package com.chrgue.spring.demo.controllers

import com.chrgue.spring.demo.ProductsRepository
import com.chrgue.spring.demo.models.Product
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
class ProductsController(private val repository: ProductsRepository) {

    @GetMapping("/products")
    fun products():Flux<Product>{
        return repository.getAll();
    }
}