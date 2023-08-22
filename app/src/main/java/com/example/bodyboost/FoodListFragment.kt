package com.example.bodyboost

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.util.Calendar

class FoodListFragment : Fragment() {

    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var timeButton: ImageButton
    private lateinit var timeTextView: EditText

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
        timeTextView = rootView.findViewById(R.id.text_time)

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
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)
                val timeText = String.format("%02d:%02d", selectedHour, selectedMinute)
                Toast.makeText(requireContext(), "$timeText", Toast.LENGTH_SHORT)


            }, hour, minute, true).show()
    }
}