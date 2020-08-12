package com.alpha.smartpick.driver.editProfilePkg;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.HomeActivity;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.editProfilePkg.getuserPkgmodel.UserProfileModle;
import com.alpha.smartpick.driver.editProfilePkg.userPkgmodel.UpDatedUserProfileModle;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.CheckNetwork;
import com.alpha.smartpick.driver.utility.Constants;
import com.alpha.smartpick.driver.utility.CustomProgressbar;
import com.alpha.smartpick.driver.utility.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {
    RelativeLayout rlsaveandNext;
    private CircleImageView civProfileUserId;
    AppCompatImageView ivbackId;
    AppCompatTextView galaryPickImageId,tiewdobId;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 100;
    private static final int SELECT_PICTURE = 101;
    private ApiServices apiServices;
    private String userId, profilImgPath, doctStatus, doctorImage;
    private File fileForImage;
    private String[] Gender;
    final Calendar myCalendar = Calendar.getInstance();
    private AppCompatSpinner spgenderId;
    private static Animation shakeAnimation;
    private View vigenderId,viewDobId;
    private TextInputEditText textUserNameId,tiewaddressId,tfmobileNoId,tiewEmailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        setContentView(R.layout.activity_edit_profile);
        Gender  = new String[]{getResources().getString(R.string.gender), "male", "female"};
        init();
        genderspiner();
    }

    private void init() {
        rlsaveandNext = findViewById(R.id.rlsaveandNextId);
        ivbackId = findViewById(R.id.ivbackId);
        galaryPickImageId = findViewById(R.id.galaryPickImageId);
        civProfileUserId = findViewById(R.id.civProfileImgEditdocId);
        textUserNameId = findViewById(R.id.textUserNameId);
        tiewaddressId = findViewById(R.id.tiewaddressId);
        tfmobileNoId  = findViewById(R.id.tfmobileNoId);
        tiewEmailId = findViewById(R.id.tiewEmailId);
        spgenderId = findViewById(R.id.spgenderId);
        tiewdobId = findViewById(R.id.tiewdobId);
        vigenderId = findViewById(R.id.vigenderId);
        viewDobId = findViewById(R.id.viewDobId);
        rlsaveandNext.setOnClickListener(this);
        ivbackId.setOnClickListener(this);
        tiewdobId.setOnClickListener(this);
        galaryPickImageId.setOnClickListener(this);
        shakeAnimation = AnimationUtils.loadAnimation(EditProfileActivity.this,
                R.anim.shake);
        userId = AppSession.getStringPreferences(EditProfileActivity.this, Constants.USER_ID);
        User_profile(userId);
    }

    private void genderspiner() {
        spgenderId.setOnItemSelectedListener(EditProfileActivity.this);
        ArrayAdapter aa = new ArrayAdapter(EditProfileActivity.this, R.layout.spinner_item, Gender);
        aa.setDropDownViewResource(R.layout.spinner_item);
        spgenderId.setAdapter(aa);
        spgenderId.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (spgenderId.getSelectedItem().toString().equals(getResources().getString(R.string.gender))) {
                    ((TextView) spgenderId.getSelectedView()).setTextColor(getResources().getColor(R.color.textblack));
                } else {
                    ((TextView) spgenderId.getSelectedView()).setTextColor(Color.BLACK);
                }
            }
        });


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlsaveandNextId:
                validation(v);
                break;
            case R.id.ivbackId:
                onBackPressed();
                break;
            case R.id.galaryPickImageId:
                askStoragePermission();
                break;
            case R.id.tiewdobId:
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
        }
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
        String myFormat = "dd-MM-yyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tiewdobId.setText(sdf.format(myCalendar.getTime()));
    }
    private void validation(View v) {
        if (textUserNameId.getText().toString().isEmpty()) {
            textUserNameId.requestFocus();
            textUserNameId.setError(getResources().getString(R.string.required));
            textUserNameId.startAnimation(shakeAnimation);
            textUserNameId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }else if (!textUserNameId.getText().toString().matches("^[a-zA-Z\\s]+")) {
            textUserNameId.requestFocus();
            textUserNameId.setError(getResources().getString(R.string.invalid_user_name));
            textUserNameId.startAnimation(shakeAnimation);
            textUserNameId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewaddressId.getText().toString().isEmpty()) {
            tiewaddressId.requestFocus();
            tiewaddressId.setError(getResources().getString(R.string.required));
            tiewaddressId.startAnimation(shakeAnimation);
            tiewaddressId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }else if (tfmobileNoId.getText().toString().isEmpty()) {
            tfmobileNoId.requestFocus();
            tfmobileNoId.setError(getResources().getString(R.string.required));
            tfmobileNoId.startAnimation(shakeAnimation);
            tfmobileNoId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }else if (tfmobileNoId.getText().toString().length() < 10) {
            tfmobileNoId.requestFocus();
            tfmobileNoId.setError(getResources().getString(R.string.invalid_mobile_number));
            tfmobileNoId.startAnimation(shakeAnimation);
            tfmobileNoId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (tiewEmailId.getText().toString().trim().isEmpty()) {
            tiewEmailId.requestFocus();
            tiewEmailId.setError(getResources().getString(R.string.required));
            tiewEmailId.startAnimation(shakeAnimation);
            tiewEmailId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (!Constants.emailValidator(tiewEmailId.getText().toString().trim())) {
            tiewEmailId.requestFocus();
            tiewEmailId.setError(getResources().getString(R.string.invalid_email));
            tiewEmailId.startAnimation(shakeAnimation);
            tiewEmailId.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else if (spgenderId.getSelectedItem().toString().trim() == getResources().getString(R.string.gender)) {
            Toast.makeText(this, getResources().getString(R.string.select_gender), Toast.LENGTH_SHORT).show();
            vigenderId.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            vigenderId.startAnimation(shakeAnimation);
        }else if (TextUtils.isEmpty(tiewdobId.getText().toString())) {
            tiewdobId.requestFocus();
            Toast.makeText(this, getResources().getString(R.string.dob_required), Toast.LENGTH_SHORT).show();
            viewDobId.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            viewDobId.startAnimation(shakeAnimation);
        } else if (CheckNetwork.isNetAvailable(this)) {
            updateUser(textUserNameId.getText().toString().trim(),tiewaddressId.getText().toString().trim(), tfmobileNoId.getText().toString().trim() ,tiewEmailId.getText().toString(),
                    spgenderId.getSelectedItem().toString(), tiewdobId.getText().toString().trim());
        } else {
            Toast.makeText(this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(EditProfileActivity.this, HomeActivity.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }


    private void updateUser(final String textUserNameId, final String tiewaddressId, final String tfmobileNoId, final String tiewEmailId, final String Gender,final String doba) {
        CustomProgressbar.showProgressBar(EditProfileActivity.this, false);
        MultipartBody.Part bnnerimgFileStation = null;
        MultipartBody.Part imgFileStation = null;
        if (profilImgPath == null) {
        } else {

            fileForImage = new File(profilImgPath);
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), fileForImage);
            imgFileStation = MultipartBody.Part.createFormData("attachment_image", fileForImage.getName(), requestFileOne);
        }
        MultipartBody.Part user_type = MultipartBody.Part.createFormData("user_type", "2");
        MultipartBody.Part profile_id = MultipartBody.Part.createFormData("profile_id", userId);
        MultipartBody.Part username = MultipartBody.Part.createFormData("username", textUserNameId);
        MultipartBody.Part address = MultipartBody.Part.createFormData("address", String.valueOf(tiewaddressId));
        MultipartBody.Part gender = MultipartBody.Part.createFormData("gender", Gender);
        MultipartBody.Part phone_number = MultipartBody.Part.createFormData("phone_number", String.valueOf(tfmobileNoId));
        MultipartBody.Part email = MultipartBody.Part.createFormData("email", String.valueOf(tiewEmailId));
        MultipartBody.Part dob = MultipartBody.Part.createFormData("dob", String.valueOf(doba));
        if (profilImgPath == null) {
            apiServices.updateWithoutMyProfileuser(user_type,profile_id,username, address,gender,phone_number,email, dob)
                    .enqueue(new Callback<UpDatedUserProfileModle>() {
                        @Override
                        public void onResponse(Call<UpDatedUserProfileModle> call, Response<UpDatedUserProfileModle> response) {
                            if (response.isSuccessful()) {
                                CustomProgressbar.hideProgressBar();
                                UpDatedUserProfileModle upDatedDoctorProfileModle = response.body();
                                if (upDatedDoctorProfileModle.getStatus()) {
                                    Toast.makeText(EditProfileActivity.this, "Updated", Toast.LENGTH_LONG).show();
                                    AppSession.setStringPreferences(EditProfileActivity.this, Constants.USERNAME, upDatedDoctorProfileModle.getAllActivities().getUsername());
                                    AppSession.setStringPreferences(EditProfileActivity.this, Constants.PROFILE_IMAGE, upDatedDoctorProfileModle.getAllActivities().getImage());

                                } else {
                                    Toast.makeText(EditProfileActivity.this, ""+upDatedDoctorProfileModle.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<UpDatedUserProfileModle> call, Throwable t) {
                            CustomProgressbar.hideProgressBar();
                        }
                    });
        } else {
            apiServices.updateMyProfilepatient(user_type,profile_id,username, address,gender,phone_number,email, dob,imgFileStation)
                    .enqueue(new Callback<UpDatedUserProfileModle>() {
                        @Override
                        public void onResponse(Call<UpDatedUserProfileModle> call, Response<UpDatedUserProfileModle> response) {
                            if (response.isSuccessful()) {
                                CustomProgressbar.hideProgressBar();
                                UpDatedUserProfileModle upDatedDoctorProfileModle = response.body();
                                if (upDatedDoctorProfileModle.getStatus()) {
                                    Toast.makeText(EditProfileActivity.this, "Updated", Toast.LENGTH_LONG).show();
                                    AppSession.setStringPreferences(EditProfileActivity.this, Constants.USERNAME, upDatedDoctorProfileModle.getAllActivities().getUsername());
                                    AppSession.setStringPreferences(EditProfileActivity.this, Constants.PROFILE_IMAGE, upDatedDoctorProfileModle.getAllActivities().getImage());

                                    // AppSession.setStringPreferences(EditProfilePatientActivity.this, Constants.DOCTOR_FULL_NAME, tiewnamedocStr);
                                       } else {
                                    Toast.makeText(EditProfileActivity.this, ""+upDatedDoctorProfileModle.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<UpDatedUserProfileModle> call, Throwable t) {
                            CustomProgressbar.hideProgressBar();
                        }
                    });
        }
    }







    private void askStoragePermission() {
        if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(EditProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_READ_EXTERNAL_STORAGE);
            }
        } else {
            chooseFromGallery();
        }
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseFromGallery();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = ImagePicker.getImageFromResult(EditProfileActivity.this, resultCode, data);
                    civProfileUserId.setImageBitmap(bitmap);
                    profilImgPath = getRealPathFromURI(EditProfileActivity.this, imageUri);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void User_profile(String userId) {
        CustomProgressbar.showProgressBar(EditProfileActivity.this, false);
        apiServices.Userprofile("2",userId).enqueue(new Callback<UserProfileModle>() {
            @Override
            public void onResponse(Call<UserProfileModle> call, Response<UserProfileModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    UserProfileModle getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        try {
                            tiewEmailId.setText(getLoginModle.getAllActivities().get(0).getEmail());
                            tfmobileNoId.setText(getLoginModle.getAllActivities().get(0).getPhoneNumber());
                            textUserNameId.setText(getLoginModle.getAllActivities().get(0).getUsername());

                            tiewaddressId.setText(getLoginModle.getAllActivities().get(0).getAddress());
                            if (getLoginModle.getAllActivities().get(0).getImage().isEmpty()) {
                            } else {
                               Picasso.with(EditProfileActivity.this)
                                       .load(RetrofitClient.USER_PROFILE_URL+getLoginModle.getAllActivities().get(0).getImage()).placeholder(R.drawable.progress_animation).into(civProfileUserId);
                            }
                            AppSession.setStringPreferences(EditProfileActivity.this, Constants.USERNAME, getLoginModle.getAllActivities().get(0).getUsername());
                            if (getLoginModle.getAllActivities().get(0).getGender().equalsIgnoreCase("male")) {
                                spgenderId.setSelection(1);
                            } else if (getLoginModle.getAllActivities().get(0).getGender().equalsIgnoreCase("female")){
                                spgenderId.setSelection(2);
                            }else {
                                spgenderId.setSelection(0);
                            }
                            if (getLoginModle.getAllActivities().get(0).getDob().isEmpty()){
                                tiewdobId.setText(getResources().getString(R.string.dob));
                            }else {
                                tiewdobId.setText(getLoginModle.getAllActivities().get(0).getDob());
                            }

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                CustomProgressbar.hideProgressBar();
                                String message = jsonObject.getString("message");
                                //Toast.makeText(EditProfileActivity.this, "" + message, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<UserProfileModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    }
