package com.example.aqqhome.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.PheDuyetAdapter
import com.example.aqqhome.model.PheDuyetModel
import com.example.aqqhome.model.PheDuyetModel2
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class XemPheDuyetActivity : AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var pheduyet: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xem_phe_duyet)
        pheduyet = findViewById(R.id.pheduyet)
        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
        var ApartmentID = MyPref.get(this,"UserInfo","Manager").toString()
        val apiAQQHome = RetrofitClient.instance
        apiAQQHome.admingetpheduyet(null,ApartmentID).enqueue(object : retrofit2.Callback<PheDuyetModel2> {
            override fun onResponse(
                call: Call<PheDuyetModel2>,
                response: Response<PheDuyetModel2>
            ) {
                var ketqua:PheDuyetModel2 = response.body()!!
                if(ketqua.isSuccess){
                    var data: List<PheDuyetModel>? = ketqua.data
                    val layoutManager = LinearLayoutManager(this@XemPheDuyetActivity)
                    pheduyet.layoutManager = layoutManager
                    var heDuyetAdapter =
                        PheDuyetAdapter(data)
                    pheduyet.adapter = heDuyetAdapter
                }else{
                    val emptybox = findViewById<ImageView>(R.id.emptybox)
                    emptybox.visibility = ImageView.VISIBLE
                    val emptytext = findViewById<TextView>(R.id.emptytext)
                    emptytext.visibility = TextView.VISIBLE
                }
            }
            override fun onFailure(call: Call<PheDuyetModel2>, t: Throwable) {
            }
        })



    }
}