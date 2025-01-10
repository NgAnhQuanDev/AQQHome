package com.example.aqqhome.ui.adapter;
import static com.example.aqqhome.utils.utils.url2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.ui.order.HoadonActivity;
import com.example.aqqhome.R;
import com.example.aqqhome.ui.order.ThanhToanXongActivity;
import com.example.aqqhome.model.ThongBaoModel;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.utils.MyPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ViewHolder> {
    private List<ThongBaoModel> thongbaos;
    private final Context context;
    private String RoomName;
    private String name;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text,tv_time;
        private RelativeLayout nofi;

        public ViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
            nofi = view.findViewById(R.id.nofi);
            tv_time = view.findViewById(R.id.tv_time);

        }
    }

    public ThongBaoAdapter(List<ThongBaoModel> thongbaos,Context context) {
        this.thongbaos = thongbaos;
        this.context = context;
    }

    @Override
    public ThongBaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nofi, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ThongBaoAdapter.ViewHolder holder, int position) {
        ThongBaoModel thongbao = thongbaos.get(position);
            holder.tv_time.setText(thongbao.getNgay());
            String userType = MyPref.INSTANCE.get(context, "UserInfo", "Type").toString();
        RoomName = MyPref.INSTANCE.get(context, "RoomID", "RoomName");
        if (RoomName == null) {
            RoomName = "defaultRoomName";
        }

        name = MyPref.INSTANCE.get(context, "UserInfo", "name");
        if (name == null) {
            name = "defaultName";
        }
            if (userType.equals("1") && thongbao.getType().equals("2") || userType.equals("1") && thongbao.getType().equals("3")) {

                holder.text.setText("Phòng " +thongbao.getRoomID()+" đã thanh toán hóa đơn #"+thongbao.getHoadonID());
                holder.text.setText(thongbao.getText());


            }
            else if (userType.equals("0")) {

                if (thongbao.getType().equals("1")) {
                    holder.text.setText("Phòng "+ RoomName +" đã có hóa đơn mới");
                } else if (thongbao.getType().equals("2")) {
                    holder.text.setText(name+" đã thanh toán cho hóa đơn #"+thongbao.getHoadonID());
                } else {
                    holder.text.setText(thongbao.getText());
                }
            }




            if(thongbao.getSeen().equals("0")){
                holder.nofi.setBackgroundColor(Color.parseColor("#CACEF1"));
                holder.text.setTypeface(null, Typeface.BOLD);
            }
        holder.nofi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url2)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiAQQHome apiAQQHome = retrofit.create(ApiAQQHome.class);
                Call<ThongBaoModel> call = apiAQQHome.capnhapseen(thongbao.getIDTB());
                call.enqueue(new Callback<ThongBaoModel>() {

                    @Override
                    public void onResponse(Call<ThongBaoModel> call, Response<ThongBaoModel> response) {
                        if(thongbao.getType().equals("1")){
                            Intent intent = new Intent(context, HoadonActivity.class);
                            context.startActivity(intent);
                        }
                        else if(thongbao.getType().equals("2")){
                            Intent intent = new Intent(context, ThanhToanXongActivity.class);
                            int firstElement = Integer.parseInt(thongbao.getHoadonID());
                            intent.putExtra("FIRST_ELEMENT", firstElement);


                            context.startActivity(intent);
                        }



                    }

                    @Override
                    public void onFailure(Call<ThongBaoModel> call, Throwable t) {

                    }
                });

            }
        });
        }



    @Override
    public int getItemCount() {
        return thongbaos.size();
    }
}
