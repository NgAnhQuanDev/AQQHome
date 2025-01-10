package com.example.aqqhome.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.Hoadonmodel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HoadonAdapter extends RecyclerView.Adapter<HoadonAdapter.ViewHolder> {
    private List<Hoadonmodel> hoadons;
    private OnItemClickListener listener;
    private String ManagementFee;
    private ProductSelectionListener selectionListener;
    private String GarbageFee;
    private String Area;



    public HoadonAdapter(List<Hoadonmodel> hoadons, OnItemClickListener listener, String ManagementFee, String GarbageFee, String Area) {
        this.hoadons = hoadons;
        this.listener = listener;

        this.ManagementFee = ManagementFee;
        this.GarbageFee = GarbageFee;
        this.Area = Area;


    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView monthTextView,type,roomid; // as an example
        TextView debtTextView;
        CheckBox checkBox;


        public ViewHolder(View view) {
            super(view);
            monthTextView = view.findViewById(R.id.monthTextView);
            debtTextView = view.findViewById(R.id.debtTextView);
            type = view.findViewById(R.id.type);
            checkBox = view.findViewById(R.id.checkBox);
            roomid = view.findViewById(R.id.roomid);
        }

        public void bind(final Hoadonmodel hoadon, final OnItemClickListener listener) {
            roomid.setText("Phòng: " + hoadon.getRoomName());

            monthTextView.setText("Tháng " + (hoadon.ngaytao.getMonth() + 1)+ "/" + (hoadon.ngaytao.getYear() + 1900) );
            // Format the debt to show commas and the currency symbol
            String fm = String.valueOf(hoadon.getDebt());
            Locale localeVN = new Locale("vi", "VN");
            fm = NumberFormat.getCurrencyInstance(localeVN).format(Double.parseDouble(fm));
            debtTextView.setText(fm);
            String loaihinh = hoadon.getServiceType();
            if(loaihinh.equals("Garbage") && loaihinh != null) type.setText("Loại chi phí: Phí vệ sinh");
            else if(loaihinh.equals("Manager") && loaihinh != null) type.setText("Loại chi phí: Phí quản lý");
            else if(loaihinh.equals("Parking") && loaihinh != null) type.setText("Loại chi phí: Phí gửi xe");
            else type.setText("Loại hình chi phí: Khác");


//            itemView.setOnClickListener(v -> {
//                listener.onItemClick(hoadon);
//                Integer fee = Integer.parseInt(ManagementFee);
//                Integer area = Integer.parseInt(Area);
//                String ketqua = String.valueOf(fee * area);
//                Integer rac = Integer.parseInt(GarbageFee);
//
//                String details = "Diện tích: " + Area + "\n"
//                        + "Phí quản lý: " + ManagementFee + " x " + Area + " = " + ketqua + "\n"
//                        + "Phí rác: " + GarbageFee ;
//
//                // Sử dụng AlertDialog để hiển thị chi tiết
//                new AlertDialog.Builder(v.getContext())
//                        .setTitle("Chi tiết hóa đơn cho Tháng " + (hoadon.getNgaytao().getMonth() + 1))
//                        .setMessage(details)
//                        .setPositiveButton("Đóng", null)
//                        .show();
//            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hoadonmodel hoadon = hoadons.get(position);
        holder.bind(hoadon, listener);


        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(hoadon.isSelected());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int amount = Integer.parseInt(hoadon.getDebt());
            Integer hoadonID = hoadon.getHoadonID();
            if (isChecked) {
                selectionListener.onCheckBoxClicked(true, amount,hoadonID);
            } else {
                selectionListener.onCheckBoxClicked(false, -amount,hoadonID);
            }
        });;
    }



    @Override
    public int getItemCount() {
        return hoadons.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Hoadonmodel hoadon);
    }
    public interface ProductSelectionListener {
        void onCheckBoxClicked(boolean isChecked, int amount,int hoadonID);
    }


    ///checkbox
    public void setProductSelectionListener(ProductSelectionListener listener) {
        this.selectionListener = listener;
    }
}

