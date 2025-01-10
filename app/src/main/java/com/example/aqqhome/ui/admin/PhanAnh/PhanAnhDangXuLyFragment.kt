package com.example.aqqhome.ui.admin.PhanAnh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.AdminGopyAdapter
import com.example.aqqhome.model.gopymodel2
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient

class PhanAnhDangXuLyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gopyAdapter: AdminGopyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_phan_anh_dang_xu_ly, container, false)
        setupRecyclerView(view)
        loadData()
        return view
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.rv_dang_xu_ly)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun loadData() {
        val apartmentID = MyPref.get(requireContext(), "UserInfo", "Manager").toString()
        val apiAQQHome: ApiAQQHome = RetrofitClient.instance
        val call = apiAQQHome.admingetgopy(apartmentID)

        call.enqueue(object : retrofit2.Callback<gopymodel2> {
            override fun onResponse(call: retrofit2.Call<gopymodel2>, response: retrofit2.Response<gopymodel2>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        val filteredData = result.getData().filter { it.trangThai == "1" }
                        gopyAdapter =
                            AdminGopyAdapter(
                                filteredData
                            )
                        recyclerView.adapter = gopyAdapter
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<gopymodel2>, t: Throwable) {
                println(t.message)
            }
        })
    }
}
