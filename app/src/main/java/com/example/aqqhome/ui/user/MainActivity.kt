package com.example.aqqhome.ui.user
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aqqhome.BangtinFragment
import com.example.aqqhome.R
import com.example.aqqhome.databinding.ActivityMainBinding
import com.example.aqqhome.ui.fragment.CaidatFragment
import com.example.aqqhome.fragment.HomeAdminFragment
import com.example.aqqhome.ui.fragment.HomeFragment
import com.example.aqqhome.ui.fragment.ThongbaoFragment
import com.example.aqqhome.model.ApiResponse
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var Type:String

    private lateinit var binding: ActivityMainBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.swipeRefreshLayout.setOnRefreshListener(this);

        setContentView(binding.root)
        Type = MyPref.get(this,"UserInfo","Type").toString()
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        if(Type.equals("1")) {
            replaceFragment(HomeAdminFragment())

            binding.swipeRefreshLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))


        }else{
            replaceFragment(HomeFragment())
        }
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> if (Type == "1") replaceFragment(HomeAdminFragment()) else replaceFragment(
                    HomeFragment()
                )
                R.id.caidat -> replaceFragment(CaidatFragment())
                R.id.thongbao -> replaceFragment(ThongbaoFragment())
                R.id.bangtin -> replaceFragment(BangtinFragment())


            }
            true
        }
        test()
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            test()
            binding.swipeRefreshLayout.setRefreshing(false)
        }, 1500)
    }
    private fun test() {
        val apiAQQHome: ApiAQQHome = RetrofitClient.instance
        val RoomID = MyPref.get(this, "RoomID", "RoomID") as? String ?: ""
        if (RoomID.isNotBlank()) {
            apiAQQHome.getsoluongthongbao(RoomID).enqueue(object : retrofit2.Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val message = response.body()?.message
                    if (message?.toIntOrNull() ?: 0 > 0) {
                        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.thongbao)
                        badge.isVisible = true
                        if (message != null) {
                            badge.number = message.toInt()
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "onFailure: ${t.message}")
                }
            })
        }
    }
}
