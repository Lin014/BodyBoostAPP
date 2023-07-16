package com.example.bodyboost

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpwd)

        val BackButton = findViewById<View>(R.id.back) as Button
        BackButton.setOnClickListener {
            val intent = Intent(this@ForgetPasswordActivity,LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
        val resetpwd_btn = findViewById<View>(R.id.resetpwd) as Button
        resetpwd_btn.setOnClickListener{
            AlertDialog.Builder(this)
                //AlertDialog.Builder (context: Context)
                //參數放要傳入的 MainActivity Context
                .setTitle("確認重設密碼")
                .setMessage("請至輸入的信箱收取郵件")  //訊息內容
                .setPositiveButton("確認") {_,_ ->
                    intent.setClass(this@ForgetPasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                }
                .setNegativeButton("取消",null)
                .create()
                .show()
        }
    }
}