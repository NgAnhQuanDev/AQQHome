package com.example.aqqhome.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aqqhome.R;
import com.example.aqqhome.model.Hoadonmodel;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class DaThanhToanAdapter extends RecyclerView.Adapter<DaThanhToanAdapter.ViewHolder> {
    private List<Hoadonmodel> hoadons;


    public DaThanhToanAdapter(List<Hoadonmodel> hoadons) {
        this.hoadons = hoadons;



    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_time,tv_ma,tv_money,tv_noidung;


        public ViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_time = view.findViewById(R.id.tv_time);
            tv_ma = view.findViewById(R.id.tv_ma);
            tv_money = view.findViewById(R.id.tv_money);
            tv_noidung = view.findViewById(R.id.tv_noidung);

        }

        public void bind(final Hoadonmodel hoadon, final OnItemClickListener listener) {
            tv_name.setText("Hóa đơn tháng " + (hoadon.ngaytao.getMonth() + 1)+ "/" + (hoadon.ngaytao.getYear() + 1900));
            String fm = String.valueOf(hoadon.getDebt());
            Locale localeVN = new Locale("vi", "VN");
            fm = NumberFormat.getCurrencyInstance(localeVN).format(Double.parseDouble(fm));
            tv_money.setText(fm);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            tv_time.setText("Ngày thanh toán: " +sdf.format(hoadon.ngaytao));
            tv_ma.setText("Mã hóa đơn: " + hoadon.getHoadonID());
            if (hoadon.getPhuongthuc()!= null &&hoadon.getPhuongthuc().equals("1"))  tv_noidung.setText("Phương thức thanh toán: Ví điện tử AQQPay" );
            else tv_noidung.setText("Phương thức thanh toán: Ví điện tử ZaloPay");





        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhtoan, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hoadonmodel hoadon = hoadons.get(position);
        holder.bind(hoadon, null);

    }

    @Override
    public int getItemCount() {
        return hoadons.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Hoadonmodel hoadon);

    }
}
