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
import org.springframework.web.filter.RequestContextFilter


@Configuration
class AppConfig (
    private val userDetailsService: CustomUserDetailsService
){
    @Bean
    fun getRestTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity, requestContextFilter: RequestContextFilter): SecurityFilterChain {

        http
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, "/password").authenticated()
                    .requestMatchers(HttpMethod.GET,"/token").authenticated()
                    .requestMatchers(
                        "/swagger/**", "/swagger",
                        "/swagger-ui/**", "/swagger-ui",
                        "/ollama-api/**", "/ollama-api",
                        "/", "/about"
                    ).authenticated()
                    .requestMatchers(HttpMethod.POST ,"/token/delete", "/token").hasRole("ADMIN")
                    .requestMatchers("/users", "/users/delete").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/favicon.ico","/images/**", "/css/**", "/js/**").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .anyRequest().denyAll()
            }
            .formLogin{
                it
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login?error")
            }
            .logout{
                it
                    .permitAll()
                    .logoutSuccessUrl("/login?logout")
            }
            .csrf{
                it
                    .ignoringRequestMatchers("/api/**")
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