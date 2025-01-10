package com.example.aqqhome.ui.order

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aqqhome.R
import com.example.aqqhome.model.Hoadonmodel
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.ui.user.MainActivity
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.Locale

class ThanhToanXongActivity : AppCompatActivity() {
    private lateinit var sotien:TextView
    private lateinit var textttv2:TextView
    private lateinit var maphieuthu:TextView
    private lateinit var phuongthuc:TextView
    private lateinit var retrofit:Retrofit
    private lateinit var id:String
    private lateinit var back:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanh_toan_xong)
        sotien = findViewById(R.id.sotien)
        textttv2 = findViewById(R.id.textttv2)
        maphieuthu = findViewById(R.id.maphieuthu)
        phuongthuc = findViewById(R.id.phuongthuc)
        back = findViewById(R.id.back)
        var Type:String = MyPref.get(this,"UserInfo","Type").toString()
        back.setOnClickListener {
            if(Type.equals("1")){
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
        val intent = intent
        val id2 = intent.getStringExtra("FIRST_ELEMENT")
        val id = intent.getIntExtra("FIRST_ELEMENT", -1).toString()

        retrofit = Retrofit.Builder()
            .baseUrl(utils.url2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        val call = apiAQQHome.thongtinhoadon(id)
        call.enqueue(object : retrofit2.Callback<Hoadonmodel> {
            override fun onResponse(
                call: retrofit2.Call<Hoadonmodel>, response: retrofit2.Response<Hoadonmodel>) {
                val thongTinHoaDon = response.body()
                if(thongTinHoaDon?.success == true)
                textttv2.text = thongTinHoaDon?.ngaythanhtoan.toString()
                maphieuthu.setText("#" + thongTinHoaDon?.HoadonID.toString())
                if(thongTinHoaDon?.phuongthuc.equals("1")) phuongthuc.setText("Ví điện tử AQQPay")
                else phuongthuc.setText("Ví điện tử ZaloPay")
                val tongtien = intent.getStringExtra("TONGTIEN") ?: "0"
                if(tongtien.equals("0")){
                    var fm: String = thongTinHoaDon?.debt.toString()
                    val localeVN = Locale("vi", "VN")
                    fm = NumberFormat.getCurrencyInstance(localeVN).format(fm.toDouble())
                    sotien.setText(fm)
                }
                else{
                var fm: String = tongtien
                val localeVN = Locale("vi", "VN")
                fm = NumberFormat.getCurrencyInstance(localeVN).format(fm.toDouble())
                sotien.setText(fm)
                }
            }
            override fun onFailure(call: retrofit2.Call<Hoadonmodel>, t: Throwable) {
                println(t.message)
            }
        })
    }
}