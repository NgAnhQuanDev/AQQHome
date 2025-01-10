package com.example.aqqhome.ui.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.GopyAdapter
import com.example.aqqhome.model.gopymodel
import com.example.aqqhome.model.gopymodel2
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import com.google.android.material.button.MaterialButton

class Gopy2Activity : AppCompatActivity() {
    private lateinit var btnSend: MaterialButton
    private lateinit var back: ImageView
    private lateinit var RoomID:String
    private lateinit var phananh: RecyclerView
    private lateinit var gopyAdapter: GopyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gopy2)
        btnSend = findViewById(R.id.btnSend)
        back = findViewById(R.id.back)
        btnSend.setOnClickListener{
            intent = Intent(this, GopyActivity::class.java)
            startActivity(intent)

        }
        back.setOnClickListener{
            finish()
        }
        RoomID = MyPref.get(this,"RoomID","RoomID").toString()
        var apiAQQHome: ApiAQQHome = RetrofitClient.instance
        val call = apiAQQHome.getgopy(RoomID)
        call.enqueue(object : retrofit2.Callback<gopymodel2> {
            override fun onResponse(call: retrofit2.Call<gopymodel2>, response: retrofit2.Response<gopymodel2>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        var data: MutableList<gopymodel>? = result.getData()
                        phananh = findViewById(R.id.phananh)
                        val layoutManager = LinearLayoutManager(this@Gopy2Activity)
                        phananh.layoutManager = layoutManager
                        gopyAdapter =
                            GopyAdapter(data)
                        phananh.adapter = gopyAdapter


                        if (result.isSuccess) {


                        }
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<gopymodel2>, t: Throwable) {
                println(t.message)
            }
        })


    }
}