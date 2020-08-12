package com.alpha.smartpickuser.notificationPkg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.ApiPkg.ApiServices;
import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.HomeActivity;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.initial.SplashActivity;
import com.alpha.smartpickuser.notificationPkg.adapterPkg.NotificationAdapter;
import com.alpha.smartpickuser.notificationPkg.getNotificationListPkg.Datum;
import com.alpha.smartpickuser.notificationPkg.getNotificationListPkg.GetNotificationListModel;
import com.alpha.smartpickuser.notificationPkg.getNotificationListPkg.NotificationreadModel;
import com.alpha.smartpickuser.utility.AppSession;
import com.alpha.smartpickuser.utility.Constants;
import com.alpha.smartpickuser.utility.CustomProgressbar;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationActivity extends AppCompatActivity implements NotificationAdapter.NotificationOnClickListener,View.OnClickListener {
    private RecyclerView rvnotification;
    private NotificationAdapter notificationAdapter;
    private AppCompatImageView ivbackId;
    private ApiServices apiServices;
    private List<Datum> getBookingModlesList;
    private RelativeLayout renotificaionId,rlrefresId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
        Notificaoticlear();
    }

    private void init() {
        ivbackId = findViewById(R.id.ivbackId);
        rvnotification = findViewById(R.id.rvnotificationId);
        renotificaionId = findViewById(R.id.renotificaionId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvnotification.setLayoutManager(layoutManager);
        notificationAdapter = new NotificationAdapter(this, this);
        rvnotification.setAdapter(notificationAdapter);
        ivbackId.setOnClickListener(this);
        rlrefresId= findViewById(R.id.rlrefresId);
        userId = AppSession.getStringPreferences(NotificationActivity.this, Constants.USER_ID);
        rlrefresId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getBookingModlesList.clear();
                    Notificaoticlear();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivbackId:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    private void ridehistory() {

        apiServices.notificaitonList(userId).enqueue(new Callback<GetNotificationListModel>() {
            @Override
            public void onResponse(Call<GetNotificationListModel> call, Response<GetNotificationListModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GetNotificationListModel getBookingModle = response.body();
                    if (getBookingModle.getStatus().equals(true)) {
                        getBookingModlesList = getBookingModle.getData();
                        notificationAdapter.ridehistoryList(getBookingModlesList);
                    } else {
                        renotificaionId.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNotificationListModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });


    }
    private void Notificaoticlear() {
        CustomProgressbar.showProgressBar(NotificationActivity.this, false);
        apiServices.notificaoticlear(userId).enqueue(new Callback<NotificationreadModel>() {
            @Override
            public void onResponse(Call<NotificationreadModel> call, Response<NotificationreadModel> response) {
                if (response.isSuccessful()) {
                    NotificationreadModel getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        ridehistory();
                    }else {
                        ridehistory();
                    }
                }else {
                    if (response.code() == 400) {
                        if(!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
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
            public void onFailure(Call<NotificationreadModel> call, Throwable t) {
                Log.d("test", String.valueOf(t));

            }
        });
    }




    @Override
    public void onClick(View view, int position) {

    }

}
