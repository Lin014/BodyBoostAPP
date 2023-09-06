package com.example.bodyboost.Register

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bodyboost.MainActivity
import com.example.bodyboost.Model.Profile
import com.example.bodyboost.R
import com.example.bodyboost.RetrofitManager
import com.example.bodyboost.UserSingleton
import com.example.bodyboost.databinding.ActivityTargetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class TargetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTargetBinding
    private val currentUser = UserSingleton.user
    private val retrofitAPI = RetrofitManager.getInstance()
    private val goalMapping = mapOf(
        "維持健康" to "health",
        "減重" to "weight",
        "增肌" to "muscle",
        "減脂" to "fat"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val targetItems = goalMapping.keys.toList()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, targetItems)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.target.adapter = arrayAdapter

        val finishButton = findViewById<Button>(R.id.finish)
        finishButton.setOnClickListener {
            currentUser?.let { it1 -> getCurrentProfile(it1.id) }
        }
    }


    private fun getCurrentProfile(userId: Int) {
        retrofitAPI.getProfile(userId).enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                when (response.code()) {
                    200 -> {
                        val currentProfile = response.body()
                        if (currentProfile != null) {
                            val selectedGoal = goalMapping[binding.target.selectedItem.toString()]
                            if (selectedGoal != null) {
                                currentProfile.goal = selectedGoal
                                updateProfile(currentProfile)
                            } else {
                                showToast("請選擇目標")
                            }
                        }
                    }
                    400 -> showToast("格式錯誤")
                    404 -> showToast("查無此資料")
                    else -> showToast("伺服器故障: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                showToast("伺服器故障")
            }
        })
    }
    private fun updateProfile(profile: Profile) {
        val userId = currentUser?.id
        if (userId != null) {
            retrofitAPI.update_goal(userId, profile).enqueue(object : Callback<Profile> {
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    when (response.code()) {
                        200 -> {
                            startActivityWithGoal(profile)
                        }
                        400 -> showToast("格式錯誤")
                        404 -> showToast("查無此資料")
                        else -> showToast("伺服器故障: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    showToast("伺服器故障")
                }
            })
        }
    }
    private fun startActivityWithGoal(goal: Profile) {
        val intent = when (goal.goal) {
            "health" -> {
                showToast("歡迎加入BodyBoost")
                Intent(this@TargetActivity, MainActivity::class.java)
            }
            "weight" -> Intent(this@TargetActivity, Target1::class.java)
            "fat" -> Intent(this@TargetActivity, Target2::class.java)
            "muscle" -> Intent(this@TargetActivity, Target3::class.java)
            else -> throw IllegalArgumentException("未知目標: ${goal.goal}")
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}