package com.example.bodyboost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class UpgradeFragment : Fragment() {
    private lateinit var month_upgrade_btn: Button
    private lateinit var year_upgrade_btn: Button
    private val currentUser = UserSingleton.user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upgrade, container, false)
        month_upgrade_btn = view.findViewById<Button>(R.id.month)
        month_upgrade_btn.setOnClickListener {

        }
        year_upgrade_btn = view.findViewById<Button>(R.id.year)
        return view
    }

}