package com.example.aqqhome.ui.payment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aqqhome.R;
import com.example.aqqhome.ui.order.ThanhToanXongActivity;
import com.example.aqqhome.utils.Zalopay.AppInfo;
import com.example.aqqhome.utils.Zalopay.CreateOrder;
import com.example.aqqhome.model.Hoadonmodel;
import com.example.aqqhome.network.ApiAQQHome;
import com.example.aqqhome.network.RetrofitClient;
import com.example.aqqhome.utils.MyPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import vn.momo.momo_partner.AppMoMoLib;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;


public class ThanhToanActivity extends AppCompatActivity {
    private ImageView back;
    private Button continue_button;
    private String RoomID;
    private SharedPreferences sharedPreferences;
    private RadioButton radio_zalopay;
    private RadioButton radio_aqqpay;
    private RadioButton radio_momopay;
    private ProgressBar progressBar;
    private ArrayList<Integer> hoadonID;

    private String amount;

    private Retrofit retrofit;
    String hoadonIDs;
    String AQQPay = "0";
    private ProgressDialog progressDialog;


    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "HoangNgoc";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "HoangNgoc";
    private String description = "Thanh toán dịch vụ ABC";
    private Button thanhtoan;
    private String receivedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);

        back = findViewById(R.id.back);
        retrofit = RetrofitClient.getRetrofitInstance();
        continue_button = findViewById(R.id.continue_button);
        radio_zalopay = findViewById(R.id.radio_zalopay);
        radio_aqqpay = findViewById(R.id.radio_aqqpay);
        radio_momopay = findViewById(R.id.radio_momo);

        back.setOnClickListener(v -> {
            finish();
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        Intent intent = getIntent();
        receivedData = intent.getStringExtra("sono");
        RoomID = intent.getStringExtra("Room");
        amount = receivedData;
        hoadonID = getIntent().getIntegerArrayListExtra("hoadonIDs");

        hoadonIDs = TextUtils.join(",", hoadonID);
        TextView tong_tien = findViewById(R.id.tong_tien);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formatt = format.format(Double.parseDouble(receivedData));

        tong_tien.setText(formatt);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_aqqpay.isChecked()==true){
                    AQQPay = "1";
                    ThanhToan(AQQPay);

                }
                if (radio_zalopay.isChecked()==true){
                    ThanhToanZaloPay();
                }
                if (radio_momopay.isChecked()==true){
                    ThanhToanMomo();
                }
                if (radio_aqqpay.isChecked()==false && radio_zalopay.isChecked()==false && radio_momopay.isChecked()==false){
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                }
            }

        });



    }

    private void ThanhToanMomo() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", "1111"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "11111"); //gán nhãn
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0");
        eventValue.put("description", description); //mô tả đơn hàng - short description
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    String AQQPay = "0";
                    ThanhToan("0");
                    Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    Log.e("data",data.getStringExtra("data"));
                    Log.e("phonenumber",data.getStringExtra("phonenumber"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server

                    } else {
                        Toast.makeText(this, "Không nhận được token", Toast.LENGTH_SHORT).show();
                    }
                } else if(data.getIntExtra("status", -1) == 1) {

                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Toast.makeText(this, "message: " + message, Toast.LENGTH_SHORT).show();
                } else if(data.getIntExtra("status", -1) == 2) {

                    Toast.makeText(this, "Hủy thanh toán", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không nhận được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Hủy thanh toán", Toast.LENGTH_SHORT).show();
        }
    }

    private void ThanhToanZaloPay() {
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("returncode");

            if (code.equals("1")) {
                String tokenn = data.getString("zptranstoken");

                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, tokenn, "demozpdkk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        AQQPay = "0";
                        ThanhToan(AQQPay);
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ThanhToan(String AQQPay) {
        if (AQQPay.equals("1")) {
            // Show the confirmation dialog first
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận thanh toán");
            builder.setMessage("Bạn có chắc chắn muốn thanh toán?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progressDialog = new ProgressDialog(ThanhToanActivity.this);
                    progressDialog.setMessage("Vui lòng đợi...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            performPayment(AQQPay);
                        }
                    }, 5000);
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog and do not perform the payment
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else {
            performPayment(AQQPay);
        }
    }

    private void performPayment(String AQQPay) {
        ApiAQQHome apiAQQHome = retrofit.create(ApiAQQHome.class);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String token = sharedPreferences.getString("JWT", "");
        String UserID = sharedPreferences.getString("UserID", "");
        String ApartmentID = MyPref.INSTANCE.get(this,"RoomID","ApartmentID");
        Call<Hoadonmodel> call = apiAQQHome.thanhtoan(token, UserID, hoadonIDs,ApartmentID, AQQPay);

        call.enqueue(new retrofit2.Callback<Hoadonmodel>() {
            @Override
            public void onResponse(Call<Hoadonmodel> call, retrofit2.Response<Hoadonmodel> response) {
                if (response.isSuccessful()) {



                    Intent intent = new Intent(ThanhToanActivity.this, ThanhToanXongActivity.class);
                    int firstElement = hoadonID.get(0);
                    intent.putExtra("FIRST_ELEMENT", firstElement);
                    intent.putExtra("TONGTIEN", receivedData);


                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(ThanhToanActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Hoadonmodel> call, Throwable t) {
                Toast.makeText(ThanhToanActivity.this, "Phương thức thanh toán bị lỗi, vui lòng quay lại sau " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}