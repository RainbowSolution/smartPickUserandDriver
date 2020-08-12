package com.alpha.smartpickuser.notificationPkg.adapterPkg;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.livetrackPkg.LiveTrackActivity;
import com.alpha.smartpickuser.notificationPkg.getNotificationListPkg.Datum;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvNotificationId.setText(getBookingModles.get(position).getMessage());
        holder.userNameId.setText(getBookingModles.get(position).getDriverUsername());
        holder.numberplate.setText(context.getResources().getString(R.string.vehicle_number)+" : \n"+getBookingModles.get(position).getVehicleNumber());
        holder.driverNumber.setText(context.getResources().getString(R.string.driver_number)+" : \n"+getBookingModles.get(position).getDriverNumber());
        if (getBookingModles.get(position).getDriverImage().isEmpty()) {
        } else {
            Picasso.with(context).load(RetrofitClient.USER_PROFILE_URL+getBookingModles.get(position).getDriverImage()).placeholder(R.drawable.progress_animation).into(holder.driverProifleId);
        }

        if (getBookingModles.get(position).getBookingData().getIsStatus().equals("3")){
            holder.viewdoneId.setVisibility(View.VISIBLE);
            holder.viewdetilsId.setVisibility(View.GONE);
        }else {
            holder.viewdoneId.setVisibility(View.GONE);
            holder.viewdetilsId.setVisibility(View.VISIBLE);
        }

        holder.viewdetilsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity mainAct = (Activity) context;
                Intent intent = new Intent(context, LiveTrackActivity.class);
                intent.putExtra("invoice_id",getBookingModles.get(position).getBookingData().getId());
                intent.putExtra("date",getBookingModles.get(position).getDateTime());
                intent.putExtra("user_name",getBookingModles.get(position).getUserData().getUsername());
                intent.putExtra("user_profile",getBookingModles.get(position).getUserData().getImage());
                intent.putExtra("amount",getBookingModles.get(position).getBookingData().getAmount());
                intent.putExtra("driver_id",getBookingModles.get(position).getBookingData().getDriverId());
                intent.putExtra("distance",getBookingModles.get(position).getBookingData().getDistance());
                intent.putExtra("pick_location",getBookingModles.get(position).getBookingData().getPickLocation());
                intent.putExtra("drop_location",getBookingModles.get(position).getBookingData().getDropLocation());
                intent.putExtra("type",getBookingModles.get(position).getBookingData().getVehicleType());
                intent.putExtra("user_number",getBookingModles.get(position).getUserData().getPhoneNumber());
                intent.putExtra("reciver_contact",getBookingModles.get(position).getBookingData().getReceiverName());
                intent.putExtra("reciver_number",getBookingModles.get(position).getBookingData().getReceiverMobilenumber());
                intent.putExtra("status",getBookingModles.get(position).getBookingData().getStatus());
                mainAct.startActivity(intent);
                mainAct.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        });
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
        AppCompatTextView tvNotificationId,userNameId,numberplate,driverNumber;
        AppCompatButton viewdetilsId,viewdoneId;
        CircleImageView driverProifleId;
        public ViewHolder( View itemView) {
            super(itemView);
            tvNotificationId = itemView.findViewById(R.id.tvNotificationId);
            userNameId = itemView.findViewById(R.id.userNameId);
            viewdetilsId = itemView.findViewById(R.id.viewdetilsId);
            driverProifleId = itemView.findViewById(R.id.driverProifleId);
            numberplate = itemView.findViewById(R.id.numberplate);
            driverNumber = itemView.findViewById(R.id.driverNumber);
            viewdoneId = itemView.findViewById(R.id.viewdoneId);
        }

        @Override
        public void onClick(View v) {
        }
    }
}