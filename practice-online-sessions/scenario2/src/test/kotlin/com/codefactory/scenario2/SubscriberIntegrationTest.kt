package com.codefactory.scenario2

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.isA
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SubscriberIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `create subscriber returns created resource`() {
        val payload = CreateSubscriberRequest(
            email = "ana@example.com",
            fullName = "Ana Garcia",
        )

        mockMvc.perform(
            post("/subscribers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)),
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").isNumber)
            .andExpect(jsonPath("$.email").value("ana@example.com"))
            .andExpect(jsonPath("$.fullName").value("Ana Garcia"))
            .andExpect(jsonPath("$.createdAt", isA(String::class.java)))
    }

    @Test
    fun `create subscriber validates request`() {
        val payload = mapOf(
            "email" to "not-an-email",
            "fullName" to "",
        )

        mockMvc.perform(
            post("/subscribers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)),
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `create subscriber rejects exact duplicate email`() {
        val payload = CreateSubscriberRequest(
            email = "duplicate@example.com",
            fullName = "First",
        )

        mockMvc.perform(
            post("/subscribers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)),
        ).andExpect(status().isCreated)

        mockMvc.perform(
            post("/subscribers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload.copy(fullName = "Second"))),
        ).andExpect(status().isConflict)
    }

    @Test
    fun `list subscribers returns empty list by default`() {
        mockMvc.perform(get("/subscribers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(0)))
    }

    @Test
    fun `list subscribers returns all stored subscribers`() {
        val first = CreateSubscriberRequest("anna@example.com", "Anna")
        val second = CreateSubscriberRequest("bob@example.com", "Bob")

        mockMvc.perform(
            post("/subscribers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(first)),
        ).andExpect(status().isCreated)

        mockMvc.perform(
            post("/subscribers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(second)),
        ).andExpect(status().isCreated)

        mockMvc.perform(get("/subscribers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(2)))
    }
}
