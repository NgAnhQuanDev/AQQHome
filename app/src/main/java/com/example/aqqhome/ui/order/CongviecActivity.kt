package com.example.aqqhome.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.aqqhome.R
import com.example.aqqhome.model.SoLuongCongViecModel
import com.example.aqqhome.ui.admin.CongViecDinhKyActivity
import com.example.aqqhome.ui.admin.PhanAnh.AdminXemPhanAnhActivity
import com.example.aqqhome.ui.admin.XemPheDuyetActivity
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class CongviecActivity : AppCompatActivity() {
    private lateinit var xetduyet: LinearLayout
    private lateinit var cpheduyet: TextView
    private lateinit var cdoitiepnhan: TextView
    private lateinit var cdangxuly: TextView
    private lateinit var ccongviecconhan: TextView
    private lateinit var ccongviecsaphethan: TextView
    private lateinit var ccongviechethan: TextView
    private lateinit var phananhchungcu: LinearLayout
    private lateinit var congviecdinhky: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congviec)
        val back = findViewById<ImageView>(R.id.back)
        cpheduyet = findViewById(R.id.cpheduyet)
        cdoitiepnhan = findViewById(R.id.cdoitiepnhan)
        cdangxuly = findViewById(R.id.cdangxuly)
        ccongviecconhan = findViewById(R.id.ccongviecconhan)
        ccongviecsaphethan = findViewById(R.id.ccongviecsaphethan)
        ccongviechethan = findViewById(R.id.ccongviechethan)
        back.setOnClickListener {
            finish()
        }
        xetduyet = findViewById(R.id.xetduyet)
        phananhchungcu = findViewById(R.id.phananhchungcu)

        xetduyet.setOnClickListener {
            intent.setClass(this, XemPheDuyetActivity::class.java)
            startActivity(intent)
        }
        phananhchungcu.setOnClickListener {
            intent.setClass(this, AdminXemPhanAnhActivity::class.java)
            startActivity(intent)
        }
        congviecdinhky = findViewById(R.id.congviecdinhky)
        congviecdinhky.setOnClickListener {
            intent.setClass(this, CongViecDinhKyActivity::class.java)
            startActivity(intent)
        }

        val ApartmentID = MyPref.get(this, "UserInfo", "Manager")
        val ApiAQQHome = RetrofitClient.instance
        ApiAQQHome.getsoluongcv(ApartmentID.toString())
            .enqueue(object : retrofit2.Callback<SoLuongCongViecModel> {
                override fun onResponse(
                    call: Call<SoLuongCongViecModel>,
                    response: Response<SoLuongCongViecModel>
                ) {
                    var ketqua = response.body()
                    if (ketqua != null) {
                        if(ketqua.isSuccess){
                            cpheduyet.text = ("Đang chờ: "+ ketqua.c_pheduyet.toString())
                            cdoitiepnhan.text =("Đợi tiếp nhận: "+ ketqua.c_gopy0.toString())
                            cdangxuly.text = ("Đang xử lý: "+ketqua.c_gopy1.toString())
                            ccongviecconhan.text = ("Còn hạn: "+ketqua.c_congviecconhan.toString())
                            ccongviecsaphethan.text = ("Sắp hết hạn: "+ketqua.c_congviecsaphethan.toString())
                            ccongviechethan.text = ("Quá hạn: "+ketqua.c_congviecquahan.toString())
                        }
                    }
                }
                override fun onFailure(call: Call<SoLuongCongViecModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
}}

