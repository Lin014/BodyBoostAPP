package com.example.bodyboost

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import com.example.bodyboost.entity.Food
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodTypeActivity : AppCompatActivity() {

    private val userId: Int = 1
    private val receivedIntent = intent
    private var progressDialog: ProgressDialog? = null
    private val retrofitAPI = RetrofitManager.getInstance()

    private var foodList: List<Food>? = null
    private var foodListAdapter: FoodListAdapter? = null

    private val spinnerItems = listOf("五穀澱粉類", "蛋肉魚類", "蔬菜類", "水果類", "乳品類", "豆類", "飲料類", "酒類", "油脂與堅果類", "零食點心", "速食類", "調味品", "菜餚類", "其他類別")
    private lateinit var listView: ListView
    private var optionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_type)

        // findViewById
        val spinner: Spinner = findViewById(R.id.spinner_type)
        val foodOptions: FloatingActionButton = findViewById(R.id.button_food_options)
        val back: Button = findViewById(R.id.back)
        listView = findViewById(R.id.listView)

        // set spinner
        if (receivedIntent != null) {
            optionId = receivedIntent.getIntExtra("optionId", 0)
            spinner.setSelection(optionId)
        }

        // setOnClickListener
        back.setOnClickListener {
            finish()
        }
        foodOptions.setOnClickListener {
            navigateActivity(FoodOptionsActivity())
        }

    }

    private fun displayFood(foodId: Int) {
        loadProgressDialog()
        val call = retrofitAPI.searchFoodById("2", userId.toString(), 1, 50)
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                displayFoodResponse(response)
            }
            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                showToast("請求失敗：" + t.message)
                t.printStackTrace()
                dismissProgressDialog()
                println(t.message)
            }
        })
    }

    private fun displayFoodResponse(response: Response<List<Food>>) {
        if (response.isSuccessful) {
            val food: List<Food>? = response.body()
            if (food != null) {
                when (response.code()) {
                    200 -> {
                        this.foodList = food
                        foodListAdapter = FoodListAdapter(this, foodList!!)
                        foodListView(foodListAdapter!!)
                    }
                    404 -> showToast("404 錯誤")
                    else -> showToast("伺服器故障")
                }
            } else {
                showToast("伺服器返回數據為空")
                println(response.toString())
            }
        } else {
            showToast("搜尋食物請求失敗：" + response.message())
            println(response.toString())
        }
        dismissProgressDialog()
    }

    private fun foodListView(foodListAdapter: FoodListAdapter) {
        listView.adapter = foodListAdapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = Intent(this@FoodTypeActivity, FoodInfoActivity::class.java)
                val food: Food? = foodList?.get(position)
                if (food != null) {
                    intent.putExtra("food", food)
                    startActivity(intent)
                } else {
                    showToast("食物資料為空")
                }
            }
    }

    private fun navigateActivity(newActivity: Activity) {
        val intent = Intent(this, newActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

    private fun dismissProgressDialog() { progressDialog?.dismiss() }

    private fun loadProgressDialog() {
        progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setMessage("Loading...")
            setCanceledOnTouchOutside(false)
            show()
        }
    }
}