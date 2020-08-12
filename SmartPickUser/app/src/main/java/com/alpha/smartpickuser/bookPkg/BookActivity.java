package com.alpha.smartpickuser.bookPkg;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.ApiPkg.ApiServices;
import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.HomeActivity;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.bookPkg.bookingPkgmodel.ApplyPromoCodeModel;
import com.alpha.smartpickuser.bookPkg.bookingPkgmodel.BookingAmountModel;
import com.alpha.smartpickuser.bookPkg.bookingPkgmodel.BookingModle;
import com.alpha.smartpickuser.bookPkg.goodtypesPkg.GoodsTypesDialogFragment;
import com.alpha.smartpickuser.bookPkg.recevierPkg.ReceiverConnectDetailsFragment;
import com.alpha.smartpickuser.ridehistoryPkg.adapterPkg.CustomAdapter;
import com.alpha.smartpickuser.ridehistoryPkg.getbookingPkg.LocationItem;
import com.alpha.smartpickuser.utility.AppSession;
import com.alpha.smartpickuser.utility.CheckNetwork;
import com.alpha.smartpickuser.utility.Constants;
import com.alpha.smartpickuser.utility.CustomProgressbar;
import com.google.android.material.snackbar.Snackbar;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity  extends AppCompatActivity implements View.OnClickListener, GoodsTypesDialogFragment.ItemClickListener,ReceiverConnectDetailsFragment.ItemClickListenerReciverDetails {
    AppCompatEditText textdroplocaiton,textlandmarkid,textdescription,text_promocode;
    AppCompatTextView reacceptId,textamt,
            textdistance,texttime,txt_d_loc,
            texttype,textpicklocation,tvtotalammountId;
    private String payment_type,promocode,total;
    LinearLayout view_qty;
    LinearLayout viewrcontact;
    LinearLayout view_cash;
    LinearLayout view_lift;
    LinearLayout view_packing;
    LinearLayout lihelperid;
    LinearLayout view_drop;
    String subtotal,straa;
    String pick_location,drop_location,datemain,
            vehicletype,user_id,pick_up_letter,pick_up_location_multiple_and_single,
            distance,recivername,recivercontactnumber,lifitfacility,parkingrequrment;
    private CoordinatorLayout coordinatorLayout;
    private AppCompatImageView img_truck;
    boolean clicked=true;
    Double lat_drop,lng_drop;
    private ApiServices apiServices;
    ArrayList<LocationItem> droplocationList;
    RecyclerView listView;
    AppCompatButton  AddToPromocodeId,buttonViewPromocoded;
    ScrollableNumberPicker ScrollableNumberPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_truck);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Intent intent = getIntent();
        pick_location =intent.getStringExtra("pick_location");
        drop_location = intent.getStringExtra("drop_location");
        lat_drop = intent.getDoubleExtra("lat_drop",0.0);
        lng_drop = intent.getDoubleExtra("lng_drop",0.0);
        datemain = intent.getStringExtra("date");
        vehicletype = intent.getStringExtra("vehicletype");
        distance = intent.getStringExtra("distance");
        pick_up_location_multiple_and_single = intent.getStringExtra("pick_up_location_multiple_and_single");
        pick_up_letter = intent.getStringExtra("pick_up_letter");
        AppSession.setStringPreferences(BookActivity.this, Constants.VEHICLE_TYPE,vehicletype);
        user_id = AppSession.getStringPreferences(BookActivity.this, Constants.USER_ID);
        init();
        parseDateToddMMyyyy();
        recivername ="";
        recivercontactnumber ="";
        promocode ="0.0";
    }

    private void init() {
        AppCompatImageView ivbackId = findViewById(R.id.ivbackId);
        reacceptId = findViewById(R.id.btn_right);
        view_qty = findViewById(R.id.view_qty);
        viewrcontact = findViewById(R.id.view_r_contact);
        textamt = findViewById(R.id.txt_amt);
        listView = findViewById(R.id.listView);
        txt_d_loc = findViewById(R.id.txt_d_loc);
        textdistance = findViewById(R.id.txt_distance);
        textlandmarkid = findViewById(R.id.land_mark_id);
        textpicklocation = findViewById(R.id.txt_p_loc);
        view_cash = findViewById(R.id.view_cash);
        view_drop = findViewById(R.id.view_drop);
        view_lift = findViewById(R.id.view_lift);
        text_promocode = findViewById(R.id.text_promocode);
        view_packing = findViewById(R.id.view_packing);
        texttime = findViewById(R.id.txt_time);
        texttype = findViewById(R.id.txt_type);
        img_truck = findViewById(R.id.img_truck);
        lihelperid = findViewById(R.id.lihelperid);
        textdescription = findViewById(R.id.text_des);
        ScrollableNumberPicker = findViewById(R.id.number_picker_horizontal);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        AddToPromocodeId = findViewById(R.id.AddToPromocodeId);
        buttonViewPromocoded = findViewById(R.id.buttonViewPromocoded);
        ivbackId.setOnClickListener(this);
        reacceptId.setOnClickListener(this);
        view_qty.setOnClickListener(this);
        viewrcontact.setOnClickListener(this);
        view_cash.setSelected(true);
        view_lift.setOnClickListener(this);
        view_packing.setOnClickListener(this);
        texttime.setText(getResources().getString(R.string.txt_pickup_time)+" :"+datemain);
        textdistance.setText(getResources().getString(R.string.distance)+" :" +distance);
        switch (vehicletype) {
            case "1":
                img_truck.setBackgroundResource(R.drawable.ic_one_ton_on);
                lihelperid.setVisibility(View.GONE);
                break;
            case "2":
                img_truck.setBackgroundResource(R.drawable.ic_three_ton_on);
                break;
            case "3":
                img_truck.setBackgroundResource(R.drawable.ic_three_ton_cov_on);
                break;
        }
        parkingrequrment = "No";
        JSONArray jre = null;
        try {
            jre = new JSONArray(pick_location);
            for (int j = 0; j < jre.length(); j++) {
                JSONObject jobject = jre.getJSONObject(j);
                textpicklocation.setText(jobject.getString("address"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AddToPromocodeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPromoCode();
            }
        });
        if (pick_up_location_multiple_and_single.equals("1")){
            view_drop.setVisibility(View.VISIBLE);
            viewrcontact.setVisibility(View.VISIBLE);
            txt_d_loc.setText(drop_location);
        }else {
            try {
                droplocationList = new ArrayList<>();
                JSONArray array = new JSONArray(drop_location);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String droplocation = jsonObject.getString("address");
                    String reciver_name = jsonObject.getString("reciver_name");
                    String reciver_number = jsonObject.getString("reciver_number");
                    LocationItem model = new LocationItem();
                    model.setDroplocation(droplocation);
                    model.setReciver_name(reciver_name);
                    model.setReciver_number(reciver_number);
                    droplocationList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            listView.setLayoutManager(layoutManager);
            CustomAdapter adapter = new CustomAdapter(this, droplocationList);
            listView.setAdapter(adapter);
        }
    }

    public void parseDateToddMMyyyy() {
        String inputPattern = "MMM d yyyy HH:mm";
        String outputPattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        try {
            date = inputFormat.parse(datemain);
            straa = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivbackId:
                onBackPressed();
                break;
            case R.id.btn_right:
                if (pick_up_location_multiple_and_single.equals("1")){
                    addStock(v);
                }else {
                    validation(v);
                }
                break;
            case R.id.view_qty:
                Goodstypes(v);
                break;
            case R.id.view_r_contact:
                ReciverContectDetails(v);
                break;
            case R.id.view_lift:
                if (clicked){
                    view_lift.setSelected(true);
                    clicked = false;
                    //lifitfacility ="yes";
                }else {
                    view_lift.setSelected(false);
                    clicked = true;
                  //  lifitfacility ="No";
                }
                break;
            case R.id.view_packing:
                if (clicked){
                    view_packing.setSelected(true);
                    clicked = false;
                    parkingrequrment ="Yes";
                }else {
                    view_packing.setSelected(false);
                    clicked = true;
                    parkingrequrment ="No";
                }
                break;

        }

    }

    private void validation(View v) {
        if (TextUtils.isEmpty(textpicklocation.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Please select valid pick location !", Snackbar.LENGTH_LONG);
            snackbar.show();
        }else if (TextUtils.isEmpty(texttype.getText().toString().trim())){
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.Please_select_goods_type), Snackbar.LENGTH_LONG);
            snackbar.show();
        }else if (CheckNetwork.isNetAvailable(BookActivity.this)) {
           /* booking(textamt.getText().toString().trim(),
                    textdistance.getText().toString().trim(),
                    texttime.getText().toString().trim(),
                    pick_location,
                    textlandmarkid.getText().toString().trim(),
                    texttype.getText().toString().trim(),
                    textdescription.getText().toString().trim());*/
           if (distance.equals("null")){
               Toast.makeText(this, "Please check distance", Toast.LENGTH_SHORT).show();
           }else if (distance.equals("0")) {
               Toast.makeText(this, "Please check distance", Toast.LENGTH_SHORT).show();
           }else {
               bookingAmount();
           }

        } else {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Check network connection !", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void Goodstypes(View view) {
        GoodsTypesDialogFragment goodsTypesDialogFragment =
                GoodsTypesDialogFragment.newInstance();
        goodsTypesDialogFragment.show(getSupportFragmentManager(),
                GoodsTypesDialogFragment.TAG);
    }

    public void ReciverContectDetails(View view) {
        ReceiverConnectDetailsFragment receiverConnectDetailsFragment =
                ReceiverConnectDetailsFragment.newInstance();
        receiverConnectDetailsFragment.show(getSupportFragmentManager(),
                ReceiverConnectDetailsFragment.TAG);
    }
    @Override public void onItemClick(String item) {
        texttype.setText(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BookActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }

    public void book_diloge() {
        final Dialog dialog = new Dialog(BookActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        dialog.setContentView(R.layout.booking_conform);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationa;
        RelativeLayout rlSigninId = dialog.findViewById(R.id.rlSigninId);
        tvtotalammountId = dialog.findViewById(R.id.tvtotalammountId);
        tvtotalammountId.setText(getResources().getString(R.string.txt_amt)+" : "+ total + " ETB");
        rlSigninId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                lifitfacility = String.valueOf(ScrollableNumberPicker.getValue());
                booking1(textamt.getText().toString().trim(),
                        texttime.getText().toString().trim(),
                        pick_location,
                        textlandmarkid.getText().toString().trim(),
                        texttype.getText().toString().trim(),
                        textdescription.getText().toString().trim());
                }
            });
            dialog.show();

    }

    @Override
    public void onItemClickname(String name) {
        recivername = name;
    }
    @Override
    public void onItemclicknumber(String number) {
      recivercontactnumber = number;
    }
    private void booking(final String amount,final String distance, final String time,
                         final String pickuplocation,
                         final String landmark,final String type,final String description) {
        CustomProgressbar.showProgressBar(BookActivity.this, false);
        apiServices.getbooking(pickuplocation,drop_location,vehicletype,
                type,parkingrequrment,"100 AED","Cash",distance,recivername,recivercontactnumber,lifitfacility,landmark,description,datemain).enqueue(new Callback<BookingModle>() {
            @Override
            public void onResponse(Call<BookingModle> call, Response<BookingModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    BookingModle getRegistrationModle = response.body();
                    if (getRegistrationModle.getStatus() == true) {
                        book_diloge();
                    }else {
                        CustomProgressbar.hideProgressBar();
                        Toast.makeText(BookActivity.this, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 400) {
                    if (!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            CustomProgressbar.hideProgressBar();
                            String message = jsonObject.getString("message");
                            Toast.makeText(BookActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }

        });
    }
    private void applyPromoCode() {
        CustomProgressbar.showProgressBar(BookActivity.this, false);
        apiServices.applyPromoCode(text_promocode.getText().toString(), "5").enqueue(new Callback<ApplyPromoCodeModel>() {
            @Override
            public void onResponse(Call<ApplyPromoCodeModel> call, Response<ApplyPromoCodeModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    ApplyPromoCodeModel getRegistrationModle = response.body();
                    if (getRegistrationModle.getStatus() == true) {
                        promocode = getRegistrationModle.getData().getNewAmount();
                        AddToPromocodeId.setVisibility(View.GONE);
                        buttonViewPromocoded.setVisibility(View.VISIBLE);
                    }else {
                        CustomProgressbar.hideProgressBar();
                        Toast.makeText(BookActivity.this, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 400) {
                    if (!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            CustomProgressbar.hideProgressBar();
                            String message = jsonObject.getString("message");
                            Toast.makeText(BookActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApplyPromoCodeModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }

        });
    }
    private void bookingAmount() {
        CustomProgressbar.showProgressBar(BookActivity.this, false);
        apiServices.bookingAmount(vehicletype, distance,promocode).enqueue(new Callback<BookingAmountModel>() {
            @Override
            public void onResponse(Call<BookingAmountModel> call, Response<BookingAmountModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    BookingAmountModel getRegistrationModle = response.body();
                    if (getRegistrationModle.getStatus() == true) {
                        total = String.valueOf(getRegistrationModle.getCalculatAmount().getTotal());
                        float a = Float.parseFloat(total);
                        float b = Float.parseFloat(promocode);
                        total = String.valueOf(a-b);
                        book_diloge();
                    }else {
                        CustomProgressbar.hideProgressBar();
                        Toast.makeText(BookActivity.this, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 400) {
                    if (!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            CustomProgressbar.hideProgressBar();
                            String message = jsonObject.getString("message");
                            Toast.makeText(BookActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingAmountModel> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }

        });
    }
    private void booking1(final String amount,  final String time,
                         final String pickuplocation,
                         final String landmark,final String type,final String description) {
        CustomProgressbar.showProgressBar(BookActivity.this, false);
        apiServices.Booking(user_id,pickuplocation,drop_location,vehicletype,
                type,parkingrequrment,total+" ETB","Cash",distance+" km",recivername,recivercontactnumber,lifitfacility,landmark,description,datemain,pick_up_letter,straa).enqueue(new Callback<BookingModle>() {
            @Override
            public void onResponse(Call<BookingModle> call, Response<BookingModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    BookingModle getRegistrationModle = response.body();
                    if (getRegistrationModle.getStatus() == true) {
                        book_diloge1();
                    }else {
                        CustomProgressbar.hideProgressBar();
                        Toast.makeText(BookActivity.this, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 400) {
                    if (!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            CustomProgressbar.hideProgressBar();
                            String message = jsonObject.getString("message");
                            Toast.makeText(BookActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }

        });
    }

    public void book_diloge1() {
        final Dialog dialog = new Dialog(BookActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        dialog.setContentView(R.layout.dilog_book);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        RelativeLayout rlSigninId = dialog.findViewById(R.id.rlSigninId);
        rlSigninId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });
        dialog.show();

    }

    public void addStock(View v) {
        JSONArray jsonArray = new JSONArray();
        JSONObject PickLocation = new JSONObject();
        try {
            PickLocation.put("address", drop_location);
            PickLocation.put("latitude", lat_drop);
            PickLocation.put("longitude", lng_drop);
            PickLocation.put("reciver_name",recivername);
            PickLocation.put("reciver_number",recivercontactnumber);
            jsonArray.put(PickLocation);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //    registerUser();
        drop_location = jsonArray.toString();
        validation(v);

    }
}
