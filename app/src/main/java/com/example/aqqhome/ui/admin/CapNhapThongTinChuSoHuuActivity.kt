package com.example.aqqhome.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.aqqhome.R
import com.google.android.material.textfield.TextInputEditText

class CapNhapThongTinChuSoHuuActivity : AppCompatActivity() {
    private lateinit var name: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var ngaybangiao: TextInputEditText
    private lateinit var cccd: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cap_nhap_thong_tin_chu_so_huu)
        val back = findViewById<ImageView>(R.id.back)
//        intent.putExtra("id", id)
//        intent.putExtra("name", name)
//        intent.putExtra("phone", sodienthoai1)
//        intent.putExtra("ngaybangiao", ngaybangiao1)
        var id = intent.getStringExtra("id")
        var namee = intent.getStringExtra("name")
        var phonee = intent.getStringExtra("phone")
        var ngaybangiaoo = intent.getStringExtra("ngaybangiao")
        var cancuoc = intent.getStringExtra("cancuoc")
        name = findViewById(R.id.name)
        phone = findViewById(R.id.phone)
        ngaybangiao = findViewById(R.id.ngaybangiao)
        cccd = findViewById(R.id.cccd)
        name.setText(namee)
        phone.setText(phonee)
        ngaybangiao.setText(ngaybangiaoo)
        cccd.setText(cancuoc)




        back.setOnClickListener {
            finish()
        }
    }
}