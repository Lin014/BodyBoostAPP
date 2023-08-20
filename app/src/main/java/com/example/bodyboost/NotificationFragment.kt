package com.example.bodyboost

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class NotificationFragment : Fragment() {
    private lateinit var addNotificationTimeBtn: Button
    private lateinit var notificationSwitch: Switch
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 123
    var timeDialog: OnTimeSetListener? = null
    private val retrofitAPI = RetrofitManager.getInstance()
    private var is_alerted = false
    private var setting: Setting? = null
    private val currectUser = UserSingleton.user
    private var isAlerted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.notification, container, false)

        // 檢查並請求通知權限
        checkAndRequestNotificationPermission()
        addNotificationTimeBtn = view.findViewById<Button>(R.id.time_setting)
        addNotificationTimeBtn.setOnClickListener {
            showTimePicker()
        }

        notificationSwitch = view.findViewById<Switch>(R.id.notification_switch)
        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            is_alerted = isChecked
        }

        val listItems = listOf(" Monday ", "Tuesday","Wednesday", "Thursday","Friday","Saturday","Sunday")

        // 下拉式選單
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listItems) as SpinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
            }
        }
        return view
    }

    private fun showTimePicker() {
        Log.d("NotificationFragment", "showTimePicker() called")
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                showConfirmationDialog(selectedTime)
            },
            hour, // 设置初始小时
            minute, // 设置初始分钟
            true
        )
        timePickerDialog.show()
    }

    private fun showConfirmationDialog(selectedTime: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("確認所選時間")
        builder.setMessage("您選擇的時間為：$selectedTime，確定嗎？")

        builder.setPositiveButton("確定") { _, _ ->
            // 設定選擇的時間到按鈕上，然後關閉時間選擇器
            addNotificationTimeBtn.text = selectedTime
        }

        builder.setNegativeButton("取消") { _, _ ->
            // 使用者選擇取消，不執行任何操作
        }

        builder.show()
    }

    private fun checkAndRequestNotificationPermission() {
        if (!NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
            showNotificationPermissionDialog()
        } else {

            //updateSetting(theme, animCharName, isAlerted, alertDay, alertTime)
        }
    }

    private fun showNotificationPermissionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("請授予通知權限")
        builder.setMessage("您需要授予通知權限才能收到我們的通知。")

        builder.setPositiveButton("去設定") { _, _ ->
            // 前往應用程式設定畫面，讓使用者可以授予通知權限
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireContext().packageName, null)
            intent.data = uri
            startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE)
        }

        builder.setNegativeButton("取消") { _, _ ->
            var isAlerted = false
        }

        builder.show()
    }
    private fun updateSetting(theme: String, animCharName: String, isAlerted: Boolean, alertDay: String, alertTime: String) {
        val userId = currectUser?.id

        val settingData = RetrofitAPI.SettingData(theme, animCharName, isAlerted, alertDay, alertTime)

        userId?.let {
            retrofitAPI.UpdateSetting(it, settingData).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // 更新成功
                    } else {
                        // 更新失敗
                        showToast("設置更新失敗: ${response.message()}", requireContext())
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // 處理失敗
                    showToast("设置更新失敗: ${t.message}", requireContext())
                    t.printStackTrace()
                }
            })
        }
    }
    private fun showToast(message: String, requireContext: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}