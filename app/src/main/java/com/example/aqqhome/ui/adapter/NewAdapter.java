package com.example.aqqhome.ui.adapter;
import static com.example.aqqhome.utils.utils.url2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.aqqhome.R;
import com.example.aqqhome.model.newfeedmodel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {
    private List<newfeedmodel> newfeed;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameview,tv_comment,tv_status;
        public TextView timeview;
        public TextView tv_name;
        public TextView textview;
        public ImageView avatarImageView;
        public ImageView imgView_like,imgView_likee,imgView_likeee;
        public ShapeableImageView imgView_proPic;



        public ViewHolder(View view) {
            super(view);
              tv_name = view.findViewById(R.id.tv_name);
              imgView_proPic = view.findViewById(R.id.imgView_proPic);
              tv_status = view.findViewById(R.id.tv_status);
            imgView_like = view.findViewById(R.id.imgView_like);
            imgView_likee = view.findViewById(R.id.imgView_likee);
            imgView_likeee = view.findViewById(R.id.imgView_likeee);


        }
    }

    public NewAdapter(List<newfeedmodel> newfeed) {
        this.newfeed = newfeed;
    }

    @Override
    public NewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_newfeed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewAdapter.ViewHolder holder, int position) {
        newfeedmodel neww = newfeed.get(position);

        holder.tv_name.setText(neww.getName());
        holder.tv_status.setText(neww.getText());
        loadImage(holder.imgView_proPic, neww.getAvatar_path());

        if(neww.getUrl1() != null) {
            holder.imgView_like.setVisibility(View.VISIBLE);
            loadImage(holder.imgView_like, neww.getUrl1());
        } else {
            holder.imgView_like.setVisibility(View.GONE);
        }
        holder.imgView_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_large_avatar);

                ImageView largeAvatarImageView = dialog.findViewById(R.id.largeAvatarImageView);

                Glide.with(v.getContext())
                        .load(url2 + neww.getUrl1())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(largeAvatarImageView);
                //Đóng dialog khi nhấn nút đóng
                ImageButton closeButton = dialog.findViewById(R.id.closeButton);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        if(neww.getUrl2() != null) {
            holder.imgView_likee.setVisibility(View.VISIBLE);
            loadImage(holder.imgView_likee, neww.getUrl2());
        } else {
            holder.imgView_likee.setVisibility(View.GONE);
        }

        holder.imgView_likee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_large_avatar);

                ImageView largeAvatarImageView = dialog.findViewById(R.id.largeAvatarImageView);

                Glide.with(v.getContext())
                        .load(url2 + neww.getUrl2())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(largeAvatarImageView);
                //Đóng dialog khi nhấn nút đóng
                ImageButton closeButton = dialog.findViewById(R.id.closeButton);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        if(neww.getUrl3() != null) {
            holder.imgView_likeee.setVisibility(View.VISIBLE);
            loadImage(holder.imgView_likeee, neww.getUrl3());
        } else {
            holder.imgView_likeee.setVisibility(View.GONE);
        }
        holder.imgView_likeee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_large_avatar);

                ImageView largeAvatarImageView = dialog.findViewById(R.id.largeAvatarImageView);

                Glide.with(v.getContext())
                        .load(url2 + neww.getUrl3())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(largeAvatarImageView);

                ImageButton closeButton = dialog.findViewById(R.id.closeButton);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

    }

    private void loadImage(ImageView imageView, String imagePath) {
        String baseUrl = url2 ;
        String fullUrl = baseUrl + imagePath;

        Glide.with(imageView.getContext())
                .load(fullUrl)
                .placeholder(R.drawable.avatar)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.avatar)
                .into(imageView);

    }


    @Override
    public int getItemCount() {
        return (newfeed != null) ? newfeed.size() : 0;

    }
}
