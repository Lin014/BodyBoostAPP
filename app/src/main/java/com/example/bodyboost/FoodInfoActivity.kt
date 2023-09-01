package com.example.bodyboost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class FoodInfoActivity : AppCompatActivity() {

    private lateinit var back: Button
    private lateinit var add: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_info)

        // findViewById
        back = findViewById(R.id.backButton)
        add = findViewById(R.id.addButton)

        // setOnClickListener
        add.setOnClickListener {
            Toast.makeText(this, "新增成功", Toast.LENGTH_SHORT).show()
            finish()
        }
        back.setOnClickListener {
            Toast.makeText(this, "返回上一頁", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}