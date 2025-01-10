package com.example.aqqhome.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aqqhome.databinding.ActivityAddroomBinding
import com.example.aqqhome.model.roommodel2
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.network.RetrofitClient
import com.example.aqqhome.utils.KeyboardUtils.hideKeyboard
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.customdialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Random

class AddRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddroomBinding
    private lateinit var code: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddroomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val retrofit = RetrofitClient.getRetrofitInstance()

        binding.apply {
            back.setOnClickListener { finish() }

            themcanhobtn.setOnClickListener {
                val roomName = tencanho.text.toString()
                val area = dientich.text.toString()
                val numberOfFloors = tang.text.toString()
                val parkingCardNumber = "0"
                var code = generateRandomCode(8) ?: ""

                binding.tencanho.setText("")
                binding.dientich.setText("")
                binding.tang.setText("")

                hideKeyboard(this@AddRoomActivity)

                if (listOf(roomName, area, numberOfFloors, parkingCardNumber, code).any { it.isEmpty() }) {
                    customdialog.showdialog("Lỗi", "Vui lòng nhập đầy đủ thông tin", "OK", this@AddRoomActivity)
                } else {
                    val apartmentID = sharedPreferences.getString("Manager", "")
                    val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
                    val token:String = MyPref.get(this@AddRoomActivity, "UserInfo", "JWT") ?: ""

                    val call = apiAQQHome.themcanho(token,roomName, area, numberOfFloors, parkingCardNumber, code, apartmentID)

                    call.enqueue(object : Callback<roommodel2> {
                        override fun onResponse(call: Call<roommodel2>, response: Response<roommodel2>) {
                            val roommodel = response.body()

                            if (response.isSuccessful && roommodel?.isSuccess() == true) {
                                Toast.makeText(this@AddRoomActivity, "Thêm căn hộ thành công", Toast.LENGTH_SHORT).show()
                            } else {
                                customdialog.showdialog("Lỗi", "Thêm căn hộ thất bại", "OK", this@AddRoomActivity)
                            }
                        }

                        override fun onFailure(call: Call<roommodel2>, t: Throwable) {
                            customdialog.showdialog("Lỗi", "Lỗi kết nối: ${t.message}", "OK", this@AddRoomActivity)
                        }
                    })
                }
            }
        }
    }
    fun generateRandomCode(length: Int): String? {
        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random()
        val code = StringBuilder()
        for (i in 0 until length) {
            val randomChar = characters[random.nextInt(characters.length)]
            code.append(randomChar)
        }
        return code.toString()
    }
}
