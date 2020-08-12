package com.alpha.smartpickuser.droplocationAddFragmentPkg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.droplocationAddFragmentPkg.Roomdatabase.DatabaseClient;
import com.alpha.smartpickuser.utility.CustomModel;
import com.alpha.smartpickuser.utility.CustomProgressbar;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddDropActivity extends AppCompatActivity implements View.OnClickListener, ReceiverConnectDetailsDropFragment.ItemClickListenerReciverDetails {
    private AppCompatTextView txt_start_loc,txt_drop_loc,btn_route;
    private String pickLocaition,droplcoation,droplcoation1,condition;
    private AppCompatImageView iv_add_location;
    private static final String TAG = "";
    private static final int AUTOCOMPLETE_REQUEST_CODE = 101;
    private RecyclerView rvlistitem;
    private RvAdapter rvAdapter;
    List<PlaceModel> models;
    int position;
    String recivername,recivercontactnumber,productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_drops);
        getTasks();
        Intent intent = getIntent();
        pickLocaition = intent.getStringExtra("pick_locaiton");
        txt_start_loc = findViewById(R.id.txt_start_loc);
        iv_add_location = findViewById(R.id.iv_add_location);
        rvlistitem = findViewById(R.id.rv_list_item);
        btn_route = findViewById(R.id.btn_route);
        iv_add_location.setOnClickListener(this);
        txt_start_loc.setText(pickLocaition);
        rvlistitem.setHasFixedSize(true);
        btn_route.setOnClickListener(this);
        models = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddDropActivity.this, LinearLayoutManager.VERTICAL, false);
        rvlistitem.setLayoutManager(layoutManager);
        rvAdapter = new RvAdapter(getApplicationContext(), models,
                new RvAdapter.Onclick() {
                    @Override
                    public void onEvent(View view,PlaceModel model, int pos) {
                        position = pos;
                        productId = model.getProduct_id();
                        ReciverContectDetails(view);
                    }
                });
        rvlistitem.setAdapter(rvAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_location:
                onSearchCalled();
                break;
            case R.id.btn_route:
                CustomModel.getInstance().changeState(models);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
                break;
        }
    }
    public void ReciverContectDetails(View view) {
        ReceiverConnectDetailsDropFragment receiverConnectDetailsFragment =
                ReceiverConnectDetailsDropFragment.newInstance();
        receiverConnectDetailsFragment.show(getSupportFragmentManager(),
                ReceiverConnectDetailsDropFragment.TAG);
    }
    public void onSearchCalled() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields) //NIGERIA
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                String address = place.getAddress();
                LatLng latLng = place.getLatLng();
                if (models.size() < 4) {
                    insertMethod(place.getId(),place.getAddress(), "", "", latLng.latitude, latLng.longitude);
                } else {
                    Toast.makeText(this, "only  add 5 location", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(AddDropActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {


            }
        }
    }

    private void insertMethod(String placeid,String droplocation, String receivername, String receivernumber, Double latitude, Double longitude) {
      /*  Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("droplocation", droplocation);
            jsonObject.put("receivername", receivername);
            jsonObject.put("receivernumber", receivernumber);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            Task model = gson.fromJson(String.valueOf(jsonObject), Task.class);

            getTasks();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                PlaceModel task = new PlaceModel();
                task.setProduct_id(placeid);
                task.setDroplocation(droplocation);
                task.setLatitude(latitude);
                task.setLongitude(longitude);
                task.setReceivername(receivername);
                task.setReceivernumber(receivernumber);
                task.setIs_booking_verify("0");
                task.setFinished(false);
                DatabaseClient.getInstance(AddDropActivity.this).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getTasks();
            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }






    @Override
    public void onItemClickname(String name) {
        recivername = name;

    }
    @Override
    public void onItemclicknumber(String number) {
        recivercontactnumber = number;
        updateTask();
    }

    private void updateTask() {
        CustomProgressbar.showProgressBar(AddDropActivity.this,false);
        class UpdateTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(AddDropActivity.this).getAppDatabase()
                        .taskDao()
                        .updatea(recivername , recivercontactnumber, productId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                CustomProgressbar.hideProgressBar();
                getTasks();
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }
    private void getTasks() {
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
            protected void onPostExecute(List<PlaceModel> tasks) {
                super.onPostExecute(tasks);
                models = tasks;
                rvAdapter.allproductlist(tasks);
                rvAdapter.notifyDataSetChanged();
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }


}
