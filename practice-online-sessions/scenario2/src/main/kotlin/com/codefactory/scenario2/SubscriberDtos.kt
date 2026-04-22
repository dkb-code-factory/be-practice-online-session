package com.codefactory.scenario2

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateSubscriberRequest(
    @field:Email
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val fullName: String,
)

data class SubscriberResponse(
    val id: Long,
    val email: String,
    val fullName: String,
    val createdAt: String,
)
