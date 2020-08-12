package com.alpha.smartpick.driver.initial;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpick.driver.utility.CheckNetwork;
import com.alpha.smartpick.driver.utility.CustomProgressbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout rlSigninId;
    private AppCompatTextView TvSignupId;
    private ApiServices apiServices;
    private TextInputEditText etmobileNoId;
    private static Animation shakeAnimation;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();
        getLocationPermission();
    }
    private void init() {
        rlSigninId = findViewById(R.id.rlSigninId);
        rlSigninId.setOnClickListener(this);
        TvSignupId = findViewById(R.id.TvSignupId);
        etmobileNoId = findViewById(R.id.etmobileNoId);
        TvSignupId.setOnClickListener(this);
        shakeAnimation = AnimationUtils.loadAnimation(SignInActivity.this, R.anim.shake);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSigninId:
                validation(v);
                break;
            case R.id.TvSignupId:
                Intent intent1 = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }


    }
    private void validation(View v) {
        if (etmobileNoId.getText().toString().isEmpty()) {
            etmobileNoId.requestFocus();
            etmobileNoId.setError(getResources().getString(R.string.required));
            etmobileNoId.startAnimation(shakeAnimation);
            etmobileNoId.getBackground().mutate().setColorFilter(ContextCompat.getColor(SignInActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (etmobileNoId.getText().toString().length() < 10) {
            etmobileNoId.requestFocus();
            etmobileNoId.setError(getResources().getString(R.string.invalid_mobile_number));
            etmobileNoId.startAnimation(shakeAnimation);
            etmobileNoId.getBackground().mutate().setColorFilter(ContextCompat.getColor(SignInActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (CheckNetwork.isNetAvailable(SignInActivity.this)) {
            generate_otp(etmobileNoId.getText().toString().trim());
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void generate_otp(final String number) {
        CustomProgressbar.showProgressBar(SignInActivity.this, false);
        apiServices.otp_generate(number,"2").enqueue(new Callback<GenerateOtpModle>() {
            @Override
            public void onResponse(Call<GenerateOtpModle> call, Response<GenerateOtpModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GenerateOtpModle loginModle = response.body();
                    if (loginModle.getStatus()== true) {
                        Intent intent = new Intent(SignInActivity.this, VerifyOtpActivity.class);
                        intent.putExtra("number", loginModle.getPhoneNumber());
                        intent.putExtra("otp", ""+loginModle.getOtp());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    } else {
                        Toast.makeText(SignInActivity.this, ""+loginModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                CustomProgressbar.hideProgressBar();
                                jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                Toast.makeText(SignInActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(SignInActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(SignInActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                else {
                }
            }
        }

    }

}

