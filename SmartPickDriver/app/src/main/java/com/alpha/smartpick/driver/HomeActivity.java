package com.alpha.smartpick.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.editProfilePkg.EditProfileActivity;
import com.alpha.smartpick.driver.initial.SplashActivity;
import com.alpha.smartpick.driver.notificationPkg.NotificationActivity;
import com.alpha.smartpick.driver.notificationPkg.getNotificaitonList.NotificationSmsCount;
import com.alpha.smartpick.driver.ridehistoryPkg.RideHistoryActivity;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.Constants;
import com.alpha.smartpick.driver.utility.PrefData;
import com.alpha.smartpick.driver.utility.YourPreference;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,HomeFragmentMain.AddTitleInterFace {
    AppCompatImageView ivLogoHome,ivnotificaontHomeId;
    DrawerLayout drawer;
    NavigationView navigationView;
    LinearLayout LieditProfileid;
    AppCompatTextView as,ivNofificationcoutId,tvUserNameId;
    View currentView;
    String userId,username,userimage,vehicletype;
    private ApiServices apiServices;
    private AppCompatImageView ivprofileeditId;
    CircleImageView circleImageView;
    private String language;
    Context con;
    PrefData prefData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
        userId = AppSession.getStringPreferences(HomeActivity.this, Constants.USER_ID);
        username = AppSession.getStringPreferences(HomeActivity.this, Constants.USERNAME);
        userimage = AppSession.getStringPreferences(HomeActivity.this, Constants.PROFILE_IMAGE);
        if (username != null){
            tvUserNameId.setText(username);
           // Picasso.with(HomeActivity.this).load(RetrofitClient.USER_PROFILE_URL+userimage).placeholder(R.drawable.progress_animation).into(ivprofileeditId);
        }
        if (userimage.isEmpty()){

        }else {
            Picasso.with(HomeActivity.this).load(RetrofitClient.USER_PROFILE_URL+userimage).placeholder(R.drawable.progress_animation).into(circleImageView);
        }
        Notificaoticlear();
        con = getApplicationContext();
        prefData = new PrefData(con);
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivLogoHome = findViewById(R.id.ivLogoHomeId);
        ivnotificaontHomeId = findViewById(R.id.ivnotificaontHomeId);
        ivLogoHome.setOnClickListener(this);
        ivNofificationcoutId = findViewById(R.id.ivNofificationcoutId);
        ivnotificaontHomeId.setOnClickListener(this);
        ivprofileeditId = findViewById(R.id.ivprofileeditId);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        replaceFragement(new HomeFragmentMain());
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        vehicletype = AppSession.getStringPreferences(HomeActivity.this, Constants.VEHICLE_TYPE);
        LieditProfileid = navigationView.getHeaderView(0).findViewById(R.id.LieditProfileid);
        tvUserNameId = navigationView.getHeaderView(0).findViewById(R.id.tvUserNameId);
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
                    Intent intent = new Intent(HomeActivity.this, RideHistoryActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                }  else if (id == R.id.nav_notification) {
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(true);
                    navigationView.getMenu().getItem(3).setChecked(false);
                    navigationView.getMenu().getItem(4).setChecked(false);
                    navigationView.getMenu().getItem(5).setChecked(false);
                    Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    finish();
                } else if (id == R.id.nav_about_me) {
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    navigationView.getMenu().getItem(3).setChecked(true);
                    navigationView.getMenu().getItem(4).setChecked(false);
                    navigationView.getMenu().getItem(5).setChecked(false);
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
                }else if (id ==R.id.nav_logout){
                    navigationView.getMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(1).setChecked(false);
                    navigationView.getMenu().getItem(2).setChecked(false);
                    navigationView.getMenu().getItem(3).setChecked(false);
                    navigationView.getMenu().getItem(4).setChecked(false);
                    navigationView.getMenu().getItem(5).setChecked(true);
                    YourPreference yourPrefrence = YourPreference.getInstance(HomeActivity.this);
                    yourPrefrence.saveData("status", "");
                    Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    finish();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
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
                Intent intent1 = new Intent(HomeActivity.this, EditProfileActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                finish();
                break;
        }
    }
    public void addFragment(Fragment fragment, boolean addToBackStack,
                            String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(null);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.flHomeId, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.exit))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        } else {
                            System.exit(0);
                            finish();
                        }
                        HomeActivity.super.onBackPressed();
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void replaceFragement(HomeFragmentMain homeFragment) {
        FragmentTransaction home = getSupportFragmentManager().beginTransaction();
        home.replace(R.id.flHomeId, homeFragment);
        home.commit();

    }


    private void Notificaoticlear() {
        apiServices.notificaoticout(userId,vehicletype).enqueue(new Callback<NotificationSmsCount>() {
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

    @Override
    public void passData(String data) {
        ivNofificationcoutId.setText(data);

    }
}
