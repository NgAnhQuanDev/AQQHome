package com.example.aqqhome.ui.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.ChatModel;

import com.example.aqqhome.utils.MyPref;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatModel> list;
    private final Context context;

    private String ApartmentID;

    public ChatAdapter(List<ChatModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.ApartmentID = MyPref.INSTANCE.get(context,"UserInfo","Manager");
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, it_tv_tinnhancuoi;
        public ImageView img_stt, img_image;

        public ChatViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.it_tv_name);
            it_tv_tinnhancuoi = itemView.findViewById(R.id.it_tv_tinnhancuoi);
            img_stt = itemView.findViewById(R.id.it_tv_tt);
            img_image = itemView.findViewById(R.id.it_img_avt);

        }
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatModel account = list.get(position);

        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

        if (account.getDaxem() != null && !account.getDaxem().equals("1")) {
            holder.tv_name.setTextColor(Color.parseColor("#000000"));
            holder.tv_name.setText("Phòng " + account.getRoomName());
            holder.tv_name.setTypeface(boldTypeface);

            holder.it_tv_tinnhancuoi.setText(account.getLastMessage());
            holder.it_tv_tinnhancuoi.setTextColor(Color.parseColor("#000000"));
            holder.it_tv_tinnhancuoi.setTypeface(boldTypeface);
        } else {
            holder.tv_name.setTextColor(Color.parseColor("#222222")); // Sửa màu sắc về mặc định
            holder.tv_name.setText("Phòng " + account.getRoomName());
            holder.tv_name.setTypeface(Typeface.DEFAULT); // Sửa kiểu chữ về mặc định

            holder.it_tv_tinnhancuoi.setText(account.getLastMessage());
            holder.it_tv_tinnhancuoi.setTextColor(Color.parseColor("#222222")); // Sửa màu sắc về mặc định
            holder.it_tv_tinnhancuoi.setTypeface(Typeface.DEFAULT); // Sửa kiểu chữ về mặc định
        }

        holder.img_stt.setImageResource(R.drawable.dot);
        holder.img_image.setImageResource(R.drawable.logoaqqhome);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailChatActivity.class);
                intent.putExtra("RoomID", account.getRoomID());
                intent.putExtra("ApartmentID", ApartmentID);
                intent.putExtra("RoomName", account.getRoomName());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filter(ArrayList<ChatModel> filter) {
        list = filter;
        notifyDataSetChanged();
    }
}
