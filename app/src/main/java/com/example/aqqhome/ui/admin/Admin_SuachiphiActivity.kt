package com.example.aqqhome.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aqqhome.ui.ActivityDichvu.DienActivity
import com.example.aqqhome.ui.ActivityDichvu.GuixeActivity
import com.example.aqqhome.ui.ActivityDichvu.NuocActivity
import com.example.aqqhome.ui.ActivityDichvu.QuanlyActivity
import com.example.aqqhome.ui.ActivityDichvu.VesinhActivity
import com.example.aqqhome.R
import com.example.aqqhome.model.ApiResponsee
import com.example.aqqhome.model.costsmodel
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Response

class Admin_SuachiphiActivity : AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var dien: LinearLayout
    private lateinit var guixe: LinearLayout
    private lateinit var nuoc: LinearLayout
    private lateinit var vesinh: LinearLayout
    private lateinit var quanly: LinearLayout
    private lateinit var khac: LinearLayout
    private lateinit var quanlysudung: LinearLayout
    private lateinit var diensudung: LinearLayout
    private lateinit var nuocsudung: LinearLayout
    private lateinit var guixesudung: LinearLayout
    private lateinit var vesinhsudung: LinearLayout
    private lateinit var khacsudung: LinearLayout
    private lateinit var phidien: String
    private lateinit var phiguixe: String
    private lateinit var phivesinh: String
    private lateinit var phiquanly: String
    private lateinit var phikhac: String
    private lateinit var phinuoc: String
    private lateinit var chotso: MaterialButton

    private lateinit var phiguixeused: String
    private lateinit var phivesinhused: String
    private lateinit var phiquanlyused: String
    private lateinit var phikhacused: String
    private lateinit var ApartmentID: String
    private lateinit var Type: String
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_suachiphi)
        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
        diensudung = findViewById(R.id.diensudung)
        nuocsudung = findViewById(R.id.nuocsudung)
        chotso = findViewById(R.id.chotso)
        guixesudung = findViewById(R.id.sudungguixe)
        vesinhsudung = findViewById(R.id.vesinhsudung)
        quanlysudung = findViewById(R.id.quanlysudung)
        khacsudung = findViewById<LinearLayout>(R.id.khacsudung)
        dien = findViewById(R.id.dien)
        guixe = findViewById(R.id.guixe)
        nuoc = findViewById(R.id.nuoc)
        vesinh = findViewById(R.id.vesinh)
        quanly = findViewById(R.id.quanly)
        khac = findViewById(R.id.khac)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            loaddata()
            swipeRefreshLayout.isRefreshing = false
        }
        Type = MyPref.get(this, "UserInfo", "Type") ?: ""
        if (Type.equals("1")) {
            chotso.visibility = LinearLayout.VISIBLE
        } else {
            chotso.visibility = LinearLayout.GONE
        }
        chotso.setOnClickListener {
            ApartmentID = MyPref.get(this, "UserInfo", "Manager") ?: ""
            AlertDialog.Builder(this)
                    .setTitle("Chốt sổ")
                    .setMessage("Bạn có chắc chắn muốn chốt sổ không?")
                    .setPositiveButton("Có") { dialog, which ->
                        val apiAQQHome: ApiAQQHome = RetrofitClient.instance
                        apiAQQHome.chotso(ApartmentID).enqueue(object : retrofit2.Callback<ApiResponsee> {
                            override fun onResponse(call: Call<ApiResponsee>, response: Response<ApiResponsee>) {
                                Toast.makeText(this@Admin_SuachiphiActivity, "Chốt số thành công", Toast.LENGTH_SHORT).show()

                            }

                            override fun onFailure(call: Call<ApiResponsee>, t: Throwable) {
                                Toast.makeText(this@Admin_SuachiphiActivity, "Chốt số thất bại", Toast.LENGTH_SHORT).show()
                            }

                        })
                    }
                    .setNegativeButton("Không") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
        }
        loaddata()
    }
    private fun loaddata() {
        ApartmentID = MyPref.get(this, "UserInfo", "Manager") ?: ""
        if (ApartmentID == "") {
            ApartmentID = MyPref.get(this, "RoomID", "ApartmentID") ?: ""
        }
        val apiAQQHome: ApiAQQHome = RetrofitClient.instance
        apiAQQHome.trangthaichiphi2(ApartmentID).enqueue(object : retrofit2.Callback<costsmodel> {
            override fun onResponse(call: Call<costsmodel>, response: Response<costsmodel>) {
                var ketqua = response.body()
                if (ketqua != null) {
                    if(ketqua.isSuccess){
                        if(ketqua.isManagerServiceUsed.equals("1")){
                            quanlysudung.visibility = LinearLayout.VISIBLE

                        }else{
                            quanlysudung.visibility = LinearLayout.GONE
                        }

                        if(ketqua.isGarbageServiceUsed.equals("1")){
                            vesinhsudung.visibility = LinearLayout.VISIBLE
                        }else{
                            vesinhsudung.visibility = LinearLayout.GONE
                        }
                        if(ketqua.isOtherServiceUsed.equals("1")){
                            khacsudung.visibility = LinearLayout.VISIBLE
                        }else{
                            khacsudung.visibility = LinearLayout.GONE
                        }
                        if(ketqua.numberOfParkingCardsUsed.equals("1")){
                            guixesudung.visibility = LinearLayout.VISIBLE
                        }else{
                            guixesudung.visibility = LinearLayout.GONE
                        }
                        phivesinh = ketqua.garbageFee
                        phiquanly = ketqua.managementFee
                        phikhac = ketqua.khac
                        phiguixe = ketqua.guixe
                        phivesinhused = ketqua.isGarbageServiceUsed
                        phiquanlyused = ketqua.isManagerServiceUsed
                        phikhacused = ketqua.isOtherServiceUsed
                        phiguixeused = ketqua.numberOfParkingCardsUsed
                    }
                }
            }
            override fun onFailure(call: Call<costsmodel>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        quanly.setOnClickListener {
            val newIntent = Intent(this@Admin_SuachiphiActivity, QuanlyActivity::class.java) // Replace 'CurrentActivity' with the name of your current activity
            newIntent.putExtra("phiquanly", phiquanly)
            newIntent.putExtra("phiquanlyused", phiquanlyused)
            startActivity(newIntent)
        }
        dien.setOnClickListener {
            intent = intent.setClass(this, DienActivity::class.java)
            startActivity(intent)
        }
        guixe.setOnClickListener {
            val newIntent = Intent(this@Admin_SuachiphiActivity, GuixeActivity::class.java)
            newIntent.putExtra("phiguixe", phiguixe)
            newIntent.putExtra("phiguixeused", phiguixeused)
            startActivity(newIntent)
        }
        nuoc.setOnClickListener {
            intent = intent.setClass(this, NuocActivity::class.java)
            startActivity(intent)
        }
        vesinh.setOnClickListener {
            val newIntent = Intent(this@Admin_SuachiphiActivity, VesinhActivity::class.java) // Replace 'CurrentActivity' with the name of your current activity
            newIntent.putExtra("phivesinh", phivesinh)
            newIntent.putExtra("phivesinhused", phivesinhused)
            startActivity(newIntent)
        }
    }
}
