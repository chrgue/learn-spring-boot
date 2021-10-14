package io.chrgue.learn.springboot.showcase

import io.chrgue.learn.springboot.domain.Product
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsControllerIT(@Autowired private val webTestClient: WebTestClient) {

    @Test
    fun receiveProducts() {
        webTestClient.get()
                .uri("/products")
                .exchange()
                .expectStatus()
                .is2xxSuccessful
                .expectBodyList(Product::class.java)
                .hasSize(3)
    }
}