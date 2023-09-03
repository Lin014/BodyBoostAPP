package com.example.bodyboost.entity

import java.io.Serializable

data class Food(
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
    val food_type_id: Int,
    val store_id: Int
) : Serializable
