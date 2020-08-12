package com.alpha.smartpick.driver.notificationPkg.adapterPkg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpick.driver.ApiPkg.RetrofitClient;
import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.notificationPkg.getNotificaitonList.Datum;
import com.alpha.smartpick.driver.passengerPkg.PassengerDetailsActivity;
import com.squareup.picasso.Picasso;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private NotificationOnClickListener notificationOnClickListener;
    private List<Datum> getBookingModles;
    public NotificationAdapter(Context context,NotificationOnClickListener notificationOnClickListener) {
        this.context = context;
        this.notificationOnClickListener = notificationOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.notification_card_row                                                 , parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.tvNotificationId.setText(getBookingModles.get(position).getMessage());
            holder.userNameId.setText(getBookingModles.get(position).getPatientsData().getUsername());
            if (getBookingModles.get(position).getPatientsData().getImage().toString().isEmpty()) {
            } else {
                Picasso.with(context).load(RetrofitClient.USER_PROFILE_URL+getBookingModles.get(position).getPatientsData().getImage()).placeholder(R.drawable.progress_animation).into(holder.driverProifleId);
            }
            Log.d("res",getBookingModles.get(position).getPatientsData().getUsername());
            holder.viewdetilsId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity mainAct = (Activity) context;
                    Intent intent = new Intent(context, PassengerDetailsActivity.class);
                    intent.putExtra("invoice_id",getBookingModles.get(position).getBookingId());
                    intent.putExtra("date",getBookingModles.get(position).getBookingData().getPickDateTime());
                    intent.putExtra("user_name",getBookingModles.get(position).getPatientsData().getUsername());
                    intent.putExtra("user_profile",getBookingModles.get(position).getPatientsData().getImage());
                    intent.putExtra("amount",getBookingModles.get(position).getBookingData().getAmount());
                    intent.putExtra("user_id",getBookingModles.get(position).getBookingData().getDriverId());
                    intent.putExtra("distance",getBookingModles.get(position).getBookingData().getDistance());
                    intent.putExtra("pick_location",getBookingModles.get(position).getBookingData().getPickLocation());
                    intent.putExtra("drop_location",getBookingModles.get(position).getBookingData().getDropLocation());
                    intent.putExtra("type",getBookingModles.get(position).getBookingData().getVehicleType());
                    intent.putExtra("goodstype",getBookingModles.get(position).getBookingData().getStufName());
                    intent.putExtra("user_number",getBookingModles.get(position).getPatientsData().getPhoneNumber());
                    intent.putExtra("status",getBookingModles.get(position).getBookingData().getIsStatus());
                    intent.putExtra("description",getBookingModles.get(position).getBookingData().getDescription());
                    intent.putExtra("packaging",getBookingModles.get(position).getBookingData().getPackagingRequired());
                    intent.putExtra("lift_facility",getBookingModles.get(position).getBookingData().getLiftFacility());
                    mainAct.startActivity(intent);
                    mainAct.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                }
            });
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return getBookingModles == null ? 0 : getBookingModles.size();
    }
    public void ridehistoryList(List<Datum> getBookingModles) {
        this.getBookingModles = getBookingModles;
        notifyDataSetChanged();
    }
    public interface NotificationOnClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        AppCompatTextView tvNotificationId,userNameId;
        AppCompatButton viewdetilsId;
        CircleImageView driverProifleId;
        public ViewHolder( View itemView) {
            super(itemView);
            tvNotificationId = itemView.findViewById(R.id.tvNotificationId);
            userNameId = itemView.findViewById(R.id.userNameId);
            viewdetilsId = itemView.findViewById(R.id.viewdetilsId);
            driverProifleId = itemView.findViewById(R.id.driverProifleId);
        }

        @Override
        public void onClick(View v) {
        }
    }
}