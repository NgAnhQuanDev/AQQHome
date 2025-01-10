package com.example.aqqhome.ui.ActivityDichvu

import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.aqqhome.R
import java.util.Locale


class NgonNguActivity : AppCompatActivity() {
    private lateinit var radioviet: RadioButton
    private lateinit var radioenglish: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngon_ngu)

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            finish()
        }

        radioenglish = findViewById(R.id.radioenglish)
        radioviet = findViewById(R.id.radioviet)
        radioviet.setOnClickListener {
            setLocale("vi")
        }

        radioenglish.setOnClickListener {
            setLocale("en")
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        recreate()
    }
}