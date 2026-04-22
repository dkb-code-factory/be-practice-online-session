package com.codefactory.scenario1

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.isEmptyString
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
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `creates a transaction`() {
        val request = CreateTransactionRequest(
            description = " Salary ",
            amount = BigDecimal("2500.00"),
            type = TransactionType.INCOME,
        )

        mockMvc.perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)),
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").isNumber)
            .andExpect(jsonPath("$.description").value("Salary"))
            .andExpect(jsonPath("$.amount").value(2500.00))
            .andExpect(jsonPath("$.type").value("INCOME"))
            .andExpect(jsonPath("$.createdAt").isString)
    }

    @Test
    fun `rejects invalid payload`() {
        val payload = mapOf(
            "description" to "   ",
            "amount" to 0,
            "type" to "EXPENSE",
        )

        mockMvc.perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)),
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `returns empty list when there are no transactions`() {
        mockMvc.perform(get("/transactions"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(0)))
    }

    @Test
    fun `returns all transactions with newest first`() {
        createTransaction("Coffee", BigDecimal("3.50"), TransactionType.EXPENSE)
        createTransaction("Freelance", BigDecimal("600.00"), TransactionType.INCOME)

        mockMvc.perform(get("/transactions"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(2)))
            .andExpect(jsonPath("$[0].description").value("Freelance"))
            .andExpect(jsonPath("$[0].type").value("INCOME"))
            .andExpect(jsonPath("$[1].description").value("Coffee"))
            .andExpect(jsonPath("$[1].type").value("EXPENSE"))
    }

    private fun createTransaction(description: String, amount: BigDecimal, type: TransactionType) {
        val request = CreateTransactionRequest(
            description = description,
            amount = amount,
            type = type,
        )

        mockMvc.perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)),
        )
            .andExpect(status().isCreated)
    }
}
