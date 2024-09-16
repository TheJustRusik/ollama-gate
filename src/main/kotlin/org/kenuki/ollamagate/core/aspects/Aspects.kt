package org.kenuki.ollamagate.core.aspects

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.kenuki.ollamagate.core.exceptions.AuthorizationException
import org.kenuki.ollamagate.core.repositories.TokenRepository
import org.kenuki.ollamagate.core.services.TokenService
import org.kenuki.ollamagate.web.dtos.requests.GenerateDTO
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import kotlin.system.measureTimeMillis

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AddHeadHelloToModel

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TokenSecured

@Aspect
@Component
class Aspects (
    val tokenRepository: TokenRepository,
    val tokenService: TokenService
){
    @Before("@annotation(AddHeadHelloToModel) && args(model, ..)")
    fun addUserDetails(model: Model) {
        val authentication = SecurityContextHolder.getContext().authentication
        val role = authentication.authorities.first().authority
        val username = authentication.name
        val isAdmin = role == "ROLE_ADMIN"

        model.addAttribute(
            "head_hello",
            "Hi $username! ${if (isAdmin) "Admin panel" else "Token panel"}"
        )
        model.addAttribute("admin", isAdmin)
    }
    @Around("@annotation(TokenSecured)")
    fun securedTokenAndLogged(joinPoint: ProceedingJoinPoint) : Any {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

        val token = request.getHeader(HttpHeaders.AUTHORIZATION) ?: throw AuthorizationException("No header")

        if (!tokenService.verifyToken(token))
            throw AuthorizationException("Fake header")

        val args = joinPoint.args
        val tokenEntity = tokenRepository.getTokenByToken(token)!!

        if ( !tokenEntity.models.split(" ").contains( (args[0] as GenerateDTO).model ) )
            throw AuthorizationException("Wrong model")

        val result: Any
        val executionTime = measureTimeMillis {
            result = joinPoint.proceed(args)
        }

        tokenEntity.usedTimes++
        tokenEntity.millisSpent += executionTime
        tokenRepository.save(tokenEntity)

        return result
    }
}