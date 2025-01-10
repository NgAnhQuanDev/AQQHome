package com.example.aqqhome.ui.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aqqhome.R;
import com.example.aqqhome.model.ChatModel2;
import com.example.aqqhome.utils.MyPref;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter2 extends RecyclerView.Adapter<ChatAdapter2.ViewHolder> {

    private List<ChatModel2> messageList;
    private String roomID;
    private String Type;
    private static final int MESSAGE_TYPE_LEFT = 0;
    private static final int MESSAGE_TYPE_RIGHT = 1;
    private Context context;
    private ArrayList<ChatModel2> chatList;


    public ChatAdapter2(Context context, ArrayList<ChatModel2> chatList) {
        this.context = context;
        this.chatList = chatList;
        this.roomID = MyPref.INSTANCE.get(context, "RoomID", "RoomID");
        this.Type = MyPref.INSTANCE.get(context, "UserInfo", "Type");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sender, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receiver, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatModel2 chat = chatList.get(position);
        holder.tv_message_chat.setText(chat.getMessage());
        holder.tv_time_chat.setText(chat.getTime());
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_message_chat,tv_time_chat;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_message_chat = itemView.findViewById(R.id.tv_message_chat);
            tv_time_chat = itemView.findViewById(R.id.tv_time_chat);
        }
    }
    @Override
    public int getItemViewType(int position) {
        boolean isAdmin = chatList.get(position).getAdmin();

        if ("1".equals(Type)) {
            return isAdmin ? MESSAGE_TYPE_RIGHT : MESSAGE_TYPE_LEFT;
        } else {
            return isAdmin ? MESSAGE_TYPE_LEFT : MESSAGE_TYPE_RIGHT;
        }
}
}
