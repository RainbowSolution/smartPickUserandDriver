package com.alpha.smartpickuser.livetrackPkg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.alpha.smartpickuser.ApiPkg.ApiServices;
import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.HomeActivity;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.editProfilePkg.EditProfileActivity;
import com.alpha.smartpickuser.initial.SplashActivity;
import com.alpha.smartpickuser.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpickuser.livetrackPkg.driverLiveTracking.DriverRootModel;
import com.alpha.smartpickuser.utility.AppSession;
import com.alpha.smartpickuser.utility.Constants;
import com.alpha.smartpickuser.utility.CustomProgressbar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class LiveTrackActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {
    private RelativeLayout searchRides,reEndRideId;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    private GoogleApiClient mGoogleApiClient;
    private Marker mCurrLocationMarker;
    private String invoice_id,date,user_name,user_profile,amount,distance,goodstype,user_number,type,picklocaitona,droplocationa;
    private CircleImageView circleImageView;
    private AppCompatImageView img_truck;
    private AppCompatTextView userNameId,userNumberId,amountId,text_date,destaince;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentLocationMarker;
    private Location currentLocation;
    private ApiServices apiServices;
    private boolean firstTimeFlag = true;
    private double lat_pick, lng_pick, lat_drop, lng_drop;
    String userId,driver_id;
    Polyline line;
    LatLng latLng;
    String droplocation;
    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            currentLocation = locationResult.getLastLocation();
            if (firstTimeFlag && googleMap != null) {
                animateCamera(currentLocation);
                firstTimeFlag = false;
            }
            showMarker(currentLocation);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_track);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Intent intent = getIntent();
        invoice_id=  intent.getStringExtra("invoice_id");
        date= intent.getStringExtra("date");
        user_name=intent.getStringExtra("user_name");
        user_profile= intent.getStringExtra("user_profile");
        amount =intent.getStringExtra("amount");
        distance =intent.getStringExtra("distance");
        goodstype =intent.getStringExtra("goodstype");
        driver_id = intent.getStringExtra("driver_id");
        picklocaitona = intent.getStringExtra("pick_location");
        droplocationa = intent.getStringExtra("drop_location");
        user_number =intent.getStringExtra("user_number");
        type =intent.getStringExtra("type");
        userId = AppSession.getStringPreferences(LiveTrackActivity.this, Constants.USER_ID);
        init();
    }


    private void init() {
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        AppCompatImageView ivbackId = findViewById(R.id.ivbackId);
        img_truck = findViewById(R.id.img_truck);
        amountId = findViewById(R.id.amountId);
        userNameId = findViewById(R.id.userNameId);
        text_date = findViewById(R.id.text_date);
        destaince = findViewById(R.id.destaince);
        ivbackId.setOnClickListener(this);
        reEndRideId = findViewById(R.id.reEndRideId);
        circleImageView = findViewById(R.id.ivuserprofileimageId);
        userNumberId = findViewById(R.id.userNumberId);
        reEndRideId.setOnClickListener(this);
        userNameId.setText(user_name);
        userNumberId.setText(user_number);
        amountId.setText(amount);
        text_date.setText(date);
        destaince.setText(distance);
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
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        JSONArray jre = null;
        try {
            jre = new JSONArray(picklocaitona);
            for (int j = 0; j < jre.length(); j++) {
                JSONObject jobject = jre.getJSONObject(j);
                lat_pick = jobject.getDouble("latitude");
                lng_pick = jobject.getDouble("longitude");
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_map_icon)).position(new LatLng(lat_pick,lng_pick)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            coordList = new ArrayList<>();
            JSONArray array = new JSONArray(droplocationa);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                coordList.add(new LatLng(jsonObject.getDouble("latitude"),jsonObject.getDouble("longitude")));
                LatLng orignal = new LatLng(jsonObject.getDouble("latitude"),jsonObject.getDouble("longitude"));
                googleMap.addMarker(new MarkerOptions().position(orignal));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LiveTrackActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(this, "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }
    private void animateCamera(@NonNull Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
        GoogleDirection.withServerKey("AIzaSyANqhj1K94Ckkodt5fWLgE_HYxMgN8Q2hs")
                .from(new LatLng(lat_pick,lng_pick))
                .and(coordList)
                .to(latLng)
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction) {
                        if(direction.isOK()) {
                            List<LatLng> encodedString = direction.getRouteList().get(0).getOverviewPolyline().getPointList();
                            line = googleMap.addPolyline(new PolylineOptions()
                                    .addAll(encodedString)
                                    .width(10)
                                    .color(Color.RED)
                                    .geodesic(true)
                            );
                        }
                    }
                    @Override
                    public void onDirectionFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(12).build();
    }
    private void showMarker(@NonNull Location currentLocation) {
        latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        JSONArray jsonArray = new JSONArray();
        JSONObject PickLocation = new JSONObject();
        try {
            PickLocation.put("latitude", currentLocation.getLatitude());
            PickLocation.put("longitude",  currentLocation.getLongitude());
            jsonArray.put(PickLocation);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        droplocation = jsonArray.toString();
        locationtracking();
        if (currentLocationMarker == null)
            currentLocationMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_map_icon)).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        googleMap = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LiveTrackActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finishAffinity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivbackId:
                onBackPressed();
                break;
            case R.id.reEndRideId:
                get_done();
                break;
        }
    }


    private void locationtracking() {
        apiServices.getDriverRoot(invoice_id,driver_id).enqueue(new Callback<DriverRootModel>() {
            @Override
            public void onResponse(Call<DriverRootModel> call, Response<DriverRootModel> response) {
                if (response.isSuccessful()) {
                    DriverRootModel getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        JSONArray jre = null;
                        try {
                            jre = new JSONArray(getLoginModle.getData().getDriverLocation());
                            for (int j = 0; j < jre.length(); j++) {
                               JSONObject jobject = jre.getJSONObject(j);
                               double lat_pick1 = jobject.getDouble("latitude");
                               double lng_pick2 = jobject.getDouble("longitude");
                               googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_map_icon)).position(new LatLng(lat_pick1,lng_pick2)));
                            }
                        } catch (JSONException e ) {
                            e.printStackTrace();
                        }catch (NullPointerException e){
                            e.fillInStackTrace();
                        }

                      //  googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_map_icon)).position(latLng));
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
            public void onFailure(Call<DriverRootModel> call, Throwable t) {
                Log.d("test", String.valueOf(t));

            }
        });
    }

    private void get_done() {
        CustomProgressbar.showProgressBar(LiveTrackActivity.this, false);
        apiServices.bookingacception(driver_id,invoice_id,"3").enqueue(new Callback<GenerateOtpModle>() {
            @Override
            public void onResponse(Call<GenerateOtpModle> call, Response<GenerateOtpModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GenerateOtpModle loginModle = response.body();
                    if (loginModle.getStatus()== true) {
                        Intent intent = new Intent(LiveTrackActivity.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                        finishAffinity();
                    } else {
                        Toast.makeText(LiveTrackActivity.this, ""+loginModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                CustomProgressbar.hideProgressBar();
                                jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                Toast.makeText(LiveTrackActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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

}
