package com.example.techtrack.models

data class Request(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val urgency: String = "",
    val photoUrl: String? = null,
    val status: String = "pending", // in_progress, done
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val commentFromAsu: String? = null
)
