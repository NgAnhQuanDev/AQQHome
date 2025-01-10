package com.example.aqqhome.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.HoadonAdapter
import com.example.aqqhome.ui.fragment.DaThanhToanFragment
import com.example.aqqhome.model.Hoadonmodel
import com.example.aqqhome.model.feemodel
import com.example.aqqhome.ui.payment.ThanhToanActivity
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.ui.admin.Admin_SuachiphiActivity
import com.example.aqqhome.ui.user.MainActivity
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.utils
import com.google.android.material.button.MaterialButton
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.Date
import java.util.Locale

class HoadonActivity : AppCompatActivity(), HoadonAdapter.ProductSelectionListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HoadonAdapter
    private lateinit var RoomID: String
    private lateinit var retrofit : Retrofit
    private lateinit var tongtien: TextView
    private lateinit var xemdichvu: ImageView
    private var tong : Int = 0
    private var tong2 : Int = 0
    private lateinit var ManagementFee: String
    private lateinit var Area: String
    private lateinit var GarbageFee: String
    private lateinit var pay: MaterialButton
    private lateinit var back : ImageView
    private var hoadons: List<Hoadonmodel> = listOf()
    val hoadonIDs = mutableListOf<Int>()
    private lateinit var ggg: LinearLayout
    private lateinit var vvs: View
    private lateinit var khonghoadon: ImageView
    private lateinit var textthongbao: TextView
    private var dem: Int =0


    private var apiCompletedCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoadon)
        initializeRetrofit()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        RoomID = MyPref.get(this, "RoomID", "RoomID") as String
        tongtien = findViewById(R.id.tongtien)
        back = findViewById(R.id.back)
        ggg = findViewById(R.id.ggg)
        vvs = findViewById(R.id.vvs)
        pay = findViewById(R.id.pay)
        khonghoadon = findViewById(R.id.khonghoadon)
        textthongbao = findViewById(R.id.textthongbao)
        xemdichvu = findViewById(R.id.xemdichvu)


        val daThanhToanFragment = DaThanhToanFragment()
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
                    if(dem!=0){
                        khonghoadon.visibility = ImageView.GONE
                        textthongbao.visibility = TextView.GONE
                        vvs.visibility = View.VISIBLE
                        ggg.visibility = LinearLayout.VISIBLE
                    }
                    if(dem==0){
                        khonghoadon.visibility = ImageView.VISIBLE
                        textthongbao.visibility = TextView.VISIBLE
                        vvs.visibility = View.GONE
                        ggg.visibility = LinearLayout.GONE
                    }
                }

                R.id.rumor -> {
                    toggleViews(true)
                    transaction.replace(R.id.frameLayoutContainer, daThanhToanFragment)
                    ggg.visibility = LinearLayout.GONE
                    vvs.visibility = ImageView.GONE
                    khonghoadon.visibility = ImageView.GONE
                    textthongbao.visibility = TextView.GONE
                }
            }
            transaction.commit()
        }
        pay.setOnClickListener {
            val intent = Intent(this@HoadonActivity, ThanhToanActivity::class.java).apply {
                putExtra("sono", tong2.toString())
                putExtra("Room", RoomID)
                putIntegerArrayListExtra("hoadonIDs", ArrayList(hoadonIDs))
            }
            startActivity(intent)
            finish()
        }

        back.setOnClickListener {
            val intent = Intent(this@HoadonActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        xemdichvu.setOnClickListener {
            val intent = Intent(this@HoadonActivity, Admin_SuachiphiActivity::class.java)
            startActivity(intent)
            finish()
        }
        fetchFees()
        fetchHoadons()
    }

    private fun initializeRetrofit() {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateDeserializer())
            .create()
        retrofit = Retrofit.Builder()
            .baseUrl(utils.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun fetchFees() {
        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        val token:String = MyPref.get(this, "UserInfo", "JWT").toString()
        val callFee = apiAQQHome.getchiphi(RoomID,token)
        callFee.enqueue(object : Callback<feemodel> {
            override fun onResponse(call: Call<feemodel>, response: Response<feemodel>) {
                if (response.isSuccessful) {
                    val text = response.body()

                    if (text != null) {
                        ManagementFee = text.managementFee
                    }
                    if (text != null) {
                        Area = text.area
                    }
                    if (text != null) {
                        GarbageFee = text.garbageFee
                    }

                }
                checkAllDataLoaded()
            }

            override fun onFailure(call: Call<feemodel>, t: Throwable) {
                checkAllDataLoaded()
            }
        })
    }

    private fun fetchHoadons() {
        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        val token:String = MyPref.get(this, "UserInfo", "JWT").toString()
        val call: Call<List<Hoadonmodel>> = apiAQQHome.gethoadon(RoomID,token)
        call.enqueue(object : Callback<List<Hoadonmodel>> {
            override fun onResponse(call: Call<List<Hoadonmodel>>, response: Response<List<Hoadonmodel>>) {
                if (response.isSuccessful) {
                    hoadons = response.body() ?: emptyList()
                    for (hoadon in hoadons) {
                        if (hoadon.daThanhToan == "0") {
                            tong += hoadon.debt.toInt()
                            dem++

                        }
                    }
                    if (dem != 0) {
                        ggg.visibility = LinearLayout.VISIBLE
                        vvs.visibility = ImageView.VISIBLE
                        khonghoadon.visibility = ImageView.GONE
                        textthongbao.visibility = TextView.GONE


                    }
                }
                checkAllDataLoaded()
            }

            override fun onFailure(call: Call<List<Hoadonmodel>>, t: Throwable) {
                checkAllDataLoaded()
            }
        })
    }

    private fun checkAllDataLoaded() {
        apiCompletedCount++
        if (apiCompletedCount == 2) {
            displayHoadonsAndFees()
        }
    }

    private fun displayHoadonsAndFees() {

        val unpaidHoadons = hoadons.filter { it.daThanhToan == "0" }
        adapter = HoadonAdapter(
            unpaidHoadons,
            { selectedHoadon ->

            },
            ManagementFee,
            GarbageFee,
            Area
        )
        recyclerView.adapter = adapter
        adapter.setProductSelectionListener(this)
    }

    fun toggleViews(isDaThanhToan: Boolean) {
        val visibility = if (isDaThanhToan) View.GONE else View.VISIBLE

        findViewById<RecyclerView>(R.id.recyclerView).visibility = visibility



    }

    override fun onCheckBoxClicked(isChecked: Boolean, amount: Int, hoadonID: Int) {
        updateTotalAmount(amount)
        if (isChecked) {
            hoadonIDs.add(hoadonID)
        } else {
            hoadonIDs.remove(hoadonID)
        }
    }
    fun updateTotalAmount(amount: Int) {
        tong2 += amount
        val format = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(tong2.toDouble())
        tongtien.setText(format)
    }




}

