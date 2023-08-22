package com.example.bodyboost

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodTypeFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var textView: TextView
    private lateinit var foodList: FloatingActionButton
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_food_type, container, false)

        //findViewById
        spinner = rootView.findViewById(R.id.spinner_type)
        textView = rootView.findViewById(R.id.textview)
        foodList = rootView.findViewById(R.id.button_foodList)
        button = rootView.findViewById(R.id.button_food)

        val items = listOf("五穀澱粉類", "蛋肉魚類", "蔬菜類", "水果類", "乳品類", "豆類",
            "飲料類", "酒類", "油脂與堅果類", "零食點心", "速食類", "調味品", "菜餚類", "其他類別", "自訂")
        val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val option = items[position]
                //Toast.makeText(context, option, Toast.LENGTH_SHORT).show()
                textView.text = option
                // api test
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //setOnClickListener
        foodList.setOnClickListener {
            foodList()
        }

        button.setOnClickListener {
            foodInformation()
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun foodInformation() {
        val foodInfoFragment = FoodInfoFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.fragment_container, foodInfoFragment)
        fragmentTransaction.commit()
    }

    private fun foodList() {
        val foodListFragment = FoodListFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.fragment_container, foodListFragment)
        fragmentTransaction.commit()
    }
}