package com.example.bodyboost.Model

data class DailyBonus(
    val id: Int,
    val date: String,
    val user_id: Int // 關聯到 User 資料表中的 id 欄位
)
