package com.example.bodyboost.Model

import com.google.type.DateTime

data class DietRecord(
    val id: Int,
    val date: DateTime,
    val label: String,
    val serving_amount: Float,
    val name: String,
    val calorie: Float,
    val size: Float,
    val unit: String,
    val protein: Float,
    val fat: Float,
    val carb: Float,
    val sodium: Float,
    val modify: Boolean,
    val food_type_id: Int,
    val store_id: Int,
    val user_id: Int
)
