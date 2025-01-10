package com.example.aqqhome.ui.user

import android.os.Bundle
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import com.example.aqqhome.R

class LoadActivity : AppCompatActivity() {
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }




    }




}