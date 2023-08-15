package com.example.bodyboost

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private var progressDialog: ProgressDialog? = null
    private val retrofitAPI = RetrofitManager.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val enterAcc = findViewById<EditText>(R.id.username)
        val enterPwd = findViewById<EditText>(R.id.password)
        val enterCheckPwd = findViewById<EditText>(R.id.check_password)
        val enterEmail = findViewById<EditText>(R.id.email)

        //-----------下一步按鈕-------------
        val registerButton = findViewById<Button>(R.id.register)
        registerButton.setOnClickListener {
            val account = enterAcc.text.toString()
            val password = enterPwd.text.toString()
            val checkedPassword = enterCheckPwd.text.toString()
            val email = enterEmail.text.toString()

            when {
                password != checkedPassword -> showToast("密碼輸入錯誤")
                account.isEmpty() || password.isEmpty() || checkedPassword.isEmpty() || email.isEmpty() -> showToast("請確認所有欄位皆已填")
                else -> addUser(account, password, email)
            }
        }
        //-----------返回按鈕-------------
        val BackButton = findViewById<View>(R.id.back) as Button
        BackButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }
    private fun addUser(account: String, password: String, email: String) {
        val addUserData = RetrofitAPI.AddUserData(account, password, email)
        retrofitAPI.addUser(addUserData).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                handleAddUserResponse(response)
            }
            override fun onFailure(call: Call<Users>, t: Throwable) {
                val toast = Toast(applicationContext)
                toast.setText("請求失敗：" + t.message)
                t.printStackTrace() // 印出錯誤堆疊以便排查問題
                dismissProgressDialogAndShowToast(toast)
            }
        })
    }
    private fun handleAddUserResponse(response: Response<Users>) {
        val toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT)
        if (response.isSuccessful) {
            val users: Users? = response.body()
            if (users != null) {
                UserSingleton.user = users
                //val newUserId = users.id
                //toast.setText("完成，使用者 ID: $newUserId")
                //this@RegisterActivity.users = users
                toast.setText("完成")
                startRegisterInfoActivity()
            } else {
                toast.setText("伺服器返回的數據為空")
            }
        } else {
            when (response.code()) {
                400 -> toast.setText("帳號密碼已存在")
                else -> toast.setText("伺服器故障: ${response.message()}")
            }
        }
        dismissProgressDialogAndShowToast(toast)
    }
    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    private fun dismissProgressDialogAndShowToast(toast: Toast) {
        progressDialog?.dismiss()
        toast.show()
    }
    private fun startRegisterInfoActivity() {
        val intent = Intent(this@RegisterActivity, VerificationCode::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out)
    }
}