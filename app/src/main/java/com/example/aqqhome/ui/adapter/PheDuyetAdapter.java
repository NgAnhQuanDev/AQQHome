package com.example.aqqhome.ui.adapter;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.aqqhome.model.Xacminhpheduyetmodel;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.utils.MyPref;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PheDuyetAdapter extends RecyclerView.Adapter<PheDuyetAdapter.PheDuyetViewHolder> {
    private final List<PheDuyetModel> pheduyetList;
    private String kichhoat;
    public PheDuyetAdapter(List<PheDuyetModel> pheduyetList) {
        this.pheduyetList = pheduyetList;
    }
    @Override
    public PheDuyetAdapter.PheDuyetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adminxempheduyet, parent, false);
        return new PheDuyetAdapter.PheDuyetViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PheDuyetAdapter.PheDuyetViewHolder holder, int position) {
        PheDuyetModel pheduyet = pheduyetList.get(position);
        holder.idpheduyet.setText("Phê Duyệt : #PD000" + pheduyet.getPheDuyetID());
        holder.name.setText("● Chủ hộ:                        " + pheduyet.getName());
        holder.macode.setText("Mã căn hộ: " + pheduyet.getCode());
        holder.roomname.setText("● Tên phòng:                 " + pheduyet.getNameroom());
        holder.tang.setText("● Tầng:                           " + pheduyet.getTang());
        holder.mahopdong.setText("● Mã số hợp đồng:      " + pheduyet.getMasohopdong());
        holder.time.setText("● Thời gian:                     " + pheduyet.getTime());
        if (pheduyet.getKichhoat().equals("0")) {
            holder.kichhoat.setText("Đợi xét duyệt");
            holder.kichhoat.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
        } else if (pheduyet.getKichhoat().equals("1")) {
            holder.kichhoat.setText("Đã xét duyệt");
        } else {
            holder.kichhoat.setText("Từ chối xét duyệt");
            holder.kichhoat.setTextColor(holder.itemView.getResources().getColor(R.color.red));
        }
        holder.name.setVisibility(View.GONE);
        holder.roomname.setVisibility(View.GONE);
        holder.tang.setVisibility(View.GONE);
        holder.mahopdong.setVisibility(View.GONE);
        holder.time.setVisibility(View.GONE);
        holder.btn_tuchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setCancelable(true);
                builder.setTitle("Từ chối kích hoạt");
                builder.setMessage("Bạn có chắc chắn từ chối kích hoạt không?");
                builder.setPositiveButton("Đồng ý",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ApiAQQHome apiAQQHome = com.example.aqqhome.utils.RetrofitClient.INSTANCE.getInstance();
                                kichhoat = "2";
                                apiAQQHome.getcode(pheduyet.getNameroom(), pheduyet.getApartmentID(), pheduyet.getPheDuyetID(),kichhoat ).enqueue(new Callback<Xacminhpheduyetmodel>() {
                                    @Override
                                    public void onResponse(Call<Xacminhpheduyetmodel> call, Response<Xacminhpheduyetmodel> response) {
                                        holder.btn_duyet.setVisibility(View.GONE);
                                        holder.btn_tuchoi.setVisibility(View.GONE);
                                        holder.kichhoat.setText("Từ chối kích hoạt");
                                        holder.kichhoat.setTextColor(holder.itemView.getResources().getColor(R.color.red));
                                    }
                                    @Override
                                    public void onFailure(Call<Xacminhpheduyetmodel> call, Throwable t) {

                                        holder.btn_duyet.setVisibility(View.GONE);
                                        holder.btn_tuchoi.setVisibility(View.GONE);
                                        holder.kichhoat.setText("Từ chối kích hoạt");
                                        holder.kichhoat.setTextColor(holder.itemView.getResources().getColor(R.color.red));
                                    }
                                });
                            }
                        });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        holder.btn_duyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setCancelable(true);
                builder.setTitle("Xác nhận kích hoạt");
                builder.setMessage("Bạn có chắc chắn xác kích hoạt không?");
                builder.setPositiveButton("Đồng ý",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ApiAQQHome apiAQQHome = com.example.aqqhome.utils.RetrofitClient.INSTANCE.getInstance();
                                kichhoat = "1";
                                holder.macode.setVisibility(View.VISIBLE);
                                apiAQQHome.getcode(pheduyet.getNameroom(), pheduyet.getApartmentID(), pheduyet.getPheDuyetID(),kichhoat ).enqueue(new Callback<Xacminhpheduyetmodel>() {
                                    @Override
                                    public void onResponse(Call<Xacminhpheduyetmodel> call, Response<Xacminhpheduyetmodel> response) {
                                        Xacminhpheduyetmodel ketqua = response.body();
                                        if (ketqua.isSuccess()) {
                                            holder.btn_duyet.setVisibility(View.GONE);
                                            holder.btn_tuchoi.setVisibility(View.GONE);
                                            holder.kichhoat.setText("Đã kích hoạt");
                                            holder.kichhoat.setTextColor(holder.itemView.getResources().getColor(R.color.green));
                                            String apartmentID = MyPref.INSTANCE.get(v.getContext(), "UserInfo", "Manager");
                                        } else {
                                            Toast.makeText(v.getContext(), "Có lỗi gì đó ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Xacminhpheduyetmodel> call, Throwable t) {
                                        Toast.makeText(v.getContext(), "Có lỗi gì đó xảy ra", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        holder.lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.name.getVisibility() == View.GONE) {
                    // Mở rộng
                    expand(holder.name);
                    expand(holder.time);
                    expand(holder.roomname);
                    expand(holder.tang);
                    expand(holder.mahopdong);
                    if (pheduyet.getKichhoat().equals("0")) {
                        holder.btn_duyet.setVisibility(View.VISIBLE);
                        holder.btn_tuchoi.setVisibility(View.VISIBLE);
                    }
                    if (pheduyet.getCode().equals("")) {
                        collapse(holder.macode);
                    } else {
                        expand(holder.macode);
                        holder.macode.setText("Mã căn hộ: " + pheduyet.getCode());
                    }
                    holder.arrow.setImageResource(R.drawable.baseline_arrow_drop_up_24);
                } else {
                    // Thu gọn
                    collapse(holder.name);
                    collapse(holder.time);
                    collapse(holder.roomname);
                    collapse(holder.tang);
                    collapse(holder.mahopdong);
                    collapse(holder.macode);
                    holder.btn_duyet.setVisibility(View.GONE);
                    holder.btn_tuchoi.setVisibility(View.GONE);
                    holder.arrow.setImageResource(R.drawable.baseline_arrow_drop_down_24_black);
                }
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
        return pheduyetList.size();
    }
    static class PheDuyetViewHolder extends RecyclerView.ViewHolder {
        final TextView idpheduyet, name, roomname, tang, mahopdong, time, kichhoat, macode;
        final LinearLayout lin1;
        final ImageView arrow;
        final MaterialButton btn_tuchoi, btn_duyet;
        public PheDuyetViewHolder(@NonNull View itemView) {
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
            btn_tuchoi = itemView.findViewById(R.id.btn_tuchoi);
            btn_duyet = itemView.findViewById(R.id.btn_duyet);
        }
    }
}
