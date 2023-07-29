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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportFragment : Fragment() {

    private lateinit var weightChart: LineChart
    private lateinit var caloriesChart: LineChart
    private lateinit var nutrientChart: PieChart

    private lateinit var editTextWeight: EditText
    private lateinit var addWeightButton: Button

    // date and weight data list
    private val dateList = mutableListOf<String>()
    private val weightList = mutableListOf<Float>()
    // weight chart use data
    private val weightChartMaxAndMinList = mutableListOf<Float>()

    // calories data list
    private val caloriesList = mutableListOf<Int>()
    private val dateList2 = mutableListOf<String>()

    // nutrient data list
    private  val nutrientList = mutableListOf<PieEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_report, container, false)

        // findViewById
        weightChart = rootView.findViewById(R.id.lineChart_weight)
        caloriesChart = rootView.findViewById(R.id.lineChart_calorie)
        nutrientChart = rootView.findViewById(R.id.pieChart_nutrient)
        editTextWeight = rootView.findViewById(R.id.editText_weight)
        addWeightButton = rootView.findViewById(R.id.button1)

        // api write here
        // get dateList and weightList data: WeightHistory

        weightChartInit()
        caloriesChartInit()
        nutrientChartInit()

        addWeightButton.setOnClickListener {
            inputWeight()
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun weightChartInit() {
        // init weight chart
        weightChart.description.isEnabled = false
        weightChart.axisRight.isEnabled = false
        weightChart.setNoDataText("目前尚無資料")
        weightChart.setNoDataTextColor(Color.BLACK)
        weightChart.setDrawGridBackground(false)
        weightChart.setDrawBorders(false)
        weightChart.invalidate()
        // set weightChartMaxAndMinList
        for (i in 0..600 step 4) {
            weightChartMaxAndMinList.add(i.toFloat())
        }
        // updateData
        if (dateList.isNotEmpty()) {
            setWeightChart(findWeightChartMaxValue(weightList.max()), findWeightChartMinValue(weightList.min()))
        }
    }

    private fun findWeightChartMaxValue(weightListMaxValue: Float): Float {
        var max = 0f
        for (value in weightChartMaxAndMinList) {
            if (value > weightListMaxValue) {
                max = value.toFloat()
                break
            }
        }
        return max
    }

    private fun findWeightChartMinValue(weightListMinValue: Float): Float {
        var min = 0f
        for (value in weightChartMaxAndMinList.reversed()) {
            if (value < weightListMinValue) {
                min = value.toFloat()
                break
            }
        }
        return min
    }

    private fun setWeightChart(yMax: Float, yMin: Float) {
        // set X
        weightChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = IndexAxisValueFormatter(dateList)
            labelCount = dateList.size
            granularity = 1f
            setDrawGridLines(false)
        }
        // set Y
        weightChart.axisLeft.apply {
            axisMaximum = yMax // weight max value
            axisMinimum = yMin // weight min value
            labelCount = 5
        }
        // add Y data
        val yList = mutableListOf<Entry>()
        var index = 0f
        for (weight in weightList) {
            yList.add(Entry(index, weight))
            index += 1f
        }
        // set Y data
        val lineDataSet = LineDataSet(yList, "體重")
        lineDataSet.color = 0xFFE39D8F.toInt()
        lineDataSet.setDrawValues(false)
        lineDataSet.lineWidth = 1.5f
        lineDataSet.circleRadius = 4f
        lineDataSet.setCircleColor(0xFFE39D8F.toInt())
        val lineData = LineData(lineDataSet)

        // update weightChart data
        weightChart.data = lineData
        weightChart.setVisibleXRangeMaximum(4f)
        weightChart.invalidate()
    }

    private fun inputWeight() {
        val weightString = editTextWeight.text.toString()
        if (weightString.isNotEmpty()) {
            val weight = weightString.toFloat()
            showDatePicker(weight)
        }
    }

    private fun showDatePicker(weight: Float) {
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


                // 使用 SimpleDateFormat 將日期格式化為 'YYYY-MM-DD' 的字串
                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                val selectedDateFormatted = dateFormat.format(selectedCalendar.time)

                // add new date and weight data to weight list
                // get month and day
                val dateResult = selectedDateFormatted.substring(5)
                dateList.add(dateResult)
                weightList.add(weight)
                // update weightChart
                setWeightChart(findWeightChartMaxValue(weightList.max()), findWeightChartMinValue(weightList.min()))

                // api write here
                // update date and weight data: profile and WeightHistory
            },
            year,
            month,
            day
        )
        editTextWeight.text.clear()
        datePickerDialog.show()
    }

    private fun caloriesChartInit() {
        // init calories chart
        caloriesChart.description.isEnabled = false
        caloriesChart.axisRight.isEnabled = false
        caloriesChart.setNoDataText("目前尚無資料")
        caloriesChart.setNoDataTextColor(Color.BLACK)
        caloriesChart.setDrawGridBackground(false)
        caloriesChart.setDrawBorders(false)
        caloriesChart.invalidate()
        // set weightChartMaxAndMinList
        for (i in 0..600 step 4) {
            weightChartMaxAndMinList.add(i.toFloat())
        }
        // updateData
        if (dateList.isNotEmpty()) {
            setWeightChart(findWeightChartMaxValue(weightList.max()), findWeightChartMinValue(weightList.min()))
        }
    }

    private fun setCaloriesChart() {}

    private fun nutrientChartInit() {
        nutrientChart.apply {
            description.isEnabled = false
            setNoDataText("目前尚無資料")
            setNoDataTextColor(Color.BLACK)
            invalidate()
        }
//        //testData
//        carb * 4 = c //碳水化合物熱量
//        protein * 4 = p //蛋白質熱量
//        fat * 9 = f //脂肪熱量
//        calorie - c - p - f = e //其他熱量
        nutrientList.add(PieEntry(50f, "碳水化合物"))
        nutrientList.add(PieEntry(20f, "蛋白質"))
        nutrientList.add(PieEntry(20f, "脂肪"))
        nutrientList.add(PieEntry(10f, "其他"))
        // updateData
//        if (nutrientList.isNotEmpty()) {
            setNutrientChart()
//        }
    }

    private fun setNutrientChart() {
        // set nutrient
        // color
        val pieColors = ArrayList<Int>()
        pieColors.add(0xFFE39D8F.toInt())
        pieColors.add(0xFFF0A697.toInt())
        pieColors.add(0xFFFFC6BA.toInt())
        pieColors.add(0xFFE38A78.toInt())
        // set PieDataSet
        val pieDataSet = PieDataSet(nutrientList, "營養成分比例")
        pieDataSet.colors = pieColors
        pieDataSet.valueTextSize = 10f
        // set PieData
        val pieData = PieData(pieDataSet).apply {
            setDrawValues(true)
            setValueFormatter(PercentFormatter(nutrientChart))
            setValueTextColor(Color.BLACK)
        }
        // set attributes
        nutrientChart.apply {
            setUsePercentValues(true)
            setEntryLabelColor(Color.BLACK)
            setHoleColor(Color.TRANSPARENT)
            isDrawHoleEnabled = true
            holeRadius = 50f
            setDrawCenterText(false)
        }

        // update data
        nutrientChart.data = pieData
        nutrientChart.invalidate()
    }
}

