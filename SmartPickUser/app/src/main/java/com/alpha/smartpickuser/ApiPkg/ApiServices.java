package com.alpha.smartpickuser.ApiPkg;

import com.alpha.smartpickuser.bookPkg.bookingPkgmodel.ApplyPromoCodeModel;
import com.alpha.smartpickuser.bookPkg.bookingPkgmodel.BookingAmountModel;
import com.alpha.smartpickuser.bookPkg.bookingPkgmodel.BookingModle;
import com.alpha.smartpickuser.bookPkg.goodtypesPkg.cartmodelPkg.CardModel;
import com.alpha.smartpickuser.editProfilePkg.getuserPkgmodel.UserProfileModle;
import com.alpha.smartpickuser.editProfilePkg.userPkgmodel.UpDatedUserProfileModle;
import com.alpha.smartpickuser.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpickuser.initial.otpverficationPkg.OtpModle;
import com.alpha.smartpickuser.initial.signupPkg.SignUpModle;
import com.alpha.smartpickuser.livetrackPkg.driverLiveTracking.DriverRootModel;
import com.alpha.smartpickuser.notificationPkg.getNotificationListPkg.GetNotificationListModel;
import com.alpha.smartpickuser.notificationPkg.getNotificationListPkg.NotificationSmsCount;
import com.alpha.smartpickuser.notificationPkg.getNotificationListPkg.NotificationreadModel;
import com.alpha.smartpickuser.ridehistoryPkg.getbookingPkg.GetBookingModle;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {
    @FormUrlEncoded
    @POST("Authentication/addBooking")
    Call<BookingModle> Booking(
            @Field("user_id") String str,
            @Field("pick_location") String str2,
            @Field("drop_location") String str3,
            @Field("vehicle_type") String str4,
            @Field("stuf_name") String str5,
            @Field("packaging_required") String str6,
            @Field("amount") String str7,
            @Field("payment_type") String str8,
            @Field("distance") String str9,
            @Field("receiver_name") String str10,
            @Field("receiver_mobilenumber") String str11,
            @Field("lift_facility") String str12,
            @Field("landmark") String str13,
            @Field("description") String str14,
            @Field("pick_date_time") String str15,
            @Field("pick_up_letter") String str16,
            @Field("pick_letter_time") String str17);

    @FormUrlEncoded
    @POST("Authentication/User_registration")
    Call<SignUpModle> SignUp(@Field("username") String str, @Field("phone_number") String str2, @Field("fcm_tokan") String str3);

    @FormUrlEncoded
    @POST("Authentication/getProfile")
    Call<UserProfileModle> Userprofile(@Field("user_type") String str, @Field("profile_id") String str2);

    @FormUrlEncoded
    @POST("Authentication/applyPromoCode")
    Call<ApplyPromoCodeModel> applyPromoCode(@Field("coupon_code") String str, @Field("amount") String str2);

    @FormUrlEncoded
    @POST("Authentication/bookingAmount")
    Call<BookingAmountModel> bookingAmount(@Field("vehicle_type") String str, @Field("distance") String str2, @Field("promocode_amount") String str3);

    @FormUrlEncoded
    @POST("Authentication/bookingAcceptReject")
    Call<GenerateOtpModle> bookingacception(@Field("driver_id") String str, @Field("booking_id") String str2, @Field("status") String str3);

    @FormUrlEncoded
    @POST("Patients/getDriverRoot")
    Call<DriverRootModel> getDriverRoot(@Field("booking_id") String str, @Field("driver_id") String str2);

    @FormUrlEncoded
    @POST("Authentication/getBookingamount")
    Call<BookingModle> getbooking(@Field("pick_location") String str, @Field("drop_location") String str2, @Field("vehicle_type") String str3, @Field("stuf_name") String str4, @Field("packaging_required") String str5, @Field("amount") String str6, @Field("payment_type") String str7, @Field("distance") String str8, @Field("receiver_name") String str9, @Field("receiver_mobilenumber") String str10, @Field("lift_facility") String str11, @Field("landmark") String str12, @Field("description") String str13, @Field("pick_date_time") String str14);

    @FormUrlEncoded
    @POST("Authentication/getvehiclebycatid")
    Call<CardModel> goodId(@Field("cat_id") String str);

    @FormUrlEncoded
    @POST("Authentication/patientNotificationList")
    Call<GetNotificationListModel> notificaitonList(@Field("patient_id") String str);

    @FormUrlEncoded
    @POST("Authentication/paitentreadUnreadNotification")
    Call<NotificationreadModel> notificaoticlear(@Field("patient_id") String str);

    @FormUrlEncoded
    @POST("Authentication/patientNotificationCount")
    Call<NotificationSmsCount> notificaoticout(@Field("user_id") String str);

    @FormUrlEncoded
    @POST("Authentication/generateOtp")
    Call<GenerateOtpModle> otp_generate(@Field("phone") String str, @Field("user_type") String str2);

    @FormUrlEncoded
    @POST("Authentication/getBookingbyuserid")
    Call<GetBookingModle>ridehistory(@Field("user_id") String str);

    @POST("Authentication/editProfile")
    @Multipart
    Call<UpDatedUserProfileModle> updateMyProfilepatient(
            @Part MultipartBody.Part part,
            @Part MultipartBody.Part part2,
            @Part MultipartBody.Part part3,
            @Part MultipartBody.Part part4,
            @Part MultipartBody.Part part5,
            @Part MultipartBody.Part part6,
            @Part MultipartBody.Part part7,
            @Part MultipartBody.Part part8,
            @Part MultipartBody.Part part9);

    @POST("Authentication/editProfile")
    @Multipart
    Call<UpDatedUserProfileModle> updateWithoutMyProfileuser(@Part MultipartBody.Part part, @Part MultipartBody.Part part2, @Part MultipartBody.Part part3, @Part MultipartBody.Part part4, @Part MultipartBody.Part part5, @Part MultipartBody.Part part6, @Part MultipartBody.Part part7, @Part MultipartBody.Part part8);

    @FormUrlEncoded
    @POST("Authentication/otpVarification")
    Call<OtpModle> verification_otp(@Field("otp") String str, @Field("mobile") String str2, @Field("user_type") String str3, @Field("fcm_token") String str4);
}
