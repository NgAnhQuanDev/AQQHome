package com.example.aqqhome.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.UserpheDuyetAdapter
import com.example.aqqhome.model.PheDuyetModel
import com.example.aqqhome.model.PheDuyetModel2
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class UserXemPheDuyetActivity : AppCompatActivity() {
    private lateinit var recyclerViewUserXemPheDuyet: RecyclerView
    private lateinit var imgNoData: ImageView
    private lateinit var tvNoData: TextView
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_xem_phe_duyet)
        back = findViewById(R.id.back)
        recyclerViewUserXemPheDuyet = findViewById(R.id.recyclerViewUserXemPheDuyet)
        var UserID = MyPref.get(this,"UserInfo","UserID").toString()
        back.setOnClickListener {
            finish()
        }
        val apiAQQHome = RetrofitClient.instance
        apiAQQHome.getpheduyet(UserID,null).enqueue(object : retrofit2.Callback<PheDuyetModel2> {
            override fun onResponse(
                call: Call<PheDuyetModel2>,
                response: Response<PheDuyetModel2>
            ) {
                var ketqua: PheDuyetModel2 = response.body()!!
                if(ketqua.isSuccess){
                    var data: List<PheDuyetModel>? = ketqua.data

                    val layoutManager = LinearLayoutManager(this@UserXemPheDuyetActivity)
                    recyclerViewUserXemPheDuyet.layoutManager = layoutManager
                    var heDuyetAdapter =
                        UserpheDuyetAdapter(data)
                    recyclerViewUserXemPheDuyet.adapter = heDuyetAdapter


                }
                if(ketqua.data == null){
                    imgNoData = findViewById(R.id.imgNoData)
                    tvNoData = findViewById(R.id.tvNoData)
                    imgNoData.visibility = View.VISIBLE
                    tvNoData.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<PheDuyetModel2>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}