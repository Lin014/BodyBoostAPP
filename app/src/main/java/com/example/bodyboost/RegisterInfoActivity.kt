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
        val finishButton = findViewById<View>(R.id.finish) as Button
        finishButton.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@RegisterInfoActivity, MainActivity::class.java)
            startActivity(intent)
            val toast = Toast.makeText(this@RegisterInfoActivity, "註冊成功", Toast.LENGTH_LONG)
            toast.show()
        }
        val BackButton = findViewById<View>(R.id.back) as Button
        BackButton.setOnClickListener {
            val intent = Intent(this@RegisterInfoActivity,RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }
}