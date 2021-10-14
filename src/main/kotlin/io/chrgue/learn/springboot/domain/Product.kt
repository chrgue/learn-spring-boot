package io.chrgue.learn.springboot.domain

data class Product(
    val id: String,
    val price: Long,
    val name: String,
    val clickCount: Int
)
