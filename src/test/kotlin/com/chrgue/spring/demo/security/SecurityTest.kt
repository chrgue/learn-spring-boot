package com.chrgue.spring.demo.security

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@ActiveProfiles("security")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun unknownUserIsUnauthorized() {
        webTestClient.get()
            .uri("/user-content")
            .exchange()
            .expectStatus()
            .isUnauthorized

    }

    @Test
    @WithAnonymousUser
    fun anonymousUserIsForbidden() {
        webTestClient.get()
            .uri("/user-content")
            .exchange()
            .expectStatus()
            .isForbidden

    }

    @Test
    @WithMockUser
    fun userIsAuthorized() {
        webTestClient.get()
            .uri("/user-content")
            .exchange()
            .expectStatus()
            .is2xxSuccessful
    }

    @Test
    @WithMockUser
    fun userHasNoAccessToAdminEndpoint() {
        webTestClient.get()
            .uri("/admin")
            .exchange()
            .expectStatus()
            .isForbidden
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun adminUserHasAccessToAdminEndpoint() {
        webTestClient.get()
            .uri("/admin")
            .exchange()
            .expectStatus()
            .is2xxSuccessful
    }
}