package io.chrgue.learn.springboot.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class InternalController {

    @GetMapping("/user-content")
    fun payload() = Mono.just("user content")

    @GetMapping("/admin")
    fun admin() = Flux.just("admin content")
}