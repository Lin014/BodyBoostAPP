package com.example.bodyboost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_food)

        // findViewById
        val custom: Button = findViewById(R.id.button_custom)
        val grains: ImageButton = findViewById(R.id.button2)
        val eggMeatFish: ImageButton = findViewById(R.id.button3)
        val vegetables: ImageButton = findViewById(R.id.button4)
        val fruits: ImageButton = findViewById(R.id.button5)
        val dairy: ImageButton = findViewById(R.id.button6)
        val beans: ImageButton = findViewById(R.id.button7)
        val beverages: ImageButton = findViewById(R.id.button8)
        val alcohol: ImageButton = findViewById(R.id.button9)
        val nuts: ImageButton = findViewById(R.id.button10)
        val snacks: ImageButton = findViewById(R.id.button11)
        val fastFood: ImageButton = findViewById(R.id.button12)
        val condiment: ImageButton = findViewById(R.id.button13)
        val dish: ImageButton = findViewById(R.id.button14)
        val others: ImageButton = findViewById(R.id.button15)
        val searchView: EditText = findViewById(R.id.search_food)
        val foodOptions: FloatingActionButton = findViewById(R.id.button_food_options)
        val back: Button = findViewById(R.id.back)

        // setOnClickListener
        custom.setOnClickListener {
            replaceFragment(FoodTypeFragment())
        }
        grains.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("五榖澱粉類")
        }
        eggMeatFish.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("蛋肉魚類")
        }
        vegetables.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("蔬菜類")
        }
        fruits.setOnClickListener {
            replaceFragment(FoodTypeFragment())
            replaceFragment(FoodTypeFragment())
//            goFoodType("水果類")
        }
        dairy.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("乳品類")
        }
        beans.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("豆類")
        }
        beverages.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("飲料類")
        }
        alcohol.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("酒類")
        }
        nuts.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("油脂與堅果類")
        }
        snacks.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("零食點心")
        }
        fastFood.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("速食類")
        }
        condiment.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("調味品")
        }
        dish.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("菜餚類")
        }
        others.setOnClickListener {
            replaceFragment(FoodTypeFragment())
//            goFoodType("其他類別")
        }
        foodOptions.setOnClickListener {
            val intent = Intent(this, FoodOptionsActivity::class.java)
            startActivity(intent)
        }
        back.setOnClickListener {
            finish()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }

//    private fun goFoodType(text: String) {
//        //Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
//        val foodTypeFragment = FoodTypeFragment()
//        val fragmentManager: FragmentManager = this.supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.replace(R.id.fragment_container, foodTypeFragment)
//        fragmentTransaction.commit()
//    }
}