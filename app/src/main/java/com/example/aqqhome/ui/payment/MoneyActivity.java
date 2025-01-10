package com.example.aqqhome.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aqqhome.R;
import com.example.aqqhome.utils.Zalopay.AppInfo;
import com.example.aqqhome.utils.Zalopay.CreateOrder;
import com.example.aqqhome.model.bankmodel;
import com.example.aqqhome.model.usermodel2;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.network.RetrofitClient;
import com.example.aqqhome.ui.user.MainActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class MoneyActivity extends AppCompatActivity {
    private View naptien,viewContactt;
    private ImageView back;
    private TextView sotien;
    private String amount ;
    private String UserID;
    private  String Sotiennap;
    private final String thoigiannap = "CURRENT_TIMESTAMP";
    private Retrofit retrofit;
    private String token;
    private Spinner spinner;
    private RadioButton radio_zalopay,radio_momo;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        retrofit = RetrofitClient.getRetrofitInstance();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        naptien = findViewById(R.id.naptien);
        sotien = findViewById(R.id.sotien);
        back = findViewById(R.id.back);
        viewContactt = findViewById(R.id.viewContactt);
        UserID = sharedPreferences.getString("UserID", "");
        back.setOnClickListener(v -> {
            String type = sharedPreferences.getString("Type", "");
            if (type.equals("1")) {
                Intent i = new Intent(MoneyActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } else if (type.equals("0")) {
                Intent i = new Intent(MoneyActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        viewContactt.setOnClickListener(v -> {
            Intent i = new Intent(MoneyActivity.this, LichsunapActivity.class);
            startActivity(i);
        });
        ApiAQQHome apiAQQHome = retrofit.create(ApiAQQHome.class);
        token = sharedPreferences.getString("JWT", "");
        Call<usermodel2> call = apiAQQHome.getmoney(token,UserID);
        call.enqueue(new retrofit2.Callback<usermodel2>() {
            @Override
            public void onResponse(Call<usermodel2> call, retrofit2.Response<usermodel2> response) {
                if (response.isSuccessful()) {
                    String money = response.body().getMoney();
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    String formattedMoney = formatter.format(Double.parseDouble(money));
                    sotien.setText(formattedMoney);
                } else {
                    Toast.makeText(MoneyActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<usermodel2> call, Throwable t) {
                Toast.makeText(MoneyActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

        naptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MoneyActivity.this);
                bottomSheetDialog.setContentView(R.layout.naptien_bottomsheet_layout);
                bottomSheetDialog.show();
                MaterialButton button_topup = bottomSheetDialog.findViewById(R.id.button_topup);
                spinner = bottomSheetDialog.findViewById(R.id.spinner);
                back = bottomSheetDialog.findViewById(R.id.back);
                radio_zalopay = bottomSheetDialog.findViewById(R.id.radio_zalopay);
                radio_momo = bottomSheetDialog.findViewById(R.id.radio_momo);
                back.setOnClickListener(v1 -> {
                    bottomSheetDialog.dismiss();
                });
                button_topup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinner.getSelectedItem().toString().equals("Vui lòng chọn giá trị nạp tiền          ◣")) {
                            Toast.makeText(MoneyActivity.this, "Vui lòng chọn giá trị", Toast.LENGTH_SHORT).show();
                        } else {
                            if (radio_zalopay.isChecked()) {
                                Naptien();
                                bottomSheetDialog.dismiss();
                            } else if (radio_momo.isChecked()) {
                                Toast.makeText(MoneyActivity.this, "Cổng thanh toán đang bảo trì \n Vui lòng quay lại sau", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MoneyActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    private void Naptien() {
        amount = spinner.getSelectedItem().toString().replaceAll("\\.", "");
        Sotiennap = spinner.getSelectedItem().toString().replaceAll("\\.", "");
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("returncode");

            if (code.equals("1")) {
                String tokenn = data.getString("zptranstoken");

                ZaloPaySDK.getInstance().payOrder(MoneyActivity.this, tokenn, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        ApiAQQHome apiAQQHome = retrofit.create(ApiAQQHome.class);
                        String Id = UserID;
                        String MaGD = "Thanh toán bằng ZaloPay";
                        Call<bankmodel> call = apiAQQHome.naptien(token,UserID,Id,MaGD, Sotiennap, thoigiannap);
                        call.enqueue(new retrofit2.Callback<bankmodel>() {
                            @Override
                            public void onResponse(Call<bankmodel> call, retrofit2.Response<bankmodel> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(MoneyActivity.this, "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                                    refreshBalance();
                                } else {
                                    Toast.makeText(MoneyActivity.this, "Nạp tiền thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<bankmodel> call, Throwable t) {
                                Toast.makeText(MoneyActivity.this, "Phương thức thanh toán bị lỗi, vui lòng quay lại sau " +t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        Toast.makeText(MoneyActivity.this, "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        Toast.makeText(MoneyActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshBalance() {
        ApiAQQHome apiAQQHome = retrofit.create(ApiAQQHome.class);
        Call<usermodel2> call = apiAQQHome.getmoney(token,UserID);
        call.enqueue(new retrofit2.Callback<usermodel2>() {
            @Override
            public void onResponse(Call<usermodel2> call, retrofit2.Response<usermodel2> response) {
                if (response.isSuccessful()) {
                    String money = response.body().getMoney();
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    String formattedMoney = formatter.format(Double.parseDouble(money));
                    sotien.setText(formattedMoney);
                } else {
                    Toast.makeText(MoneyActivity.this, "Không thể lấy số tiền mới", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<usermodel2> call, Throwable t) {
                Toast.makeText(MoneyActivity.this, "Lỗi khi lấy số tiền mới: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
