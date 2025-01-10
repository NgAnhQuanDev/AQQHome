package com.example.aqqhome.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.example.aqqhome.ui.order.PheDuyetActivity
import com.example.aqqhome.R
import com.example.aqqhome.ui.auth.UserXemPheDuyetActivity
import com.example.aqqhome.model.TestModell
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.utils.RetrofitClient
import com.google.android.material.button.MaterialButton

class ChonChungCuPheDuyetActivity : AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var mailbox: ImageView
    private lateinit var btn: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chon_chung_cu_phe_duyet)
        val spinner: Spinner = findViewById(R.id.spinner)
        back = findViewById(R.id.back)
        back.setOnClickListener(View.OnClickListener {
            finish()
        })
        mailbox = findViewById(R.id.mailbox)
        mailbox.setOnClickListener(View.OnClickListener {
            intent = Intent(this, UserXemPheDuyetActivity::class.java)
            startActivity(intent)
        })
        val apiAQQhome: ApiAQQHome = RetrofitClient.instance
        apiAQQhome.getchungcu().enqueue(object : retrofit2.Callback<TestModell> {
            override fun onResponse(
                call: retrofit2.Call<TestModell>,
                response: retrofit2.Response<TestModell>
            ) {
                val apartmentNames = response.body()?.nameApartment
                val adapter = ArrayAdapter<String>(
                    this@ChonChungCuPheDuyetActivity,
                    android.R.layout.simple_spinner_item
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter.add("Vui lòng chọn chung cư của bạn")
                apartmentNames?.forEach { name ->
                    adapter.add(name)
                }
                spinner.adapter = adapter
            }

            override fun onFailure(call: retrofit2.Call<TestModell>, t: Throwable) {
                Toast.makeText(
                    this@ChonChungCuPheDuyetActivity,
                    "Cập nhật thất bại",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        btn = findViewById(R.id.btn)
        btn.setOnClickListener(View.OnClickListener {
            var item = spinner.selectedItem.toString()
            if(item == "Vui lòng chọn chung cư của bạn"){
                Toast.makeText(this, "Vui lòng chọn chung cư của bạn", Toast.LENGTH_SHORT).show()
            }else{
                intent = Intent(this, PheDuyetActivity::class.java)
                intent.putExtra("chungcu", spinner.selectedItem.toString())
                startActivity(intent)
            }


        })


    }
}