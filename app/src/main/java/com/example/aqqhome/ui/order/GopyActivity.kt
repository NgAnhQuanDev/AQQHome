package com.example.aqqhome.ui.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import com.example.aqqhome.R
import com.example.aqqhome.model.gopymodel
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.ui.user.MainActivity
import com.example.aqqhome.utils.KeyboardUtils.hideKeyboard
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import com.google.android.material.button.MaterialButton
import retrofit2.Response

class GopyActivity : AppCompatActivity() {
    private lateinit var etNote: AppCompatEditText
    private lateinit var btnSend: MaterialButton
    private lateinit var text: String
    private lateinit var back:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gopy)
        etNote = findViewById(R.id.etNote)
        btnSend = findViewById(R.id.btnSend)
        back = findViewById(R.id.back)
        btnSend.setOnClickListener {
            send()
        }
        back.setOnClickListener{
            finish()
        }


    }

    private fun send() {
        if (etNote.text.toString().isEmpty()) {
            etNote.error = "Vui lòng nhập góp ý"
            return
        } else {
            text = etNote.text.toString()
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Bạn có chắc muốn gửi góp ý này chứ?")
                .setCancelable(false)
                .setPositiveButton("Đồng ý") { dialog, id ->
                    etNote.setText("")
                    hideKeyboard(this)

                    val RoomID:String = MyPref.get(this,"RoomID","RoomID").toString()
                    val ApartmentID:String = MyPref.get(this,"RoomID","ApartmentID").toString()
                    val UserID:String  = MyPref.get(this,"UserInfo","UserID").toString()
                    var aqiAQQHome: ApiAQQHome = RetrofitClient.instance
                    val call = aqiAQQHome.guigopy(ApartmentID,RoomID,UserID,text)
                    call.enqueue(object : retrofit2.Callback<gopymodel> {
                        override fun onResponse(
                            call: retrofit2.Call<gopymodel>, response: Response<gopymodel>
                        ) {
                            var Ketqua: gopymodel? = response.body()
                            if (Ketqua != null) {
                                if (Ketqua.isSuccess){
                                    Toast.makeText(this@GopyActivity,"Gửi phản ánh thành công",Toast.LENGTH_SHORT).show()
                                    intent = Intent(this@GopyActivity, MainActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }

                        override fun onFailure(call: retrofit2.Call<gopymodel>, t: Throwable) {
                            val dialogb = AlertDialog.Builder(this@GopyActivity)
                            dialogb.setTitle("Lỗi")
                            dialogb.setPositiveButton("Ok"){a, id ->
                                a.cancel()
                            }
                            dialogb.create().show()
                        }


                    })

                }
                .setNegativeButton("Hủy") { diallog, id ->
                    diallog.cancel()
                    hideKeyboard(this)
                }
                .setTitle("Xác nhận")
            dialogBuilder.create().show()
        }
    }
}