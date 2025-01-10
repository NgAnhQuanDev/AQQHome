package com.example.aqqhome.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.DaThanhToanAdapter
import com.example.aqqhome.model.Hoadonmodel
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.network.RetrofitClient
import com.example.aqqhome.utils.MyPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DaThanhToanFragment : Fragment() {

    private lateinit var retrofit: Retrofit
    private var daThanhToanHoadons: List<Hoadonmodel> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_da_thanh_toan, container, false)
        retrofit = RetrofitClient.getRetrofitInstance()
        setupRecyclerView(view)
        fetchHoadons()
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_da_thanh_toan)
        val adapter =
            DaThanhToanAdapter(daThanhToanHoadons)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun fetchHoadons() {
        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        val roomID = MyPref.get(requireContext(), "RoomID", "RoomID").toString()
        val token = MyPref.get(requireContext(), "UserInfo", "JWT").toString()
        val call: Call<List<Hoadonmodel>> = apiAQQHome.gethoadon(roomID, token)

        call.enqueue(object : Callback<List<Hoadonmodel>> {
            override fun onResponse(
                call: Call<List<Hoadonmodel>>,
                response: Response<List<Hoadonmodel>>
            ) {
                if (response.isSuccessful) {
                    val hoadons = response.body() ?: emptyList()
                    daThanhToanHoadons = hoadons.filter { it.daThanhToan == "1" }
                    setupRecyclerView(view?: return)
                }
            }

            override fun onFailure(call: Call<List<Hoadonmodel>>, t: Throwable) {

            }
        })
    }
}
