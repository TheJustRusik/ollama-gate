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
    var millisSpent : Long = 0,
    @Column(columnDefinition = "bigint")
    var usedTimes : Long = 0,
    @Column(columnDefinition = "text")
    var models: String,
) {
    val timeSpentFormatted: String
        get() {
            val days = millisSpent / (24 * 60 * 60 * 1000)
            val hours = millisSpent / (60 * 60 * 1000) % 60
            val minutes = millisSpent / (60 * 1000) % 60
            val seconds = millisSpent / 1000 % 60

            return when {
                days > 0 -> String.format("%dd %dh %dm %ds", days, hours, minutes, seconds)
                hours > 0 -> String.format("%dh %dm %ds", hours, minutes, seconds)
                minutes > 0 -> String.format("%dm %ds", minutes, seconds)
                else -> String.format("%ds", seconds)
            }
        }
}