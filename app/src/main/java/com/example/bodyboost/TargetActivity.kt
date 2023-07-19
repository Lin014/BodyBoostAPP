package com.example.bodyboost

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.FieldPosition

class TargetActivity : AppCompatActivity(){
    private lateinit var target:Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)
        target = findViewById(R.id.target)
        val targetItems = listOf("維持健康","減重")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,targetItems)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        target.adapter = arrayAdapter
        target.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@TargetActivity,"$selectedItem", Toast.LENGTH_SHORT)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        val finishButton = findViewById<View>(R.id.finish) as Button
        finishButton.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@TargetActivity, MainActivity::class.java)
            startActivity(intent)
            val toast = Toast.makeText(this@TargetActivity, "註冊成功", Toast.LENGTH_LONG)
            toast.show()
        }
//        val BackButton = findViewById<View>(R.id.back) as Button
//        BackButton.setOnClickListener {
//            val intent = Intent(this@RegisterInfoActivity,RegisterActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
//        }
    }
}