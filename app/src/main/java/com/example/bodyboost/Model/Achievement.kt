package com.example.bodyboost.Model

data class Achievement(
    val id: Int,
    val name: String,
    val description: String,
    val isUnlocked: Boolean,
    val imageResource: Int // 圖片資源 ID
)
