package com.alpha.smartpick.driver.homeFragmentPkg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpick.driver.ApiPkg.ApiServices;
import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.homeFragmentPkg.adapterPkg.DriverRideAdapter;
import com.alpha.smartpick.driver.homeFragmentPkg.getbookingPkg.AvailableStatusModel;
import com.alpha.smartpick.driver.homeFragmentPkg.getbookingPkg.Datum;
import com.alpha.smartpick.driver.homeFragmentPkg.getbookingPkg.GetBookingModle;
import com.alpha.smartpick.driver.passengerPkg.PassengerDetailsActivity;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.Constants;
import com.alpha.smartpick.driver.utility.CustomProgressbar;
import com.alpha.smartpick.driver.utility.RecyclerItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements DriverRideAdapter.RideHistoryClickListener{
    private RecyclerView rvrideId;
    private DriverRideAdapter driverRideAdapter;
    private ApiServices apiServices;
    private List<Datum> getBookingModlesList;
    private RelativeLayout renotificaionId,rlrefresId;
    private String vehicletype,userid;
    private String status,avability;
    private SwitchCompat switch3;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        init(view);
        ridehistory();
        return view;
    }
    private void init(View view) {
        rvrideId= view.findViewById(R.id.rvrideId);
        renotificaionId = view.findViewById(R.id.renotificaionId);
        switch3 = view.findViewById(R.id.switch3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvrideId.setLayoutManager(layoutManager);
        driverRideAdapter = new DriverRideAdapter(getActivity(),this);
        rlrefresId = view.findViewById(R.id.rlrefresId);
        vehicletype = AppSession.getStringPreferences(getActivity(), Constants.VEHICLE_TYPE);
        userid = AppSession.getStringPreferences(getActivity(), Constants.USER_ID);
        avability = AppSession.getStringPreferences(getActivity(), Constants.DATA);
        rvrideId.setAdapter(driverRideAdapter);
        rvrideId.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvrideId, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Datum pu = getBookingModlesList.get(position);
                Intent intent = new Intent(getActivity(), PassengerDetailsActivity.class);
                intent.putExtra("invoice_id",pu.getId());
                intent.putExtra("date",pu.getPickDateTime());
                intent.putExtra("user_name",pu.getUsername());
                intent.putExtra("user_profile",pu.getImage());
                intent.putExtra("amount",pu.getAmount());
                intent.putExtra("user_id",pu.getDriverId());
                intent.putExtra("distance",pu.getDistance());
                intent.putExtra("pick_location",pu.getPickLocation());
                intent.putExtra("drop_location",pu.getDropLocation());
                intent.putExtra("type",pu.getVehicleType());
                intent.putExtra("goodstype",pu.getStufName());
                intent.putExtra("statusa",pu.getIsStatus());
                intent.putExtra("user_number",pu.getPhoneNumber());
                intent.putExtra("reciver_contact",pu.getReceiverName());
                intent.putExtra("reciver_number",pu.getReceiverMobilenumber());
                intent.putExtra("status",pu.getIsStatus());
                intent.putExtra("description",pu.getDescription());
                intent.putExtra("packaging",pu.getPackagingRequired());
                intent.putExtra("lift_facility",pu.getLiftFacility());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        rlrefresId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getBookingModlesList.clear();
                    ridehistory();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                   status = "online";
                    driverAvailableStatus();
                }
                else
                {
                    status = "offline";
                    driverAvailableStatus();
                    //triggered due to programmatic assignment using 'setChecked()' method.
                }
            }
        });

        if (avability == null){
            switch3.setChecked(true);
        }else if (avability.equals("online")){
            switch3.setChecked(true);
        }else if (avability.equals("offline")){
            switch3.setChecked(false);
        }


    }


    @Override
    public void onClick(View view, int position) {
        switch (view.getId()){
            case R.id.rlSigninId:
                Intent intent = new Intent(getActivity(), PassengerDetailsActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                break;
        }
    }

    private void ridehistory() {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.ridehistory(vehicletype,userid).enqueue(new Callback<GetBookingModle>() {
            @Override
            public void onResponse(Call<GetBookingModle> call, Response<GetBookingModle> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    GetBookingModle getBookingModle = response.body();
                    if (getBookingModle.getStatus().equals(true)) {
                        getBookingModlesList = getBookingModle.getData();
                        driverRideAdapter.ridehistoryList(getBookingModlesList);
                    }else {
                        renotificaionId.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<GetBookingModle> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    private void driverAvailableStatus() {
        CustomProgressbar.showProgressBar(getActivity(), false);
        apiServices.driverAvailableStatus(userid,status).enqueue(new Callback<AvailableStatusModel>() {
            @Override
            public void onResponse(Call<AvailableStatusModel> call, Response<AvailableStatusModel> response) {
                if (response.isSuccessful()) {
                    CustomProgressbar.hideProgressBar();
                    AvailableStatusModel getLoginModle = response.body();
                    if (getLoginModle.getStatus() == true) {
                        AppSession.setStringPreferences(getActivity(), Constants.DATA, getLoginModle.getData());
                    }
                }else {
                    if (response.code() == 400) {
                        if(!response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                CustomProgressbar.hideProgressBar();
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
            public void onFailure(Call<AvailableStatusModel> call, Throwable t) {
                Log.d("test", String.valueOf(t));
                CustomProgressbar.hideProgressBar();

            }
        });
    }
}
