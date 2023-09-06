package com.example.bodyboost.Model

data class CustomFood(
    val id: Int,
    val name: String,
    val calorie: Float,
    val size: Float,
    val unit: String,
    val protein: Float,
    val fat: Float,
    val carb: Float,
    val sodium: Float,
    val modify: Boolean,
    val type_id: Int,
    val store_id: Int,
    val user_id: Int
)
