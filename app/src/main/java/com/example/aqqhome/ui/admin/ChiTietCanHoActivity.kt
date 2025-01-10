package com.example.aqqhome.ui.admin

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aqqhome.R

class ChiTietCanHoActivity : AppCompatActivity() {
    private lateinit var tensohuu: TextView
    private lateinit var sodienthoai: TextView
    private lateinit var socancuoc: TextView
    private lateinit var ngaybangiao: TextView
    private lateinit var mahopdong: TextView
    private lateinit var ngaydangky: TextView
    private lateinit var edit1: ImageView
    private lateinit var edit2: ImageView


    private lateinit var nameroom: TextView
    private lateinit var dientich: TextView
    private lateinit var code: TextView
    private lateinit var tang: TextView

    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chitietcanho)
        nameroom = findViewById(R.id.nameroom)
        dientich = findViewById(R.id.dientich)
        code = findViewById(R.id.code)
        tang = findViewById(R.id.tang)
        edit1 = findViewById(R.id.edit1)
        edit2 = findViewById(R.id.edit2)


        var id = intent.getStringExtra("id")
        var name = intent.getStringExtra("name")
        var area = intent.getStringExtra("area")
        var floor = intent.getStringExtra("floor")
        var codee = intent.getStringExtra("code")
        val chusohuu1 = intent.getStringExtra("chusohuu")
        val socancuoc1 = intent.getStringExtra("socancuoc")
        val sodienthoai1 = intent.getStringExtra("sodienthoai")
        val ngaybangiao1 = intent.getStringExtra("ngaybangiao")
        val mahopdong1 = intent.getStringExtra("mahopdong")
        val ngaydangky1 = intent.getStringExtra("ngaydangky")
        val parking1 = intent.getStringExtra("parking")

        if (id != null) {
            nameroom.setText(name)
            dientich.setText(area)
            tang.setText(floor)
            code.setText(codee)

        }else{nameroom.setText("-")
            dientich.setText("-")
            code.setText("-")
            tang.setText("-")}

        if(chusohuu1 != null) {
            tensohuu = findViewById(R.id.tensohuu)
            tensohuu.setText(chusohuu1)
        }else{tensohuu.setText("-")}
        if(socancuoc1 != null) {
            socancuoc = findViewById(R.id.socancuoc)
            socancuoc.setText(socancuoc1)
        }else{socancuoc.setText("-")}
        if(sodienthoai1 != null) {
            sodienthoai = findViewById(R.id.sodienthoai)
            sodienthoai.setText(sodienthoai1)
        }else{sodienthoai.setText("-")}
        if(ngaybangiao1 != null) {
            ngaybangiao = findViewById(R.id.ngaybangiao)
            ngaybangiao.setText(ngaybangiao1)
        }else{ngaybangiao.setText("-")}
        if(mahopdong1 != null) {
            mahopdong = findViewById(R.id.mahopdong)
            mahopdong.setText(mahopdong1)
        }else{mahopdong.setText("-")}
        if(ngaydangky1 != null) {
            ngaydangky = findViewById(R.id.ngaydangky)
            ngaydangky.setText(ngaydangky1)
        }else{ngaydangky.setText("-")}
        if(parking1 != null) {
            val parking = findViewById<TextView>(R.id.parking)
            parking.setText(parking1)
        }else{val parking = findViewById<TextView>(R.id.parking)
            parking.setText("-")}





        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
        val xoacanho = findViewById<TextView>(R.id.xoacanho)
        val content = "Xóa căn hộ"
        val spannableString = SpannableString(content)
        spannableString.setSpan(UnderlineSpan(), 0, content.length, 0)
        xoacanho.text = spannableString

        edit1.setOnClickListener {
            intent = Intent(this, com.example.aqqhome.ui.admin.CapNhapThongTinChuSoHuuActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            intent.putExtra("phone", sodienthoai1)
            intent.putExtra("ngaybangiao", ngaybangiao1)
            intent.putExtra("cancuoc", socancuoc1)

            startActivity(intent)
        }
        edit2.setOnClickListener {
            intent = Intent(this, CapNhapThongTinQuanLyActivity::class.java)
            startActivity(intent)
        }
    }



}

