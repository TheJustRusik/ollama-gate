package org.kenuki.ollamagate.core.entities

import jakarta.persistence.*

@Entity
@Table(name = "tokens")
class Token (
    @Id
    @Column(length = 255, nullable = false)
    var title: String,
    @Column(columnDefinition = "text", nullable = false)
    var description: String,
    @Column(length = 255)
    var token: String,
    @ManyToOne
    var owner: User,
    @Column(columnDefinition = "bigint")
    val secondSpent : Long = 0,
    @Column(columnDefinition = "bigint")
    val usedTimes : Long = 0
)