package com.example.bodyboost

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class RegisterInfoActivity : AppCompatActivity() {
    private var profile: Profile? = null
    private var enterBirthday: TextView? = null
    private var progressDialog: ProgressDialog? = null
    private var calendar: Calendar = Calendar.getInstance()
    private val retrofitAPI = RetrofitManager.getInstance()
    private val currentUser = UserSingleton.user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_info)

        enterBirthday = findViewById(R.id.birthday)
        val genderSpinner = findViewById<Spinner>(R.id.gender)
        val genderOptions = arrayOf("男", "女")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        val nextButton = findViewById<Button>(R.id.nextpage)
        nextButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString()
            val height = findViewById<EditText>(R.id.height).text.toString()
            val weight = findViewById<EditText>(R.id.weight).text.toString()
            val birthday = enterBirthday?.text.toString()
            val gender = when (genderSpinner.selectedItemPosition) {
                0 -> 1 // 男
                1 -> 2 // 女
                else -> 0 // 預設值或其他情況
            }

            if (name.isEmpty() || height.isEmpty() || weight.isEmpty() || birthday.isEmpty()) {
                showToast("請確認所有欄位皆已填")
            } else {
                currentUser?.let { user ->
                    val userId = user.id
                    retrofitAPI.getSpecialUserInfo(userId)
                    addProfile(name, gender, birthday, height.toDouble(), weight.toDouble(), userId)
                }
            }
        }

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this@RegisterInfoActivity, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out)
        }
    }

    private fun addProfile(
        name: String,
        gender: Int,
        birthday: String,
        height: Double,
        weight: Double,
        userID: Int
    ) {
        val addProfileData = RetrofitAPI.ProfileData(name, gender, height, weight, birthday, userID)
        val call: Call<Profile> = retrofitAPI.addProfile(addProfileData)
        call.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                handleAddProfileResponse(response)
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                val toast = Toast.makeText(applicationContext, "請求失敗：" + t.message, Toast.LENGTH_SHORT)
                t.printStackTrace() // 印出錯誤堆疊以便排查問題
                dismissProgressDialogAndShowToast(toast)
            }
        })
    }

    private fun handleAddProfileResponse(response: Response<Profile>) {
        val toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT)
        if (response.isSuccessful) {
            val profile: Profile? = response.body()
            if (profile != null) {
                this@RegisterInfoActivity.profile = profile
                toast.setText("完成")
                startTargetActivity()
            } else {
                toast.setText("伺服器返回的數據為空")
            }
        } else {
            when (response.code()) {
                400 -> toast.setText("資訊有誤")
                else -> toast.setText("伺服器故障: ${response.message()}")
            }
        }
        dismissProgressDialogAndShowToast(toast)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun dismissProgressDialogAndShowToast(toast: Toast) {
        progressDialog?.dismiss()
        toast.show()
    }


    private fun startTargetActivity() {
        val intent = Intent(this@RegisterInfoActivity, Guide1::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out)
    }

    fun datePicker(v: View) {
        val dialog = DatePickerDialog(
            v.context,
            { _, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DATE] = dayOfMonth
                enterBirthday?.text = "$year/${month + 1}/$dayOfMonth"
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
        dialog.show()
    }
}

