package com.example.aqqhome.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.aqqhome.R
import com.example.aqqhome.model.usermodel2
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.network.RetrofitClient
import com.example.aqqhome.utils.MyPref
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Retrofit

class ChangePassActivity : AppCompatActivity() {
    private lateinit var oldpass: TextInputEditText
    private lateinit var newpass: TextInputEditText
    private lateinit var update: MaterialButton
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        oldpass = findViewById(R.id.oldpass)
        newpass = findViewById(R.id.newpass)
        update = findViewById(R.id.update)
        retrofit = RetrofitClient.getRetrofitInstance();
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener { finish() }

        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        update.setOnClickListener {
            val Matkhaucu:String = oldpass.text.toString()
            val Matkhau:String = newpass.text.toString()
            val Matkhaune2:String = newpass.text.toString()
            val token = MyPref.get(this, "UserInfo", "JWT")
            val UserID = MyPref.get(this, "UserInfo", "UserID")



            if (Matkhau == Matkhaune2) {
                val call = apiAQQHome.updateuser(token, UserID, Matkhau, Matkhaucu)
                call.enqueue(object : retrofit2.Callback<usermodel2> {
                    override fun onResponse(
                        call: retrofit2.Call<usermodel2>,
                        response: retrofit2.Response<usermodel2>
                    ) {
                        if (response.isSuccessful) {
                            val apiResponse = response.body()
                            if (apiResponse?.isSuccess == true) {
                                Toast.makeText(
                                    this@ChangePassActivity,
                                    "Cập nhật thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@ChangePassActivity,
                                    "Cập nhật thất bại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@ChangePassActivity,
                                "Cập nhật thất bại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<usermodel2>, t: Throwable) {
                        Toast.makeText(
                            this@ChangePassActivity,
                            "Cập nhật thất bại",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {
                Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show()
            }

        }
    }


}
