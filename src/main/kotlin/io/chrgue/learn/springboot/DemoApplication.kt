package io.chrgue.learn.springboot

import io.chrgue.learn.springboot.services.ProductSorter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.plugin.core.config.EnablePluginRegistries

@SpringBootApplication
@EnablePluginRegistries(ProductSorter::class)
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
