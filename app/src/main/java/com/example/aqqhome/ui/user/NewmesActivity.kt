package com.example.aqqhome.ui.user

import ImagePreviewAdapter
import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aqqhome.R
import com.example.aqqhome.model.Responsemodel
import com.example.aqqhome.model.newfeedmodel
import com.example.aqqhome.network.ApiAQQHome
import com.example.aqqhome.network.RetrofitClient
import com.example.aqqhome.utils.MyPref
import com.google.android.material.button.MaterialButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class NewmesActivity : AppCompatActivity() {
    private val retrofit by lazy { RetrofitClient.getRetrofitInstance() }
    private val apiAQQHome by lazy { retrofit.create(ApiAQQHome::class.java) }
    private val token by lazy { MyPref.get(this, "UserInfo", "JWT").toString() }
    private val apartmentID by lazy { MyPref.get(this, "RoomID", "ApartmentID").toString() }
    private val roomName by lazy { MyPref.get(this, "RoomID", "RoomName").toString() }
    private val userID by lazy { MyPref.get(this, "UserInfo", "UserID").toString() }
    private val name by lazy { MyPref.get(this, "UserInfo", "name").toString() }

    private val selectedImageUris = ArrayList<Uri>()
    private lateinit var imagesPreviewAdapter: ImagePreviewAdapter

    private val REQUEST_IMAGE_PICK = 1001
    private val PERMISSION_REQUEST_CODE = 1002
    private val MAX_IMAGE_COUNT = 3
    private val IMAGE_PICK_CAMERA_CODE = 500
    private val cameraPermission: Array<String>
        get() {
            TODO()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newmes)
        checkPermissions()
        setupViews()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun setupViews() {
        val back: ImageView = findViewById(R.id.back)
        val postEditText: AppCompatEditText = findViewById(R.id.postEditText)
        val postButton: MaterialButton = findViewById(R.id.postButton)
        val charCountTextView: TextView = findViewById(R.id.charCountTextView)
        val selectedImagesRecyclerView: RecyclerView = findViewById(R.id.selectedImagesContainer)

        back.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() }
        postEditText.addTextChangedListener(createTextWatcher(postButton, charCountTextView))
        postButton.setOnClickListener { postNewfeed(postEditText.text.toString().trim()) }

        setupImagesRecyclerView(selectedImagesRecyclerView)
        setupPickImagesButton()
    }

    private fun createTextWatcher(postButton: Button, charCountTextView: TextView): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val currentCharCount = s?.length ?: 0
                val maxCharCount = 300
                charCountTextView.text = "$currentCharCount/$maxCharCount kí tự"
                postButton.isEnabled = currentCharCount <= maxCharCount
            }
        }
    }

    private fun setupImagesRecyclerView(recyclerView: RecyclerView) {
        imagesPreviewAdapter = ImagePreviewAdapter(selectedImageUris)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = imagesPreviewAdapter
    }

    private fun setupPickImagesButton() {
        val pickImagesButton: LinearLayout = findViewById(R.id.pickImagesButton)
        pickImagesButton.setOnClickListener {
            showImagePickDialog()

        }
    }

    private fun showImagePickDialog() {
        val option = arrayOf("Camera", "Thư viện ảnh", "Xóa ảnh")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Mời bạn chọn")
        builder.setItems(option) { dialog, which ->
            if (which == 1) {
                if (selectedImageUris.size >= MAX_IMAGE_COUNT) {
                    Toast.makeText(this, "Bạn chỉ có thể chọn tối đa 3 ảnh", Toast.LENGTH_SHORT).show()
                }else{

                    val intent = Intent(Intent.ACTION_PICK).apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    }
                    startActivityForResult(intent, REQUEST_IMAGE_PICK)
                }


            }

        }
        builder.create().show()
    }

    private fun postNewfeed(text: String) {
        val urls = selectedImageUris.map { uri -> getFileNameFromUri(uri) }
        val Url1 = urls.getOrNull(0)
        val Url2 = urls.getOrNull(1)
        val Url3 = urls.getOrNull(2)
        apiAQQHome.postbangtin(token, apartmentID, apartmentID, roomName, userID, text, name,Url1,Url2,Url3)
            .enqueue(object : Callback<newfeedmodel> {
                override fun onResponse(call: Call<newfeedmodel>, response: Response<newfeedmodel>) {
                    if (response.isSuccessful && response.body()?.isSuccess() == true) {
                        selectedImageUris.forEach { uri ->
                            uploadImage(uri)
                        }
                        Toast.makeText(this@NewmesActivity, "Đăng bài thành công", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@NewmesActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@NewmesActivity, "Lỗi khi đăng", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<newfeedmodel>, t: Throwable) {
                    Toast.makeText(this@NewmesActivity, "Lỗi mạng hoặc máy chủ: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun uploadImage(selectedImage: Uri) {
        val apiAQQHome = com.example.aqqhome.utils.RetrofitClient.instance
        val file = File(getPathFromUri(selectedImage))
        val fileName = getFileNameFromUri(selectedImage) ?: "default.jpg"
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val imagePart = MultipartBody.Part.createFormData("image", fileName, requestFile)
        val call = apiAQQHome.uploadImagee(imagePart)

        call.enqueue(object : Callback<Responsemodel> {
            override fun onResponse(call: Call<Responsemodel>, response: Response<Responsemodel>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.isSuccess() == true) {
                    } else {
                        Toast.makeText(this@NewmesActivity, "Server trả về lỗi: ${response.body()?.getMessage()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@NewmesActivity, "Request không thành công. Mã lỗi: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Responsemodel>, t: Throwable) {
                Toast.makeText(this@NewmesActivity, "Đổi ảnh đại diện thất bại do không kết nối mạng", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getPathFromUri(uri: Uri): String? {
        // Kiểm tra nếu URI là content URI
        if (uri.scheme.equals("content", ignoreCase = true)) {
            val returnCursor = contentResolver.query(uri, null, null, null, null)
            val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor?.moveToFirst()
            val name = nameIndex?.let { returnCursor.getString(it) }
            val file = File(cacheDir, name ?: "")
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return file.path
        } else if (uri.scheme.equals("file", ignoreCase = true)) {
            return uri.path
        }
        return null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val clipData = data.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val imageUri = clipData.getItemAt(i).uri
                    if (selectedImageUris.size < MAX_IMAGE_COUNT) {
                        selectedImageUris.add(imageUri)
                    }
                }
            } else {
                data.data?.let { uri ->
                    if (selectedImageUris.size < MAX_IMAGE_COUNT) {
                        selectedImageUris.add(uri)
                    }
                }
            }
            imagesPreviewAdapter.notifyDataSetChanged()
        }
    }

    private fun getFileNameFromUri(uri: Uri): String {
        return "avatars/image_${uri.hashCode()}.jpg"
    }



}
