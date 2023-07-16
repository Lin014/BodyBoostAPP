package com.example.bodyboost
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var add_btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val listItems = listOf("滷肉飯 200 kcal","珍珠奶茶 200 kcal","珍珠奶茶 200 kcal","珍珠奶茶 200 kcal")

        //下拉式選單
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,listItems) as SpinnerAdapter
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
            }
        }
            add_btn = view.findViewById<Button>(R.id.add_btn)
            add_btn.setOnClickListener{
                //Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
            }
        return view
    }
}