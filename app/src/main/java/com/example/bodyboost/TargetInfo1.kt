package com.example.bodyboost

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TargetInfo1 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.target_lose_weight_intr)
        val guideButton = findViewById<Button>(R.id.guide)
        guideButton.setOnClickListener {
            val intent = Intent(this@TargetInfo1, TargetInfo2::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out)
        }
    }
}