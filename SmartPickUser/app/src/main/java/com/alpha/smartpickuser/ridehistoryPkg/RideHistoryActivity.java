package com.alpha.smartpickuser.ridehistoryPkg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.ApiPkg.ApiServices;
import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.initial.SplashActivity;
import com.alpha.smartpickuser.livetrackPkg.LiveTrackActivity;
import com.alpha.smartpickuser.ridehistoryPkg.adapterPkg.RideHistoryAdapter;
import com.alpha.smartpickuser.ridehistoryPkg.getbookingPkg.AllActivity;
import com.alpha.smartpickuser.ridehistoryPkg.getbookingPkg.GetBookingModle;
import com.alpha.smartpickuser.utility.AppSession;
import com.alpha.smartpickuser.utility.Constants;
import com.alpha.smartpickuser.utility.CustomProgressbar;
import com.alpha.smartpickuser.utility.PrefData;
import com.alpha.smartpickuser.utility.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideHistoryActivity extends AppCompatActivity implements RideHistoryAdapter.RideHistoryClickListener,View.OnClickListener {
    private RecyclerView rvrideHistoryId;
    private RideHistoryAdapter rideHistoryAdapter;
    private AppCompatImageView ivbackId;
    private ApiServices apiServices;
    private String user_id;
    private List<AllActivity> getBookingModlesList;
    private RelativeLayout renotificaionId;
    Context con;
    PrefData prefData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_history);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        user_id = AppSession.getStringPreferences(RideHistoryActivity.this, Constants.USER_ID);
        init();
        ridehistory();
        con = getApplicationContext();
        prefData = new PrefData(con);
    }
    private void init() {
        ivbackId = findViewById(R.id.ivbackId);
        rvrideHistoryId = findViewById(R.id.rvrideHistoryId);
        renotificaionId = findViewById(R.id.renotificaionId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvrideHistoryId.setLayoutManager(layoutManager);
        rideHistoryAdapter = new RideHistoryAdapter(this, this);
        rvrideHistoryId.setAdapter(rideHistoryAdapter);
        ivbackId.setOnClickListener(this);
        rvrideHistoryId.addOnItemTouchListener(new RecyclerItemClickListener(RideHistoryActivity.this, rvrideHistoryId, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AllActivity pu = getBookingModlesList.get(position);
                Intent intent = new Intent(RideHistoryActivity.this, HistoryDetailsActivity.class);
                intent.putExtra("invoice_id",pu.getId());
                intent.putExtra("date",pu.getPickDateTime());
                intent.putExtra("user_name",pu.getUsername());
                intent.putExtra("user_profile",pu.getImage());
                intent.putExtra("amount",pu.getAmount());
                intent.putExtra("distance",pu.getDistance());
                intent.putExtra("pick_location",pu.getPickLocation());
                intent.putExtra("drop_location",pu.getDropLocation());
                intent.putExtra("type",pu.getVehicleType());
                intent.putExtra("goodstype",pu.getStufName());
                intent.putExtra("statusa",pu.getIsStatus());
                intent.putExtra("user_number",pu.getPhoneNumber());
                intent.putExtra("reciver_contact",pu.getReceiverName());
                intent.putExtra("reciver_number",pu.getReceiverMobilenumber());
                intent.putExtra("driver_id",pu.getDriverId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }


    private void ridehistory() {
        CustomProgressbar.showProgressBar(RideHistoryActivity.this, false);
        apiServices.ridehistory(user_id).enqueue(new Callback<GetBookingModle>() {
            @Override
            public void onResponse(Call<GetBookingModle> call, Response<GetBookingModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GetBookingModle getBookingModle = response.body();
                    if (getBookingModle.getStatus().equals(true)) {
                        getBookingModlesList = getBookingModle.getAllActivities();
                        rideHistoryAdapter.ridehistoryList(getBookingModlesList);
                    }else {
                        renotificaionId.setVisibility(View.VISIBLE);
                    }

                }
            }
            @Override
            public void onFailure(Call<GetBookingModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivbackId:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }


}
