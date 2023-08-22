package com.example.bodyboost

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class FoodInfoFragment : Fragment() {

    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_food_info, container, false)

        // findViewById
        addButton = rootView.findViewById(R.id.addButton)

        addButton.setOnClickListener {
            Toast.makeText(this.context, "新增成功", Toast.LENGTH_SHORT).show()
        }

        // Inflate the layout for this fragment
        return rootView
    }
}