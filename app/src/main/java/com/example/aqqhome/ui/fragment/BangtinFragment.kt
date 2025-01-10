package com.example.aqqhome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.ui.adapter.NewAdapter
import com.example.aqqhome.model.ApiNewFeed
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.network.RetrofitClient
import com.example.aqqhome.ui.user.NewmesActivity
import com.example.aqqhome.utils.MyPref
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class BangtinFragment : Fragment() {
    private lateinit var retrofit: Retrofit
    private lateinit var addnew: FloatingActionButton
    private lateinit var back: ImageView
    private lateinit var recycler_view: RecyclerView
    private lateinit var call:Call<ApiNewFeed>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bangtin, container, false)

        addnew = view.findViewById(R.id.addnew)
        recycler_view = view.findViewById(R.id.recyclerView)
        retrofit = RetrofitClient.getRetrofitInstance()
        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        val context = requireContext()
        val apartmentID2 = MyPref.get(context, "UserInfo", "Manager") as? String ?: ""
        val apartmentID = MyPref.get(context, "RoomID", "ApartmentID") as? String ?: ""
        val token = MyPref.get(context, "UserInfo", "JWT") as? String ?: ""
        call = if (!apartmentID2.isNullOrEmpty()) {
            apiAQQHome.getnewfeed(token, apartmentID2)
        } else {
            apiAQQHome.getnewfeed(token, apartmentID)
        }


        call.enqueue(object : Callback<ApiNewFeed> {
            override fun onResponse(call: Call<ApiNewFeed>, response: Response<ApiNewFeed>) {
                if (response.isSuccessful) {
                    val apiNewFeed = response.body()
                    if (apiNewFeed?.isSuccess() == true) {
                        val newfeedList = apiNewFeed.getRecords()
                        val layoutManager = LinearLayoutManager(requireContext())
                        recycler_view.layoutManager = layoutManager
                        val newfeedAdapter =
                            NewAdapter(newfeedList)
                        recycler_view.adapter = newfeedAdapter
                    } else {

                    }
                } else {

                }
            }

            override fun onFailure(call: Call<ApiNewFeed>, t: Throwable) {

            }
        })

        addnew.setOnClickListener {
            startActivity(Intent(activity, NewmesActivity::class.java))
            activity?.finish()
        }



        return view
    }
}
