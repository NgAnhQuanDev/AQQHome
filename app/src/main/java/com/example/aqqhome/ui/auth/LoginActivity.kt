package com.example.aqqhome.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.auth0.android.jwt.JWT
import com.example.aqqhome.ui.user.NewCodeActivity
import com.example.aqqhome.R
import com.example.aqqhome.model.usermodel2
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.network.RetrofitClient
import com.example.aqqhome.ui.user.MainActivity
import com.example.aqqhome.utils.KeyboardUtils.hideKeyboard
import com.example.aqqhome.utils.customdialog
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {
    private lateinit var emailid: TextInputEditText
    private lateinit var passwordid: TextInputEditText
    private lateinit var SignInbtn: Button
    private lateinit var Signupbtn: TextView
    private lateinit var admin: AppCompatButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencess: SharedPreferences
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailid = findViewById(R.id.emaillo2)
        passwordid = findViewById(R.id.passwordlo2)
        SignInbtn = findViewById(R.id.SignInbtn)
        Signupbtn = findViewById(R.id.Signupbtn)

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        sharedPreferencess = getSharedPreferences("RoomID", MODE_PRIVATE)
        retrofit = RetrofitClient.getRetrofitInstance()

        SignInbtn.setOnClickListener {
            val email = emailid.text.toString()
            val pwd = passwordid.text.toString()
            hideKeyboard(this)

            when {
                email.isEmpty() && pwd.isEmpty() -> {
                    customdialog.showdialog("Lỗi", "Vui lòng nhập tài khoản và mật khẩu", "OK", this)
                }
                email.isEmpty() -> {
                    customdialog.showdialog("Lỗi", "Vui lòng nhập tài khoản", "OK", this)
                    emailid.requestFocus()
                }
                pwd.isEmpty() -> {
                    customdialog.showdialog("Lỗi", "Vui lòng nhập mật khẩu", "OK", this)
                    passwordid.requestFocus()
                }
                else -> {
                    val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
                    val call = apiAQQHome.dangnhap(email, pwd)
                    call.enqueue(object : Callback<usermodel2> {
                        override fun onResponse(call: Call<usermodel2>, response: Response<usermodel2>) {
                            val usermodel = response.body()
                            if (response.isSuccessful && usermodel?.isSuccess == true) {
                                Toast.makeText(this@LoginActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                val jwt = JWT(usermodel.jwt)
                                with(sharedPreferences.edit()) {
                                    jwt.getClaim("Email")?.asString()?.let { putString("email", it) }
                                    jwt.getClaim("FullName")?.asString()?.let { putString("name", it) }
                                    jwt.getClaim("PhoneNumber")?.asString()?.let { putString("phone", it) }
                                    jwt.getClaim("Type")?.asString()?.let { putString("Type", it) }
                                    jwt.getClaim("UserID")?.asString()?.let { putString("UserID", it) }
                                    jwt.getClaim("Manager")?.asString()?.let { putString("Manager", it) }
                                    jwt.getClaim("avatar_path")?.asString()?.let { putString("avatar_path", it) }
                                    jwt.getClaim("uid")?.asString()?.let { putString("uid", it) }


                                    // Lưu lại JWT token
                                    putString("JWT", usermodel.jwt)

                                    apply()
                                }



                        val intent = when (usermodel.type) {
                                    "1" -> Intent(this@LoginActivity, MainActivity::class.java)
                                    else -> Intent(this@LoginActivity, NewCodeActivity::class.java)
                                }
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<usermodel2>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }

        Signupbtn.setOnClickListener {
            val i = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(i)
        }
    }
}
