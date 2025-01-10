package com.example.aqqhome.ui.admin.PhanAnh

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.AdminGopyAdapter
import com.example.aqqhome.model.gopymodel
import com.example.aqqhome.model.gopymodel2
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient


class AdminXemPhanAnhActivity : AppCompatActivity() {
    private lateinit var ApartmentID:String
    private lateinit var recyclerView: RecyclerView
    private lateinit var gopyAdapter: AdminGopyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_xem_phan_anh)
        val back = findViewById<ImageView>(R.id.back)
        val PhanAnhDangXuLyFragment = PhanAnhDangXuLyFragment()
        val PhanAnhDaXulyFragment = PhanAnhDaXuLyFragment()
        val toggle = findViewById<RadioGroup>(R.id.toggle)
        toggle.setOnCheckedChangeListener { _, checkedId ->
            val transaction = supportFragmentManager.beginTransaction()
            when (checkedId) {
                R.id.calm -> {
                    // Nếu là "Chưa thanh toán", ẩn Fragment và hiển thị nội dung trong Activity
                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.frameLayoutContainer)
                    if (fragment != null) {
                        transaction.remove(fragment)
                    }
                    toggleViews(false)
                }

                R.id.rumor -> {
                    toggleViews(true)
                    transaction.replace(R.id.frameLayoutContainer, PhanAnhDangXuLyFragment)

                }
                R.id.calmm -> {
                    toggleViews(true)
                    transaction.replace(R.id.frameLayoutContainer, PhanAnhDaXulyFragment)

                }
            }
            transaction.commit()
        }


        back.setOnClickListener {
            finish()
        }
        ApartmentID = MyPref.get(this,"UserInfo","Manager").toString()
        var apiAQQHome: ApiAQQHome = RetrofitClient.instance
        val call = apiAQQHome.admingetgopy(ApartmentID)
        call.enqueue(object : retrofit2.Callback<gopymodel2> {
            override fun onResponse(call: retrofit2.Call<gopymodel2>, response: retrofit2.Response<gopymodel2>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        val data: MutableList<gopymodel> = result.getData()

                        // Lọc dữ liệu
                        val filteredData = data.filter { it.trangThai == "0" }

                        recyclerView = findViewById(R.id.recyclerView)
                        val layoutManager = LinearLayoutManager(this@AdminXemPhanAnhActivity)
                        recyclerView.layoutManager = layoutManager
                        gopyAdapter =
                            AdminGopyAdapter(
                                filteredData
                            )
                        recyclerView.adapter = gopyAdapter

                        if (result.isSuccess) {
                            // Thêm bất kỳ xử lý nào nếu cần
                        }
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<gopymodel2>, t: Throwable) {
                println(t.message)
            }
        })



    }

    fun toggleViews(isDaThanhToan: Boolean) {
        val visibility = if (isDaThanhToan) View.GONE else View.VISIBLE

        findViewById<RecyclerView>(R.id.recyclerView).visibility = visibility



    }
}