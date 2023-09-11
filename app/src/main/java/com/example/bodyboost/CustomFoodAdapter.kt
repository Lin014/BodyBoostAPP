package com.example.bodyboost

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bodyboost.Model.CustomFood

class CustomFoodAdapter(private val context: Context, private val customFoodList: List<CustomFood>) : BaseAdapter() {
    override fun getCount(): Int = customFoodList.size

    override fun getItem(position: Int): Any = customFoodList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        val listName = view.findViewById<TextView>(R.id.listName)
        val listCalorie = view.findViewById<TextView>(R.id.listCalories)
        listName.text = customFoodList[position].name
        listCalorie.text = customFoodList[position].calorie.toString()
        return view
    }
}