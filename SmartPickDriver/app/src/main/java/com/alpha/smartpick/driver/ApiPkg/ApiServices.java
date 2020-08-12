package com.alpha.smartpick.driver.ApiPkg;

import com.alpha.smartpick.driver.editProfilePkg.getuserPkgmodel.UserProfileModle;
import com.alpha.smartpick.driver.editProfilePkg.userPkgmodel.UpDatedUserProfileModle;
import com.alpha.smartpick.driver.homeFragmentPkg.getbookingPkg.AvailableStatusModel;
import com.alpha.smartpick.driver.homeFragmentPkg.getbookingPkg.GetBookingModle;
import com.alpha.smartpick.driver.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpick.driver.initial.otpverficationPkg.OtpModle;
import com.alpha.smartpick.driver.initial.signupPkg.SignUpModle;
import com.alpha.smartpick.driver.livetrackPkg.gerroutePkg.GetRoutemodel;
import com.alpha.smartpick.driver.notificationPkg.getNotificaitonList.GetNotificationListModel;
import com.alpha.smartpick.driver.notificationPkg.getNotificaitonList.NotificationSmsCount;
import com.alpha.smartpick.driver.notificationPkg.getNotificaitonList.NotificationreadModel;
import com.alpha.smartpick.driver.ridehistoryPkg.getbookinghistoryModelPkg.GetBookingHistoryModle;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {
    @FormUrlEncoded
    @POST("Authentication/driver_registration")
    Call<SignUpModle> SignUp(@Field("username") String str, @Field("phone_number") String str2, @Field("fcm_tokan") String str3, @Field("Vehicle_Type") String str4, @Field("vehicle_number") String str5);

    @FormUrlEncoded
    @POST("Authentication/getProfile")
    Call<UserProfileModle> Userprofile(@Field("user_type") String str, @Field("profile_id") String str2);

    @FormUrlEncoded
    @POST("Authentication/bookingAcceptReject")
    Call<GenerateOtpModle> bookingacception(@Field("driver_id") String str, @Field("booking_id") String str2, @Field("status") String str3);

    @FormUrlEncoded
    @POST("Authentication/driverBookingDetailsByid")
    Call<GetBookingHistoryModle> bookinghistory(@Field("driver_id") String str);

    @FormUrlEncoded
    @POST("Authentication/driverLocation")
    Call<SignUpModle> driverLocation(@Field("booking_id") String str, @Field("driver_id") String str2, @Field("driver_location") String str3,@Field("latitude") String str4,@Field("longnitue") String str5,@Field("location_info") String str6);

    @FormUrlEncoded
    @POST("Authentication/getBookingRouteById")
    Call<GetRoutemodel> getBookingRouteById(@Field("booking_id") String str);

    @FormUrlEncoded
    @POST("Authentication/driverNotificationList")
    Call<GetNotificationListModel> notificaitonList(@Field("driver_id") String str, @Field("vehicle_type") String str2);

    @FormUrlEncoded
    @POST("Authentication/readUnreadNotification")
    Call<NotificationreadModel> notificaoticlear(@Field("driver_id") String str);

    @FormUrlEncoded
    @POST("Authentication/driverNotificationCount")
    Call<NotificationSmsCount> notificaoticout(@Field("driver_id") String str, @Field("vehicle_type") String str2);
    @FormUrlEncoded
    @POST("Authentication/driverAvailableStatus")
    Call<AvailableStatusModel> driverAvailableStatus(@Field("driver_id") String driver_id,
                                                     @Field("status") String status);

    @FormUrlEncoded
    @POST("Authentication/generateOtp")
    Call<GenerateOtpModle> otp_generate(@Field("phone") String str, @Field("user_type") String str2);
    @FormUrlEncoded
    @POST("Authentication/generateBookingOtp")
    Call<GenerateOtpModle> otp_generate_location(@Field("driver_id") String str,
                                                 @Field("booking_id") String str2,
                                                 @Field("mobile") String str3);

    @FormUrlEncoded
    @POST("Authentication/getBookingByVehicle")
    Call<GetBookingModle> ridehistory(@Field("vehicle_type") String str, @Field("driver_id") String str2);

    @POST("Authentication/editProfile")
    @Multipart
    Call<UpDatedUserProfileModle> updateMyProfilepatient(@Part MultipartBody.Part part, @Part MultipartBody.Part part2, @Part MultipartBody.Part part3, @Part MultipartBody.Part part4, @Part MultipartBody.Part part5, @Part MultipartBody.Part part6, @Part MultipartBody.Part part7, @Part MultipartBody.Part part8, @Part MultipartBody.Part part9);

    @POST("Authentication/editProfile")
    @Multipart
    Call<UpDatedUserProfileModle> updateWithoutMyProfileuser(@Part MultipartBody.Part part, @Part MultipartBody.Part part2, @Part MultipartBody.Part part3, @Part MultipartBody.Part part4, @Part MultipartBody.Part part5, @Part MultipartBody.Part part6, @Part MultipartBody.Part part7, @Part MultipartBody.Part part8);

    @FormUrlEncoded
    @POST("Authentication/otpVarification")
    Call<OtpModle> verification_otp(@Field("otp") String str, @Field("mobile") String str2, @Field("user_type") String str3, @Field("fcm_token") String str4);

    @FormUrlEncoded
    @POST("Authentication/verifyBookingOtp")
    Call<OtpModle> verification_otp_booking( @Field("driver_id") String str1,
                                             @Field("mobile") String str2,
                                            @Field("otp") String str,
                                            @Field("booking_id") String str3);
    @FormUrlEncoded
    @POST("Authentication/driverLocationUpdate")
    Call<SignUpModle> driverLocationUpdate(@Field("driver_id") String str,
                                     @Field("latitude") String str2,
                                     @Field("longnitue") String str3);


}
