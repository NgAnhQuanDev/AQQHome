package com.example.aqqhome.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.models.SlideModel
import com.example.aqqhome.ui.order.DetailChatActivity
import com.example.aqqhome.ui.order.Gopy2Activity

import com.example.aqqhome.ui.order.HoadonActivity
import com.example.aqqhome.ui.order.HotlineActivity
import com.example.aqqhome.ui.payment.MoneyActivity
import com.example.aqqhome.ui.user.NewCodeActivity
import com.example.aqqhome.R
import com.example.aqqhome.ui.adapter.CanHoAdapter
import com.example.aqqhome.databinding.FragmentHomeBinding
import com.example.aqqhome.model.CanHoModel
import com.example.aqqhome.ui.user.LoadActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencess: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.themcanho.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(R.layout.themcanho_bottomsheet_layout)

            val recyclerView: RecyclerView? = bottomSheetDialog.findViewById(R.id.recycler_view)
            val canHoList = listOf(
                CanHoModel("14-01", "Trương Đình Hội"),

            )
            val adapter = CanHoAdapter(canHoList)
            if (recyclerView != null) {
                recyclerView.adapter = adapter
            }
            if (recyclerView != null) {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }


            val btn_doi: MaterialButton? = bottomSheetDialog.findViewById(R.id.btn_doi)
            btn_doi?.setOnClickListener {
                startActivity(Intent(requireContext(), NewCodeActivity::class.java))
            }
            val back: ImageView? = bottomSheetDialog.findViewById(R.id.back)
            back?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.show()
        }

        val imageList = arrayListOf(
            SlideModel("https://bdsweb.com.vn/upload_images/images/bbds/banner-bat-dong-san-01.jpg", null, null),
            SlideModel("https://megaon.vn/wp-content/uploads/2020/03/QT-web-24.03.png", null, null),
            SlideModel("https://intphcm.com/data/upload/thong-tin-poster-bat-dong-san.jpg", null, null)
        )
        binding.imageSlider.setImageList(imageList)

        sharedPreferences = requireActivity().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        sharedPreferencess = requireActivity().getSharedPreferences("RoomID", AppCompatActivity.MODE_PRIVATE)
        val phone = sharedPreferences.getString("phone", "Guest")
        val userName = sharedPreferences.getString("name", "Guest")
        val sophong = sharedPreferencess.getString("RoomName", "Guest")

        binding.nameinf.text = userName
        binding.phoneinf.text = phone
        binding.sophonginf.text = sophong


        setupClickListeners()

        return binding.root
    }

    private fun setupClickListeners() {
        sharedPreferences = requireActivity().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        sharedPreferencess = requireActivity().getSharedPreferences("RoomID", AppCompatActivity.MODE_PRIVATE)

        val RoomID = sharedPreferencess.getString("RoomID", "Guest")
        val ApartmentID = sharedPreferencess.getString("ApartmentID", "Guest")


        binding.tienich.setOnClickListener { startActivity(Intent(requireContext(), LoadActivity::class.java)) }
        binding.shop.setOnClickListener { startActivity(Intent(requireContext(), LoadActivity::class.java)) }
        binding.gopy.setOnClickListener {
            val intent = Intent(context, DetailChatActivity::class.java)
            intent.putExtra("RoomID", RoomID)
            intent.putExtra("ApartmentID", ApartmentID)
            context?.startActivity(intent)

        }
        binding.hotline.setOnClickListener { startActivity(Intent(requireContext(), HotlineActivity::class.java)) }
        binding.credit.setOnClickListener { startActivity(Intent(requireContext(), MoneyActivity::class.java)) }
        binding.thongtin.setOnClickListener { startActivity(Intent(requireContext(), Gopy2Activity::class.java)) }
        binding.bill.setOnClickListener { startActivity(Intent(requireContext(), HoadonActivity::class.java)) }
    }


}
