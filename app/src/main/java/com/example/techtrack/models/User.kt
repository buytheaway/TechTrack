package com.example.techtrack.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "employee" // или "asu"
)
