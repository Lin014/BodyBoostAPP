package com.example.bodyboost

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.example.bodyboost.Model.CustomFood
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomFoodAddActivity : AppCompatActivity() {

    val currentUser = UserSingleton.user
    private var userId: Int = 0
    private var progressDialog: ProgressDialog? = null
    private val retrofitAPI = RetrofitManager.getInstance()

    private lateinit var name: String
    private var calorie: Float = 0.0f
    private var size: Float = 0.0f
    private var unit: String = "g"
    private var protein: Float? = null
    private var fat: Float? = null
    private var carb: Float? = null
    private var sodium: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_food_add)
        if (currentUser != null) {
            userId = currentUser.id
        }

        // findViewById
        val back = findViewById<Button>(R.id.back)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val inputFinished = findViewById<Button>(R.id.inputFinished)
        val foodName = findViewById<EditText>(R.id.foodName)
        val foodCalorie = findViewById<EditText>(R.id.foodCalorie)
        val foodSize = findViewById<EditText>(R.id.foodSize)
        val foodProtein = findViewById<EditText>(R.id.foodProtein)
        val foodFat = findViewById<EditText>(R.id.foodFat)
        val foodCarb = findViewById<EditText>(R.id.foodCarb)
        val foodSodium = findViewById<EditText>(R.id.foodSodium)

        // setOn
        back.setOnClickListener {
            finish()
        }
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton_g -> unit = "g"
                R.id.radioButton_ml -> unit = "ml"
            }
        }
        inputFinished.setOnClickListener {
            if (areEditTextsEmpty(foodName, foodCalorie, foodSize)) {
                name = foodName.text.toString().trim()
                calorie = foodCalorie.text.toString().trim().toFloat()
                size = foodSize.text.toString().trim().toFloat()
                protein = foodProtein.text.toString().trim().toFloatOrNull()
                fat = foodFat.text.toString().trim().toFloatOrNull()
                carb = foodCarb.text.toString().trim().toFloatOrNull()
                sodium = foodSodium.text.toString().trim().toFloatOrNull()

                addCustomFood(name, calorie, size, unit, protein, fat, carb, sodium, true)
            }
        }
    }

    private fun addCustomFood(name: String, calorie: Float, size: Float, unit: String, protein: Float?, fat: Float?, carb: Float?, sodium: Float?, modify: Boolean) {
        loadProgressDialog()
        val addCustomFoodData = RetrofitAPI.CustomFoodData(name, calorie, size, unit, protein, fat, carb, sodium, modify, 1, 1, userId)
        val call = retrofitAPI.addCustomFood(addCustomFoodData)
        call.enqueue(object : Callback<List<CustomFood>> {
            override fun onResponse(call: Call<List<CustomFood>>, response: Response<List<CustomFood>>) {
                addCustomFoodResponse(response)
            }
            override fun onFailure(call: Call<List<CustomFood>>, t: Throwable) {
                showToast("新增食物請求失敗：" + t.message)
                t.printStackTrace()
                dismissProgressDialog()
                println(t.message)
            }
        })
    }

    private fun addCustomFoodResponse(response: Response<List<CustomFood>>) {
        if (response.isSuccessful) {
            val customFood: List<CustomFood>? = response.body()
            if (customFood != null) {
                when (response.code()) {
                    200 -> {
                        showToast("新增完成")
                        finish()
                    }
                    404 -> showToast("404 錯誤")
                    else -> showToast("伺服器故障")
                }
            } else {
                showToast("新增自訂食物失敗")
                println(response.toString())
            }
        } else {
            showToast("新增自訂食物請求失敗：" + response.message())
            println(response.toString())
        }
        dismissProgressDialog()
    }

    private fun areEditTextsEmpty(vararg editTexts: EditText): Boolean {
        // check EditText is Empty
        for (editText in editTexts) {
            val text = editText.text.toString().trim()
            if (text.isBlank()) {
                editText.error = "此項目為必填"
                return false
            }
        }
        return true
    }

    private fun loadProgressDialog() {
        progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setMessage("Loading...")
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}