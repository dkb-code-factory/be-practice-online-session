package com.codefactory.scenario1

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CreateTransactionRequest(
    @field:NotBlank
    val description: String,
    @field:NotNull
    @field:DecimalMin("0.01")
    val amount: BigDecimal,
    @field:NotNull
    val type: TransactionType,
)

data class TransactionResponse(
    val id: Long,
    val description: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val createdAt: String,
)
