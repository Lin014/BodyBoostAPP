package com.example.bodyboost.sport

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bodyboost.R

class SportFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private lateinit var sportFrameLayout: FrameLayout
    private lateinit var searchBarEditText: EditText
    private lateinit var sportCancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sports, container, false)

//        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        sportFrameLayout = view.findViewById(R.id.sportFrameLayout)
        searchBarEditText = view.findViewById(R.id.searchBarEditText)
        sportCancelButton = view.findViewById(R.id.sportCancelButton)
        //val adapter = YourRecyclerViewAdapter(dataList) // dataList 數據列表
        //recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val sportInflater:LayoutInflater  = layoutInflater
        val bottomView1: View = sportInflater.inflate(R.layout.sport_bottom_view_1, null)
        sportFrameLayout.addView(bottomView1)

        searchBarEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 当 EditText 获得焦点时执行的操作
                changeFrameLayout()
            } else {
                // 当 EditText 失去焦点时执行的操作
            }
        }

        searchBarEditText.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 在文本变化时执行的操作
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本改变之后执行的操作
            }
        })
        sportCancelButton.setOnClickListener{ goneSportCancelButton() }

        return view
    }

    public fun setSearchBarEditTextClearFocus() {
        searchBarEditText.clearFocus()
    }

    private fun changeFrameLayout() {
        val sportInflater:LayoutInflater  = layoutInflater
        val bottomView2: View = sportInflater.inflate(R.layout.sport_bottom_view_2, null)

        sportCancelButton.visibility = View.VISIBLE
        sportFrameLayout.removeAllViews()
        sportFrameLayout.addView(bottomView2)
    }

    private fun goneSportCancelButton() {
        sportCancelButton.visibility = View.GONE

        val sportInflater:LayoutInflater  = layoutInflater
        val bottomView1: View = sportInflater.inflate(R.layout.sport_bottom_view_1, null);
        sportFrameLayout.removeAllViews()
        sportFrameLayout.addView(bottomView1)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShortsFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): SportFragment {
            val fragment = SportFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}