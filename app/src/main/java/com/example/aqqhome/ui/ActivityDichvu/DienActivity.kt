package com.example.aqqhome.ui.ActivityDichvu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.aqqhome.R
import com.example.aqqhome.utils.MyPref

class DienActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dien)
        val suachiphi = findViewById<TextView>(R.id.suachiphi)
        val trangthai = findViewById<LinearLayout>(R.id.trangthai)
        val Type:String = MyPref.get(this,"UserInfo","Type").toString()
        if (Type == "1"){
            val suachiphi = findViewById<TextView>(R.id.suachiphi)
            suachiphi.visibility = View.VISIBLE
            trangthai.visibility = View.VISIBLE
        }else{
            trangthai.visibility = View.GONE
            suachiphi.visibility = View.GONE
        }
        val back = findViewById<ImageView>(R.id.back)
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
        back.setOnClickListener {
            finish()
        }
    }
}