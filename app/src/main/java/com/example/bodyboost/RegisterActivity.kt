package com.example.bodyboost

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<View>(R.id.next) as Button
        registerButton.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@RegisterActivity, RegisterInfoActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
        val BackButton = findViewById<View>(R.id.back) as Button
        BackButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }
}