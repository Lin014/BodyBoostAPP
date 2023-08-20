package com.example.bodyboost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class SearchFoodFragment : Fragment() {

    private lateinit var button1: Button
    private lateinit var button2: ImageButton
    private lateinit var button3: ImageButton
    private lateinit var button4: ImageButton
    private lateinit var button5: ImageButton
    private lateinit var button6: ImageButton
    private lateinit var button7: ImageButton
    private lateinit var button8: ImageButton
    private lateinit var button9: ImageButton
    private lateinit var button10: ImageButton
    private lateinit var button11: ImageButton
    private lateinit var button12: ImageButton
    private lateinit var button13: ImageButton
    private lateinit var button14: ImageButton
    private lateinit var button15: ImageButton
    private lateinit var searchView: SearchView

    private var selectNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search_food, container, false)

        // findViewById
        button1 = rootView.findViewById(R.id.button_custom)
        button2 = rootView.findViewById(R.id.button2)
        button3 = rootView.findViewById(R.id.button3)
        button4 = rootView.findViewById(R.id.button4)
        button5 = rootView.findViewById(R.id.button5)
        button6 = rootView.findViewById(R.id.button6)
        button7 = rootView.findViewById(R.id.button7)
        button8 = rootView.findViewById(R.id.button8)
        button9 = rootView.findViewById(R.id.button9)
        button10 = rootView.findViewById(R.id.button10)
        button11 = rootView.findViewById(R.id.button11)
        button12 = rootView.findViewById(R.id.button12)
        button13 = rootView.findViewById(R.id.button13)
        button14 = rootView.findViewById(R.id.button14)
        button15 = rootView.findViewById(R.id.button15)

        // setOnClickListener
        button1.setOnClickListener {
            selectNumber = 14
            foodType("自訂類別")
        }
        button2.setOnClickListener {
            selectNumber = 0
            foodType("五榖澱粉類")
        }
        button3.setOnClickListener {
            selectNumber = 1
            foodType("蛋肉魚類")
        }
        button4.setOnClickListener {
            selectNumber = 2
            foodType("蔬菜類")
        }
        button5.setOnClickListener {
            selectNumber = 3
            foodType("水果類")
        }
        button6.setOnClickListener {
            selectNumber = 4
            foodType("乳品類")
        }
        button7.setOnClickListener {
            selectNumber = 5
            foodType("豆類")
        }
        button8.setOnClickListener {
            selectNumber = 6
            foodType("飲料類")
        }
        button9.setOnClickListener {
            selectNumber = 7
            foodType("酒類")
        }
        button10.setOnClickListener {
            selectNumber = 8
            foodType("油脂與堅果類")
        }
        button11.setOnClickListener {
            selectNumber = 9
            foodType("零食點心")
        }
        button12.setOnClickListener {
            selectNumber = 10
            foodType("速食類")
        }
        button13.setOnClickListener {
            selectNumber = 11
            foodType("調味品")
        }
        button14.setOnClickListener {
            selectNumber = 12
            foodType("菜餚類")
        }
        button15.setOnClickListener {
            selectNumber = 13
            foodType("其他類別")
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun foodType(text: String) {
        //Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
        val foodTypeFragment = FoodTypeFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.fragment_container, foodTypeFragment)
        fragmentTransaction.commit()
    }
}