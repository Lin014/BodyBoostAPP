package com.example.bodyboost.Model

data class Setting(
    val id: Int,
    val theme: String,
    val anim_char_name: String,
    var is_alerted: Boolean,
    val alert_day: String,
    val alert_time: String,
    val user_id: Int
)
