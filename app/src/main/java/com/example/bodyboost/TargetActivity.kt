package com.example.bodyboost

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class TargetActivity : AppCompatActivity(){
    private lateinit var target:Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)

        target = findViewById(R.id.target)
        val targetItems = listOf("維持健康","減重","增肌","減脂")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,targetItems)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        target.adapter = arrayAdapter
        target.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // 根據使用者的選擇跳傳至不同的 Activity
                when (parent.getItemAtPosition(position).toString()) {
                    "維持健康" -> {
                        val intent = Intent(this@TargetActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                    "減重" -> {
                        val intent = Intent(this@TargetActivity, TargetInfo1::class.java)
                        startActivity(intent)
                    }
                    "減脂" -> {
                        val intent = Intent(this@TargetActivity, TargetInfo2::class.java)
                        startActivity(intent)
                    }
                    "增肌" -> {
                        val intent = Intent(this@TargetActivity,TargetInfo3::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        val FinishButton = findViewById<View>(R.id.finish) as Button
        FinishButton.setOnClickListener {
            val intent = Intent(this@TargetActivity,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }
}