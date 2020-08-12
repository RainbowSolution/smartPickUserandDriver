package com.alpha.smartpick.driver.livetrackPkg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
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
import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.HomeActivity;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpick.driver.initial.signupPkg.SignUpModle;
import com.alpha.smartpick.driver.livetrackPkg.gerroutePkg.GetRoutemodel;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.Constants;
import com.alpha.smartpick.driver.utility.CustomProgressbar;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private CircleImageView ivuserprofileimageId;
    private boolean firstTimeFlag = true;
    private double lat_pick, lng_pick, lat_drop, lng_drop;
    String userId;
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
        picklocaitona = intent.getStringExtra("picklocation");
        droplocationa = intent.getStringExtra("droplocation");
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
        ivuserprofileimageId = findViewById(R.id.ivuserprofileimageId);
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
        if (user_profile.isEmpty()) {
        } else {
            Picasso.with(LiveTrackActivity.this).load(RetrofitClient.USER_PROFILE_URL+user_profile).placeholder(R.drawable.progress_animation).into(ivuserprofileimageId);
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
        Log.d("latitude", String.valueOf(currentLocation.getLatitude()));
        Log.d("latitude", String.valueOf(currentLocation.getLongitude()));
        String latituded = String.valueOf(currentLocation.getLatitude());
        String logitude = String.valueOf(currentLocation.getLongitude());
        String strAdd = "";
        String  location_name ="";
        Geocoder geocoder = new Geocoder(LiveTrackActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(),  currentLocation.getLongitude(), 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                CustomProgressbar.hideProgressBar();
                strAdd = strReturnedAddress.toString();
                if (strAdd.contains("Unnamed Road,")){
                    strAdd = strReturnedAddress.toString().replace("Unnamed Road,","");
                }else if (strAdd.contains("Unnamed Road -")){
                    strAdd = strReturnedAddress.toString().replace("Unnamed Road -","");
                }else if (strAdd.contains("Unnamed Road ")){
                    strAdd = strReturnedAddress.toString().replace("Unnamed Road ","");
                 }
                    JSONArray jsonArray = new JSONArray();
                    JSONObject PickLocation = new JSONObject();
                    try {
                        PickLocation.put("address", strAdd);
                        PickLocation.put("latitude", currentLocation.getLatitude());
                        PickLocation.put("longitude", currentLocation.getLongitude());
                        jsonArray.put(PickLocation);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    droplocation = jsonArray.toString();
                   signup(droplocation,latituded,logitude,strAdd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (currentLocationMarker == null)
            currentLocationMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_map_icon)).position(latLng));
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
        apiServices.getBookingRouteById(invoice_id).enqueue(new Callback<GetRoutemodel>() {
            @Override
            public void onResponse(Call<GetRoutemodel> call, Response<GetRoutemodel> response) {
                if (response.isSuccessful()) {
                    GetRoutemodel getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        for (int j = 0; j < getLoginModle.getData().getPicupLocation().size(); j++) {
                          googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_map_icon)).position(new LatLng(getLoginModle.getData().getPicupLocation().get(j).getLatitude(),getLoginModle.getData().getPicupLocation().get(j).getLongitude())));
                          lat_pick = getLoginModle.getData().getPicupLocation().get(j).getLatitude();
                          lng_pick = getLoginModle.getData().getPicupLocation().get(j).getLongitude();
                        }
                        ArrayList<LatLng> coordList = new ArrayList<LatLng>();
                        for (int i =0; i<getLoginModle.getData().getDropLocation().size();i++){
                            coordList.add(new LatLng(getLoginModle.getData().getDropLocation().get(i).getLatitude(),getLoginModle.getData().getDropLocation().get(i).getLongitude()));
                            LatLng orignal = new LatLng(getLoginModle.getData().getDropLocation().get(i).getLatitude(),getLoginModle.getData().getDropLocation().get(i).getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(orignal));
                        }
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
            public void onFailure(Call<GetRoutemodel> call, Throwable t) {
                Log.d("test", String.valueOf(t));

            }
        });
    }

    private void signup(final String droppoint,final String latitude,final String longnitue,final String address) {
            apiServices.driverLocation(invoice_id,userId,droppoint,latitude,longnitue,address).enqueue(new Callback<SignUpModle>() {
            @Override
            public void onResponse(Call<SignUpModle> call, Response<SignUpModle> response) {
                if (response.isSuccessful()) {
                    SignUpModle getRegistrationModle = response.body();
                    if (getRegistrationModle.getStatus() == true) {
                        //Toast.makeText(LiveTrackActivity.this, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LiveTrackActivity.this, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 400) {
                    if (!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString("message");
                            Toast.makeText(LiveTrackActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpModle> call, Throwable t) {

            }

        });
    }

    private void get_done() {
        CustomProgressbar.showProgressBar(LiveTrackActivity.this, false);
        apiServices.bookingacception(userId,invoice_id,"3").enqueue(new Callback<GenerateOtpModle>() {
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
