package com.alpha.smartpick.driver.passengerPkg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpick.driver.initial.otpverficationPkg.OtpModle;
import com.alpha.smartpick.driver.notificationPkg.NotificationActivity;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.Constants;
import com.alpha.smartpick.driver.utility.CustomProgressbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapterA extends RecyclerView.Adapter<CustomAdapterA.ViewHolder>{

    Context context;
    ArrayList<LocationItem> arrayList;
    private ApiServices apiServices;
    private String userId;
    public CustomAdapterA(Context context, ArrayList<LocationItem> arrayList) {
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        userId = AppSession.getStringPreferences(context, Constants.USER_ID);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomAdapterA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_drop_location
                , parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterA.ViewHolder holder, int position) {
        holder.droplocation.setText(arrayList.get(position).getDroplocation());
        if (arrayList.get(position).getReciver_name().isEmpty()){
            holder.reciver_name.setVisibility(View.GONE);
            holder.tvstatusId.setVisibility(View.GONE);
        }else {
            holder. reciver_name.setText(context.getResources().getString(R.string.reciver_name)+" : "+arrayList.get(position).getReciver_name());
        }
        if (arrayList.get(position).getReciver_number().isEmpty()){
            holder.reciver_number.setVisibility(View.GONE);
            holder.tvstatusId.setVisibility(View.GONE);
        }else {
            holder.reciver_number.setText(context.getResources().getString(R.string.reciver_nubmer)+" : "+arrayList.get(position).getReciver_number());
        }
        if (arrayList.get(position).getIs_booking_verify().equalsIgnoreCase("0")){
            holder.tvstatusId.setText(context.getResources().getString(R.string.arrive));
        }else {
            holder.tvstatusId.setText(context.getResources().getString(R.string.booking_done));
        }
        holder.tvstatusId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.get(position).getIs_booking_verify().equalsIgnoreCase("0")){
                    //generate_otp(arrayList.get(position).getBooking_id(),arrayList.get(position).getReciver_number());
                    otp_dilog(arrayList.get(position).getBooking_id(),arrayList.get(position).getReciver_number());
                }else {
                    Toast.makeText(context, "Booking Done", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }



    public void otp_dilog(String booking_id,String numer) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dilog_otp_verify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        TextInputEditText etOTpNoId = dialog.findViewById(R.id.etOTpNoId);
        AppCompatImageView ivlogoutCloseId = dialog.findViewById(R.id.ivlogoutCloseId);
        RelativeLayout rlSendId = dialog.findViewById(R.id.rlSendId);
        rlSendId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             oTpVerification(booking_id,numer,etOTpNoId.getText().toString());
            }
        });
        ivlogoutCloseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        AppCompatTextView droplocation, email,reciver_name,reciver_number,tvstatusId;
        public ViewHolder( View itemView) {
            super(itemView);
            droplocation = (AppCompatTextView) itemView.findViewById(R.id.txt_d_loc);
            reciver_name =(AppCompatTextView) itemView.findViewById(R.id.reciver_name);
            reciver_number = (AppCompatTextView) itemView.findViewById(R.id.reciver_number);
            tvstatusId = (AppCompatTextView) itemView.findViewById(R.id.tvstatusId);
        }

        @Override
        public void onClick(View v) {
        }
    }

    private void generate_otp(String booking_id, String number) {
        CustomProgressbar.showProgressBar(context, false);
        apiServices.otp_generate_location(userId,booking_id,number).enqueue(new Callback<GenerateOtpModle>() {
            @Override
            public void onResponse(Call<GenerateOtpModle> call, Response<GenerateOtpModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GenerateOtpModle loginModle = response.body();
                    if (loginModle.getStatus()== true) {

                    } else {
                        Toast.makeText(context, ""+loginModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                CustomProgressbar.hideProgressBar();
                                jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GenerateOtpModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }
    private void oTpVerification(String booking_id,String number,String otp) {
        CustomProgressbar.showProgressBar(context, false);
        apiServices.verification_otp_booking(userId,number,otp,booking_id).enqueue(new Callback<OtpModle>() {
            @Override
            public void onResponse(Call<OtpModle> call, Response<OtpModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    OtpModle getRegistrationModle = response.body();
                    if (getRegistrationModle.getStatus() == true) {
                        Activity mainAct = (Activity) context;
                        Intent intent= new Intent(context, NotificationActivity.class);
                        mainAct.startActivity(intent);
                        mainAct.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        mainAct.finishAffinity();
                    }else {
                        Toast.makeText(context, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 400) {
                    if (!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            CustomProgressbar.hideProgressBar();
                            String message = jsonObject.getString("message");
                            Toast.makeText(context, "" +message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OtpModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }

        });
    }
}
