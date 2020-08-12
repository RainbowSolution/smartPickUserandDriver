package com.alpha.smartpickuser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.alpha.smartpickuser.droplocationAddFragmentPkg.AddDropActivity;
import com.alpha.smartpickuser.livetrackPkg.LatLngInterpolator;
import com.alpha.smartpickuser.livetrackPkg.MarkerAnimation;
import com.alpha.smartpickuser.utility.CustomProgressbar;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MainFragment  extends Fragment implements OnMapReadyCallback,View.OnClickListener {
    private static final String TAG = "";
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    private GoogleMap googleMap;
    private Marker currentLocationMarker;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private CameraPosition mCameraPosition;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private LinearLayout view_drop,view_pickup;
    private AppCompatImageView img_truck, img_truck_med, img_truck_cov,btn_add, green_image_icon, read_image_icon;
    private boolean clicked = false;
    private View line_start;
    private double lat_pick, lng_pick, lat_drop, lng_drop;
    private AppCompatTextView txt_p_loc, txt_d_loc;
    Polyline line;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 101 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), "AIzaSyAiR53jportsnGTs3vpQMTDLkQBgG7NO64");
        }
        init(view);
        return view;
    }

    private void init(View view) {
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        view_drop = view.findViewById(R.id.view_drop);
        view_pickup = view.findViewById(R.id.view_pickup);
        green_image_icon = view.findViewById(R.id.green_image_icon);
        read_image_icon = view.findViewById(R.id.read_image_icon);
        line_start = view.findViewById(R.id.line_start);
        view_pickup.setSelected(true);
        view_pickup.setOnClickListener(this);
        view_drop.setOnClickListener(this);
        txt_p_loc = view.findViewById(R.id.txt_p_loc);
        txt_d_loc = view.findViewById(R.id.txt_d_loc);
        txt_p_loc.setOnClickListener(this);
        txt_d_loc.setOnClickListener(this);
        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
        View mapView = mapFrag.getView();
        moveCompassButton(mapView);
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
           /*  lat =  googleMap.getCameraPosition().target.latitude;
                lng =  googleMap.getCameraPosition().target.longitude;
              Geocoder geocoder;
                List<Address> addresses;
                CustomProgressbar.showProgressBar(getActivity(), false);
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();*/

                if (clicked) {
                    lat_drop = googleMap.getCameraPosition().target.latitude;
                    lng_drop = googleMap.getCameraPosition().target.longitude;
                    CustomProgressbar.showProgressBar(getActivity(), false);
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute();

                } else {
                    lat_pick = googleMap.getCameraPosition().target.latitude;
                    lng_pick = googleMap.getCameraPosition().target.longitude;
                    getCompleteAddressString(lat_pick, lng_pick);
                }


            }
        });


    }



    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");

                }
                CustomProgressbar.hideProgressBar();
                strAdd = strReturnedAddress.toString();
                txt_p_loc.setText(strReturnedAddress.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String strAdd;
        @Override
        protected String doInBackground(String... params) {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat_drop, lng_drop, 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");
                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    CustomProgressbar.hideProgressBar();
                    strAdd = strReturnedAddress.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return strAdd;
        }


        @Override
        protected void onPostExecute(String result) {
            CustomProgressbar.hideProgressBar();
            txt_d_loc.setText(result);
            googleMap.clear();
            LatLng orignal = new LatLng(lat_pick,lng_pick);
            LatLng des = new LatLng(lat_drop,lng_drop);
            GoogleDirection.withServerKey("AIzaSyAiR53jportsnGTs3vpQMTDLkQBgG7NO64")
                    .from(new LatLng(lat_pick,lng_pick))
                    .to(new LatLng(lat_drop,lng_drop))
                    .avoid(AvoidType.FERRIES)
                    .avoid(AvoidType.HIGHWAYS)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction) {
                            if(direction.isOK()) {
                                googleMap.addMarker(new MarkerOptions().position(orignal));
                                googleMap.addMarker(new MarkerOptions().position(des));
                                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                                googleMap.addPolyline(DirectionConverter.createPolyline(getActivity(), directionPositionList, 5, Color.RED));
                            }

                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }

    }
    private void animateCamera(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(16).build();
    }

    private void showMarker(@NonNull Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        if (currentLocationMarker == null)
            currentLocationMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_map_icon)).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }



    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), 9));

                            }
                        } else {
                            googleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, 9));
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                } else {
                    Toast.makeText(getActivity(), "vvass", Toast.LENGTH_SHORT).show();
                }
            }
        }
        updateLocationUI();
    }


    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_drop:
                clicked = true;
                view_drop.setSelected(true);
                view_pickup.setSelected(false);
                line_start.setVisibility(View.GONE);
                green_image_icon.setVisibility(View.GONE);
                read_image_icon.setVisibility(View.VISIBLE);
                break;
            case R.id.view_pickup:
                clicked = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                break;
            case R.id.txt_p_loc:
                clicked = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields) //NIGERIA
                        .build(getActivity());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.txt_d_loc:
                clicked = true;
                view_drop.setSelected(true);
                view_pickup.setSelected(false);
                line_start.setVisibility(View.GONE);
                green_image_icon.setVisibility(View.GONE);
                read_image_icon.setVisibility(View.VISIBLE);
                List<Place.Field> fields1 = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent1 = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields1) //NIGERIA
                        .build(getActivity());
                startActivityForResult(intent1, AUTOCOMPLETE_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btn_add:
                Intent intent2 = new Intent(getActivity(), AddDropActivity.class);
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                break;

        }
    }
    private void moveCompassButton(View mapView){
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 0);
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();
                if (clicked) {
                    assert latLng != null;
                    lat_drop =latLng.latitude;
                    lng_drop = latLng.longitude;
                    txt_d_loc.setText(place.getAddress());
                    CustomProgressbar.showProgressBar(getActivity(), false);
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute();
                } else {
                    assert latLng != null;
                    lat_pick = latLng.latitude;
                    lng_pick = latLng.longitude;
                    txt_p_loc.setText(place.getAddress());
                    getCompleteAddressString(lat_pick, lng_pick);
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getActivity(), "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
