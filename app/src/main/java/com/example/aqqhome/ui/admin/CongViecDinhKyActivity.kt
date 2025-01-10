package com.example.aqqhome.ui.admin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.CongViecAdapter
import com.example.aqqhome.model.Congviecmodel
import com.example.aqqhome.model.Congviecmodel2
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class CongViecDinhKyActivity : AppCompatActivity() {
    private lateinit var congviecAdapter: CongViecAdapter
    private lateinit var add: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cong_viec_dinh_ky)
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            finish()
        }
        add = findViewById(R.id.add)
add.setOnClickListener {
            val intent = android.content.Intent(this, ThemCongViecActivity::class.java)
            startActivity(intent)
        }
        val ApartmentID = MyPref.get(this, "UserInfo", "Manager")
        val ApiAQQHome = RetrofitClient.instance
        ApiAQQHome.getcongviec(ApartmentID.toString())
            .enqueue(object : retrofit2.Callback<Congviecmodel2> {
                override fun onResponse(
                    call: Call<Congviecmodel2>,
                    response: Response<Congviecmodel2>
                ) {
                    var ketqua = response.body()
                    if (ketqua != null) {
                        if(ketqua.isSuccess){
                            val data : MutableList<Congviecmodel>? = ketqua?.data
                            val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
                            val layoutManager = LinearLayoutManager(this@CongViecDinhKyActivity)
                            recyclerView.layoutManager = layoutManager
                            congviecAdapter =
                                CongViecAdapter(
                                    data
                                )
                            recyclerView.adapter = congviecAdapter
                        }

                    }
                }
                override fun onFailure(call: Call<Congviecmodel2>, t: Throwable) {
                    TODO("Not yet implemented")
                }


            })
    }
}