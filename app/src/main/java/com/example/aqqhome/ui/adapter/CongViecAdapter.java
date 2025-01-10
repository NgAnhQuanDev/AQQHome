package com.example.aqqhome.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.Congviecmodel;

import java.util.List;

public class CongViecAdapter extends RecyclerView.Adapter<CongViecAdapter.GopyViewHolder> {
    private final List<Congviecmodel> congviecList;

    public CongViecAdapter(List<Congviecmodel> congviecList) {
        this.congviecList = congviecList;
    }

    @Override
    public CongViecAdapter.GopyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_congviec, parent, false);
        return new CongViecAdapter.GopyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CongViecAdapter.GopyViewHolder holder, int position) {
        Congviecmodel congviec = congviecList.get(position);
        holder.noidung.setText(congviec.getNoidung());
        holder.loai.setText(congviec.getLoai());
        holder.time.setText(congviec.getNgayhethan());






    }



    @Override
    public int getItemCount() {
        return congviecList.size();
    }

    static class GopyViewHolder extends RecyclerView.ViewHolder {
        final TextView noidung;
        final TextView loai;
        final TextView time;


        public GopyViewHolder(@NonNull View itemView) {
            super(itemView);
            noidung = itemView.findViewById(R.id.noidung);
            loai = itemView.findViewById(R.id.loai);
            time = itemView.findViewById(R.id.time);


        }
    }


}

