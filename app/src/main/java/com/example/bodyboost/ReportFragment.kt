package com.example.bodyboost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class ReportFragment : Fragment() {

    private lateinit var weightChart: LineChart
    private lateinit var calorieChart: LineChart
    private lateinit var editTextWeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    val rootView = inflater.inflate(R.layout.fragment_report, container, false)

        weightChart = rootView.findViewById(R.id.lineChart_weight)
        calorieChart = rootView.findViewById(R.id.lineChart_calorie)
        editTextWeight = rootView.findViewById(R.id.editText_weight)

        //在沒有資料時設置空白圖表
        if (DataIsEmpty(weightChart)) {
            SetNoDataChart(weightChart)
        } else {
            //初始化圖表
            SetUpLineChart()
        }

        if (DataIsEmpty(calorieChart)) {
            SetNoDataChart(calorieChart)
        } else {
            //初始化圖表
            SetUpLineChart()
        }

        // Inflate the layout for this fragment
        return rootView
    }

    fun SetUpLineChart() {
        val weights = mutableListOf<Entry>()
        weights.add(Entry(0f, 55f)) //體重資料
        weights.add(Entry(1f, 54f))
        weights.add(Entry(2f, 56f))
        weights.add(Entry(3f, 53f))
        weights.add(Entry(4f, 52f))
        weights.add(Entry(5f, 54f))

        val dataSet = LineDataSet(weights, "體重(kg)")
        dataSet.color = R.color.Lavender //線的顏色
        dataSet.setCircleColor(R.color.Lavender) //點的顏色
        dataSet.setDrawCircleHole(true) //圓點為實心空心(實心-false，空心-true)
        dataSet.lineWidth = 1.2f //線寬
        dataSet.setDrawValues(true) //顯示座標點對應 Y軸的數字(預設顯示)
        dataSet.valueTextSize = 9f //座標文字大小
        dataSet.valueTextColor = R.color.TextColor
        weightChart.legend.isEnabled = false //不顯示圖例(預設顯示)
        weightChart.description.isEnabled = false //不顯示 Description Label (預設顯示)
        weightChart.data = LineData(dataSet)

        //X軸相關設定
        val xAxis = weightChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM //X軸顯示位置(預設為上方，分為上方內/外側、下方內/外側及上下同時顯示)
        xAxis.textColor = R.color.TextColor //X軸標籤顏色
        xAxis.textSize = 11f //標籤文字大小
        xAxis.labelCount = weights.size //X軸標籤個數
        xAxis.spaceMin = 0.5f //折線起點距離左側Y軸距離
        xAxis.spaceMax = 0.5f //折線終點距離右側Y軸距離
        xAxis.setDrawGridLines(false) //不顯示每個座標點對應X軸的線(預設顯示)

        //Y軸相關設定
        val rightAxis = weightChart.axisRight
        val leftAxis = weightChart.axisLeft
        rightAxis.isEnabled = false            //不顯示右側Y軸
        leftAxis.textColor = R.color.TextColor //Y軸標籤顏色
        leftAxis.textSize = 11f                //標籤文字大小

        //設定只有X軸可以滑動
        weightChart.isDragEnabled = true
        weightChart.setScaleEnabled(false)  //禁用縮放功能，以確保只有滑動有效
        weightChart.setPinchZoom(false)     //禁用縮放手勢
        weightChart.isScaleXEnabled = false //禁用X軸的縮放功能
        weightChart.isScaleYEnabled = false //禁用Y軸的縮放功能

        //折線圖顯示
        weightChart.invalidate()
    }

    private fun DataIsEmpty(lineChart: LineChart): Boolean {
        //在這裡檢查折線圖資料是否為空

        return true
    }

    private fun SetNoDataChart(lineChart: LineChart) {
        //在沒有資料時的空圖表

        //空圖表的資料
        val entries = mutableListOf<Entry>()
        entries.add(Entry(0f, 0f))

        val dataSet = LineDataSet(entries, "空白資料")
        dataSet.color = ColorTemplate.COLORFUL_COLORS[0] //線的顏色
        dataSet.setDrawCircles(false)                    //不顯示折線圖的圓圈
        dataSet.setDrawIcons(false)                      //不顯示圖標
        dataSet.setDrawValues(false)                     //不顯示折線圖的值
        dataSet.setDrawHighlightIndicators(false)        //不顯示強調部分

        //X軸設定
        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM //X軸標籤位置(預設為上方，分為上方內/外側、下方內/外側及上下同時顯示)
        xAxis.textColor = R.color.TextColor         //X軸標籤顏色
        xAxis.textSize = 11f                        //標籤文字大小
        xAxis.setDrawGridLines(false)               //不顯示每個座標點對應X軸的線(預設顯示)
        xAxis.axisMinimum = 0f                      //X軸最小值

        //Y軸設定
        val yAxisLeft: YAxis = lineChart.axisLeft
        val yAxisRight = lineChart.axisRight
        yAxisLeft.setDrawAxisLine(false)        //隱藏左側軸線
        yAxisRight.setDrawAxisLine(false)       //隱藏右側軸線
        yAxisRight.setDrawLabels(false)         //隱藏右側標籤
        yAxisLeft.textColor = R.color.TextColor //Y軸標籤顏色
        yAxisLeft.textSize = 11f                //標籤文字大小
        yAxisLeft.axisMinimum = 0f              //Y軸最小值

        //其他設定
        lineChart.isScaleXEnabled = false        //禁用X軸縮放功能
        lineChart.isScaleYEnabled = false        //禁用Y軸縮放功能
        lineChart.legend.isEnabled = false       //隱藏圖例
        lineChart.description.isEnabled = false  //隱藏簡述

        //折線圖資料
        lineChart.data = LineData(dataSet)

        //刷新折線圖
        lineChart.invalidate()
    }
}