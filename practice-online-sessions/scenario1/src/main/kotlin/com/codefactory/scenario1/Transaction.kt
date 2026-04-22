package com.codefactory.scenario1

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.Instant

@Entity
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    var description: String,
    @Column(nullable = false, precision = 12, scale = 2)
    var amount: BigDecimal,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: TransactionType,
    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),
)

enum class TransactionType {
    INCOME,
    EXPENSE,
}
