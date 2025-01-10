package com.example.aqqhome.ui.adapter;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.gopymodel;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminGopyAdapter extends RecyclerView.Adapter<AdminGopyAdapter.GopyViewHolder> {
    private final List<gopymodel> gopyList;

    public AdminGopyAdapter(List<gopymodel> gopyList) {
        this.gopyList = gopyList;
    }

    @Override
    public AdminGopyAdapter.GopyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgopy, parent, false);
        return new AdminGopyAdapter.GopyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminGopyAdapter.GopyViewHolder holder, int position) {
        gopymodel gopy = gopyList.get(position);
        holder.idgopy.setText("Mã phản ánh: #PA4444" + gopy.getGopyID());
        holder.text.setText(gopy.getText());
        holder.time.setText(gopy.getTime());
        String[] myArray = {"Chọn trạng thái", "Đang xử lý", "Đã xử lý"}; // Mảng dữ liệu cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(holder.itemView.getContext(),  R.layout.custom_spinner_itemm, myArray);
        holder.spinner_data.setAdapter(adapter);

        holder.spinner_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (!selectedItem.equals("Chọn trạng thái")) {
                    if (selectedItem.equals("Đang xử lý")) {
                        holder.trangthai.setText("Đang xử lý");

                        holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.orange));
                        holder.lin10.setBackgroundColor(holder.itemView.getResources().getColor(R.color.orange));
                        ApiAQQHome apiAQQHome = RetrofitClient.INSTANCE.getInstance();
                        apiAQQHome.trangthaiphananh(gopy.getGopyID(), "1").enqueue(new Callback<gopymodel>() {
                            @Override
                            public void onResponse(Call<gopymodel> call, Response<gopymodel> response) {
                                Toast.makeText(holder.itemView.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                removeItem(holder.getAdapterPosition()); // Xóa item sau khi cập nhật thành công
                            }

                            @Override
                            public void onFailure(Call<gopymodel> call, Throwable t) {
                                Toast.makeText(holder.itemView.getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (selectedItem.equals("Đã xử lý")) {
                        holder.trangthai.setText("Đã xử lý");

                        holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.green));
                        holder.lin10.setBackgroundColor(holder.itemView.getResources().getColor(R.color.green));
                        holder.spinner_data.setVisibility(View.GONE);
                        ApiAQQHome apiAQQHome = RetrofitClient.INSTANCE.getInstance();
                        apiAQQHome.trangthaiphananh(gopy.getGopyID(), "2").enqueue(new Callback<gopymodel>() {
                            @Override
                            public void onResponse(Call<gopymodel> call, Response<gopymodel> response) {
                                Toast.makeText(holder.itemView.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                removeItem(holder.getAdapterPosition()); // Xóa item sau khi cập nhật thành công
                            }

                            @Override
                            public void onFailure(Call<gopymodel> call, Throwable t) {
                                Toast.makeText(holder.itemView.getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
             
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (gopy.getTrangThai().equals("0")) {
            holder.trangthai.setText("Đang tiếp nhận");
            holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
            holder.lin10.setBackgroundColor(holder.itemView.getResources().getColor(R.color.yellow));
        } else if (gopy.getTrangThai().equals("1")) {
            holder.trangthai.setText("Đang xử lý");
            holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.orange));
            holder.lin10.setBackgroundColor(holder.itemView.getResources().getColor(R.color.orange));
        } else if (gopy.getTrangThai().equals("2")) {
            holder.trangthai.setText("Đã xử lý");
            holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.green));
            holder.lin10.setBackgroundColor(holder.itemView.getResources().getColor(R.color.green));
            holder.spinner_data.setVisibility(View.GONE);
        }else {
            holder.trangthai.setText("NULL");
            holder.trangthai.setTextColor(holder.itemView.getResources().getColor(R.color.grey));
        }






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
        final LinearLayout lin10;

        public GopyViewHolder(@NonNull View itemView) {
            super(itemView);
            idgopy = itemView.findViewById(R.id.idgopy);
            text = itemView.findViewById(R.id.text);
            time = itemView.findViewById(R.id.time);
            arrow = itemView.findViewById(R.id.arrow);
            lin1 = itemView.findViewById(R.id.lin1);
            trangthai = itemView.findViewById(R.id.trangthai);
            spinner_data = itemView.findViewById(R.id.spinner_data);
            lin10 = itemView.findViewById(R.id.lin10);
        }
    }
    public void removeItem(int position) {
        gopyList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, gopyList.size());
    }


}

