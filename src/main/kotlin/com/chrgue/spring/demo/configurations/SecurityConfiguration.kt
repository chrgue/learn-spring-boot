package com.chrgue.spring.demo.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@Profile("security")
class SecurityConfiguration {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .authorizeExchange().pathMatchers("/admin/**").hasAnyRole("ADMIN")
            .and()
            .authorizeExchange().anyExchange().authenticated()

        return http.build()
    }


    @Bean
    fun userDetailsService(): UserDetailsService {

        val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

        val defaultUser = User.builder()
            .username("user")
            .password(encoder.encode("password"))
            .roles("USER")
            .build()

        val admin = User.builder()
            .username("admin")
            .password(encoder.encode("password"))
            .roles("USER", "ADMIN")
            .build()

        return InMemoryUserDetailsManager(defaultUser, admin)
    }
}