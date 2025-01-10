package com.example.aqqhome.ui.order

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.aqqhome.R

class HotlineActivity : AppCompatActivity() {
    private lateinit var contact:ImageView
    private lateinit var back:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotline)
        val Phone:String ="0394309941"
        val i: Intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$Phone"))
        contact = findViewById(R.id.contact)
        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
        contact.setOnClickListener {
            startActivity(i)
        }

    }
}