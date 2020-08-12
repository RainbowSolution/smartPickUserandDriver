package com.alpha.smartpickuser;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.alpha.smartpickuser.ApiPkg.ApiServices;
import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.bookPkg.BookActivity;
import com.alpha.smartpickuser.droplocationAddFragmentPkg.AddDropActivity;
import com.alpha.smartpickuser.droplocationAddFragmentPkg.PlaceModel;
import com.alpha.smartpickuser.droplocationAddFragmentPkg.Roomdatabase.DatabaseClient;
import com.alpha.smartpickuser.editProfilePkg.EditProfileActivity;
import com.alpha.smartpickuser.initial.SplashActivity;
import com.alpha.smartpickuser.notificationPkg.NotificationActivity;
import com.alpha.smartpickuser.notificationPkg.getNotificationListPkg.NotificationSmsCount;
import com.alpha.smartpickuser.ridehistoryPkg.RideHistoryActivity;
import com.alpha.smartpickuser.utility.AppSession;
import com.alpha.smartpickuser.utility.CheckNetwork;
import com.alpha.smartpickuser.utility.Constants;
import com.alpha.smartpickuser.utility.CustomModel;
import com.alpha.smartpickuser.utility.CustomProgressbar;
import com.alpha.smartpickuser.utility.PrefData;
import com.alpha.smartpickuser.utility.YourPreference;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener , OnMapReadyCallback , CustomModel.OnCustomStateListener {
    AppCompatImageView ivLogoHome,ivnotificaontHomeId;
    DrawerLayout drawer;
    NavigationView navigationView;
    LinearLayout LieditProfileid;
    private RelativeLayout relativeid,repickupNowId, repickupLaterId;
    AppCompatTextView as,ivNofificationcoutId;
    CircleImageView circleImageView;
    View currentView;
    AppCompatTextView tvUserNameId;
    private String username,userimage;
    private static FragmentManager fragmentManager;
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
    private boolean clickedmoviestart = false;
    private View line_start;
    private double lat_pick, lng_pick, lat_drop, lng_drop;
    private AppCompatTextView txt_p_loc, txt_d_loc,txt_multiple;
    Polyline line;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 101 ;
    private String currentDateMain, datemain, vehicletype;
    private Calendar myCalendar = Calendar.getInstance();
    String pickLocaiton,droplocation,user_id;
    private ApiServices apiServices;
    private String language;
    Context con;
    PrefData prefData;
    private String distance,pickuplocationsigalandmultiple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        if (!Places.isInitialized()) {
            Places.initialize(this
                    , "AIzaSyANqhj1K94Ckkodt5fWLgE_HYxMgN8Q2hs");
        }
        CustomModel.getInstance().setListener(HomeActivity.this);
        init();
        initA();
        username = AppSession.getStringPreferences(HomeActivity.this, Constants.USERNAME);
        user_id = AppSession.getStringPreferences(HomeActivity.this, Constants.USER_ID);
        userimage = AppSession.getStringPreferences(HomeActivity.this, Constants.PROFILE_IMAGE);
        if (username != null){
            tvUserNameId.setText(username);
        }
        if (userimage.isEmpty()){

        }else {
            Picasso.with(HomeActivity.this).load(RetrofitClient.USER_PROFILE_URL+userimage).placeholder(R.drawable.progress_animation).into(circleImageView);
        }
        Notificaoticlear();
        pickuplocationsigalandmultiple="1";
        con = getApplicationContext();
        prefData = new PrefData(con);

    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivLogoHome = findViewById(R.id.ivLogoHomeId);
        ivnotificaontHomeId = findViewById(R.id.ivnotificaontHomeId);
        ivLogoHome.setOnClickListener(this);
        ivnotificaontHomeId.setOnClickListener(this);
        ivNofificationcoutId = findViewById(R.id.ivNofificationcoutId);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        tvUserNameId = navigationView.getHeaderView(0).findViewById(R.id.tvUserNameId);
        LieditProfileid = navigationView.getHeaderView(0).findViewById(R.id.LieditProfileid);
        circleImageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        LieditProfileid.setOnClickListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    navigationView.getMenu().getItem(0).setChecked(true);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    navigationView.getMenu().getItem(3).setChecked(false);
                    navigationView.getMenu().getItem(4).setChecked(false);
                    navigationView.getMenu().getItem(5).setChecked(false);
                }else if (id == R.id.nav_ride_history) {
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(true);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    navigationView.getMenu().getItem(3).setChecked(false);
                    navigationView.getMenu().getItem(4).setChecked(false);
                    navigationView.getMenu().getItem(5).setChecked(false);
                    drawer.closeDrawer(Gravity.LEFT);
                    Intent intent = new Intent(HomeActivity.this, RideHistoryActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                }else if (id == R.id.nav_notification) {
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(true);
                    navigationView.getMenu().getItem(3).setChecked(false);
                    navigationView.getMenu().getItem(4).setChecked(false);
                    navigationView.getMenu().getItem(5).setChecked(false);
                    drawer.closeDrawer(Gravity.LEFT);
                    Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    finish();
                }  else if (id == R.id.nav_about_me) {
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    navigationView.getMenu().getItem(3).setChecked(true);
                    navigationView.getMenu().getItem(4).setChecked(false);
                    navigationView.getMenu().getItem(5).setChecked(false);
                    drawer.closeDrawer(Gravity.LEFT);
                    String number = ("tel:" + getResources().getString(R.string.about_me));
                    Intent mIntent = new Intent(Intent.ACTION_VIEW);
                    mIntent.setData(Uri.parse(number));
                    startActivity(mIntent);
                }else if (id ==R.id.nav_laungvage){
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    navigationView.getMenu().getItem(3).setChecked(false);
                    navigationView.getMenu().getItem(4).setChecked(true);
                    navigationView.getMenu().getItem(5).setChecked(false);
                    language_out();
                } else if (id ==R.id.nav_logout){
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    navigationView.getMenu().getItem(3).setChecked(false);
                    navigationView.getMenu().getItem(4).setChecked(false);
                    navigationView.getMenu().getItem(4).setChecked(true);
                    log_out();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void initA() {
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        view_drop = findViewById(R.id.view_drop);
        view_pickup = findViewById(R.id.view_pickup);
        green_image_icon = findViewById(R.id.green_image_icon);
        read_image_icon = findViewById(R.id.read_image_icon);
        line_start = findViewById(R.id.line_start);
        view_pickup.setSelected(true);
        view_pickup.setOnClickListener(this);
        view_drop.setOnClickListener(this);
        txt_p_loc = findViewById(R.id.txt_p_loc);
        txt_d_loc = findViewById(R.id.txt_d_loc);
        txt_p_loc.setOnClickListener(this);
        txt_d_loc.setOnClickListener(this);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        LinearLayout row_truck = findViewById(R.id.row_truck);
        LinearLayout row_truck_med = findViewById(R.id.row_truck_med);
        LinearLayout row_truck_cov = findViewById(R.id.row_truck_cov);
        img_truck = findViewById(R.id.img_truck);
        img_truck_med = findViewById(R.id.img_truck_med);
        img_truck_cov = findViewById(R.id.img_truck_cov);
        img_truck.setSelected(true);
        row_truck.setOnClickListener(this);
        row_truck_med.setOnClickListener(this);
        row_truck_cov.setOnClickListener(this);
        relativeid = findViewById(R.id.relativeid);
        repickupLaterId = findViewById(R.id.repickupLaterId);
        repickupLaterId.setOnClickListener(this);
        repickupNowId = findViewById(R.id.repickupNowId);
        repickupNowId.setOnClickListener(this);
        txt_multiple = findViewById(R.id.txt_multiple);
        vehicletype = "1";
        getcurrentdate();

    }
    public void language_out() {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.language_diloge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        final AppCompatCheckBox cbHindiId = dialog.findViewById(R.id.cbHindiId);
        final AppCompatCheckBox cbEnglishid = dialog.findViewById(R.id.cbEnglishid);
        ImageView ivlogoutClose = dialog.findViewById(R.id.ivlogoutCloseId);
        RelativeLayout rlOkId = dialog.findViewById(R.id.rlOkId);
        if (prefData.getCurrentLanguage().equals("ar")){
            cbHindiId.setChecked(true);
        }else if (prefData.getCurrentLanguage().equals("eng")){
            cbEnglishid.setChecked(true);
        }
        ivlogoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        cbHindiId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    cbEnglishid.setChecked(false);
                    language = "ar";
                }
            }
        });
        cbEnglishid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    language = "eng";
                    cbHindiId.setChecked(false);
                }
            }
        });
        rlOkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language==null){
                    Toast.makeText(HomeActivity.this, "select Language", Toast.LENGTH_SHORT).show();
                }else {
                    Locale myLocale = new Locale(language);
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = myLocale;
                    res.updateConfiguration(conf, dm);
                    prefData.setCurrentLanguage(language);
                    Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }

            }
        });
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLogoHomeId:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    drawer.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.ivnotificaontHomeId:
                Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                finish();
                break;
            case R.id.LieditProfileid:
                drawer.closeDrawer(Gravity.LEFT);
                Intent intent1 = new Intent(HomeActivity.this, EditProfileActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
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
                googleMap.clear();
                clickedmoviestart = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                txt_multiple.setVisibility(View.GONE);
                btn_add.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_p_loc:
                clicked = false;
                googleMap.clear();
                clickedmoviestart = false;
                view_drop.setSelected(false);
                view_pickup.setSelected(true);
                line_start.setVisibility(View.VISIBLE);
                green_image_icon.setVisibility(View.VISIBLE);
                read_image_icon.setVisibility(View.GONE);
                txt_multiple.setVisibility(View.GONE);
                btn_add.setVisibility(View.VISIBLE);
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent2 = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields) //NIGERIA
                        .build(HomeActivity.this);
                startActivityForResult(intent2, AUTOCOMPLETE_REQUEST_CODE);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.txt_d_loc:
                clicked = true;
                view_drop.setSelected(true);
                view_pickup.setSelected(false);
                line_start.setVisibility(View.GONE);
                green_image_icon.setVisibility(View.GONE);
                read_image_icon.setVisibility(View.VISIBLE);
                if (clickedmoviestart){
                    Intent intent4 = new Intent(HomeActivity.this, AddDropActivity.class);
                    intent4.putExtra("pick_locaiton",txt_p_loc.getText().toString());
                    startActivity(intent4);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                }else {
                    List<Place.Field> fields1 = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                    Intent intent3 = new Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.FULLSCREEN, fields1) //NIGERIA
                            .build(HomeActivity.this);
                    startActivityForResult(intent3, AUTOCOMPLETE_REQUEST_CODE);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                break;
            case R.id.btn_add:
                if (txt_d_loc.getText().toString().isEmpty()){
                    List<Place.Field> fields2 = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                    Intent intent4 = new Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.FULLSCREEN, fields2) //NIGERIA
                            .build(HomeActivity.this);
                    startActivityForResult(intent4, AUTOCOMPLETE_REQUEST_CODE);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else {
                    AppSession.setStringPreferences(HomeActivity.this,"drop",txt_d_loc.getText().toString());
                    Intent intent4 = new Intent(HomeActivity.this, AddDropActivity.class);
                    intent4.putExtra("pick_locaiton",txt_p_loc.getText().toString());
                    startActivity(intent4);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                }
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
            case R.id.repickupLaterId:
                if (CheckNetwork.isNetAvailable(HomeActivity.this)) {
                    validation1(v);
                }else {
                    Snackbar snackbar = Snackbar
                            .make(relativeid, "Check network connection !", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                break;
            case R.id.repickupNowId:
                validation(v);
                break;
        }
    }


    private void validation1(View v) {
        if (TextUtils.isEmpty(txt_p_loc.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(relativeid, getResources().getString(R.string.Please_select_valid_pick_location), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (TextUtils.isEmpty(txt_d_loc.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(relativeid,  getResources().getString(R.string.Please_select_valid_drop_location), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (CheckNetwork.isNetAvailable(HomeActivity.this)) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, date, myCalendar
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
                    .make(relativeid, getResources().getString(R.string.Please_select_valid_pick_location), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (TextUtils.isEmpty(txt_d_loc.getText().toString().trim())) {
            Snackbar snackbar = Snackbar
                    .make(relativeid, getResources().getString(R.string.Please_select_valid_drop_location), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (CheckNetwork.isNetAvailable(HomeActivity.this)) {
            if (pickuplocationsigalandmultiple.equals("1")){
                Intent intent1 = new Intent(HomeActivity.this, BookActivity.class);
                intent1.putExtra("pick_location", pickLocaiton);
                intent1.putExtra("drop_location", droplocation);
                intent1.putExtra("lat_drop",lat_drop);
                intent1.putExtra("lng_drop",lng_drop);
                intent1.putExtra("date", currentDateMain);
                intent1.putExtra("distance",distance);
                intent1.putExtra("pick_up_letter","0");
                intent1.putExtra("pick_up_location_multiple_and_single",pickuplocationsigalandmultiple);
                intent1.putExtra("vehicletype", vehicletype);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
            }else {
                Intent intent1 = new Intent(HomeActivity.this, BookActivity.class);
                intent1.putExtra("pick_location", pickLocaiton);
                intent1.putExtra("drop_location", droplocation);
                intent1.putExtra("date", currentDateMain);
                intent1.putExtra("distance",distance);
                intent1.putExtra("pick_up_letter","0");
                intent1.putExtra("pick_up_location_multiple_and_single",pickuplocationsigalandmultiple);
                intent1.putExtra("vehicletype", vehicletype);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
            }

        } else {
            Snackbar snackbar = Snackbar
                    .make(relativeid, "Check network connection !", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.exit))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                androidx.appcompat.app.AlertDialog alert = builder.create();
                alert.show();
            }
    public void log_out() {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.log_out);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        ImageView ivlogoutClose = dialog.findViewById(R.id.ivlogoutCloseId);
        RelativeLayout rllogout = dialog.findViewById(R.id.rllogoutId);
        ivlogoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        rllogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YourPreference yourPrefrence = YourPreference.getInstance(HomeActivity.this);
                yourPrefrence.saveData("status", "");
                Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();

            }
        });
        dialog.show();

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
                if (clickedmoviestart){
                }else {
                    if (clicked) {
                        lat_drop = googleMap.getCameraPosition().target.latitude;
                        lng_drop = googleMap.getCameraPosition().target.longitude;
                        CustomProgressbar.showProgressBar(HomeActivity.this, false);
                        AsyncTaskRunner1 runner = new AsyncTaskRunner1();
                        runner.execute();
                    } else {
                        lat_pick = googleMap.getCameraPosition().target.latitude;
                        lng_pick = googleMap.getCameraPosition().target.longitude;
                        getCompleteAddressString(lat_pick, lng_pick);
                    }
                }
            }
        });

    }

    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
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
                if (strAdd.contains("Unnamed Road,")){
                    strAdd = strReturnedAddress.toString().replace("Unnamed Road,","");
                }else if (strAdd.contains("Unnamed Road -")){
                    strAdd = strReturnedAddress.toString().replace("Unnamed Road -","");
                }else if (strAdd.contains("Unnamed Road ")){
                    strAdd = strReturnedAddress.toString().replace("Unnamed Road ","");
                }
                txt_p_loc.setText(strAdd);
                JSONArray jsonArray = new JSONArray();
                JSONObject PickLocation = new JSONObject();
                try {
                    PickLocation.put("address", strAdd);
                    PickLocation.put("latitude", LATITUDE);
                    PickLocation.put("longitude", LONGITUDE);
                    jsonArray.put(PickLocation);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //    registerUser();
                pickLocaiton = jsonArray.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stateChanged() {
            pickuplocationsigalandmultiple="2";
            CustomProgressbar.showProgressBar(HomeActivity.this,false);
            class GetTasks extends AsyncTask<Void, Void,List<PlaceModel>> {
                @Override
                protected List<PlaceModel> doInBackground(Void... voids) {
                    List<PlaceModel> taskList = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .taskDao()
                            .getAll();
                    return taskList;
                }

                @Override
                protected void onPostExecute(List<PlaceModel> modelState) {
                    super.onPostExecute(modelState);
                    CustomProgressbar.hideProgressBar();
                    googleMap.clear();
                    addStock(modelState);
                    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
                    coordList.add(new LatLng(lat_pick,lng_pick));
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(lat_pick,lng_pick)));
                    for (int i =0; i<modelState.size();i++){
                        coordList.add(new LatLng(modelState.get(i).getLatitude(), modelState.get(i).getLongitude()));
                        LatLng orignal = new LatLng(modelState.get(i).getLatitude(), modelState.get(i).getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(orignal));

                    }
                    try {
                        if (modelState.size() ==0){
                            googleMap.clear();
                            AsyncTaskRunner runner = new AsyncTaskRunner();
                            runner.execute();
                        }else {
                            clickedmoviestart =true;
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(new LatLng(lat_pick,lng_pick))));
                            txt_multiple.setVisibility(View.VISIBLE);
                            btn_add.setVisibility(View.GONE);
                            txt_d_loc.setText(modelState.get(modelState.size() - 1).getDroplocation());
                            LatLng des = new LatLng(modelState.get(modelState.size()-1).getLatitude(),modelState.get(modelState.size()-1).getLongitude());
                            GoogleDirection.withServerKey("AIzaSyANqhj1K94Ckkodt5fWLgE_HYxMgN8Q2hs")
                                    .from(new LatLng(lat_pick,lng_pick))
                                    .and(coordList)
                                    .to(des)
                                    .avoid(AvoidType.FERRIES)
                                    .avoid(AvoidType.HIGHWAYS)
                                    .execute(new DirectionCallback() {
                                        @Override
                                        public void onDirectionSuccess(Direction direction) {
                                            if(direction.isOK()) {
                                                List<LatLng> encodedString = direction.getRouteList().get(0).getOverviewPolyline().getPointList();
                                                distance = String.valueOf(direction.getRouteList().get(0).getTotalDistance()/1000);
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
                       }catch (ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }

                }
            }
            GetTasks gt = new GetTasks();
            gt.execute();
        }


    public void addStock(List<PlaceModel> tasks) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < tasks.size(); i++) {
            JSONObject PickLocation = new JSONObject();
            try {
                PickLocation.put("address", tasks.get(i).getDroplocation());
                PickLocation.put("latitude", tasks.get(i).getLatitude());
                PickLocation.put("longitude", tasks.get(i).getLongitude());
                PickLocation.put("reciver_name",tasks.get(i).getReceivername());
                PickLocation.put("reciver_number",tasks.get(i).getReceivernumber());
                jsonArray.put(PickLocation);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            droplocation = jsonArray.toString();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String strAdd;
        @Override
        protected String doInBackground(String... params) {
            Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat_drop, lng_drop, 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");
                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    JSONArray jsonArray = new JSONArray();
                    JSONObject PickLocation = new JSONObject();
                    try {
                        PickLocation.put("address", strAdd);
                        PickLocation.put("latitude", lat_drop);
                        PickLocation.put("longitude", lng_drop);
                        PickLocation.put("reciver_name","");
                        PickLocation.put("reciver_number","");
                        jsonArray.put(PickLocation);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //    registerUser();
                    droplocation = jsonArray.toString();
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
            GoogleDirection.withServerKey("AIzaSyANqhj1K94Ckkodt5fWLgE_HYxMgN8Q2hs")
                    .from(new LatLng(lat_pick,lng_pick))
                    .to(new LatLng(lat_drop,lng_drop))
                    .avoid(AvoidType.FERRIES)
                    .avoid(AvoidType.HIGHWAYS)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction) {
                            if(direction.isOK()) {
                                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_map_icon)).position(orignal));
                                googleMap.addMarker(new MarkerOptions().position(des));
                                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                                googleMap.addPolyline(DirectionConverter.createPolyline(HomeActivity.this, directionPositionList, 10, Color.RED));
                                distance = String.valueOf(direction.getRouteList().get(0).getTotalDistance()/1000);
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
        return new CameraPosition.Builder().target(latLng).zoom(9).build();
    }
    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(HomeActivity.this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), 13));
                            }
                        } else {
                            googleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, 13));
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
        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this,
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
                    Toast.makeText(HomeActivity.this, "vvass", Toast.LENGTH_SHORT).show();
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
                    CustomProgressbar.showProgressBar(HomeActivity.this, false);
                    clickedmoviestart =true;
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
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(HomeActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void getcurrentdate() {
        String myFormat = "MMM d yyyy HH:mm"; //In which you need put here
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
        TimePickerDialog timepickerdialog = new TimePickerDialog(HomeActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                currentDateMain = datemain+" "+ hourOfDay+ ":"+minute;
                Intent intent1 = new Intent(HomeActivity.this, BookActivity.class);
                intent1.putExtra("pick_location", pickLocaiton);
                intent1.putExtra("distance",distance);
                intent1.putExtra("drop_location", droplocation);
                intent1.putExtra("date", currentDateMain);
                intent1.putExtra("pick_up_letter","1");
                intent1.putExtra("pick_up_location_multiple_and_single",pickuplocationsigalandmultiple);
                intent1.putExtra("vehicletype", vehicletype);
                startActivity(intent1);
               overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        }, CalendarHour, CalendarMinute, true);
        timepickerdialog.show();
    }

    private void Notificaoticlear() {
        apiServices.notificaoticout(user_id).enqueue(new Callback<NotificationSmsCount>() {
            @Override
            public void onResponse(Call<NotificationSmsCount> call, Response<NotificationSmsCount> response) {
                if (response.isSuccessful()) {
                    NotificationSmsCount getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        ivNofificationcoutId.setText(getLoginModle.getData());
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
            public void onFailure(Call<NotificationSmsCount> call, Throwable t) {
                Log.d("test", String.valueOf(t));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskRunner1 extends AsyncTask<String, String, String> {
        private String strAdd;
        @Override
        protected String doInBackground(String... params) {
            Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
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
                    if (strAdd.contains("Unnamed Road,")){
                        strAdd = strReturnedAddress.toString().replace("Unnamed Road,","");
                    }else if (strAdd.contains("Unnamed Road -")){
                        strAdd = strReturnedAddress.toString().replace("Unnamed Road -","");
                    }else if (strAdd.contains("Unnamed Road ")){
                        strAdd = strReturnedAddress.toString().replace("Unnamed Road ","");
                    }
                  //
                    //Toast.makeText(HomeActivity.this, ""+strAdd, Toast.LENGTH_SHORT).show();
                    droplocation = strAdd;
                    //droplocation = jsonArray.toString();
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
            GoogleDirection.withServerKey("AIzaSyANqhj1K94Ckkodt5fWLgE_HYxMgN8Q2hs")
                    .from(new LatLng(lat_pick,lng_pick))
                    .to(new LatLng(lat_drop,lng_drop))
                    .avoid(AvoidType.FERRIES)
                    .avoid(AvoidType.HIGHWAYS)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction) {
                            if(direction.isOK()) {
                                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_map_icon)).position(orignal));
                                googleMap.addMarker(new MarkerOptions().position(des));
                                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                                googleMap.addPolyline(DirectionConverter.createPolyline(HomeActivity.this, directionPositionList, 10, Color.RED));
                                distance = String.valueOf(direction.getRouteList().get(0).getTotalDistance()/1000);
                            }

                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }

    }


}
