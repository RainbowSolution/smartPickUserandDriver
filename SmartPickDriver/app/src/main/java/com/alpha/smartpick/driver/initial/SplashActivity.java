package com.alpha.smartpick.driver.initial;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.alpha.smartpick.driver.HomeActivity;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.utility.PrefData;
import com.alpha.smartpick.driver.utility.YourPreference;
import java.util.Locale;
public class SplashActivity extends AppCompatActivity {
    LocationManager locationManager;
    String as;
    Context con;
    PrefData prefData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        con = getApplicationContext();
        prefData = new PrefData(con);
        Locale myLocale = new Locale(prefData.getCurrentLanguage());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        prefData.setCurrentLanguage(prefData.getCurrentLanguage());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            book_diloge1();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    YourPreference yourPrefrence = YourPreference.getInstance(SplashActivity.this);
                    String ss = yourPrefrence.getData("status");
                    switch (ss) {
                        case "":
                            Intent start = new Intent(SplashActivity.this, SignInActivity.class);
                            startActivity(start);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                            finish();
                            break;
                        case "Usersign":
                            Intent i1 = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(i1);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            finish();
                            break;
                    }


                }
            }, 1000);
        }

    }
    public void book_diloge1() {
        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        dialog.setContentView(R.layout.dilog_location);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        RelativeLayout rlSigninId = dialog.findViewById(R.id.rlSigninId);
        rlSigninId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent1,2);
                dialog.dismiss();

            }
        });
        dialog.show();

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {

            if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
                book_diloge1();
            }else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        YourPreference yourPrefrence = YourPreference.getInstance(SplashActivity.this);
                        String ss = yourPrefrence.getData("status");
                        switch (ss) {
                            case "":
                                Intent start = new Intent(SplashActivity.this, SignInActivity.class);
                                startActivity(start);
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                                finish();
                                break;
                            case "Usersign":
                                Intent i1 = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(i1);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                finish();
                                break;
                        }


                    }
                }, 1000);
            }

        }

        }
    }


