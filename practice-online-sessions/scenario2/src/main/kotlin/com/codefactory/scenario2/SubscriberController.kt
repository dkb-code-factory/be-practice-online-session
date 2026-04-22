package com.codefactory.scenario2

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscribers")
class SubscriberController(
    private val subscriberService: SubscriberService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateSubscriberRequest): SubscriberResponse = subscriberService.create(request)

    @GetMapping
    fun findAll(): List<SubscriberResponse> = subscriberService.findAll()
}
