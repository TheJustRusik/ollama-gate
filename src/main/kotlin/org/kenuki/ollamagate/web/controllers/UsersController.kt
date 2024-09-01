package org.kenuki.ollamagate.web.controllers

import org.kenuki.ollamagate.core.entities.Roles
import org.kenuki.ollamagate.core.entities.User
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.kenuki.ollamagate.core.services.UsersService
import org.kenuki.ollamagate.web.dtos.requests.ChangePasswordDTO
import org.kenuki.ollamagate.web.dtos.requests.CreateUserDTO
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class UsersController (
    private val usersService: UsersService
) {

    @PostMapping("/users")
    fun createUser(@ModelAttribute createUserDTO: CreateUserDTO): String = "redirect:" + usersService.createUser(createUserDTO)


    @PostMapping("/users/delete")
    fun deleteUser(@RequestParam username: String) : String = "redirect:" + usersService.deleteUser(username)


}