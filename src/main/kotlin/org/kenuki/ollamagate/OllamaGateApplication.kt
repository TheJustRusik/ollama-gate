package org.kenuki.ollamagate

import org.kenuki.ollamagate.core.entities.Roles
import org.kenuki.ollamagate.core.entities.User
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@SpringBootApplication
class OllamaGateApplication

fun main(args: Array<String>) {
    runApplication<OllamaGateApplication>(*args)
}

@Component
class CustomCommandLineRunner (
    @Autowired(required = false)
    val logger: Logger = LoggerFactory.getLogger(CustomCommandLineRunner::class.java),

    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
): CommandLineRunner {
    override fun run(vararg args: String?) {
        if (args.contains("--create-admin")) {
            val user = User("admin", passwordEncoder.encode("admin"), Roles.ADMIN, mutableSetOf())
            userRepository.save(user)
            logger.info("Created default admin with username: ${user.username} and password: admin")
            logger.warn("CHANGE HIS PASSWORD OR DELETE THE ADMIN FOR SAFETY")
        }
    }
}