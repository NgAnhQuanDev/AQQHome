package com.example.aqqhome.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import static com.example.aqqhome.utils.utils.url_image;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.aqqhome.ui.ActivityDichvu.NgonNguActivity;

import com.example.aqqhome.ui.auth.LoginActivity;
import com.example.aqqhome.R;
import com.example.aqqhome.model.Responsemodel;
import com.example.aqqhome.model.roommodel;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.ui.user.InfoActivity;
import com.example.aqqhome.utils.RetrofitClient;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CaidatFragment extends Fragment {

    private LinearLayout lin1,lin3,lin4;
    private LinearLayout dangxuat;
    private ImageView avatar;
    Fragment fragment;
    private String url;
    private static final int REQUEST_IMAGE_PICK = 1001;
    private static final int PERMISSION_REQUEST_CODE = 1002;

    private SharedPreferences sharedPreferences, sharedPreferencess;
    private String UserID;

    public CaidatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caidat, container, false);

        dangxuat = view.findViewById(R.id.dangxuat);
        lin1 = view.findViewById(R.id.lin1);
        avatar = view.findViewById(R.id.avatar);
        lin3 = view.findViewById(R.id.lin3);
        lin4 = view.findViewById(R.id.lin4);


        sharedPreferences = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        sharedPreferencess = requireActivity().getSharedPreferences("RoomID", MODE_PRIVATE);

        UserID = sharedPreferences.getString("UserID", "");
        String imageUrl = UserID+ ".jpg";
        if(imageUrl == null || imageUrl.equals("")){
            avatar.setImageResource(R.drawable.avatar);
        }
        else {
            url = url_image +imageUrl;
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new CircleCrop()) // Áp dụng hiệu ứng làm tròn
                    .override(200, 200); // Kích thước hình tròn
            Glide.with(this)
                    .load(url)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .apply(requestOptions)
                    .into(avatar);
        }

        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
            }
        });
        lin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NgonNguActivity.class);
                startActivity(intent);
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), avatar);
                popupMenu.getMenuInflater().inflate(R.menu.avatar_dropdown_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id == R.id.view_image) {
                            final Dialog dialog = new Dialog(view.getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_large_avatar);

                            ImageView largeAvatarImageView = dialog.findViewById(R.id.largeAvatarImageView);

                            Glide.with(view.getContext())
                                    .load(url)
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
                            return true;
                        }
                         else if (id == R.id.choose_new_avatar) {
                            Intent a = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(a, REQUEST_IMAGE_PICK);
                            return true;

                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
        dangxuat.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Xác nhận", (dialog, which) -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        SharedPreferences.Editor editor1 = sharedPreferencess.edit();
                        editor.clear();
                        editor.apply();
                        editor1.clear();
                        editor1.apply();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();

                    })
                    .setNegativeButton("Quay lại", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });

        lin1.setOnClickListener(v -> {
            showHouseInfoDialog();
        });

        return view;
    }
    private void showHouseInfoDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_house_info, null);
        TextView toanha = view.findViewById(R.id.toanha);
        TextView diachi = view.findViewById(R.id.diachi);
        TextView sotang = view.findViewById(R.id.sotang);
        TextView hotline = view.findViewById(R.id.hotline);
        TextView tencanho = view.findViewById(R.id.tencanho);
        TextView dientich = view.findViewById(R.id.dientich);
        TextView tang = view.findViewById(R.id.tang);

        sharedPreferences = getActivity().getSharedPreferences("RoomID", MODE_PRIVATE);
        String ApartmentID = sharedPreferences.getString("ApartmentID", "");
        String RoomName = sharedPreferences.getString("RoomName", "");
        SharedPreferences sharedPreferencess = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        String token = sharedPreferencess.getString("JWT", "");



        Retrofit retrofit = com.example.aqqhome.network.RetrofitClient.getRetrofitInstance();
        ApiAQQHome apiAQQHome = retrofit.create(ApiAQQHome.class);
        Call<roommodel> call = apiAQQHome.getthongtin(token, ApartmentID);
        call.enqueue(new Callback<roommodel>() {
            @Override
            public void onResponse(Call<roommodel> call, Response<roommodel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    roommodel roomModel = response.body();
                    toanha.setText(String.format("Tòa Nhà: %s", roomModel.getNameApartment()));
                    diachi.setText(String.format("Địa Chỉ: %s", roomModel.getAddress()));
                    hotline.setText(String.format("Hotline: %s", roomModel.getManagerPhoneNumber()));
                    sotang.setText(String.format("Số Tầng: %s", roomModel.getFloors()));
                    tencanho.setText(String.format("Tên Căn Hộ: %s", RoomName));
                    dientich.setText(String.format("Diện Tích: %s m²", "75"));
                    tang.setText(String.format("Tầng: %s","14"));
                } else {
                    Toast.makeText(getContext(), "Không thể lấy thông tin. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<roommodel> call, Throwable t) {
                Toast.makeText(getContext(), "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view)
                .setPositiveButton("OK", (dialog, id) -> {

                    dialog.dismiss();
                });
        builder.create().show();
    }



    private void chooseImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImageFromGallery();
            } else {
                // TODO: Show a message that you need permission
            }
        }
    }
    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            uploadImageToServer(selectedImage);
        }
    }

    private void uploadImageToServer(Uri selectedImage) {
        ApiAQQHome apiAQQHome = RetrofitClient.INSTANCE.getInstance();
        File file = new File(getPathFromUri(selectedImage));


        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), UserID);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", UserID + ".jpg", requestFile);
        Call<Responsemodel> call = apiAQQHome.uploadImage(userIdBody, imagePart);
        call.enqueue(new Callback<Responsemodel>() {
            @Override
            public void onResponse(Call<Responsemodel> call, Response<Responsemodel> response) {
                if (response.isSuccessful()) {
                    Responsemodel ketqua = response.body();
                    if(ketqua.isSuccess()){
                        Toast.makeText(getActivity(), "Đổi ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                        //sang fragment home
                        fragment = new HomeFragment();
                        replaceFragment(fragment);


                    } else {
                        Toast.makeText(getActivity(), "Server trả về lỗi: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Request không thành công. Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responsemodel> call, Throwable t) {
                Toast.makeText(getActivity(), "Đổi ảnh đại diện thất bại do không kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getPathFromUri(Uri uri) {
        String path = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        }
        return path;
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}
