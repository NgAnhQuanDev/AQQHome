package com.example.firechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R



class UsersActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var back : ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        userRecyclerView = findViewById(R.id.userRecyclerView)
        back = findViewById(R.id.imgBack)
        back.setOnClickListener {
            onBackPressed()
        }



    }
}
