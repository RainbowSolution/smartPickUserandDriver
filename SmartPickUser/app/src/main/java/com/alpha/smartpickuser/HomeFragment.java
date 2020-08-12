package com.alpha.smartpickuser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.bookPkg.BookActivity;
import com.alpha.smartpickuser.utility.CheckNetwork;
import com.alpha.smartpickuser.utility.CustomProgressbar;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener,
        OnMapReadyCallback {
    private RelativeLayout repickupNowId, repickupLaterId;
    private SupportMapFragment mapFrag;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;
    private Activity activity;
    private Context context;
    private RelativeLayout relativeid;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 16;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private View line_start;
    private Calendar myCalendar = Calendar.getInstance();
    private Location mLastKnownLocation;
    private LinearLayout view_pickup;
    private LinearLayout view_drop;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private boolean clicked = false;
    private AppCompatEditText txt_p_loc, txt_d_loc;
    private AppCompatImageView img_truck, img_truck_med, img_truck_cov, green_image_icon, read_image_icon;


    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;

    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private double lat_pick, lng_pick, lat_drop, lng_drop;
    private String currentDateMain, datemain, vehicletype;

    AppCompatImageView btn_add,btn_add_a,btn_add_b,btn_add_c,btn_add_d;
    LinearLayout view_drop_a,view_drop_b,view_drop_c,view_drop_d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        Places.initialize(getActivity(), getString(R.string.google_maps_key));
        PlacesClient mPlacesClient = Places.createClient(getActivity());
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        init(view);
        return view;
    }

    private void init(View view) {
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        repickupLaterId = view.findViewById(R.id.repickupLaterId);
        repickupLaterId.setOnClickListener(this);
        repickupNowId = view.findViewById(R.id.repickupNowId);
        txt_p_loc = view.findViewById(R.id.txt_p_loc);
        txt_d_loc = view.findViewById(R.id.txt_d_loc);
        view_pickup = view.findViewById(R.id.view_pickup);
        view_drop = view.findViewById(R.id.view_drop);
        line_start = view.findViewById(R.id.line_start);
        LinearLayout row_truck = view.findViewById(R.id.row_truck);
        LinearLayout row_truck_med = view.findViewById(R.id.row_truck_med);
        LinearLayout row_truck_cov = view.findViewById(R.id.row_truck_cov);
        img_truck = view.findViewById(R.id.img_truck);
        img_truck_med = view.findViewById(R.id.img_truck_med);
        img_truck_cov = view.findViewById(R.id.img_truck_cov);
        green_image_icon = view.findViewById(R.id.green_image_icon);
        read_image_icon = view.findViewById(R.id.read_image_icon);
        relativeid = view.findViewById(R.id.relativeid);
        repickupNowId.setOnClickListener(this);
        view_pickup.setSelected(true);
        img_truck.setSelected(true);
        view_pickup.setOnClickListener(this);
        view_drop.setOnClickListener(this);
        row_truck.setOnClickListener(this);
        row_truck_med.setOnClickListener(this);
        row_truck_cov.setOnClickListener(this);
        vehicletype = "1";
        getcurrentdate();
    }


