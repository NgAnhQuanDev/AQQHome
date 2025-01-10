package com.example.aqqhome.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.aqqhome.R

class CapNhapThongTinQuanLyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cap_nhap_thong_tin_quan_ly)
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
}