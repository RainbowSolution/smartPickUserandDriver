package com.alpha.smartpickuser.initial;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.alpha.smartpickuser.ApiPkg.ApiServices;
import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.initial.generateotpPkg.GenerateOtpModle;
import com.alpha.smartpickuser.initial.signupPkg.SignUpModle;
import com.alpha.smartpickuser.utility.CheckNetwork;
import com.alpha.smartpickuser.utility.CustomProgressbar;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText etusernameId, etusermobileId;
    RelativeLayout rlSignupId;
    AppCompatTextView tvSigninId;
    private static Animation shakeAnimation;
    private ApiServices apiServices;
    private String token;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init();

    }
    private void init() {
        rlSignupId = findViewById(R.id.rlSignupId);
        rlSignupId.setOnClickListener(this);
        tvSigninId = findViewById(R.id.tvSigninId);
        etusernameId = findViewById(R.id.etusernameId);
        etusermobileId = findViewById(R.id.etusermobileId);
        tvSigninId.setOnClickListener(this);
        shakeAnimation = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);
        FirebaseApp.initializeApp(SignUpActivity.this);
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSignupId:
                if (CheckNetwork.isNetAvailable(getApplicationContext())) {
                    validation(v);
                } else {
                    Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tvSigninId:
                onBackPressed();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }

    private void validation(View v) {
        if (TextUtils.isEmpty(etusernameId.getText().toString().trim())) {
            etusernameId.requestFocus();
            etusernameId.setError(getResources().getString(R.string.required));
            etusernameId.startAnimation(shakeAnimation);
            etusernameId.getBackground().mutate().setColorFilter(ContextCompat.getColor(SignUpActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!etusernameId.getText().toString().matches("^[a-zA-Z\\s]+")) {
            etusernameId.requestFocus();
            etusernameId.setError(getResources().getString(R.string.invalid_user_name));
            etusernameId.startAnimation(shakeAnimation);
            etusernameId.getBackground().mutate().setColorFilter(ContextCompat.getColor(SignUpActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (etusermobileId.getText().toString().isEmpty()) {
            etusermobileId.requestFocus();
            etusermobileId.setError(getResources().getString(R.string.required));
            etusermobileId.startAnimation(shakeAnimation);
            etusermobileId.getBackground().mutate().setColorFilter(ContextCompat.getColor(SignUpActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (etusermobileId.getText().toString().length() < 10) {
            etusermobileId.requestFocus();
            etusermobileId.setError(getResources().getString(R.string.invalid_mobile_number));
            etusermobileId.startAnimation(shakeAnimation);
            etusermobileId.getBackground().mutate().setColorFilter(ContextCompat.getColor(SignUpActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (CheckNetwork.isNetAvailable(SignUpActivity.this)) {
            signup(etusernameId.getText().toString().trim(),etusermobileId.getText().toString().trim());
        } else {
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_LONG).show();

        }
    }


    private void signup(String userName, final String mobileNumber) {
        CustomProgressbar.showProgressBar(SignUpActivity.this, false);
        apiServices.SignUp(userName,mobileNumber,token).enqueue(new Callback<SignUpModle>() {
            @Override
            public void onResponse(Call<SignUpModle> call, Response<SignUpModle> response) {
                if (response.isSuccessful()) {
                    SignUpModle getRegistrationModle = response.body();
                    if (getRegistrationModle.getStatus() == true) {
                        generate_otp(mobileNumber);
                    }else {
                        CustomProgressbar.hideProgressBar();
                        Toast.makeText(SignUpActivity.this, ""+getRegistrationModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (response.code() == 400) {
                    if (!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            CustomProgressbar.hideProgressBar();
                            String message = jsonObject.getString("message");
                            Toast.makeText(SignUpActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }

        });
    }


    private void generate_otp(final String number) {
        apiServices.otp_generate(number,"1").enqueue(new Callback<GenerateOtpModle>() {
            @Override
            public void onResponse(Call<GenerateOtpModle> call, Response<GenerateOtpModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GenerateOtpModle loginModle = response.body();
                    if (loginModle.getStatus()== true) {
                        Intent intent = new Intent(SignUpActivity.this, VerifyOtpActivity.class);
                        intent.putExtra("number", loginModle.getPhoneNumber());
                        intent.putExtra("otp", ""+loginModle.getOtp());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    } else {
                        Toast.makeText(SignUpActivity.this, ""+loginModle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                CustomProgressbar.hideProgressBar();
                                jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                Toast.makeText(SignUpActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
