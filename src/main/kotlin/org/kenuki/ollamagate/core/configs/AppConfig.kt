package org.kenuki.ollamagate.core.configs

import org.kenuki.ollamagate.core.services.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.client.RestTemplate


@Configuration
class AppConfig (
    private val userDetailsService: CustomUserDetailsService
){
    @Bean
    fun getRestTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it
                    .requestMatchers("/").authenticated()
                    .requestMatchers("/foradmin").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/favicon.ico","/images/**", "/css/**", "/js/**").permitAll()
            }
            .formLogin{
                it
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login?error")
            }
        return http.build()
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(5)
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

}