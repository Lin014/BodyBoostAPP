package com.example.bodyboost.Manager

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignInManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SignInPrefs", Context.MODE_PRIVATE)

    // 检查用户是否可以签到
    fun canSignIn(): Boolean {
        val lastSignInDate = sharedPreferences.getString("lastSignInDate", null)

        if (lastSignInDate == null) {
            // 如果没有上次签到日期，允许签到
            return true
        }

        val currentDate = getCurrentDate()
        return currentDate != lastSignInDate
    }

    // 执行签到
    fun performSignIn() {
        // 获取当前日期
        val currentDate = getCurrentDate()

        // 保存当前日期作为上次签到日期
        sharedPreferences.edit().putString("lastSignInDate", currentDate).apply()
    }

    // 获取当前日期的字符串表示
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Calendar.getInstance().time)
        return currentDate
    }
}