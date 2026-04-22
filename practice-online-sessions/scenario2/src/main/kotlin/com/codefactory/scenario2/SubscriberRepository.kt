package com.codefactory.scenario2

import org.springframework.data.jpa.repository.JpaRepository

interface SubscriberRepository : JpaRepository<Subscriber, Long> {
    fun existsByEmail(email: String): Boolean
}
