package com.example.aqqhome.ui.order
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aqqhome.R

import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class AvatarActivity : AppCompatActivity() {

    private lateinit var tvR: TextView
    private lateinit var tvPython: TextView
    private lateinit var tvCPP: TextView
    private lateinit var tvJava: TextView
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)

        tvR = findViewById(R.id.tvR)
        tvPython = findViewById(R.id.tvPython)
        tvCPP = findViewById(R.id.tvCPP)
        tvJava = findViewById(R.id.tvJava)
        pieChart = findViewById(R.id.piechart)

        setData()
    }

    private fun setData() {
        tvPython.text = "30"
        tvCPP.text = "5"
        tvJava.text = "25"

        pieChart.addPieSlice(PieModel("Python", tvPython.text.toString().toInt().toFloat(), Color.parseColor("#66BB6A")))
        pieChart.addPieSlice(PieModel("C++", tvCPP.text.toString().toInt().toFloat(), Color.parseColor("#EF5350")))
        pieChart.addPieSlice(PieModel("Java", tvJava.text.toString().toInt().toFloat(), Color.parseColor("#29B6F6")))

        pieChart.startAnimation()
    }
}
