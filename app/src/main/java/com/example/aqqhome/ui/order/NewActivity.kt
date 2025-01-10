package com.example.aqqhome.ui.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
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

class NewActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var addnew: FloatingActionButton
    private lateinit var back: ImageView
    private lateinit var recycler_view: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        addnew = findViewById(R.id.addnew)
        recycler_view = findViewById(R.id.recyclerView)
        retrofit = RetrofitClient.getRetrofitInstance()
        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        val ApartmentID = MyPref.get(this, "RoomID", "ApartmentID").toString()
        val token:String = MyPref.get(this, "UserInfo", "JWT").toString()
        val call = apiAQQHome.getnewfeed(token,ApartmentID)
        call.enqueue(object : Callback<ApiNewFeed> {
            override fun onResponse(call: Call<ApiNewFeed>, response: Response<ApiNewFeed>) {
                if (response.isSuccessful) {
                    val apiNewFeed = response.body()
                    if (apiNewFeed?.isSuccess() == true) {
                        val newfeedList = apiNewFeed.getRecords()
                        val layoutManager = LinearLayoutManager(this@NewActivity)
                        recycler_view.layoutManager = layoutManager
                        var newfeedAdapter =
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
        addnew.setOnClickListener(View.OnClickListener { v: View? ->
            startActivity(
                Intent(
                    this@NewActivity,
                    NewmesActivity::class.java

                )
            )
            finish()
        })
        back = findViewById(R.id.back)
        back.setOnClickListener(View.OnClickListener { v: View? -> finish() })


    }


}

