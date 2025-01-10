package com.example.aqqhome.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.aqqhome.R
import com.example.aqqhome.ui.auth.ChangePassActivity

class InfoActivity : AppCompatActivity() {
    private lateinit var menu: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener { finish() }
        menu = findViewById(R.id.menu)
        menu.setOnClickListener() {
            intent = Intent(this, ChangePassActivity::class.java)
            startActivity(intent)
        }
        menu.setOnClickListener { v ->
            val popupMenu = PopupMenu(this, v)
            popupMenu.inflate(R.menu.info_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item1 -> {
                        intent = Intent(this, ChangePassActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }

    }

}




