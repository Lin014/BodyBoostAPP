package com.example.bodyboost

data class Profile(
    val id: Int,
    val name: String,
    val gender: Int,
    val birthday: String,
    val height: Double,
    val weight: Double,
    val weight_goal: Number,
    val image:String,
    val goal:String,
    val body_fat:Number,
    val user: Int // 關聯到 User 資料表中的 id 欄位
)
