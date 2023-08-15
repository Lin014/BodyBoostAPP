package com.example.bodyboost

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerificationCode : AppCompatActivity() {
    private val retrofitAPI = RetrofitManager.getInstance()
    private val currentUser = UserSingleton.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verification_code)

        val enterVerifyNum = findViewById<EditText>(R.id.verify_num)
        val verifyButton = findViewById<Button>(R.id.verify)

        verifyButton.setOnClickListener {
            currentUser?.id?.let { userId ->
                val code = enterVerifyNum.text.toString()
                val registerNumData = RetrofitAPI.RegisterNumData(code, userId)
                retrofitAPI.testRegisterNum(registerNumData).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            showToast("驗證成功")
                            startRegisterInfoActivity()
                        } else {
                            showToast("驗證失敗")
                            showFailedVerificationDialog()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        showToast("請求失敗：" + t.message)
                    }
                })
            }
        }
    }

    private fun showFailedVerificationDialog() {
        currentUser?.let { user ->
            showAlertDialog(
                title = "驗證失敗",
                message = "請重新驗證",
                positiveButtonText = "重新驗證",
                positiveButtonAction = {
                    user.account?.let { account ->
                        resendVerificationMail(account)
                    }
                },
                negativeButtonText = "取消"
            )
        }
    }

    private fun resendVerificationMail(account: String) {
        retrofitAPI.resentRegisterMail(account).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    showToast("重新發送驗證郵件成功")
                } else {
                    showToast("重新發送驗證郵件失敗")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                showToast("請求失敗：" + t.message)
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startRegisterInfoActivity() {
        val intent = Intent(this@VerificationCode, RegisterInfoActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close this activity if needed
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        positiveButtonAction: () -> Unit,
        negativeButtonText: String? = null
    ) {
        val alertDialogBuilder = AlertDialog.Builder(this@VerificationCode)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton(positiveButtonText) { dialog, _ ->
            positiveButtonAction.invoke()
            dialog.dismiss()
        }

        negativeButtonText?.let {
            alertDialogBuilder.setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
            }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}