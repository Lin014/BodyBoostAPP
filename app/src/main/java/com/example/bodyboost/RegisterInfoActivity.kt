package com.example.bodyboost

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_info)
        val nextButton = findViewById<View>(R.id.next) as Button
        nextButton.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@RegisterInfoActivity, TargetActivity::class.java)
            startActivity(intent)
        }
        val BackButton = findViewById<View>(R.id.back) as Button
        BackButton.setOnClickListener {
            val intent = Intent(this@RegisterInfoActivity,RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }
}