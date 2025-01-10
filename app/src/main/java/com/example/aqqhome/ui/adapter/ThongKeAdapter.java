package com.example.aqqhome.ui.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.ApiResponsee;
import com.example.aqqhome.model.Hoadonmodel;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.utils.RetrofitClient;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ViewHolder> {
    private List<Hoadonmodel> hoadons;

    public ThongKeAdapter(List<Hoadonmodel> hoadons) {
        this.hoadons = hoadons;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView monthTextView, type, debtTextView,test;
        TextView roomid;
        ImageView delete;

        public ViewHolder(View view) {
            super(view);
            monthTextView = view.findViewById(R.id.monthTextView);
            debtTextView = view.findViewById(R.id.debtTextView);
            type = view.findViewById(R.id.type);
            roomid = view.findViewById(R.id.roomid);
            test = view.findViewById(R.id.test);
            delete = view.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Xóa hóa đơn");
                    builder.setMessage("Bạn có chắc chắn muốn xóa hóa đơn này không?");
                    builder.setPositiveButton("Xóa",
                            (dialog, which) -> {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {

                                    String roomId = test.getText().toString();

                                    removeItem(position);
                                    ApiAQQHome apiAQQHome = RetrofitClient.INSTANCE.getInstance();
                                    apiAQQHome.xoahoadon(roomId).enqueue(new Callback<ApiResponsee>() {
                                        @Override
                                        public void onResponse(Call<ApiResponsee> call, Response<ApiResponsee> response) {
                                            ApiResponsee ketqua = response.body();
                                            Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<ApiResponsee> call, Throwable t) {
                                            Toast.makeText(v.getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                    builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hoadonmodel hoadon = hoadons.get(position);
        holder.roomid.setText("Phòng: " + hoadon.getRoomName());
        String hoadonID = String.valueOf(hoadon.getHoadonID());
        holder.test.setText((hoadonID));
        holder.monthTextView.setText("Tháng " + (hoadon.ngaytao.getMonth() + 1)+ "/" + (hoadon.ngaytao.getYear() + 1900) );

        String fm = String.valueOf(hoadon.getDebt());
        Locale localeVN = new Locale("vi", "VN");
        fm = NumberFormat.getCurrencyInstance(localeVN).format(Double.parseDouble(fm));
        holder.debtTextView.setText(fm);
        String loaihinh = hoadon.getServiceType();
        if(loaihinh.equals("Garbage") && loaihinh != null) holder.type.setText("Loại chi phí: Phí vệ sinh");
        else if(loaihinh.equals("Manager") && loaihinh != null) holder.type.setText("Loại chi phí: Phí quản lý");
        else if(loaihinh.equals("Parking") && loaihinh != null) holder.type.setText("Loại chi phí: Phí gửi xe");
        else holder.type.setText("Loại hình chi phí: Khác");


    }

    @Override
    public int getItemCount() {
        return hoadons != null ? hoadons.size() : 0;
    }

    public void removeItem(int position) {
        hoadons.remove(position);
        notifyItemRemoved(position);

    }
}
