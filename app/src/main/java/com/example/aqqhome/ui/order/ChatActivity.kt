package com.example.aqqhome.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.model.ChatModel
import com.example.aqqhome.utils.MyPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var mAth: FirebaseAuth
    var listAccount = ArrayList<ChatModel>()
    private lateinit var recycle_people: RecyclerView
    private lateinit var adapter: ChatAdapter
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mAth = FirebaseAuth.getInstance()
        recycle_people = findViewById(R.id.recycle_people)
        recycle_people.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = ChatAdapter(listAccount, this@ChatActivity)
        recycle_people.adapter = adapter
        getUserList()
        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }


    }

    private fun getUserList() {
        var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Room")
        val ApartmentID: String = MyPref.get(this, "UserInfo", "Manager") ?: ""

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listAccount.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val account = dataSnapShot.getValue(ChatModel::class.java)
                    if (account?.apartmentID == ApartmentID) {
                        listAccount.add(account)
                    }
                }
                listAccount.sortByDescending { it.lastMessageTime }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}