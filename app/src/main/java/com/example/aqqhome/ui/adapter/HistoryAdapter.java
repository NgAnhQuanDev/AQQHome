package com.example.aqqhome.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.bankmodel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<bankmodel> transactions;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView;
        public TextView amountTextView;
        public TextView timeTextView;

        public TextView token;
        public ImageView icon,icon2;

        public ViewHolder(View view) {
            super(view);
            idTextView = view.findViewById(R.id.transaction_id);
            amountTextView = view.findViewById(R.id.transaction_amount);
            timeTextView = view.findViewById(R.id.transaction_time);
            token = view.findViewById(R.id.token);
            icon = view.findViewById(R.id.icon);
            icon2 = view.findViewById(R.id.icon2);
        }
    }

    public HistoryAdapter(List<bankmodel> transactions) {
        this.transactions = transactions;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        bankmodel transaction = transactions.get(position);
        holder.idTextView.setText("Mã giao dịch: #"+transaction.getMoneyID());
        holder.timeTextView.setText(transaction.getThoigiannap());
        String fm = String.valueOf(transaction.getSotiennap());
        Locale localeVN = new Locale("vi", "VN");
        fm = NumberFormat.getCurrencyInstance(localeVN).format(Double.parseDouble(fm));
        if (transaction.getType() != null && transaction.getType().equals("0")) {

            holder.amountTextView.setText("+" + fm);

        }

        else if (transaction.getType() != null && transaction.getType().equals("1")){
            holder.amountTextView.setText("-" + fm);
            holder.icon.setImageResource(R.drawable.spending);
            holder.icon2.setImageResource(R.drawable.logoaqqhome);
        }
        holder.token.setText(transaction.getToken());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}