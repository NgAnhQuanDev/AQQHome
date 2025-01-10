package com.example.aqqhome.ui.adapter;

import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.PheDuyetModel;

import java.util.List;

public class UserpheDuyetAdapter extends RecyclerView.Adapter<UserpheDuyetAdapter.UserpheDuyetViewHolder> {
    private final List<PheDuyetModel> pheduyetList;
    private String kichhoat;
    private String code;

    public UserpheDuyetAdapter(List<PheDuyetModel> pheduyetList) {
        this.pheduyetList = pheduyetList;
    }

    @Override
    public UserpheDuyetAdapter.UserpheDuyetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pheduyet, parent, false);
        return new UserpheDuyetAdapter.UserpheDuyetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserpheDuyetAdapter.UserpheDuyetViewHolder holder, int position) {
        PheDuyetModel pheduyet = pheduyetList.get(position);
        holder.idpheduyet.setText("Mã yêu cầu: #YC" +pheduyet.getPheDuyetID());
        holder.name.setText(pheduyet.getName());
        holder.roomname.setText(pheduyet.getNameroom());
        holder.tang.setText(pheduyet.getTang());
        holder.mahopdong.setText(pheduyet.getMasohopdong());
        holder.time.setText(pheduyet.getTime());
        if(pheduyet.getCode().equals("")) {
            holder.macode.setVisibility(View.GONE);

        }
        else {
            holder.macode.setVisibility(View.VISIBLE);
            holder.macode.setText("Mã căn hộ:                        " + pheduyet.getCode());
        }
        if (pheduyet.getKichhoat().equals("0")) {
            holder.kichhoat.setText("Đang xác minh");
            holder.kichhoat.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
        } else if (pheduyet.getKichhoat().equals("1")) {
            holder.kichhoat.setText("Xác minh thành công");
            holder.kichhoat.setTextColor(holder.itemView.getResources().getColor(R.color.green));


        } else {
            holder.kichhoat.setText("Xác minh thất bại");
            holder.kichhoat.setTextColor(holder.itemView.getResources().getColor(R.color.red));
        }
        holder.macode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", holder.macode.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(v.getContext(), "Văn bản đã được sao chép", Toast.LENGTH_SHORT).show();

            }
        });











    }
    private void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        ValueAnimator va = ValueAnimator.ofInt(0, targetHeight);
        va.setDuration(300);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int)animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        va.start();
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        ValueAnimator va = ValueAnimator.ofInt(initialHeight, 0);
        va.setDuration(300);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int)animation.getAnimatedValue();
                v.requestLayout();
                if ((int) animation.getAnimatedValue() == 0) {
                    v.setVisibility(View.GONE);
                }
            }
        });
        va.start();
    }



    @Override
    public int getItemCount() {
        return pheduyetList.size();
    }

    static class UserpheDuyetViewHolder extends RecyclerView.ViewHolder {
        final TextView idpheduyet, name, roomname, tang, mahopdong, time, kichhoat, macode;
        final LinearLayout lin1;
        final ImageView arrow;



        public UserpheDuyetViewHolder(@NonNull View itemView) {
            super(itemView);
            idpheduyet = itemView.findViewById(R.id.idpheduyet);
            name = itemView.findViewById(R.id.name);
            roomname = itemView.findViewById(R.id.roomname);
            tang = itemView.findViewById(R.id.tang);
            mahopdong = itemView.findViewById(R.id.mahopdong);
            time = itemView.findViewById(R.id.time);
            kichhoat = itemView.findViewById(R.id.kichhoat);
            lin1 = itemView.findViewById(R.id.lin1);
            arrow = itemView.findViewById(R.id.arrow);
            macode = itemView.findViewById(R.id.macode);



        }
    }




}
