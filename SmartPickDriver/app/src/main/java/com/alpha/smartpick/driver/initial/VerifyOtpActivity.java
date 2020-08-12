package com.alpha.smartpick.driver.initial;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.HomeActivity;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpick.driver.initial.otpverficationPkg.OtpModle;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.Constants;
import com.alpha.smartpick.driver.utility.CustomProgressbar;
import com.alpha.smartpick.driver.utility.NetworkUtility;
import com.alpha.smartpick.driver.utility.YourPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout rlverifySigninId;
    AppCompatTextView tveditnumberId;
    private LinearLayout liresendotpMainId;
    private TextInputEditText etOtpUpId;
    private AppCompatTextView tvEnterOtp,tvResendTimerOtpId;
    private String otp, number, type_id;
    private static Animation shakeAnimation;
    private ApiServices apiServices;
    private String token;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veryfication_code);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Intent intent = getIntent();
        otp = intent.getStringExtra("otp");
        number = intent.getStringExtra("number");
        FirebaseApp.initializeApp(VerifyOtpActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        token = task.getResult().getToken();
                        Log.d("token",token);

                    }
                });

        init();
        otp_set();
    }

    private void otp_set (){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                etOtpUpId.setText(otp);
                tvEnterOtp.setText(getResources().getString(R.string.CONTINUE));
                if (NetworkUtility.isNetAvailable(VerifyOtpActivity.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (NetworkUtility.isNetAvailable(VerifyOtpActivity.this)) {
                                if (TextUtils.isEmpty(etOtpUpId.getText())) {
                                } else if (etOtpUpId.getText().length() == 6) {
                                    // oTpVerification(number);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 3000);
                    //  oTpVerification(otp, number);
                } else {
                    Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        }, 4000);
    }
    private void init() {
        tveditnumberId=findViewById(R.id.tveditnumberId);
        tveditnumberId.setOnClickListener(this);
        rlverifySigninId=findViewById(R.id.rlVerifyOtpVerityId);
        liresendotpMainId = findViewById(R.id.liresendotpMainId);
        liresendotpMainId.setOnClickListener(this);
        rlverifySigninId.setOnClickListener(this);
        etOtpUpId = findViewById(R.id.etOtpUpId);
        tvEnterOtp = findViewById(R.id.tvEnterOtpId);
        tvResendTimerOtpId = findViewById(R.id.tvResendTimerOtpId);
        shakeAnimation = AnimationUtils.loadAnimation(this,
                R.anim.shake);
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                long Mmin = (millisUntilFinished / 1000) / 60;
                long Ssec = (millisUntilFinished / 1000) % 60;
                if (Ssec < 10) {
                    tvResendTimerOtpId.setText("0" + Mmin + ":0" + Ssec);
                } else tvResendTimerOtpId.setText("0" + Mmin + ":" + Ssec);
                //tvResendTimerOtp.setText("00.0" + millisUntilFinished / 1000);
            }
            public void onFinish() {
                tvResendTimerOtpId.setText("00:00");
            }
        }.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tveditnumberId:
                Intent intent= new Intent(VerifyOtpActivity.this, SignInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                finishAffinity();
                break;
            case R.id.rlVerifyOtpVerityId:
                if (tvEnterOtp.getText().equals(getResources().getString(R.string.CONTINUE))) {
                    if (etOtpUpId.getText().toString().isEmpty()) {
                        etOtpUpId.requestFocus();
                        etOtpUpId.setError("Required");
                        etOtpUpId.startAnimation(shakeAnimation);
                        etOtpUpId.getBackground().mutate().setColorFilter(ContextCompat.getColor(VerifyOtpActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    } else if (etOtpUpId.getText().toString().length() < 4) {
                        etOtpUpId.requestFocus();
                        etOtpUpId.setError( "Wrong Otp");
                        etOtpUpId.startAnimation(shakeAnimation);
                        etOtpUpId.getBackground().mutate().setColorFilter(ContextCompat.getColor(VerifyOtpActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    } else {
                        if (NetworkUtility.isNetAvailable(VerifyOtpActivity.this)) {
                            oTpVerification();
                        } else {
                            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;
            case R.id.liresendotpMainId:
                if (tvResendTimerOtpId.getText().toString().equalsIgnoreCase("00:00")){
                    generate_otp_resend(number);
                }else {
                    Toast.makeText(this, "Please Wait....", Toast.LENGTH_SHORT).show();
                }
                //generate_otp_resend(number);
                break;
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }

    private void oTpVerification() {
        CustomProgressbar.showProgressBar(VerifyOtpActivity.this, false);
        apiServices.verification_otp(otp,number,"2",token).enqueue(new Callback<OtpModle>() {
            @Override
            public void onResponse(Call<OtpModle> call, Response<OtpModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    OtpModle getRegistrationModle = response.body();
                    if (getRegistrationModle.getStatus() == true) {
                        AppSession.setStringPreferences(getApplicationContext(), Constants.USER_ID, getRegistrationModle.getData().getId());
                        AppSession.setStringPreferences(getApplicationContext(), Constants.USERNAME, getRegistrationModle.getData().getUsername());
                        AppSession.setStringPreferences(getApplicationContext(), Constants.PHONE_NUMBER, getRegistrationModle.getData().getPhoneNumber());
                        AppSession.setStringPreferences(getApplicationContext(), Constants.VEHICLE_TYPE, getRegistrationModle.getData().getVehicleType());
                        AppSession.setStringPreferences(getApplicationContext(), Constants.PROFILE_IMAGE, getRegistrationModle.getData().getImage());
                        AppSession.setStringPreferences(getApplicationContext(), Constants.DATA, "online");
                        YourPreference yourPrefrence = YourPreference.getInstance(VerifyOtpActivity.this);
                        yourPrefrence.saveData("status","Usersign");
                        Intent intent= new Intent(VerifyOtpActivity.this,HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        finishAffinity();
                    }else {
                        Toast.makeText(VerifyOtpActivity.this, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 400) {
                    if (!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            CustomProgressbar.hideProgressBar();
                            String message = jsonObject.getString("message");
                            Toast.makeText(VerifyOtpActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OtpModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }

        });
    }


    private void generate_otp_resend(final String number) {
        CustomProgressbar.showProgressBar(VerifyOtpActivity.this, false);
        apiServices.otp_generate(number,"2").enqueue(new Callback<GenerateOtpModle>() {
            @Override
            public void onResponse(Call<GenerateOtpModle> call, Response<GenerateOtpModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GenerateOtpModle loginModle = response.body();
                    Intent intent = new Intent(VerifyOtpActivity.this, VerifyOtpActivity.class);
                    intent.putExtra("number", loginModle.getPhoneNumber());
                    intent.putExtra("otp", ""+loginModle.getOtp());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    finish();
                }else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                CustomProgressbar.hideProgressBar();
                                jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                Toast.makeText(VerifyOtpActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
