package org.kenuki.ollamagate.core.entities

import jakarta.persistence.*

@Entity
@Table(name = "tokens")
class Token (
    @Id
    @Column(length = 255)
    var title: String,
    @Column(columnDefinition = "text")
    var description: String,
    @Column(length = 255)
    var token: String,
    @ManyToOne(fetch = FetchType.LAZY)
    var owner: User
)