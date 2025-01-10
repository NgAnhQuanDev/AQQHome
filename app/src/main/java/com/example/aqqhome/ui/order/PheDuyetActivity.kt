package com.example.aqqhome.ui.order

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.aqqhome.R
import com.example.aqqhome.model.ApiResponsee
import com.example.aqqhome.model.PheDuyetModel
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Response

class PheDuyetActivity : AppCompatActivity() {
    private lateinit var textt: PheDuyetModel
    private lateinit var ApartmentID: String
    private lateinit var text: String
    private lateinit var roomNames: List<String>
    private lateinit var NameApartmentt: String
    private lateinit var autoCompleteTextView: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phe_duyet)
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        val textView: TextView = findViewById(R.id.yourTextViewId)
        val fullText =
            "Vui lòng xác nhận các thông tin trên là chính xác, và bạn đồng ý với Điều khoản Dịch vụ và Chính sách bảo mật của chúng tôi."
        val spannable = SpannableString(fullText)
        val startIndexOfHighlight = fullText.indexOf("Điều khoản Dịch vụ và Chính sách bảo mật")
        if (startIndexOfHighlight != -1) {
            spannable.setSpan(
                ForegroundColorSpan(Color.BLUE),
                startIndexOfHighlight,
                fullText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textView.text = spannable
        val linearLayoutForm: LinearLayout = findViewById(R.id.linearLayoutForm)
        val apiAQQhome: ApiAQQHome = RetrofitClient.instance
        NameApartmentt = intent.getStringExtra("chungcu").toString()
        apiAQQhome.getchungcutheoname(NameApartmentt).enqueue(object : retrofit2.Callback<ApiResponsee> {
            override fun onResponse(call: Call<ApiResponsee>, response: Response<ApiResponsee>) {
                if (response.isSuccessful) {
                    roomNames = response.body()?.records ?: emptyList()

                    val adapter = ArrayAdapter(this@PheDuyetActivity, android.R.layout.simple_dropdown_item_1line, roomNames)
                    autoCompleteTextView.setAdapter(adapter)
                } else {
                }
            }
            override fun onFailure(call: Call<ApiResponsee>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        linearLayoutForm.visibility = View.GONE
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.adapter.getItem(position) as String
            if (selectedItem != "Your default prompt text") {
                linearLayoutForm.visibility = View.VISIBLE
                ApartmentID = selectedItem
            } else {
                linearLayoutForm.visibility = View.GONE
                ApartmentID = ""

            }
        }
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
        val btnUpdate: Button = findViewById(R.id.btnupdate)
        btnUpdate.setOnClickListener {
            sendApproval()
        }
    }
    private fun sendApproval() {
        if (ApartmentID.isNotEmpty()) {
            val name: String = findViewById<TextInputEditText>(R.id.textname).text.toString()
            val nameroom: String = ApartmentID;
            val tang: String = findViewById<TextInputEditText>(R.id.tang).text.toString()
            val UserID = MyPref.get(this, "UserInfo", "UserID")
            val masohopdong: String =
                findViewById<TextInputEditText>(R.id.mahopdong).text.toString()
            val NameApartment: String = NameApartmentt;
            val apiAQQhome: ApiAQQHome = RetrofitClient.instance
            apiAQQhome.guipheduyet(
                NameApartment,
                name,
                nameroom,
                tang,
                UserID,
                masohopdong
            ).enqueue(object : retrofit2.Callback<PheDuyetModel> {
                override fun onResponse(
                    call: retrofit2.Call<PheDuyetModel>,
                    response: retrofit2.Response<PheDuyetModel>
                ) {
                    Toast.makeText(
                        this@PheDuyetActivity,
                        "Gửi yêu cầu thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish();

                }
                override fun onFailure(call: retrofit2.Call<PheDuyetModel>, t: Throwable) {
                    Toast.makeText(this@PheDuyetActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Vui lòng chọn một chung cư", Toast.LENGTH_SHORT).show()
        }
    }


}
