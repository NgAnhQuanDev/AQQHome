package com.example.aqqhome.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.roommodel2;
import com.example.aqqhome.ui.admin.ChiTietCanHoActivity;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<roommodel2> roomList;
    private OnRoomClickListener listener;

    public RoomAdapter(List<roommodel2> roomList, OnRoomClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lin1;
        public TextView roomNameView;
        public TextView name,phone;

        public ViewHolder(View itemView) {
            super(itemView);
            lin1 = itemView.findViewById(R.id.lin1);
            roomNameView = itemView.findViewById(R.id.tvMaCanHo);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listcanho2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        roommodel2 room = roomList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRoomClick(room);
            }
        });
        holder.roomNameView.setText("Tầng "+room.getNumberOfFloors()+" - Căn hộ A."+room.getRoomName());
        if(room.getChusohuu().equals("")) {
            holder.name.setText("Chủ căn hộ: -");
        }else {
            holder.name.setText("Chủ căn hộ: "+ room.getChusohuu());
        }
        if(room.getSodienthoai().equals("")) {
            holder.phone.setText("Số điện thoại: -");

        }else {
            holder.phone.setText("Số điện thoại: " +room.getSodienthoai());
        }

        holder.lin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChiTietCanHoActivity.class);
                intent.putExtra("name",  room.getRoomName());
                intent.putExtra("id",  room.getRoomID());
                intent.putExtra("floor",  room.getNumberOfFloors());
                intent.putExtra("area",  room.getArea());
                intent.putExtra("code",  room.getCode());
                intent.putExtra("parking",  room.getParkingCardNumber());
                intent.putExtra("apartment",  room.getApartmentID());
                intent.putExtra("debt",  room.getDebt());
                intent.putExtra("amount",  room.getAmount());
                intent.putExtra("chusohuu",  room.getChusohuu());
                intent.putExtra("socancuoc",  room.getSocancuoc());
                intent.putExtra("sodienthoai",  room.getSodienthoai());
                intent.putExtra("mahopdong",  room.getMahopdong());
                intent.putExtra("ngaydangky",  room.getNgaydangky());
                intent.putExtra("ngaybangiao",  room.getNgaybangiao());

                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
}