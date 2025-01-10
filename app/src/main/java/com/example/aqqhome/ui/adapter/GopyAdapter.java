package com.example.aqqhome.ui.adapter;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.gopymodel;

import java.util.List;

public class GopyAdapter extends RecyclerView.Adapter<GopyAdapter.GopyViewHolder> {
    private final List<gopymodel> gopyList;

    public GopyAdapter(List<gopymodel> gopyList) {
        this.gopyList = gopyList;
    }

    @Override
    public GopyAdapter.GopyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgopy, parent, false);
        return new GopyAdapter.GopyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GopyAdapter.GopyViewHolder holder, int position) {
        gopymodel gopy = gopyList.get(position);
        holder.idgopy.setText("Mã phản ánh: #PA4444" + gopy.getGopyID());
        holder.text.setText(gopy.getText());
        holder.time.setText(gopy.getTime());
//        holder.text.setVisibility(View.GONE);
//        holder.time.setVisibility(View.GONE);
        if (gopy.getTrangThai().equals("0")) {
            holder.trangthai.setText("Đang tiếp nhận");
            holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
        } else if (gopy.getTrangThai().equals("1")) {
            holder.trangthai.setText("Đang xử lý");
            holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.orange));
        } else if (gopy.getTrangThai().equals("2")) {
            holder.trangthai.setText("Đã xử lý");
            holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.green));
        }else {
            holder.trangthai.setText("NULL");
            holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.grey));
        }
        holder.spinner_data.setVisibility(View.GONE);


//        holder.lin1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.text.getVisibility() == View.GONE) {
//                    // Mở rộng
//                    expand(holder.text);
//                    expand(holder.time);
//                    holder.arrow.setImageResource(R.drawable.baseline_arrow_drop_up_24);
//                } else {
//                    // Thu gọn
//                    collapse(holder.text);
//                    collapse(holder.time);
//                    holder.arrow.setImageResource(R.drawable.baseline_arrow_drop_down_24_black);
//                }
//            }
//        });




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
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
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
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
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
        return gopyList.size();
    }

    static class GopyViewHolder extends RecyclerView.ViewHolder {
        final TextView idgopy;
        final TextView text;
        final TextView time, trangthai;
        final ImageView arrow;
        final LinearLayout lin1;
        final Spinner spinner_data;

        public GopyViewHolder(@NonNull View itemView) {
            super(itemView);
            idgopy = itemView.findViewById(R.id.idgopy);
            text = itemView.findViewById(R.id.text);
            time = itemView.findViewById(R.id.time);
            arrow = itemView.findViewById(R.id.arrow);
            lin1 = itemView.findViewById(R.id.lin1);
            trangthai = itemView.findViewById(R.id.trangthai);
            spinner_data = itemView.findViewById(R.id.spinner_data);
        }
    }


}
