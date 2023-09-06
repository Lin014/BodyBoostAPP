package com.example.bodyboost.Model

data class Member (
    val id: Int,
    val member_type: String,
    val phone: String,
    val is_trial: Boolean,
    val payment_type: String,
    val user_id:Int
)