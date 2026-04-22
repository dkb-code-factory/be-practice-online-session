package com.codefactory.scenario2

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant

@Entity
class Subscriber(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false)
    var fullName: String,
    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),
)
