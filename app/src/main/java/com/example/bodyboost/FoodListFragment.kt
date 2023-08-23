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

        //setOnClickListener
        timeButton.setOnClickListener {
            showTimePicker()
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun showTimePicker() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog( requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                timeButton.text = timeFormat.format(calendar.time)
                Toast.makeText(this.context, "$hour:$minute", Toast.LENGTH_SHORT)

            }, hour, minute, true)
        timePickerDialog.show()
    }
}