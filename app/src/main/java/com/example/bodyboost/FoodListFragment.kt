package com.example.bodyboost

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FoodListFragment : Fragment() {

    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var timeButton: Button
    private lateinit var continueButton: Button
    private lateinit var completeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_food_list, container, false)

        //findViewById
        timeButton = rootView.findViewById(R.id.button_time)
        continueButton = rootView.findViewById(R.id.button_continue)
        completeButton = rootView.findViewById(R.id.button_complete)

        //setOnClickListener
        timeButton.setOnClickListener {
            showTimePicker()
        }

        continueButton.setOnClickListener {
            Toast.makeText(this.context, "返回上一頁", Toast.LENGTH_SHORT)
        }

        completeButton.setOnClickListener {
            Toast.makeText(this.context, "輸入成功", Toast.LENGTH_SHORT)
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun showTimePicker() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog( requireContext(),
            TimePickerDialog.OnTimeSetListener { _, h, m ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.HOUR_OF_DAY, h)
                selectedCalendar.set(Calendar.MINUTE, m)
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                timeButton.text = timeFormat.format(selectedCalendar.time)

            }, hour, minute, true)
        timePickerDialog.show()
    }
}