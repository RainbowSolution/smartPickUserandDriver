package com.alpha.smartpick.driver;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.initial.signupPkg.SignUpModle;
import com.alpha.smartpick.driver.livetrackPkg.LatLngInterpolator;
import com.alpha.smartpick.driver.livetrackPkg.LiveTrackActivity;
import com.alpha.smartpick.driver.livetrackPkg.MarkerAnimation;
import com.alpha.smartpick.driver.notificationPkg.getNotificaitonList.NotificationSmsCount;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.Constants;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HomeFragmentMain extends Fragment implements OnMapReadyCallback,View.OnClickListener{
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
    final int radius = 100;
    private Circle circle;
    public AddTitleInterFace mCallback;
    Context context;
    String droplocation,vehicletype;
    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    public interface AddTitleInterFace {
        void passData(String data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (AddTitleInterFace) getActivity();
        } catch (Exception e) {
        }
    }
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_map, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        userId = AppSession.getStringPreferences(getActivity(), Constants.USER_ID);
        vehicletype = AppSession.getStringPreferences(getActivity(), Constants.VEHICLE_TYPE);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(getActivity(), "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(getActivity(), "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }
    private void animateCamera(@NonNull Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // Instantiates a new CircleOptions object and defines the center and radius

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }
    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(15).build();
    }
    private void showMarker(@NonNull Location currentLocation) {
        latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        String lattitude = String.valueOf(currentLocation.getLatitude());
        String logintude = String.valueOf(currentLocation.getLongitude());
        signup(lattitude,logintude);
        if (currentLocationMarker == null)
            currentLocationMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_map_icon)).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            startCurrentLocationUpdates();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        googleMap = null;
    }

    private void signup(final String latituded,final String logitude) {
        apiServices.driverLocationUpdate(userId,latituded,logitude).enqueue(new Callback<SignUpModle>() {
            @Override
            public void onResponse(Call<SignUpModle> call, Response<SignUpModle> response) {
                if (response.isSuccessful()) {
                    try {
                        SignUpModle getRegistrationModle = response.body();
                        if (getRegistrationModle.getStatus() == true) {
                           // Toast.makeText(getActivity(), "" + getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                            //  Notificaoticlear();
                        } else {
                           // Toast.makeText(getActivity(), "" + getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        if (response.code() == 400) {
                            if (!response.isSuccessful()) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response.errorBody().string());
                                    String message = jsonObject.getString("message");
                                    Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<SignUpModle> call, Throwable t) {

            }

        });
    }
    private void Notificaoticlear() {
        apiServices.notificaoticout(userId,vehicletype).enqueue(new Callback<NotificationSmsCount>() {
            @Override
            public void onResponse(Call<NotificationSmsCount> call, Response<NotificationSmsCount> response) {
                if (response.isSuccessful()) {
                    try {
                    NotificationSmsCount getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        mCallback.passData(getLoginModle.getData());
                    }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }else if (response.code() == 400) {
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

            @Override
            public void onFailure(Call<NotificationSmsCount> call, Throwable t) {
                Log.d("test", String.valueOf(t));

            }
        });
    }
}
