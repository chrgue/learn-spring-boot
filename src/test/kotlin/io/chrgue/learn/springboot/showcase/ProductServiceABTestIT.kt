package io.chrgue.learn.springboot.showcase

import io.chrgue.learn.springboot.ABTestVariant.A
import io.chrgue.learn.springboot.ABTestVariant.B
import io.chrgue.learn.springboot.ABTestVariant.C
import io.chrgue.learn.springboot.domain.Product
import io.chrgue.learn.springboot.services.ProductService
import io.chrgue.learn.springboot.stepVerify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ProductServiceABTestIT(@Autowired private val productService: ProductService) {

    @TestFactory
    fun `sorts products for each AB-test`() = listOf(
        A to arrayOf("3", "1", "2"),
        B to arrayOf("2", "3", "1"),
        C to arrayOf("1", "2", "3")
    ).map { (variant, expectedIds) ->
        dynamicTest("variant=$variant -> ${expectedIds.toList()}") {
            productService.get(variant)
                    .stepVerify()
                    .recordWith { mutableListOf() }
                    .expectNextCount(3)
                    .consumeRecordedWith { records ->
                        assertThat(records)
                                .extracting(Product::id)
                                .containsExactly(*(expectedIds.map { tuple(it) }.toTypedArray()))
                    }.verifyComplete()
        }
    }
}