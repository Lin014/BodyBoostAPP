package com.example.bodyboost

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {

    private lateinit var weightChart: LineChart
    private lateinit var calorieChart: LineChart
    private lateinit var editTextWeight: EditText
    private lateinit var addWeightButton: Button

    private val weightData = mutableListOf<Entry>()
    private val testData = mutableListOf<Entry>()
    private val weights = mutableListOf<Entry>()

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
        addWeightButton = rootView.findViewById(R.id.button1)

        weights.add(Entry(0f, 55f))
        weights.add(Entry(1f, 54f))
        weights.add(Entry(2f, 55.5f))
        weights.add(Entry(3f, 56f))
        weights.add(Entry(4f, 55f))

        //在沒有資料時設置空白圖表
        if (weightData.isEmpty()) {
            SetEmptyChart(weightChart)
        } else {
            SetLineChart(weightChart)
        }

        if (true) {
            SetEmptyChart(calorieChart)
        } else {
            SetLineChart(calorieChart)
        }

        addWeightButton.setOnClickListener {
            InputWeight()
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun SetWeightChart(weights: List<Entry>) {
        UpdateLineChart(weights, weightChart)
    }
    private fun SetCaloriesChart(calories: List<Entry>) {
        UpdateLineChart(calories, calorieChart)
    }

    fun SetLineChart(lineChart: LineChart) {
        lineChart.apply {
            setDrawGridBackground(false)  //不顯示圖表網格背景
            setDrawBorders(false)         //不顯示圖表邊框
            description.isEnabled = false //不顯示 Description Label (預設顯示)
            legend.isEnabled = false      //不顯示圖例(預設顯示)
            isDragEnabled = true          //設定只有X軸可以滑動
            setScaleEnabled(false)        //禁用縮放功能，以確保只有滑動有效
            setPinchZoom(false)           //禁用縮放手勢
            isScaleXEnabled = false       //禁用X軸的縮放功能
            isScaleYEnabled = false       //禁用Y軸的縮放功能

            //X軸設定
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM     //X軸顯示位置(預設為上方，分為上方內/外側、下方內/外側及上下同時顯示)
                textColor = R.color.TextColor             //X軸標籤顏色
                setDrawLabels(true)                       //顯示X軸標籤
                textSize = 11f                            //標籤文字大小
                labelCount = 7                            //標籤數量
                valueFormatter = DateAxisValueFormatter() //X軸標籤設定
                setDrawGridLines(false)                   //不顯示格線
                spaceMin = 20f
                spaceMax = 20f
                //granularity = (24 * 60 * 60 * 1000).toFloat() //設置間隔為一天(的秒數)
            }

            //Y軸設定
            axisLeft.apply {
                textColor = R.color.TextColor //Y軸標籤顏色
                textSize = 11f                //標籤文字大小
                valueFormatter = IntegerYAxisValueFormatter()
                granularity = 1f              //標籤間隔
            }

            axisRight.apply {
                isEnabled = false //不顯示右側Y軸
            }
        }
    }

    private fun UpdateLineChart(dataList: List<Entry>, lineChart: LineChart) {
        val dataSet = LineDataSet(dataList, "資料")

        dataSet.color = requireContext().getColor(R.color.Lavender)         //線的顏色
        dataSet.setCircleColor(requireContext().getColor(R.color.Lavender)) //點的顏色
        dataSet.setDrawCircleHole(true)                                     //圓點為實心空心(實心-false，空心-true)
        dataSet.lineWidth = 1.2f                                            //線寬
        dataSet.setDrawValues(false)                                        //顯示座標點對應 Y軸的數字(預設顯示)
        dataSet.valueTextSize = 9f                                          //數值文字大小
        dataSet.valueTextColor = R.color.TextColor                          //文字顏色

        lineChart.data = LineData(dataSet)
        lineChart.invalidate()
    }

    private fun SetEmptyChart(lineChart: LineChart) {
        //沒有資料的空圖表
        //空圖表資料
        val entries = mutableListOf<Entry>()
        entries.add(Entry(System.currentTimeMillis().toFloat(), 0f))

        lineChart.apply {
            isScaleXEnabled = false        //禁用X軸縮放功能
            isScaleYEnabled = false        //禁用Y軸縮放功能
            legend.isEnabled = false       //隱藏圖例
            description.isEnabled = false  //隱藏簡述

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM //X軸標籤位置(預設為上方，分為上方內/外側、下方內/外側及上下同時顯示)
                textColor = R.color.TextColor         //X軸標籤顏色
                textSize = 11f                        //標籤文字大小
                setDrawGridLines(false)               //不顯示每個座標點對應X軸的線(預設顯示)
                axisMinimum = 0f                      //X軸最小值
                //granularity = 1f
                labelCount = 7
                valueFormatter = DateAxisValueFormatter()
            }

            axisLeft.apply {
                setDrawAxisLine(true)        //隱藏格線
                textColor = R.color.TextColor //Y軸標籤顏色
                textSize = 11f                //標籤文字大小
                axisMinimum = 0f              //Y軸最小值
                granularity = 1f
                labelCount = 5
                valueFormatter = EmptyYAxisValueFormatter()
            }

            axisRight.apply {
                isEnabled = false       //隱藏右側軸線
                setDrawAxisLine(false)
            }
        }

        val dataSet = LineDataSet(entries, "空資料")
        //dataSet.color = ColorTemplate.COLORFUL_COLORS[0] //線的顏色
        dataSet.setDrawCircles(false)                    //不顯示折線圖的圓圈
        dataSet.setDrawIcons(false)                      //不顯示圖標
        dataSet.setDrawValues(false)                     //不顯示折線圖的值
        dataSet.setDrawHighlightIndicators(false)        //不顯示強調部分

        //折線圖資料
        lineChart.data = LineData(dataSet)
        //刷新折線圖
        lineChart.invalidate()
    }

    private fun InputWeight() {
        val weightString = editTextWeight.text.toString()
        if (weightString.isNotEmpty()) {
            val weight = weightString.toFloat()
            ShowDatePicker(weight)
        }
    }

    private fun ShowDatePicker(weight: Float) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _: DatePicker, y: Int, m: Int, d: Int ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(y, m, d)
                val selectedDateInMillis = selectedCalendar.timeInMillis
                AddWeightEntry(weight, selectedDateInMillis)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun AddWeightEntry(weight: Float, dateInMillis: Long) {
        val entry = Entry(dateInMillis.toFloat(), weight)
        weightData.add(entry)
        UpdateLineChart(weightData, weightChart)
        weightChart.xAxis.valueFormatter = DateAxisValueFormatter()
        editTextWeight.text.clear()
    }

    inner class DateAxisValueFormatter : ValueFormatter() {
        private val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())

        override fun getFormattedValue(value: Float): String {
            val dateInMillis = value.toLong()
            return dateFormat.format(Date(dateInMillis))
        }
    }

    inner class IntegerYAxisValueFormatter : ValueFormatter() {
        //設定格式為整數
        private val decimalFormat = DecimalFormat("#")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return value.toInt().toString()
        }
    }

    inner class EmptyYAxisValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            // 指定要顯示的標籤值
            val labels = arrayOf("0", "10", "20", "30", "40", "50")

            // 四捨五入取最接近的整數值
            val index = (value + 0.5f).toInt()

            return if (index in 0 until 6) {
                labels[index]
            } else {
                "" // 不在指定的標籤範圍內，返回空字串
            }
        }
    }
}

