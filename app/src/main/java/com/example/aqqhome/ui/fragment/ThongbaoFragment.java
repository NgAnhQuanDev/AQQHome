package com.example.aqqhome.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aqqhome.R;
import com.example.aqqhome.ui.adapter.ThongBaoAdapter;
import com.example.aqqhome.model.ThongBaoModel;
import com.example.aqqhome.model.ThongBaoModel2;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.ui.admin.ThongBaoMoiActivity;
import com.example.aqqhome.utils.MyPref;
import com.example.aqqhome.utils.utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThongbaoFragment extends Fragment {
    private Retrofit retrofit;
    private String RoomID;
    private FloatingActionButton mail;
    private String ApartmentID;
    private List<ThongBaoModel> thongbao2;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongbao, container, false);
        mail = view.findViewById(R.id.mail);
        if(MyPref.INSTANCE.get(getActivity(), "UserInfo", "Type").toString().equals("1")){

            mail.setVisibility(View.VISIBLE);
        }
        mail.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(getActivity(), ThongBaoMoiActivity.class);
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }

        });
        recyclerView = view.findViewById(R.id.rv_da_thanh_toan);
        getThongBao();
        return view;
    }

    private void getThongBao() {
        retrofit = new Retrofit.Builder()
                .baseUrl(utils.url2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String Type = MyPref.INSTANCE.get(getActivity(), "UserInfo", "Type").toString();
        if(Type.equals("1")) {
            RoomID = "62";
            ApartmentID ="";
        }
        if (Type.equals("0")) {
            ApartmentID = "";
            RoomID = MyPref.INSTANCE.get(getActivity(), "RoomID", "RoomID").toString();
        }

        ApiAQQHome apiAQQHome = retrofit.create(ApiAQQHome.class);
        Call<ThongBaoModel2> call = apiAQQHome.getthongbao(ApartmentID,RoomID);

        call.enqueue(new Callback<ThongBaoModel2>() {
            @Override
            public void onResponse(Call<ThongBaoModel2> call, Response<ThongBaoModel2> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ThongBaoModel2 apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        List<ThongBaoModel> thongBaoList = apiResponse.getData();
                        setupRecyclerView(thongBaoList);
                    } else {
                        Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Lỗi mạng hoặc máy chủ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ThongBaoModel2> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi mạng hoặc máy chủ: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(List<ThongBaoModel> thongBaoList) {
        ThongBaoAdapter adapter = new ThongBaoAdapter(thongBaoList, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
