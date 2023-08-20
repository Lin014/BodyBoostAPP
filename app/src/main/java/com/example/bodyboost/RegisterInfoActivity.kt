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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegisterInfoActivity : AppCompatActivity() {
    private lateinit var profile: Profile
    private lateinit var enterBirthday: TextView
    private lateinit var progressDialog: ProgressDialog
    private val calendar: Calendar = Calendar.getInstance()
    private val retrofitAPI = RetrofitManager.getInstance()
    private val currentUser = UserSingleton.user
    private val genderInt = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_info)

        enterBirthday = findViewById(R.id.birthday)
        progressDialog = ProgressDialog(this)

        val genderSpinner = findViewById<Spinner>(R.id.gender)
        val genderOptions = arrayOf("男", "女")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        val nextButton = findViewById<Button>(R.id.nextpage)
        nextButton.setOnClickListener {
            val name = getEditTextValue(R.id.name)
            val height = getEditTextValue(R.id.height)
            val weight = getEditTextValue(R.id.weight)
            val birthday = enterBirthday.text.toString()
            val gender = when (genderSpinner.selectedItemPosition) {
                0 -> "男"
                1 -> "女"
                else -> "其他"
            }
            val genderInt = convertGenderToInt(gender)

            if (validateInput(name, birthday, height, weight)) {
                val userId = currentUser?.id
                if (userId != null) {
                    addProfile(name, genderInt, birthday, height.toDouble(), weight.toDouble(), userId)
                } else {
                    showToast("未找到使用者ID")
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

    private fun getEditTextValue(viewId: Int): String {
        val editText = findViewById<EditText>(viewId)
        return editText.text.toString()
    }

    private fun validateInput(name: String, birthday: String, height: String, weight: String): Boolean {
        if (name.isEmpty() || birthday.isEmpty()|| height.isEmpty() || weight.isEmpty() ) {
            showToast("請確認所有資訊皆已填寫")
            return false
        }
        return true
    }

    private fun addProfile(
        name: String,
        gender: Int,
        birthday: String,
        height: Double,
        weight: Double,
        userID: Int
    ) {
        progressDialog.setMessage("正在提交資料...")
        progressDialog.show()
        val addProfileData =RetrofitAPI.ProfileData(name, gender, birthday, height, weight, userID)
        val call: Call<Profile>? = addProfileData?.let { retrofitAPI.addProfile(it) }
        if (call != null) {
            call.enqueue(object : Callback<Profile> {

                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    handleAddProfileResponse(response)
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    val toast = Toast(applicationContext)
                    toast.setText("請求失敗：" + t.message)
                    t.printStackTrace() // 印出錯誤堆疊以便排查問題
                    dismissProgressDialogAndShowToast(toast)
                }
            })
        }
    }

    private fun handleAddProfileResponse(response: Response<Profile>) {
        val toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT)
        if (response.isSuccessful) {
            val profile: Profile? = response.body()
            if (profile != null) {
                when (response.code()) {
                    200 -> {
                        this@RegisterInfoActivity.profile = profile
                        toast.setText("完成")
                        startTargetActivity()
                    }
                    400 -> { toast.setText("資料格式錯誤") }
                    404 -> { toast.setText("未找到使用者ID")}
                    else -> toast.setText("伺服器故障: ${response.message()}")
                }
            }
        }
        dismissProgressDialogAndShowToast(toast)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun dismissProgressDialogAndShowToast(toast: Toast) {
        progressDialog.dismiss()
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

                // 指定日期格式
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)

                // 将格式化的日期显示在 TextView 中
                enterBirthday.text = formattedDate
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
        dialog.show()
    }

    fun convertGenderToString(genderInt: Int): String {
        return when (genderInt) {
            1 -> "男"
            2 -> "女"
            else -> "其他"
        }
    }

    fun convertGenderToInt(genderString: String): Int {
        return when (genderString) {
            "男" -> 1
            "女" -> 2
            else -> 0 // 其他情况，您可以根据需要进行调整
        }
    }
}