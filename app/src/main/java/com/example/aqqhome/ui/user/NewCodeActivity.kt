package com.example.aqqhome.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import com.google.zxing.integration.android.IntentIntegrator;
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.aqqhome.R
import com.example.aqqhome.ui.auth.LoginActivity
import com.example.aqqhome.model.roommodel2
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.network.RetrofitClient
import com.example.aqqhome.utils.KeyboardUtils.hideKeyboard

import com.example.aqqhome.utils.customdialog
import com.google.zxing.integration.android.IntentResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NewCodeActivity : AppCompatActivity() {

    private lateinit var newCode: EditText
    private lateinit var newCodeBtn: Button
    private lateinit var logoutBtn: Button
    private lateinit var retrofit: Retrofit
    private lateinit var token: String
    private lateinit var tokenn: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencess: SharedPreferences
    private lateinit var qrcode : ImageView
    private lateinit var dangki: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_code)

        sharedPreferences = getSharedPreferences("RoomID", MODE_PRIVATE)
        sharedPreferencess = getSharedPreferences("UserInfo", MODE_PRIVATE)
        tokenn = sharedPreferencess.getString("JWT", "") ?: ""
        token = "Bearer " + tokenn;
        newCode = findViewById(R.id.code)
        newCodeBtn = findViewById(R.id.codebtn)
        qrcode = findViewById(R.id.qrcode)
        retrofit = RetrofitClient.getRetrofitInstance()
        dangki = findViewById(R.id.dangki)

        qrcode.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false)
            integrator.initiateScan()
        }
        dangki.setOnClickListener {
            val intent = Intent(this, ChonChungCuPheDuyetActivity::class.java)
            startActivity(intent)

        }

        newCodeBtn.setOnClickListener {
            val code = newCode.text.toString()
            if (code.isEmpty()) {
                customdialog.showdialog("Lỗi", "Vui lòng nhập mã xác nhận", "OK", this)
            } else {
                checkCode(code)
            }
        }
        logoutBtn = findViewById(R.id.logoutbtn)
        logoutBtn.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkCode(code: String) {
        val apiAQQHome = retrofit.create(ApiAQQHome::class.java)
        val call = apiAQQHome.checkcode(token,code)

        hideKeyboard(this)





        call.enqueue(object : Callback<roommodel2> {
            override fun onResponse(call: Call<roommodel2>, response: Response<roommodel2>) {
                var RoomID:String
                response.body()?.let {
                    if (it.isSuccess()) {
                        with(sharedPreferences.edit()) {
                            putString("RoomID", it.getRoomID())
                            RoomID = it.roomID
                            putString("RoomName", it.getRoomName())
                            putString("ApartmentID", it.getApartmentID())
                            apply()
                        }
                        customdialog.showdialog("Thông báo", "Mã xác nhận hợp lệ", "OK", this@NewCodeActivity)



                        Handler().postDelayed({
                            val intent = Intent(this@NewCodeActivity, MainActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            startActivity(intent)
                            finish()
                        }, 2500)
                    } else {
                        customdialog.showdialog("Lỗi", "Mã xác nhận không hợp lệ", "OK", this@NewCodeActivity)
                    }
                }
            }

            override fun onFailure(call: Call<roommodel2>, t: Throwable) {
                customdialog.showdialog("Lỗi", "Lỗi kết nối", "OK", this@NewCodeActivity)
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                // Lấy dữ liệu từ mã QR và gán vào EditText
                val qrData = result.contents
                var codee:String = qrData.replace("http://","")


                newCode.setText(codee)
            } else {
                Toast.makeText(this, "Không thể quét mã QR", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



}
