package com.example.aqqhome.ui.ActivityDichvu

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.aqqhome.R
import com.example.aqqhome.model.costsmodel
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class QuanlyActivity : AppCompatActivity() {
    private lateinit var phiquanlyused: String
    private lateinit var switch1: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quanly2)
        val intentt = intent
        val phiquanly = intentt.getStringExtra("phiquanly")
        phiquanlyused = intentt.getStringExtra("phiquanlyused").toString()
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        val formattedMoney = formatter.format(phiquanly?.toInt() ?: 0)


        val fee = findViewById<TextView>(R.id.fee)

        fee.text = formattedMoney


        val suachiphi = findViewById<TextView>(R.id.suachiphi)
        val trangthai = findViewById<LinearLayout>(R.id.trangthai)
        val Type: String = MyPref.get(this, "UserInfo", "Type").toString()
        if (Type == "1") {
            val suachiphi = findViewById<TextView>(R.id.suachiphi)
            suachiphi.visibility = View.VISIBLE
            trangthai.visibility = View.VISIBLE
        } else {
            trangthai.visibility = View.GONE
            suachiphi.visibility = View.GONE
        }
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            finish()
        }
        val content = "Sửa chi phí"
        val spannableString = SpannableString(content)
        spannableString.setSpan(UnderlineSpan(), 0, content.length, 0)
        suachiphi.text = spannableString
        val namefee = findViewById<TextView>(R.id.namefee)
        intent = Intent(this, CapNhapDichVuActivity::class.java)
        intent.putExtra("dichvu", namefee.text.toString())
        suachiphi.setOnClickListener {
            startActivity(intent)
        }


        switch1 = findViewById(R.id.switch1)
        if (phiquanlyused == "1") {
            switch1.setImageResource(R.drawable.switch_on)
        } else {
            switch1.setImageResource(R.drawable.switch_off)
        }


        switch1.setOnClickListener {
            if (phiquanlyused == "1") {
                AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Bạn có chắc muốn tắt dịch vụ này?")
                    .setPositiveButton("Có") { dialog, which ->
                        switch1.setImageResource(R.drawable.switch_off)
                        phiquanlyused = "0"
                        OnOff(phiquanlyused)
                    }
                    .setNegativeButton("Không") { dialog, which ->
                        switch1.setImageResource(R.drawable.switch_on)
                        phiquanlyused = "1"
                    }
                    .show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Bạn có chắc muốn bật dịch vụ này?")
                    .setPositiveButton("Có") { dialog, which ->
                        switch1.setImageResource(R.drawable.switch_on)
                        phiquanlyused = "1"
                        OnOff(phiquanlyused)
                    }
                    .setNegativeButton("Không") { dialog, which ->
                        switch1.setImageResource(R.drawable.switch_off)
                        phiquanlyused = "0"

                    }
                    .show()

            }
        }
    }

    private fun OnOff(phiquanlyused: String) {
        val ApiAQQHome = RetrofitClient.instance
        var data: Map<String, String> = mapOf(
            "IsManagerServiceUsed" to phiquanlyused,
            "ApartmentID" to MyPref.get(this, "UserInfo", "Manager").toString()
        )
        ApiAQQHome.trangthai(data)
            .enqueue(object : retrofit2.Callback<costsmodel> {
                override fun onResponse(call: Call<costsmodel>, response: Response<costsmodel>) {
                    Toast.makeText(this@QuanlyActivity, "Cập nhập thành công", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<costsmodel>, t: Throwable) {
                    Toast.makeText(this@QuanlyActivity, "Thất bại" + t.message, Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

}