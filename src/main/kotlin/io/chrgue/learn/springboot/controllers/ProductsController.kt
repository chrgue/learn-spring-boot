package io.chrgue.learn.springboot.controllers

import io.chrgue.learn.springboot.repositories.ProductsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductsController(private val repository: ProductsRepository) {
    @GetMapping("/products")
    fun products() = repository.getAll()
}