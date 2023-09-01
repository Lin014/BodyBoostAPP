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
import android.widget.TextView
import android.widget.Toast
import com.example.bodyboost.entity.Food
import com.example.bodyboost.entity.FoodType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodTypeFragment : Fragment() {

    private var foodList: List<Food>? = null
    private var foodTypeList: List<FoodType>? = null
    private val retrofitAPI = RetrofitManager.getInstance()
    private val userId: Int = 1
    private var progressDialog: ProgressDialog? = null
    private var foodListAdapter: FoodListAdapter? = null
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_food_type, container, false)
        val items = listOf("五穀澱粉類", "蛋肉魚類", "蔬菜類", "水果類", "乳品類", "豆類", "飲料類", "酒類",
            "油脂與堅果類", "零食點心", "速食類", "調味品", "菜餚類", "其他類別")

        //findViewById
        val spinner: Spinner = rootView.findViewById(R.id.spinner_type)
        val foodOptions: FloatingActionButton = rootView.findViewById(R.id.button_food_options)
        listView = rootView.findViewById(R.id.listView)


        val textView: TextView = rootView.findViewById(R.id.textview)
        val button: TextView = rootView.findViewById(R.id.button_food)
        val testButton: Button = rootView.findViewById(R.id.test)


        val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //setOnClickListener
        foodOptions.setOnClickListener {
            replaceActivity(FoodOptionsActivity())
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val option = items[position]
                //Toast.makeText(context, option, Toast.LENGTH_SHORT).show()
                textView.text = option
                // api test
                searchFood(position + 2)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        button.setOnClickListener {
            replaceActivity(FoodInfoActivity())
        }

        testButton.setOnClickListener {
            searchFood(2)
//            getFoodType()
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun searchFood(foodId: Int) {
        loadProgressDialog()
        var call = retrofitAPI.searchFoodById(foodId.toString(), userId.toString(), 1, 50)
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                searchFoodResponse(response)
            }
            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                val toast = Toast(context)
                toast.setText("請求失敗：" + t.message)
                t.printStackTrace()
                dismissProgressDialogAndShowToast(toast)
                println(t.message)
            }
        })
    }

    private fun searchFoodResponse(response: Response<List<Food>>) {
        val toast = Toast.makeText(context, "no message", Toast.LENGTH_SHORT)
        if (response.isSuccessful) {
            val food: List<Food>? = response.body()
            if (food != null) {
                when (response.code()) {
                    200 -> {
                        this.foodList = food
                        foodListAdapter = FoodListAdapter(this.requireContext(), foodList!!)
                        listView.adapter = foodListAdapter
                        toast.setText("成功獲取食物資料")
                    }
                    404 -> toast.setText("404 錯誤")
                    else -> toast.setText("伺服器故障")
                }
            } else {
                toast.setText("伺服器返回數據為空")
                println(response.toString())
            }
        } else {
            toast.setText("搜尋食物請求失敗： " + response.message())
            println(response.toString())
        }
        dismissProgressDialogAndShowToast(toast)
    }

    private fun dismissProgressDialogAndShowToast(toast: Toast) {
        progressDialog?.dismiss()
        toast.show()
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

/*
    private fun getFoodType() {
        loadProgressDialog()
        var call = retrofitAPI.getFoodType()
        call.enqueue(object : Callback<List<FoodType>> {
            override fun onResponse(call: Call<List<FoodType>>, response: Response<List<FoodType>>) {
                foodTypeResponse(response)
            }
            override fun onFailure(call: Call<List<FoodType>>, t: Throwable) {
                val toast = Toast(context)
                toast.setText("請求失敗：" + t.message)
                t.printStackTrace()
                dismissProgressDialogAndShowToast(toast)
                println(t.message)
            }
        })
    }
    private fun foodTypeResponse(response: Response<List<FoodType>>) {
        val toast = Toast.makeText(context, "no message", Toast.LENGTH_SHORT)
        if (response.isSuccessful) {
            val foodType: List<FoodType>? = response.body()
            if (foodType != null) {
                when (response.code()) {
                    200 -> {
                        this.foodTypeList = foodType
                        toast.setText("成功獲取食物類型")
                        println(response.toString())
                    }
                    404 -> toast.setText("404 錯誤")
                    else -> toast.setText("伺服器故障")
                }
            } else {
                toast.setText("食物類型數據為空")
                println(response.toString())
            }
        } else {
            toast.setText("請求失敗：QQ " + response.message())
            println(response.toString())
        }
        dismissProgressDialogAndShowToast(toast)
    }

 */
}