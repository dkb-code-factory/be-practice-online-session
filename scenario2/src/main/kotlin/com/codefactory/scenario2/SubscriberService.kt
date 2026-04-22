package com.codefactory.scenario2

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SubscriberService(
    private val subscriberRepository: SubscriberRepository,
) {
    fun create(request: CreateSubscriberRequest): SubscriberResponse {
        val email = request.email.trim()
        if (subscriberRepository.existsByEmail(email)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Subscriber already exists")
        }

        val subscriber = Subscriber(
            email = email,
            fullName = request.fullName.trim(),
        )

        return subscriberRepository.save(subscriber).toResponse()
    }

    fun findAll(): List<SubscriberResponse> = subscriberRepository.findAll()
        .sortedByDescending { it.createdAt }
        .map { it.toResponse() }
}

fun Subscriber.toResponse() = SubscriberResponse(
    id = id,
    email = email,
    fullName = fullName,
    createdAt = createdAt.toString(),
)
