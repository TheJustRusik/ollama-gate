package org.kenuki.ollamagate.core.aspects

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.kenuki.ollamagate.core.exceptions.AuthorizationException
import org.kenuki.ollamagate.core.repositories.TokenRepository
import org.kenuki.ollamagate.core.services.TokenService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AddHeadHelloToModel

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Secured

@Aspect
@Component
class Aspects(
    val tokenService: TokenService,
    val tokenRepository: TokenRepository
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
    @Before("@annotation(Secured)")
    fun secured() {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

        val token = request.getHeader(HttpHeaders.AUTHORIZATION) ?: throw AuthorizationException()

        if (!tokenService.verifyToken(token))
            throw AuthorizationException()

        val tokenEntity = tokenRepository.getTokenByToken(token)

        val context = SecurityContextHolder.createEmptyContext().apply {
            authentication = UsernamePasswordAuthenticationToken(
                tokenEntity!!.owner.username, null,
                listOf(SimpleGrantedAuthority("???"))//TODO
            )
        }

        SecurityContextHolder.setContext(context)
    }
}