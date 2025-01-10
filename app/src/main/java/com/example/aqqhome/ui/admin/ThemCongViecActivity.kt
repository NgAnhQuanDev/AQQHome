package com.example.aqqhome.ui.admin

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import com.example.aqqhome.ui.order.CongviecActivity
import com.example.aqqhome.R
import com.example.aqqhome.model.Congviecmodel
import com.example.aqqhome.utils.MyPref
import com.example.aqqhome.utils.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Response
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class ThemCongViecActivity : AppCompatActivity() {
    private var loai: String = "Định kỳ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_them_cong_viec)

        val editTextDate: EditText = findViewById(R.id.editTextTime)
        val noidung: TextInputEditText = findViewById(R.id.noidung)
        editTextDate.setOnClickListener { showDatePickerDialog(editTextDate) }

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener { finish() }

        val update = findViewById<MaterialButton>(R.id.update)
        val ApiAQQHome = RetrofitClient.instance
        val ApartmentID = MyPref.get(this, "UserInfo", "Manager")

        val switchExample = findViewById<Switch>(R.id.switchExample)
        switchExample.setOnCheckedChangeListener { _, isChecked ->
            loai = if (isChecked) "Gấp" else "Định kỳ"
        }

        update.setOnClickListener {
            val nd = noidung.text.toString()
            val ngay = editTextDate.text.toString()

            ApiAQQHome.themcongviec(nd, loai, ngay, ApartmentID).enqueue(object : retrofit2.Callback<Congviecmodel> {
                override fun onResponse(call: Call<Congviecmodel>, response: Response<Congviecmodel>) {
                    Toast.makeText(this@ThemCongViecActivity, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    intent = Intent(this@ThemCongViecActivity, CongviecActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<Congviecmodel>, t: Throwable) {
                    Toast.makeText(this@ThemCongViecActivity, "Cập nhật thất bại: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showDatePickerDialog(editTextDate: EditText) {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            editTextDate.setText(dateFormat.format(calendar.time))
        }

        DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}