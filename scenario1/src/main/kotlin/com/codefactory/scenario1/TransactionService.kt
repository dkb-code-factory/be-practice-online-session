package com.codefactory.scenario1

import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
) {
    fun create(request: CreateTransactionRequest): TransactionResponse {
        val transaction = Transaction(
            description = request.description.trim(),
            amount = request.amount,
            type = request.type,
        )

        return transactionRepository.save(transaction).toResponse()
    }

    fun findAll(): List<TransactionResponse> = transactionRepository.findAll()
        .sortedByDescending { it.createdAt }
        .map { it.toResponse() }
}

fun Transaction.toResponse() = TransactionResponse(
    id = id,
    description = description,
    amount = amount,
    type = type,
    createdAt = createdAt.toString(),
)
