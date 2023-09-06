package com.example.bodyboost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bodyboost.Model.Profile

class AchievementFragment : Fragment(){
    private val retrofitAPI = RetrofitManager.getInstance()
    private lateinit var goal_08_txt: TextView
    private lateinit var goal_08_img:ImageView
    private lateinit var goal_09_txt: TextView
    private lateinit var goal_09_img:ImageView
    private lateinit var profile: Profile
    private val currentUser = UserSingleton.user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_achievement, container, false)
            goal_08_img = view.findViewById(R.id.goal_08_img)
            goal_08_txt = view.findViewById(R.id.goal_08_txt)
            goal_09_img = view.findViewById(R.id.goal_09_img)
            goal_09_txt = view.findViewById(R.id.goal_09_txt)
            achieve08()
            achieve09()
        return view
    }
    private fun achieve08(){
        if(profile.weight_goal!=null){
            currentUser?.let { retrofitAPI.getweightachievement(it.id) }
            goal_08_txt.text = "瘦身達人"
            goal_08_img.setImageResource(R.drawable.goal_08)
        }
    }

    private fun achieve09(){
        if(profile.weight_goal!=null){
            currentUser?.let { retrofitAPI.getweightachievement(it.id) }
            goal_09_txt.text = "身材改造師"
            goal_09_img.setImageResource(R.drawable.goal_09)
        }
    }
}