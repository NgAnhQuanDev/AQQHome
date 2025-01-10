package com.example.aqqhome.ui.order

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.model.ChatModel2
import com.example.aqqhome.utils.KeyboardUtils.hideKeyboard
import com.example.aqqhome.utils.MyPref
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.util.Calendar

class DetailChatActivity : AppCompatActivity() {
    private lateinit var tv_name_chat: TextView
    private lateinit var recycle_message:RecyclerView
    private lateinit var ed_message:EditText
    private lateinit var img_send:ImageView
    private lateinit var messageAdapter: ChatAdapter2
    private var chatList = ArrayList<ChatModel2>()
    private lateinit var RoomID:String
    private lateinit var ApartmentID:String
    private lateinit var Type:String
    private lateinit var img_back: ImageView
    private val database = FirebaseDatabase.getInstance().getReference("messages")

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_chat)
        tv_name_chat = findViewById(R.id.tv_name_chat)
        recycle_message = findViewById(R.id.recycle_message)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycle_message.layoutManager = layoutManager
        ed_message = findViewById(R.id.ed_message)
        img_send = findViewById(R.id.img_send)
        img_back = findViewById(R.id.img_back)
        val intent = getIntent()
        RoomID = intent.getStringExtra("RoomID").toString()
        ApartmentID = intent.getStringExtra("ApartmentID").toString()
        Type = MyPref.get(this,"UserInfo","Type")?:""
        var RoomName:String  = intent.getStringExtra("RoomName").toString()
        if(Type=="1") {
            tv_name_chat.text = RoomName
        }
        if (Type=="0"){
            var ApartmentID = MyPref.get(this,"RoomID","ApartmentID")?:""
            tv_name_chat.text = "Ban Quản Lý "
        }
        img_back.setOnClickListener{
            onBackPressed()
        }
        loadMessages()
        img_send.setOnClickListener {
            val message = ed_message.text.toString()
            if (message.isNotEmpty()) {
                sendMessageToAdmin(message)
                ed_message.text.clear()
                hideKeyboard(this)
            }
        }
    }
    private fun sendMessageToAdmin(message: String) {
        ensureRoomExists()
        val currentTimestamp = System.currentTimeMillis().toString()
        if(Type=="1"){
            val chat = ChatModel2(RoomID, message, ApartmentID, DateFormat.getDateTimeInstance().format(
                Calendar.getInstance().time),true)
            database.push().setValue(chat)
        }else{
            val chat = ChatModel2(RoomID, message, ApartmentID, DateFormat.getDateTimeInstance().format(
                Calendar.getInstance().time),false)
            database.push().setValue(chat)
        }
        val roomRef = FirebaseDatabase.getInstance().getReference("Room").child(RoomID)
        roomRef.child("lastMessageTime").setValue(System.currentTimeMillis())
        roomRef.child("lastMessage").setValue(message)
        roomRef.child("ApartmentID").setValue(ApartmentID)
        roomRef.child("RoomID").setValue(RoomID)
        roomRef.child("RoomName").setValue(MyPref.get(this,"RoomID","RoomName"))

        if(Type=="1") {
            roomRef.child("daxem").setValue("1")
        }else{
            roomRef.child("daxem").setValue("0")
        }
    }

    private fun loadMessages() {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("messages")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children){
                    var chat: ChatModel2? = dataSnapShot.getValue(ChatModel2::class.java)
                    if(chat?.roomID == RoomID && chat.apartmentID == ApartmentID){
                        chatList.add(chat)
                    }
                }
                val chatAdapter = ChatAdapter2(this@DetailChatActivity, chatList)
                recycle_message.adapter = chatAdapter
                recycle_message.scrollToPosition(chatList.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun ensureRoomExists() {
        val roomRef = FirebaseDatabase.getInstance().getReference("Room")
        val specificRoomRef = roomRef.child(RoomID)

        specificRoomRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    val roomData = hashMapOf(
                        "ApartmentID" to ApartmentID,
                        "RoomID" to RoomID,
                        "RoomName" to RoomID
                    )
                    specificRoomRef.setValue(roomData)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    override fun onResume() {
        super.onResume()
        val roomRef = FirebaseDatabase.getInstance().getReference("Room").child(RoomID)
        roomRef.child("daxem").setValue("1")
    }

}