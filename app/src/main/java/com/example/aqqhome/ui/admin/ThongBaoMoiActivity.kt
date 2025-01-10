package com.example.aqqhome.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.aqqhome.R
import com.example.aqqhome.model.ApiResponsee
import com.example.aqqhome.utils.MyPref

class ThongBaoMoiActivity : AppCompatActivity() {
    private lateinit var postButton: Button
    private lateinit var postEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_bao_moi)
        postButton = findViewById(R.id.postButton)
        postEditText = findViewById(R.id.postEditText)
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
        val apartmentID = MyPref.get(this, "UserInfo", "Manager")
        val ApiAQQHome = com.example.aqqhome.utils.RetrofitClient.instance
        postButton.setOnClickListener {
            val text = postEditText.text.toString().trim()
            ApiAQQHome.thongbao(apartmentID, text).enqueue(object : retrofit2.Callback<ApiResponsee> {
                override fun onFailure(call: retrofit2.Call<ApiResponsee>, t: Throwable) {
                    println("Error")
                }

                override fun onResponse(call: retrofit2.Call<ApiResponsee>, response: retrofit2.Response<ApiResponsee>) {
                    Toast.makeText(this@ThongBaoMoiActivity, "Đã gửi thông báo", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
        }


    }
}