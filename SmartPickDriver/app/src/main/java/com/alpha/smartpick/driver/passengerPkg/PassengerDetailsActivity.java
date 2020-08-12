package com.alpha.smartpick.driver.passengerPkg;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.HomeActivity;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpick.driver.livetrackPkg.LiveTrackActivity;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.Constants;
import com.alpha.smartpick.driver.utility.CustomProgressbar;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.internal.cache.DiskLruCache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerDetailsActivity extends AppCompatActivity implements OnClickListener {
    RelativeLayout alredyrejectId;
    /* access modifiers changed from: private */
    public String amount;
    private ApiServices apiServices;
    public String date;
    private String description;
    public String distance;
    public String drop_location;
    ArrayList<LocationItem> droplocationList;
    public String goodstype;
    private AppCompatImageView img_truck;
    public String invoice_id;
    private String lift_facility;
    RecyclerView listView;
    RelativeLayout liveTrackingId;
    private String packaging;
    private String payment_status;
    public String pick_location;
    RelativeLayout reDoneId;
    RelativeLayout reacceptId;
    private String reciver_contact;
    private String reciver_number;
    RelativeLayout rerejectid;
    private LinearLayout staffhelperrequermentId;
    private String status;
    private String statusa;
    private AppCompatTextView text_date_id;
    private AppCompatTextView text_distance_type;
    private AppCompatTextView text_time_id;
    private AppCompatTextView textstaffdescribstionId;
    private AppCompatTextView textstaffhelperId;
    private AppCompatTextView textstaffnameId;
    private AppCompatTextView textstaffpakingdetailsId;
    private AppCompatTextView textusernumberId;
    private AppCompatTextView timeId;
    private AppCompatTextView txt_p_loc;
    public String type;
    private String userId;
    private CircleImageView userImageId;
    private AppCompatTextView userNameId;
    private LinearLayout userNumberId;
    private String user_id;
    public String user_name;
    public String user_number;
    public String user_profile;
    public void onCreate(Bundle savedInstanceState) {
        String str = "address";
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_passenger_details);
        this.apiServices = (ApiServices) RetrofitClient.getClient().create(ApiServices.class);
        Intent intent = getIntent();
        this.invoice_id = intent.getStringExtra("invoice_id");
        this.date = intent.getStringExtra("date");
        this.user_name = intent.getStringExtra("user_name");
        this.user_profile = intent.getStringExtra("user_profile");
        this.amount = intent.getStringExtra("amount");
        this.distance = intent.getStringExtra("distance");
        this.pick_location = intent.getStringExtra("pick_location");
        this.drop_location = intent.getStringExtra("drop_location");
        this.type = intent.getStringExtra("type");
        this.goodstype = intent.getStringExtra("goodstype");
        this.statusa = intent.getStringExtra("statusa");
        this.user_id = intent.getStringExtra("user_id");
        this.user_number = intent.getStringExtra("user_number");
        this.reciver_contact = intent.getStringExtra("reciver_contact");
        this.reciver_number = intent.getStringExtra("reciver_number");
        this.status = intent.getStringExtra("status");
        this.description = intent.getStringExtra("description");
        this.packaging = intent.getStringExtra("packaging");
        this.lift_facility = intent.getStringExtra("lift_facility");
        this.userId = AppSession.getStringPreferences(this, Constants.USER_ID);
        init();
        if (this.user_id.matches(this.userId)) {
            if (this.status.equals("3")) {
                this.rerejectid.setVisibility(View.GONE);
                this.reacceptId.setVisibility(View.GONE);
                this.liveTrackingId.setVisibility(View.GONE);
                this.reDoneId.setVisibility(View.VISIBLE);
                this.userNumberId.setVisibility(View.VISIBLE);
            } else {
                this.rerejectid.setVisibility(View.GONE);
                this.reacceptId.setVisibility(View.GONE);
                this.liveTrackingId.setVisibility(View.VISIBLE);
                this.userNumberId.setVisibility(View.VISIBLE);
            }
        } else if (!this.userId.matches(this.user_id) && !this.user_id.equals("0")) {
            this.rerejectid.setVisibility(View.GONE);
            this.reacceptId.setVisibility(View.GONE);
            this.alredyrejectId.setVisibility(View.VISIBLE);
        }
        try {
            JSONArray jre = new JSONArray(this.pick_location);
            for (int j = 0; j < jre.length(); j++) {
                this.txt_p_loc.setText(jre.getJSONObject(j).getString(str));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.droplocationList = new ArrayList<>();
            JSONArray array = new JSONArray(this.drop_location);
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
                model.setBooking_id(booking_id);
                model.setIs_booking_verify(is_booking_verify);
                this.droplocationList.add(model);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        CustomAdapterA adapter = new CustomAdapterA(this, droplocationList);
        listView.setAdapter(adapter);
    }

    private void init() {
        AppCompatImageView ivbackId = (AppCompatImageView) findViewById(R.id.ivbackId);
        this.reacceptId = (RelativeLayout) findViewById(R.id.reacceptId);
        this.rerejectid = (RelativeLayout) findViewById(R.id.rejectid);
        this.userImageId = (CircleImageView) findViewById(R.id.ivuserprofileimageId);
        this.alredyrejectId = (RelativeLayout) findViewById(R.id.alredyrejectId);
        this.staffhelperrequermentId = (LinearLayout) findViewById(R.id.staffhelperrequermentId);
        this.liveTrackingId = (RelativeLayout) findViewById(R.id.liveTrackingId);
        this.userNameId = (AppCompatTextView) findViewById(R.id.userNameId);
        this.text_distance_type = (AppCompatTextView) findViewById(R.id.text_distance_type);
        this.timeId = (AppCompatTextView) findViewById(R.id.timeId);
        this.userNumberId = (LinearLayout) findViewById(R.id.userNumberId);
        this.textusernumberId = (AppCompatTextView) findViewById(R.id.textusernumberId);
        this.listView = (RecyclerView) findViewById(R.id.listView);
        this.txt_p_loc = (AppCompatTextView) findViewById(R.id.txt_p_loc);
        this.textstaffpakingdetailsId = (AppCompatTextView) findViewById(R.id.textstaffpakingdetailsId);
        this.textstaffdescribstionId = (AppCompatTextView) findViewById(R.id.textstaffdescribstionId);
        this.textstaffhelperId = (AppCompatTextView) findViewById(R.id.textstaffhelperId);
        this.reDoneId = (RelativeLayout) findViewById(R.id.reDoneId);
        this.img_truck = (AppCompatImageView) findViewById(R.id.img_truck);
        this.textstaffnameId = (AppCompatTextView) findViewById(R.id.textstaffnameId);
        this.userNameId.setText(this.user_name);
        this.timeId.setText(this.date);
        this.textstaffdescribstionId.setText(this.description);
        this.textstaffpakingdetailsId.setText(this.packaging);
        AppCompatTextView appCompatTextView = this.text_distance_type;
        StringBuilder sb = new StringBuilder();
        sb.append("Distance:");
        sb.append(this.distance);
        appCompatTextView.setText(sb.toString());
        this.textusernumberId.setText(this.user_number);
        this.textstaffnameId.setText(this.goodstype);
        this.textstaffhelperId.setText(this.lift_facility);
        ivbackId.setOnClickListener(this);
        this.reacceptId.setOnClickListener(this);
        this.rerejectid.setOnClickListener(this);
        this.liveTrackingId.setOnClickListener(this);
        this.alredyrejectId.setOnClickListener(this);
        if (user_profile.isEmpty()) {
        } else {
            Picasso.with(PassengerDetailsActivity.this).load(RetrofitClient.USER_PROFILE_URL+user_profile).into(userImageId);
        }
        switch (type) {
            case "1":
                img_truck.setBackgroundResource(R.drawable.ic_one_ton_on);
                break;
            case "2":
                staffhelperrequermentId.setVisibility(View.VISIBLE);
                img_truck.setBackgroundResource(R.drawable.ic_three_ton_on);
                break;
            case "3":
                staffhelperrequermentId.setVisibility(View.VISIBLE);
                img_truck.setBackgroundResource(R.drawable.ic_three_ton_cov_on);
                break;
        }


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alredyrejectId /*2131296327*/:
                onBackPressed();
                return;
            case R.id.ivbackId /*2131296448*/:
                onBackPressed();
                return;
            case R.id.liveTrackingId /*2131296471*/:
                Intent intent = new Intent(this, LiveTrackActivity.class);
                intent.putExtra("invoice_id", this.invoice_id);
                intent.putExtra("user_name", this.user_name);
                intent.putExtra("user_number", this.user_number);
                intent.putExtra("distance", this.distance);
                intent.putExtra("amount", this.amount);
                intent.putExtra("date", this.date);
                intent.putExtra("picklocation", this.pick_location);
                intent.putExtra("droplocation", this.drop_location);
                intent.putExtra("goodstype", this.goodstype);
                intent.putExtra("user_profile", this.user_profile);
                intent.putExtra("type", this.type);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                return;
            case R.id.reacceptId /*2131296577*/:
                get_acceptestion();
                return;
            case R.id.rejectid /*2131296582*/:
                get_reject();
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    private void get_acceptestion() {
        CustomProgressbar.showProgressBar((Context) this, false);
        this.apiServices.bookingacception(this.userId, this.invoice_id, "1").enqueue(new Callback<GenerateOtpModle>() {
            public void onResponse(Call<GenerateOtpModle> call, Response<GenerateOtpModle> response) {
                String str = "";
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GenerateOtpModle loginModle = (GenerateOtpModle) response.body();
                    if (loginModle.getStatus().booleanValue()) {
                        PassengerDetailsActivity.this.book_diloge();
                        return;
                    }else {
                        Toast.makeText(PassengerDetailsActivity.this, ""+loginModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 400 && !response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        String message = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(PassengerDetailsActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }

            public void onFailure(Call<GenerateOtpModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    private void get_reject() {
        CustomProgressbar.showProgressBar((Context) this, false);
        this.apiServices.bookingacception(this.userId, this.invoice_id, "2").enqueue(new Callback<GenerateOtpModle>() {
            public void onResponse(Call<GenerateOtpModle> call, Response<GenerateOtpModle> response) {
                String str = "";
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GenerateOtpModle loginModle = (GenerateOtpModle) response.body();
                    if (loginModle.getStatus().booleanValue()) {
                        PassengerDetailsActivity.this.startActivity(new Intent(PassengerDetailsActivity.this, HomeActivity.class));
                        PassengerDetailsActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        return;
                    }else {
                        Toast.makeText(PassengerDetailsActivity.this, ""+loginModle.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else if (response.code() == 400 && !response.isSuccessful()) {
                    try {
                        CustomProgressbar.hideProgressBar();
                        String message = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(PassengerDetailsActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }

            public void onFailure(Call<GenerateOtpModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    public void book_diloge() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().getAttributes().gravity = 80;
        dialog.setContentView(R.layout.booking_acception);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(-1, -2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationa;
        ((RelativeLayout) dialog.findViewById(R.id.rlSigninId)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PassengerDetailsActivity.this, LiveTrackActivity.class);
                intent.putExtra("invoice_id", PassengerDetailsActivity.this.invoice_id);
                intent.putExtra("user_name", PassengerDetailsActivity.this.user_name);
                intent.putExtra("user_number", PassengerDetailsActivity.this.user_number);
                intent.putExtra("distance", PassengerDetailsActivity.this.distance);
                intent.putExtra("amount", PassengerDetailsActivity.this.amount);
                intent.putExtra("date", PassengerDetailsActivity.this.date);
                intent.putExtra("picklocation", PassengerDetailsActivity.this.pick_location);
                intent.putExtra("droplocation", PassengerDetailsActivity.this.drop_location);
                intent.putExtra("goodstype", PassengerDetailsActivity.this.goodstype);
                intent.putExtra("user_profile", PassengerDetailsActivity.this.user_profile);
                intent.putExtra("type", PassengerDetailsActivity.this.type);
                PassengerDetailsActivity.this.startActivity(intent);
                PassengerDetailsActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                PassengerDetailsActivity.this.finishAffinity();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
