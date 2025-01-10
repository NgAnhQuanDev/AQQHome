package com.example.aqqhome.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aqqhome.ui.order.ChatActivity
import com.example.aqqhome.ui.order.CongviecActivity
import com.example.aqqhome.R
import com.example.aqqhome.model.roommodel
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.network.RetrofitClient
import com.example.aqqhome.ui.admin.QuanLyActivity
import com.example.aqqhome.utils.MyPref
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Callback

class HomeAdminFragment : Fragment() {
    private lateinit var giaotiep: LinearLayout
    private lateinit var quanly: LinearLayout
    private lateinit var yeucau: LinearLayout
    private lateinit var name: TextView
    private lateinit var namechungcu: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_admin, container, false)
        val Manager:String = MyPref.get(requireContext(), "UserInfo", "Manager").toString() ?: ""
        giaotiep = view.findViewById(R.id.giaotiep)
        quanly = view.findViewById(R.id.quanly)
        yeucau = view.findViewById(R.id.yeucau)
        name = view.findViewById(R.id.name)
        namechungcu = view.findViewById(R.id.namechungcu)
        giaotiep.setOnClickListener {
            val intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("ApartmentID", Manager)
            startActivity(intent)
        }
        quanly.setOnClickListener {
            val intent = Intent(requireContext(), QuanLyActivity::class.java)
            startActivity(intent)
        }
        yeucau.setOnClickListener {
            val intent = Intent(requireContext(), CongviecActivity::class.java)
            startActivity(intent)
        }
        name.setText("Chào, "+MyPref.get(requireContext(), "UserInfo", "name").toString())
        fetchApartmentName()


        return view
    }
    private fun fetchApartmentName() {
        var retrofit:Retrofit = RetrofitClient.getRetrofitInstance()
        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        val ApartmentID = MyPref.get(requireContext(), "UserInfo", "Manager")?: ""
        val token:String = MyPref.get(requireContext(), "UserInfo", "JWT") ?: ""
        apiAQQHome.getthongtin(token,ApartmentID).enqueue(object : Callback<roommodel> {
            override fun onResponse(call: Call<roommodel>, response: Response<roommodel>) {
                if (response.isSuccessful) {
                    namechungcu.text = ("CHUNG CƯ - "+response.body()?.nameApartment?.uppercase())
                }
            }
            override fun onFailure(call: Call<roommodel>, t: Throwable) {
            }
        })
    }
}
