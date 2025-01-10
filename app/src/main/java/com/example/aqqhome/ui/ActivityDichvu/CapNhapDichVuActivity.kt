package com.example.aqqhome.ui.ActivityDichvu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.aqqhome.R
import com.example.aqqhome.model.costsmodel
import com.example.aqqhome.ui.admin.Admin_SuachiphiActivity
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Response

class CapNhapDichVuActivity : AppCompatActivity() {
    private lateinit var fee: TextInputEditText
    private lateinit var update: MaterialButton
    private lateinit var loaiphi: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cap_nhap_dich_vu)
        val back = findViewById<ImageView>(R.id.back)
        val namefee = findViewById<TextView>(R.id.namefee)
        fee = findViewById(R.id.fee)
        update = findViewById(R.id.update)
        namefee.text = intent.getStringExtra("dichvu")
        back.setOnClickListener {
            finish()
        }
        if(namefee.text.toString() == "Phí vệ sinh"){
            loaiphi = "GarbageFee"
        }
        else if(namefee.text.toString() == "Phí gửi xe"){
            loaiphi = "Guixe"
        }
        else if(namefee.text.toString() == "Phí quản lý"){
            loaiphi = "ManagementFee"
        }
        else if(namefee.text.toString() == "Phí khác"){
            loaiphi = "Khac"
        }
        update.setOnClickListener() {
            val ApiAQQHome= RetrofitClient.instance
            var data: Map<String,String> = mapOf(loaiphi to fee.text.toString(), "ApartmentID" to MyPref.get(this, "UserInfo", "Manager").toString())
            ApiAQQHome.trangthai(data).enqueue(object : retrofit2.Callback<costsmodel> {
                override fun onResponse(call: Call<costsmodel>, response: Response<costsmodel>) {
                    var ketqua = response.body()!!
                    if(ketqua.isSuccess){
                        Toast.makeText(this@CapNhapDichVuActivity, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                        intent = Intent(this@CapNhapDichVuActivity, Admin_SuachiphiActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@CapNhapDichVuActivity, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<costsmodel>, t: Throwable) {
                    Toast.makeText(this@CapNhapDichVuActivity, "Lỗi hệ thống", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}