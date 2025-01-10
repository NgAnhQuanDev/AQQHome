package com.example.aqqhome.network;

import com.example.aqqhome.model.ApiHistory;
import com.example.aqqhome.model.ApiNewFeed;
import com.example.aqqhome.model.ApiResponse;
import com.example.aqqhome.model.ApiResponsee;
import com.example.aqqhome.model.Congviecmodel;
import com.example.aqqhome.model.Congviecmodel2;
import com.example.aqqhome.model.Hoadonmodel;
import com.example.aqqhome.model.PheDuyetModel;
import com.example.aqqhome.model.PheDuyetModel2;
import com.example.aqqhome.model.Responsemodel;
import com.example.aqqhome.model.SoLuongCongViecModel;
import com.example.aqqhome.model.TestModell;
import com.example.aqqhome.model.ThongBaoModel;
import com.example.aqqhome.model.ThongBaoModel2;
import com.example.aqqhome.model.Thongkemodel;
import com.example.aqqhome.model.Xacminhpheduyetmodel;
import com.example.aqqhome.model.bankmodel;
import com.example.aqqhome.model.costsmodel;
import com.example.aqqhome.model.feemodel;
import com.example.aqqhome.model.gopymodel;
import com.example.aqqhome.model.gopymodel2;
import com.example.aqqhome.model.newfeedmodel;
import com.example.aqqhome.model.roommodel;
import com.example.aqqhome.model.roommodel2;
import com.example.aqqhome.model.servicesmodel;
import com.example.aqqhome.model.usermodel2;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiAQQHome {
    @POST("users/create")
    @FormUrlEncoded
    Call<usermodel2> dangki(
            @Field("Email") String Email,
            @Field("Pass") String Pass,
            @Field("FullName") String FullName,
            @Field("PhoneNumber") String PhoneNumber,
            @Field("uid") String uid
    );


    @GET("users/{UserID}/getMoney")
    Call<usermodel2> getmoney(
            @Header("Authorization") String token,
            @Path("UserID") String UserID
    );

    @POST("dangnhap")
    @FormUrlEncoded
    Call<usermodel2> dangnhap(
            @Field("Email") String Email,
            @Field("Pass") String Pass
    );

    @POST("checkcode")
    @FormUrlEncoded
    Call<roommodel2> checkcode(
            @Header("Authorization") String token,
            @Field("Code") String Code
    );


    @GET("apartments/details/{RoomID}/debt")
    Call<roommodel2> getbill(
            @Header("Authorization") String token,
            @Path("RoomID") String RoomID
    );


    @GET("apartments/{ApartmentID}")
    Call<roommodel> getthongtin(
            @Header("Authorization") String token,
            @Path("ApartmentID") String ApartmentID
    );

    @POST("apartmentdetails/create")
    @FormUrlEncoded
    Call<roommodel2> themcanho(
            @Header("Authorization") String token,
            @Field("RoomName") String RoomName,
            @Field("NumberOfFloors") String NumberOfFloors,
            @Field("Area") String Area,
            @Field("ParkingCardNumber") String ParkingCardNumber,
            @Field("Code") String Code,
            @Field("ApartmentID") String ApartmentID
    );

    @POST("users/{UserID}/naptien")
    @FormUrlEncoded
    Call<bankmodel> naptien(
            @Header("Authorization") String token,
            @Path("UserID") String UserID,
            @Field("UserID") String Id,
            @Field("token") String MaGD,
            @Field("Sotiennap") String Sotiennap,
            @Field("thoigiannap") String thoigiannap
    );

    @FormUrlEncoded
    @POST("gettoanha.php")
    Call<List<roommodel2>> gettoanha(@Field("ApartmentID") String apartmentID);


    @GET("apartments/{ApartmentID}/details")
    Call<ApiResponse> gettoanhaa(@Path("ApartmentID") String ApartmentID,
                                 @Header("Authorization") String token
    );


    @GET("users/{UserID}/getLichSuNap")
    Call<ApiHistory> getlichsunap(@Path("UserID") String UserID,
                                  @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("hoadon/thanhtoan")
    Call<Hoadonmodel> thanhtoan(
            @Header("Authorization") String token,
            @Field("UserID") String userID,
            @Field("HoadonID") String hoadonID,
            @Field("ApartmentID") String ApartmentID,
            @Field("AQQPay") String AQQPay
    );

    @POST("gettenchungcu.php")
    @FormUrlEncoded
    Call<roommodel> gettenchungcu(
            @Field("ApartmentID") String ApartmentID
    );

    @GET("apartments/{RoomID}/details/trangthai")
    Call<servicesmodel> trangthaichiphi(
            @Path("RoomID") String RoomID,
            @Header("Authorization") String token
    );
    @POST("trangthaichiphi.php")
    @FormUrlEncoded
    Call<costsmodel> trangthaichiphi2(
            @Field("ApartmentID") String ApartmentID

    );


    @POST("services/{RoomID}/updateOrInsert")
    @FormUrlEncoded
    Call<servicesmodel> capnhaptrangthai(
            @Header("Authorization") String token,
            @Path("RoomID") String RoomID,
            @Field("IsManagerServiceUsed") String IsManagerServiceUsed,
            @Field("IsGarbageServiceUsed") String IsGarbageServiceUsed

    );

    @GET("costs/{RoomID}/getchiphi")
    Call<feemodel> getchiphi(
            @Path("RoomID") String RoomID,
            @Header("Authorization") String token


    );

    @GET("admin/getfund/{ApartmentID}")
    Call<roommodel> getfund(
            @Header("Authorization") String token,
            @Path("ApartmentID") String ApartmentID

    );

    @POST("admingetchiphi.php")
    @FormUrlEncoded
    Call<feemodel> admingetchiphi(
            @Field("ApartmentID") String ApartmentID

    );

    @GET("apartments/{ApartmentID}/newfeed")
    Call<ApiNewFeed> getnewfeed(
            @Header("Authorization") String token,
            @Path("ApartmentID") String ApartmentID

    );

    @POST("apartments/{ApartmentID}/newfeed/add")
    @FormUrlEncoded
    Call<newfeedmodel> postbangtin(
            @Header("Authorization") String token,
            @Path("ApartmentID") String ApartmentID,
            @Field("ApartmentID") String AparID,
            @Field("RoomName") String RoomName,
            @Field("UserID") String UserID,
            @Field("Text") String text,
            @Field("Name") String Name,
            @Field("Url1") String Url1,
            @Field("Url2") String Url2,
            @Field("Url3") String Url3


    );

    @GET("apartments/details/{RoomID}/gethoadon")
    Call<List<Hoadonmodel>> gethoadon(

            @Path("RoomID") String RoomID,
            @Header("Authorization") String token


    );

    @POST("users/updateUser")
    @FormUrlEncoded
    Call<usermodel2> updateuser(
            @Header("Authorization") String token,
            @Field("UserID") String UserID,
            @Field("Pass") String Pass,
            @Field("Passold") String PassOld
    );

    @POST("testt.php")
    @Multipart
    Call<Responsemodel> uploadImage(
            @Part("UserID") RequestBody UserID,
            @Part MultipartBody.Part image
    );

    @POST("get_image.php")
    @FormUrlEncoded
    Call<Responsemodel> getImageByName(
            @Field("name") String name);


    @POST("getfund.php")
    @FormUrlEncoded
    Call<ThongBaoModel2> getthongbao(
            @Field("ApartmentID") String ApartmentID,
            @Field("RoomID") String RoomID);

    @POST("getthongtin.php")
    @FormUrlEncoded
    Call<ThongBaoModel> capnhapseen(
            @Field("IDTB") String IDTB);

    @POST("getmoney.php")
    @FormUrlEncoded
    Call<Hoadonmodel> thongtinhoadon(
            @Field("HoadonID") String HoadonID);


    @POST("guiyeucaupheduyet.php")
    @FormUrlEncoded
    Call<PheDuyetModel> guipheduyet(
            @Field("NameApartment") String NameApartment,
            @Field("name") String name,
            @Field("nameroom") String nameroom,
            @Field("tang") String tang,
            @Field("UserID") String UserID,
            @Field("masohopdong") String masohopdong

    );

    @POST("guigopy.php")
    @FormUrlEncoded
    Call<gopymodel> guigopy(
            @Field("ApartmentID") String ApartmentID,
            @Field("RoomID") String RoomID,
            @Field("UserID") String UserID,
            @Field("text") String text

    );

    @POST("getgopy.php")
    @FormUrlEncoded
    Call<gopymodel2> getgopy(
            @Field("RoomID") String RoomID


    );

    @POST("admingetgopy.php")
    @FormUrlEncoded
    Call<gopymodel2> admingetgopy(
            @Field("ApartmentID") String ApartmentID


    );

    @POST("getpheduyet.php")
    @FormUrlEncoded
    Call<PheDuyetModel2> getpheduyet(
            @Field("UserID") String UserID,
            @Field("ApartmentID") String ApartmentID


    );
    @POST("admingetpheduyet.php")
    @FormUrlEncoded
    Call<PheDuyetModel2> admingetpheduyet(
            @Field("UserID") String UserID,
            @Field("ApartmentID") String ApartmentID


    );

    @POST("Trangthaikichhoat.php")
    @FormUrlEncoded
    Call<PheDuyetModel> trangthaikichhoat(
            @Field("PheDuyetID") String PheDuyetID,
            @Field("kichhoat") String kichhoat,
            @Field("code") String code


    );

    @GET("getchungcu.php")
    Call<TestModell> getchungcu(

    );

    @POST("getsoluongthongbao.php")
    @FormUrlEncoded
    Call<ApiResponse> getsoluongthongbao(
            @Field("RoomID") String RoomID
    );

    @POST("getchungcutheoname.php")
    @FormUrlEncoded
    Call<ApiResponsee> getchungcutheoname(
            @Field("NameApartment") String NameApartment
    );

    @POST("getcode.php")
    @FormUrlEncoded
    Call<Xacminhpheduyetmodel> getcode(
            @Field("RoomName") String RoomName,
            @Field("ApartmentID") String ApartmentID,
            @Field("PheDuyetID") String PheDuyetID,
            @Field("kichhoat") String kichhoat
    );

    @POST("Trangthai.php")
    @FormUrlEncoded
    Call<costsmodel> trangthai(@FieldMap Map<String, String> fields);


    @POST("avatara.php")
    @Multipart
    Call<Responsemodel> uploadImagee(
            @Part MultipartBody.Part image
    );

    @POST("getcongviec.php")
    @FormUrlEncoded
    Call<Congviecmodel2> getcongviec(
            @Field("ApartmentID") String ApartmentID


    );
    @POST("getsoluongcv.php")
    @FormUrlEncoded
    Call<SoLuongCongViecModel> getsoluongcv(
            @Field("ApartmentID") String ApartmentID


    );

    @POST("thongke.php")
    @FormUrlEncoded
    Call<Thongkemodel> thongke(
            @Field("ApartmentID") String ApartmentID,
            @Field("Month") String Month


    );
    @POST("xoahoadon.php")
    @FormUrlEncoded
    Call<ApiResponsee> xoahoadon(
            @Field("HoadonID") String HoadonID


    );

    @POST("trangthaiphananh.php")
    @FormUrlEncoded
    Call<gopymodel> trangthaiphananh(
            @Field("GopyID") String GopyID,
            @Field("TrangThai") String TrangThai


    );
    @POST("themcongviec.php")
    @FormUrlEncoded
    Call<Congviecmodel> themcongviec(
            @Field("noidung") String noidung,
            @Field("Loai") String Loai,
            @Field("ngayhethan") String ngayhethan,
            @Field("ApartmentID") String ApartmentID


    );

    @POST("chotso.php")
    @FormUrlEncoded
    Call<ApiResponsee> chotso(
            @Field("ApartmentID") String ApartmentID


    );
    @POST("thongbao.php")
    @FormUrlEncoded
    Call<ApiResponsee> thongbao(
            @Field("apartmentID") String apartmentID,
            @Field("text") String text


    );





}




