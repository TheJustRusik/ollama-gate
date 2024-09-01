package org.kenuki.ollamagate.core.aspects

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.ui.Model

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AddHeadHelloToModel

@Aspect
@Component
class UserDetailsAspect (
    val userRepository: UserRepository
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
}