package com.example.bodyboost.entity

data class Users(
    val id: Int,
    val account: String,
    val password: String,
    val email: String,
    val created_type: String,
    val status: String
)

