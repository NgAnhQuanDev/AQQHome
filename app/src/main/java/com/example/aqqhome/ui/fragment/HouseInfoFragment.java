package com.example.aqqhome.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.roommodel;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Retrofit;


public class HouseInfoFragment extends Fragment {

    private TextView toanha,diachi,sotang,hotline, tang, tencanho, dientich;

    private SharedPreferences sharedPreferences;
    private Retrofit retrofit;

    public HouseInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_info, container, false);


        toanha = view.findViewById(R.id.toanha);
        diachi = view.findViewById(R.id.diachi);
        sotang = view.findViewById(R.id.sotang);
        hotline = view.findViewById(R.id.hotline);
        tencanho = view.findViewById(R.id.tencanho);
        dientich = view.findViewById(R.id.dientich);
        tang = view.findViewById(R.id.tang);

        sharedPreferences = getActivity().getSharedPreferences("RoomID", MODE_PRIVATE);
        String ApartmentID = sharedPreferences.getString("ApartmentID", "");
        String RoomName = sharedPreferences.getString("RoomName", "");
        SharedPreferences sharedPreferencess = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        String token = sharedPreferencess.getString("JWT", "");



        retrofit = RetrofitClient.getRetrofitInstance();
        ApiAQQHome apiAQQHome = retrofit.create(ApiAQQHome.class);
        Call<roommodel> call = apiAQQHome.getthongtin(token,ApartmentID);
        call.enqueue(new retrofit2.Callback<roommodel>() {
            @Override
            public void onResponse(Call<roommodel> call, retrofit2.Response<roommodel> response) {
                if (response.isSuccessful()){
                    toanha.setText(response.body().getNameApartment());
                    diachi.setText(response.body().getAddress());
                    hotline.setText(response.body().getManagerPhoneNumber());
                    sotang.setText(response.body().getFloors());
                    tencanho.setText(RoomName);
                    dientich.setText("75");
                }
            }
            @Override
            public void onFailure(Call<roommodel> call, Throwable t) {

            }
        });
        return view;
    }
}