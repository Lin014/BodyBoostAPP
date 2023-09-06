package com.example.bodyboost

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import com.example.bodyboost.Model.Food
import com.example.bodyboost.Model.FoodType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodTypeFragment : Fragment() {

    private val userId: Int = 1
    private var foodList: List<Food>? = null
    private var foodTypeList: List<FoodType>? = null
    private var progressDialog: ProgressDialog? = null
    private var foodListAdapter: FoodListAdapter? = null
    private val retrofitAPI = RetrofitManager.getInstance()
    private val spinnerItems = listOf("五穀澱粉類", "蛋肉魚類", "蔬菜類", "水果類", "乳品類", "豆類", "飲料類", "酒類", "油脂與堅果類", "零食點心", "速食類", "調味品", "菜餚類", "其他類別")
    private lateinit var back: Button
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_food_type, container, false)

        // findViewById
        val spinner: Spinner = rootView.findViewById(R.id.spinner_type)
        val foodOptions: FloatingActionButton = rootView.findViewById(R.id.button_food_options)
        back = rootView.findViewById(R.id.back)
        listView = rootView.findViewById(R.id.listView)

        val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // setOnClickListener
        back.setOnClickListener { replaceActivity(SearchFoodActivity()) }
        foodOptions.setOnClickListener { replaceActivity(FoodOptionsActivity()) }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val option = spinnerItems[position]
                searchFood(position + 2)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun searchFood(foodId: Int) {
        loadProgressDialog()
        val call = retrofitAPI.searchFoodById(foodId.toString(), userId.toString(), 1, 50)
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                searchFoodResponse(response)
            }
            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Toast.makeText(context, "請求失敗：" + t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                dismissProgressDialog()
                println(t.message)
            }
        })
    }

    private fun searchFoodResponse(response: Response<List<Food>>) {
        if (response.isSuccessful) {
            val food: List<Food>? = response.body()
            if (food != null) {
                when (response.code()) {
                    200 -> {
                        this.foodList = food
                        foodListAdapter = FoodListAdapter(this.requireContext(), foodList!!)
                        displayFoodListView(foodListAdapter!!)
//                        Toast.makeText(context, "成功獲取食物資料", Toast.LENGTH_SHORT).show()
                    }
                    404 -> Toast.makeText(context, "404 錯誤", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(context, "伺服器故障", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "伺服器返回數據為空", Toast.LENGTH_SHORT).show()
                println(response.toString())
            }
        } else {
            Toast.makeText(context, "搜尋食物請求失敗：" + response.message(), Toast.LENGTH_SHORT).show()
            println(response.toString())
        }
        dismissProgressDialog()
    }

    private fun displayFoodListView(foodListAdapter: FoodListAdapter) {
        listView.adapter = foodListAdapter
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(activity, FoodInfoActivity::class.java)
                val food: Food? = foodList?.get(position)
                if (food != null) {
                    intent.putExtra("food", food)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "食物資料為空", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    private fun replaceActivity(newActivity: Activity) {
        val intent = Intent(activity, newActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadProgressDialog() {
        progressDialog = ProgressDialog(this.context).apply {
            setCancelable(false)
            setMessage("Loading...")
            setCanceledOnTouchOutside(false)
            show()
        }
    }
}