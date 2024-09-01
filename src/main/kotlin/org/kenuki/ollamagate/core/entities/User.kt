package org.kenuki.ollamagate.core.entities

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Id
    @Column(length = 32)
    var username: String,
    @Column(length = 80)
    var password: String,
    @Enumerated(EnumType.ORDINAL)
    var role: Roles = Roles.USER,
    @OneToMany(mappedBy = "owner", cascade = [(CascadeType.REMOVE)], orphanRemoval = true)
    var tokens: MutableSet<Token>?
)

enum class Roles {
    USER, ADMIN
}