/*
    private void multipal(View view){
        final boolean[] btnadd = {false};
        final boolean[] btnadda = {false};
        final boolean[] btnaddb = {false};
        final boolean[] btnaddc = {false};
        final boolean[] btnaddd = {false};
        btn_add = view.findViewById(R.id.btn_add);
        btn_add_a = view.findViewById(R.id.btn_add_a);
        btn_add_b = view.findViewById(R.id.btn_add_b);
        btn_add_c =view.findViewById(R.id.btn_add_c);
        btn_add_d = view.findViewById(R.id.btn_add_d);
        view_drop_a =view.findViewById(R.id.view_drop_a);
        view_drop_a.setOnClickListener(this);
        view_drop_b =view.findViewById(R.id.view_drop_b);
        view_drop_b.setOnClickListener(this);
        view_drop_c = view.findViewById(R.id.view_drop_c);
        view_drop_c.setOnClickListener(this);
        view_drop_d = view.findViewById(R.id.view_drop_d);
        view_drop_d.setOnClickListener(this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnadd[0]){
                    view_drop_a.setVisibility(View.GONE);
                    btn_add.setBackgroundResource(R.drawable.ic_add);
                    btnadd[0] =false;
                }else {
                    view_drop_a.setVisibility(View.VISIBLE);
                    btn_add.setBackgroundResource(R.drawable.ic_remove);
                    btnadd[0] = true;
                }
            }
        });
        btn_add_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnadda[0]){
                    view_drop_b.setVisibility(View.GONE);
                    btn_add_a.setBackgroundResource(R.drawable.ic_add);
                    btnadda[0] =false;
                }else {
                    view_drop_b.setVisibility(View.VISIBLE);
                    btn_add_a.setBackgroundResource(R.drawable.ic_remove);
                    btnadda[0] = true;
                }
            }
        });
        btn_add_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnaddb[0]){
                    view_drop_c.setVisibility(View.GONE);
                    btn_add_b.setBackgroundResource(R.drawable.ic_add);
                    btnaddb[0] =false;
                }else {
                    view_drop_c.setVisibility(View.VISIBLE);
                    btn_add_b.setBackgroundResource(R.drawable.ic_remove);
                    btnaddb[0] = true;
                }
            }
        });

        btn_add_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnaddc[0]){
                    view_drop_d.setVisibility(View.GONE);
                    btn_add_c.setBackgroundResource(R.drawable.ic_add);
                    btnaddc[0] =false;
                }else {
                    view_drop_d.setVisibility(View.VISIBLE);
                    btn_add_c.setBackgroundResource(R.drawable.ic_remove);
                    btnaddc[0] = true;
                }
            }
        });
    }
*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repickupLaterId:
                validation1(v);
                break;
            case R.id.repickupNowId:
                validation(v);
                break;
            case R.id.view_drop:
                clicked = true;
                view_drop.setSelected(true);
                view_pickup.setSelected(false);
                line_start.setVisibility(View.GONE);
                green_image_icon.setVisibility(View.GONE);
                read_image_icon.setVisibility(View.VISIBLE);
                onMapReady(mMap);
                break;
            case R.id.view_pickup:
                clicked = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                break;
            case R.id.view_drop_a:
                clicked = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(false);
                view_drop_a.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                break;
            case R.id.view_drop_b:
                clicked = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(false);
                view_drop_a.setSelected(false);
                view_drop_b.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                break;
            case R.id.view_drop_c:
                clicked = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(false);
                view_drop_a.setSelected(false);
                view_drop_b.setSelected(false);
                view_drop_c.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                break;
            case R.id.view_drop_d:
                clicked = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(false);
                view_drop_a.setSelected(false);
                view_drop_b.setSelected(false);
                view_drop_c.setSelected(false);
                view_drop_d.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                break;
            case R.id.row_truck_cov:
                img_truck_cov.setSelected(true);
                img_truck_med.setSelected(false);
                img_truck.setSelected(false);
                vehicletype = "3";
                break;
            case R.id.row_truck_med:
                img_truck_cov.setSelected(false);
                img_truck_med.setSelected(true);
                img_truck.setSelected(false);
                vehicletype = "2";
                break;
            case R.id.row_truck:
                img_truck_cov.setSelected(false);
                img_truck_med.setSelected(false);
                img_truck.setSelected(true);
                vehicletype = "1";
                break;

        }

    }

    private void validation1(View v) {
        if (TextUtils.isEmpty(txt_p_loc.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(relativeid, "Please select valid pick location !", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (TextUtils.isEmpty(txt_d_loc.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(relativeid, "Please select valid drop location !", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (CheckNetwork.isNetAvailable(getActivity())) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        } else {
            Snackbar snackbar = Snackbar
                    .make(relativeid, "Check network connection !", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void validation(View v) {
        if (TextUtils.isEmpty(txt_p_loc.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(relativeid, "Please select valid pick location !", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (TextUtils.isEmpty(txt_d_loc.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(relativeid, "Please select valid drop location !", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (CheckNetwork.isNetAvailable(getActivity())) {
            Intent intent1 = new Intent(getActivity(), BookActivity.class);
            intent1.putExtra("pick_location", txt_p_loc.getText().toString().trim());
            intent1.putExtra("drop_location", txt_d_loc.getText().toString().trim());
            intent1.putExtra("date", currentDateMain);
            intent1.putExtra("vehicletype", vehicletype);
            startActivity(intent1);
            getActivity().overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
        } else {
            Snackbar snackbar = Snackbar
                    .make(relativeid, "Check network connection !", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
       // getLocationPermission();
       // updateLocationUI();
       // getDeviceLocation();
        View mapView = mapFrag.getView();

       // moveCompassButton(mapView);
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(22.7196, 75.8577),
                        new LatLng(23.1765, 75.7885),
                        new LatLng(22.9676, 76.0534),
                        new LatLng(23.2599, 77.4126),
                        new LatLng(22.7196, 75.8577)));
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline1.setTag("A");
        // Style the polyline.
        stylePolyline(polyline1);
      /*  mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
            *//*     lat =  mMap.getCameraPosition().target.latitude;
                 lng =  mMap.getCameraPosition().target.longitude;*//*
              *//*  Geocoder geocoder;
                List<Address> addresses;
                CustomProgressbar.showProgressBar(getActivity(), false);
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();*//*

                if (clicked) {
                    lat_drop = mMap.getCameraPosition().target.latitude;
                    lng_drop = mMap.getCameraPosition().target.longitude;
                    getCompleteAddressString(lat_drop, lng_drop);

                } else {
                    lat_pick = mMap.getCameraPosition().target.latitude;
                    lng_pick = mMap.getCameraPosition().target.longitude;
                    CustomProgressbar.showProgressBar(getActivity(), false);
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute();
                }


            }
        });
*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.7196, 75.8577), 10));

        // Set listeners for click events.

    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String strAdd;

        @Override
        protected String doInBackground(String... params) {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat_pick, lng_pick, 1);
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
            txt_p_loc.setText(result);


        }

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
                txt_d_loc.setText(strReturnedAddress.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
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
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), 12));

                            }
                        } else {
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, 12));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);

                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
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
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void getcurrentdate() {
        String myFormat = "MMM d yyyy   HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        currentDateMain = sdf.format(myCalendar.getTime());
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MMM d yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datemain = sdf.format(myCalendar.getTime());
        timepicker();
    }

    private void timepicker() {
        int CalendarHour, CalendarMinute;
        final String[] format = new String[1];
        final Calendar calendar = Calendar.getInstance();
        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timepickerdialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String hour = hourOfDay + "";
                if (hourOfDay == 0) {
                    hourOfDay += 12;
                } else if (hourOfDay == 12) {
                    format[0] = " PM";
                } else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    format[0] = " PM";
                } else {
                    format[0] = " Am";
                }
                String min = minute + "";
                if (minute < 10)
                    min = "0" + minute;
                String houra = hourOfDay + "";
                if (hourOfDay < 10)
                    houra = "0" + hourOfDay;
                //startMorningTimeId.setText(hourOfDay + ":" + min + format[0]);
                currentDateMain = datemain + " " + houra + ":" + min;
                Intent intent1 = new Intent(getActivity(), BookActivity.class);
                intent1.putExtra("pick_location", txt_p_loc.getText().toString().trim());
                intent1.putExtra("drop_location", txt_d_loc.getText().toString().trim());
                intent1.putExtra("date", currentDateMain);
                intent1.putExtra("vehicletype", vehicletype);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        }, CalendarHour, CalendarMinute, false);
        timepickerdialog.show();
    }
    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "A":
                // Use a custom bitmap as the cap at the start of the line.
                polyline.setStartCap(
                        new CustomCap(
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow), 10));
                break;
            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }
    private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "alpha":
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
                fillColor = COLOR_PURPLE_ARGB;
                break;
            case "beta":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_ORANGE_ARGB;
                fillColor = COLOR_BLUE_ARGB;
                break;
        }

        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }

}
