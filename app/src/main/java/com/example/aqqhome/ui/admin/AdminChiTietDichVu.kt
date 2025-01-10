package com.example.aqqhome.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.aqqhome.R

class AdminChiTietDichVu : AppCompatActivity() {
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_chi_tiet_dich_vu)
        back = findViewById(R.id.back)
        back.setOnClickListener(View.OnClickListener {
            finish()
        })

    }
}



