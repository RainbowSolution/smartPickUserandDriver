package com.alpha.smartpickuser.ridehistoryPkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.initial.SplashActivity;
import com.alpha.smartpickuser.livetrackPkg.LiveTrackActivity;
import com.alpha.smartpickuser.ridehistoryPkg.adapterPkg.CustomAdapterA;
import com.alpha.smartpickuser.ridehistoryPkg.getbookingPkg.LocationItem;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout viewpaymentdetailsId;
    private String payment_status,invoice_id,date,user_name,user_profile,amount,distance,pick_location,
            drop_location,type,goodstype,statusa,user_number,reciver_contact,reciver_number,driver_id;
    private AppCompatTextView tvIdnumberId,text_date_id,textpriceId,textdistanceId,
            userNameId,reciver_id,txt_p_loc,txt_d_loc,stuff_type,status_id,userNumberId,receiverNameId;
    private AppCompatImageView img_truck;
    private CircleImageView driverProifleId;
    RecyclerView listView;
    ArrayList<LocationItem> droplocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        Intent intent = getIntent();
        invoice_id=  intent.getStringExtra("invoice_id");
        date= intent.getStringExtra("date");
        user_name=intent.getStringExtra("user_name");
        user_profile= intent.getStringExtra("user_profile");
        amount =intent.getStringExtra("amount");
        distance =intent.getStringExtra("distance");
        pick_location =intent.getStringExtra("pick_location");
        drop_location =intent.getStringExtra("drop_location");
        type =intent.getStringExtra("type");
        goodstype =intent.getStringExtra("goodstype");
        statusa =intent.getStringExtra("statusa");
        user_number =intent.getStringExtra("user_number");
        reciver_contact =intent.getStringExtra("reciver_contact");
        reciver_number = intent.getStringExtra("reciver_number");
        driver_id = intent.getStringExtra("driver_id");
        init();
        JSONArray jre = null;
        try {
            jre = new JSONArray(pick_location);
            for (int j = 0; j < jre.length(); j++) {
                JSONObject jobject = jre.getJSONObject(j);
                txt_p_loc.setText(jobject.getString("address"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            droplocationList = new ArrayList<>();
            JSONArray array = new JSONArray(drop_location);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("id");
                String booking_id = jsonObject.getString("booking_id");
                String droplocation = jsonObject.getString("address");
                String reciver_name = jsonObject.getString("reciver_name");
                String reciver_number = jsonObject.getString("reciver_number");
                String is_booking_verify = jsonObject.getString("is_booking_verify");
                LocationItem model = new LocationItem();
                model.setDroplocation(droplocation);
                model.setReciver_name(reciver_name);
                model.setReciver_number(reciver_number);
                model.setId(id);
                model.setId(booking_id);
                model.setIs_booking_verify(is_booking_verify);
                droplocationList.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        CustomAdapterA adapter = new CustomAdapterA(this, droplocationList);
        listView.setAdapter(adapter);

    }




    private void init() {
        AppCompatImageView ivbackId = findViewById(R.id.ivbackId);
        viewpaymentdetailsId = findViewById(R.id.viewpaymentdetailsId);
        ivbackId.setOnClickListener(this);
        viewpaymentdetailsId.setOnClickListener(this);
        tvIdnumberId = findViewById(R.id.tvIdnumberId);
        text_date_id = findViewById(R.id.text_date_id);
        textpriceId = findViewById(R.id.textpriceId);
        textdistanceId = findViewById(R.id.textdistanceId);
        userNameId = findViewById(R.id.userNameId);
        driverProifleId = findViewById(R.id.driverProifleId);
        userNumberId = findViewById(R.id.userNumberId);
        txt_p_loc = findViewById(R.id.txt_p_loc);
        listView = findViewById(R.id.listView);
        //   txt_d_loc = findViewById(R.id.txt_d_loc);
        img_truck = findViewById(R.id.img_truck);
        stuff_type = findViewById(R.id.stuff_type);
        status_id = findViewById(R.id.status_id);
        tvIdnumberId.setText("#"+invoice_id);
        text_date_id.setText(date);
        textpriceId.setText(amount);
        textdistanceId.setText(distance);
        userNumberId.setText(user_number);
        userNameId.setText(user_name);

     /*   if (reciver_contact.isEmpty()){
            reciver_id.setVisibility(View.GONE);
        }else {
            receiverNameId.setText(reciver_contact);
            reciver_id.setText("Receiver Name : "+reciver_contact+" Receiver Mob. Number " +reciver_number);
        }*/
        if (user_profile.isEmpty()) {
        } else {
            Picasso.with(HistoryDetailsActivity.this).load(RetrofitClient.USER_PROFILE_URL+user_profile).into(driverProifleId);
        }
        switch (type) {
            case "1":
                img_truck.setBackgroundResource(R.drawable.ic_one_ton_on);
                break;
            case "2":
                img_truck.setBackgroundResource(R.drawable.ic_three_ton_on);
                break;
            case "3":
                img_truck.setBackgroundResource(R.drawable.ic_three_ton_cov_on);
                break;
        }
        stuff_type.setText(goodstype);
        if (statusa.equals("0")){
            status_id.setText("Pending");
        }else if (statusa.equals("1")){
            status_id.setText("Processing");
        }else if (statusa.equals("3")){
            status_id.setText("Booking Done");
            viewpaymentdetailsId.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivbackId:
                onBackPressed();
                break;
            case R.id.viewpaymentdetailsId:
                Intent intent = new Intent(HistoryDetailsActivity.this, LiveTrackActivity.class);
                intent.putExtra("invoice_id",invoice_id);
                intent.putExtra("date",date);
                intent.putExtra("user_name",user_name);
                intent.putExtra("user_profile",user_profile);
                intent.putExtra("amount",amount);
                intent.putExtra("driver_id",driver_id);
                intent.putExtra("distance",distance);
                intent.putExtra("pick_location",pick_location);
                intent.putExtra("drop_location",drop_location);
                intent.putExtra("type",type);
                intent.putExtra("user_number",user_number);
                intent.putExtra("reciver_contact",reciver_contact);
                intent.putExtra("reciver_number",reciver_number);
                intent.putExtra("status",statusa);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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
