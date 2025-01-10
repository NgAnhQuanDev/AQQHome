package com.example.aqqhome.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.CanHoModel;

import java.util.List;

public class CanHoAdapter extends RecyclerView.Adapter<CanHoAdapter.CanHoViewHolder> {
    private final List<CanHoModel> canHoList;

    public CanHoAdapter(List<CanHoModel> canHoList) {
        this.canHoList = canHoList;
    }

    @NonNull
    @Override
    public CanHoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listcanho, parent, false);
        return new CanHoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanHoViewHolder holder, int position) {
        CanHoModel canHo = canHoList.get(position);
        holder.soCanHoTextView.setText(canHo.getSoCanHo());
        holder.tenToaNhaTextView.setText(canHo.getTenToaNha());

    }

    @Override
    public int getItemCount() {
        return canHoList.size();
    }

    static class CanHoViewHolder extends RecyclerView.ViewHolder {
        final TextView soCanHoTextView;
        final TextView tenToaNhaTextView;

        CanHoViewHolder(View view) {
            super(view);
            soCanHoTextView = view.findViewById(R.id.socanho);
            tenToaNhaTextView = view.findViewById(R.id.tentoanha);
        }
    }
}
