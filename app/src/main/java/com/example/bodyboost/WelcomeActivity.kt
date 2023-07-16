package com.example.bodyboost

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // 2秒鐘進入主頁面
        Handler().postDelayed({
            startActivity(
                Intent(
                    this@WelcomeActivity,
                    LoginActivity::class.java

                )
            )
        }, 2000)
    }
}