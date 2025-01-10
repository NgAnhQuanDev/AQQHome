package com.example.aqqhome.ui.admin

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.ThongKeAdapter
import com.example.aqqhome.model.Thongkemodel
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import com.google.android.material.button.MaterialButton
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Response
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale


class ThongKeActivity : AppCompatActivity() {
    private lateinit var tvchuathanhtoan: TextView
    private lateinit var tongtienno: TextView
    private lateinit var tongchuathanhtoan2 :TextView

    private lateinit var pieChart: PieChart
    private lateinit var fund: TextView
    private var tongdathanhtoan :Float = 0.0f
    private lateinit  var tongtienchuathanhtoan :TextView
    private var tongchuathanhtoan: Float = 0.0f
    private lateinit var tvR: TextView
    private lateinit var doanhthuthang: TextView
    private lateinit var tonghoadon: TextView
    private lateinit var tonghoadondathanhtoan: TextView
    private lateinit var ApartmentID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_ke)
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            finish()
        }
        fund = findViewById(R.id.fund)
        doanhthuthang = findViewById(R.id.doanhthuthang)
        tonghoadon = findViewById(R.id.tonghoadon)
        tonghoadondathanhtoan = findViewById(R.id.tonghoadondathanhtoan)
        tvR = findViewById(R.id.tvR)
        tvchuathanhtoan = findViewById(R.id.tvchuathanhtoan)
        tongchuathanhtoan2 = findViewById(R.id.tongchuathanhtoan2)
        tongtienchuathanhtoan = findViewById(R.id.tongtienchuathanhtoan)
        tongtienno = findViewById(R.id.tongtienno)

        ApartmentID = MyPref.get(this,"UserInfo","Manager").toString()

        val textViewDate: TextView = findViewById(R.id.textViewDate)
        val loc: ImageView = findViewById(R.id.loc)
        val buttonFilter: MaterialButton = findViewById(R.id.buttonFilter)
        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var selectedMonth = calendar.get(Calendar.MONTH)
        val homnay: TextView = findViewById(R.id.homnay)

        loc.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                selectedMonth = month
                textViewDate.visibility = TextView.VISIBLE
                homnay.visibility = TextView.GONE
                textViewDate.text = "${dayOfMonth}/${month + 1}/$year"
            }, calendar.get(Calendar.YEAR), selectedMonth, calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }
        buttonFilter.setOnClickListener {
            val monthToShow = selectedMonth + 1
            textViewDate.text = "Tháng đã chọn: $monthToShow"
            val Month:String = monthToShow.toString()
            get(Month)
        }
        val ApiAQQHome = RetrofitClient.instance
        val Month ="12"
        ApiAQQHome.thongke(ApartmentID,Month).enqueue(object : retrofit2.Callback<Thongkemodel> {
            override fun onResponse(call: Call<Thongkemodel>, response: Response<Thongkemodel>) {
                val thongke = response.body()
                if (thongke != null) {
                    val money: String = thongke.fund
                    val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                    val formattedMoney = formatter.format(money.toDouble())
                    fund.text = formattedMoney
                    val doanhthu: String = thongke.tongSoTienDaThanhToan ?: "0"
                    val formatter1 = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                    val formattedMoney1 = formatter1.format(doanhthu.toDouble())
                    val tongtienno1: String = thongke.tongTienNo ?: "0"
                    val formatter3 = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                    val formattedMoney3 = formatter3.format(tongtienno1.toDouble())
                    tongtienno.text = formattedMoney3

                    val tongtienchuathanhtoan1: String = thongke.tongSoTienChuaThanhToan
                    val formatter2 = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                    val formattedMoney2 = formatter2.format(tongtienchuathanhtoan1.toDouble())
                    tongtienchuathanhtoan.text = formattedMoney2

                    doanhthuthang.text = formattedMoney1
                    tonghoadon.text = thongke.tongSoHoaDon
                    tonghoadondathanhtoan.text = thongke.tongSoHoaDonThanhToan

                    tongdathanhtoan = thongke.tongSoHoaDonThanhToan.toFloat()
                    tvR.text = thongke.tongSoHoaDonThanhToan
                    if(thongke.tongSoHoaDonThanhToan != null || thongke.tongSoHoaDonThanhToan != null) {
                        val chuathanhtoan =
                            thongke.tongSoHoaDon.toInt() - thongke.tongSoHoaDonThanhToan.toInt()

                        tvchuathanhtoan.text = chuathanhtoan.toString()
                        tongchuathanhtoan2.text = (chuathanhtoan.toString()+" hóa đơn chưa thanh toán")
                        tongchuathanhtoan = chuathanhtoan.toFloat()

                    }
                    pieChart = findViewById(R.id.piechart)

                    pieChart.addPieSlice(PieModel("Đã thanh toán", tongdathanhtoan, Color.parseColor("#66BB6A")))
                    pieChart.addPieSlice(PieModel("Chưa thanh toán", tongchuathanhtoan, Color.parseColor("#EF5350")))



                    pieChart.startAnimation()
                    val recyclerview = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerview)
                    var dulieu = thongke.data
                    recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@ThongKeActivity)
                    val adapter =
                        ThongKeAdapter(dulieu)
                    recyclerview.adapter = adapter



                }
            }

            override fun onFailure(call: Call<Thongkemodel>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun get(Month:String){
        val ApiAQQHome = RetrofitClient.instance
        ApiAQQHome.thongke(ApartmentID, Month).enqueue(object : retrofit2.Callback<Thongkemodel> {
            override fun onResponse(call: Call<Thongkemodel>, response: Response<Thongkemodel>) {
                val thongke = response.body()
                if (thongke != null) {
                    val money: String = thongke.fund
                    val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                    val formattedMoney = formatter.format(money.toDouble())
                    fund.text = formattedMoney
                    val doanhthu: String = thongke.tongSoTienDaThanhToan ?: "0"
                    val formatter1 = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                    val formattedMoney1 = formatter1.format(doanhthu.toDouble())
                    val tongtienno1: String = thongke.tongTienNo ?: "0"
                    val formatter3 = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                    val formattedMoney3 = formatter3.format(tongtienno1.toDouble())
                    tongtienno.text = formattedMoney3

                    val tongtienchuathanhtoan1: String = thongke.tongSoTienChuaThanhToan
                    val formatter2 = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                    val formattedMoney2 = formatter2.format(tongtienchuathanhtoan1.toDouble())
                    tongtienchuathanhtoan.text = formattedMoney2

                    doanhthuthang.text = formattedMoney1
                    tonghoadon.text = thongke.tongSoHoaDon
                    tonghoadondathanhtoan.text = thongke.tongSoHoaDonThanhToan

                    tongdathanhtoan = thongke.tongSoHoaDonThanhToan.toFloat()
                    tvR.text = thongke.tongSoHoaDonThanhToan
                    if(thongke.tongSoHoaDonThanhToan != null || thongke.tongSoHoaDonThanhToan != null) {
                        val chuathanhtoan =
                            thongke.tongSoHoaDon.toInt() - thongke.tongSoHoaDonThanhToan.toInt()

                        tvchuathanhtoan.text = chuathanhtoan.toString()
                        tongchuathanhtoan2.text = (chuathanhtoan.toString()+" hóa đơn chưa thanh toán")
                        tongchuathanhtoan = chuathanhtoan.toFloat()

                    }
                    pieChart = findViewById(R.id.piechart)

                    pieChart.addPieSlice(PieModel("Đã thanh toán", tongdathanhtoan, Color.parseColor("#66BB6A")))
                    pieChart.addPieSlice(PieModel("Chưa thanh toán", tongchuathanhtoan, Color.parseColor("#EF5350")))
                    pieChart.startAnimation()
                    val recyclerview = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerview)
                    var dulieu = thongke.data
                    if(dulieu == null){

                    }
                    recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@ThongKeActivity)
                    val adapter =
                        ThongKeAdapter(dulieu)
                    recyclerview.adapter = adapter
                }
            }
            override fun onFailure(call: Call<Thongkemodel>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


    }





}
