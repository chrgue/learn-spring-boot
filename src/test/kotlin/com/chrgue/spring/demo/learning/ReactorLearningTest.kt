package com.chrgue.spring.demo.learning

import com.chrgue.spring.demo.models.Product
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration

class ReactorLearningTest {
    private val fullDelay = Duration.ofMinutes(1)
    private val halfDelay = fullDelay.dividedBy(2)

    private val elements5 = Flux.just(0, 1, 2, 3, 4)
    private val elements3 = Flux.just(0, 10, 20)

    private val singleProduct = Mono.just(Product("1", 10, "product 1"))

    private val products = Flux.range(0, 5)
        .map { Product(it.toString(), 10L * it, "product $it") }

    @Test
    fun mono() {
        StepVerifier.create(singleProduct)
            .expectNext(Product("1", 10, "product 1"))
            .expectComplete()
            .verify()
    }

    @Test
    fun monoMap() {

        StepVerifier.create(
            singleProduct
                .transform(this::productToPrice)
        )
            .expectNext(10)
            .expectComplete()
            .verify()
    }

    @Test
    fun fluxExpectCount() {
        StepVerifier.create(products)
            .expectNextCount(5)
            .expectComplete()
            .verify()
    }

    @Test
    fun fluxMap() {
        StepVerifier.create(
            products
                .transform(this::productToPrice)
        )
            .expectNext(0)
            .expectNext(10)
            .expectNext(20)
            .expectNext(30)
            .expectNext(40)
            .expectComplete()
            .verify()

    }

    @Test
    fun fluxZip() {
        StepVerifier
            .withVirtualTime {
                elements5
                    .transform(this::delay)
                    .zipWith(elements3)
                    .map { it.t1 * it.t2 }
            }
            .thenAwait(fullDelay)
            .expectNext(0)
            .thenAwait(fullDelay)
            .expectNext(10)
            .thenAwait(fullDelay)
            .expectNext(40)
            .expectComplete()
            .verify()
    }

    @Test
    fun fluxMerge() {
        StepVerifier
            .withVirtualTime {
                elements5
                    .transform(this::delay)
                    .mergeWith(elements3)

            }
            .thenAwait(halfDelay)
            .expectNext(0) // first consumer from elements3
            .expectNext(10)
            .expectNext(20)
            .thenAwait(halfDelay) // now we waited full delay
            .expectNext(0) // first item from element5
            .expectNoEvent(halfDelay) // no data is arriving
            .thenAwait(halfDelay) // until we wait full delay
            .expectNext(1) // each following item needs to wait full delay
            .thenAwait(fullDelay)
            .expectNext(2)
            .thenAwait(fullDelay)
            .expectNext(3)
            .thenAwait(fullDelay)
            .expectNext(4)
            .expectComplete()
            .verify()
    }


    private fun delay(it: Flux<Int>): Flux<Int> = it.delayElements(fullDelay)
    private fun productToPrice(it: Mono<Product>) = it.map(Product::price)
    private fun productToPrice(it: Flux<Product>) = it.map(Product::price)
}