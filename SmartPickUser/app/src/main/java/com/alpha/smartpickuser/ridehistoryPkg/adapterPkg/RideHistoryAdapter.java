package com.alpha.smartpickuser.ridehistoryPkg.adapterPkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.ridehistoryPkg.getbookingPkg.AllActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RideHistoryAdapter extends RecyclerView.Adapter<RideHistoryAdapter.ViewHolder> {
    private Context context;
    private RideHistoryClickListener rideHistoryClickListener;
    private List<AllActivity> getBookingModles;
    public RideHistoryAdapter(Context context, RideHistoryClickListener rideHistoryClickListener) {
        this.context = context;
        this.rideHistoryClickListener = rideHistoryClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.ride_history_card_row                                                 , parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.invoice_id.setText("#"+getBookingModles.get(position).getTime());
        holder.text_date.setText(getBookingModles.get(position).getPickDateTime());
        holder.txt_d_loc.setText(getBookingModles.get(position).getDropLocation());
        holder.tv_price.setText(getBookingModles.get(position).getAmount());
        holder.userNameId.setText(getBookingModles.get(position).getUsername());
        JSONArray jre = null;
        try {
            jre = new JSONArray(getBookingModles.get(position).getPickLocation());
            for (int j = 0; j < jre.length(); j++) {
                JSONObject jobject = jre.getJSONObject(j);
                holder.txt_p_loc.setText(jobject.getString("address"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (getBookingModles.get(position).getImage().isEmpty()) {
        } else {
            Picasso.with(context).load(RetrofitClient.USER_PROFILE_URL+getBookingModles.get(position).getImage()).into(holder.driverProifleId);
        }
        JSONArray jre1 = null;
        try {
            jre1 = new JSONArray(getBookingModles.get(position).getDropLocation());
            for (int j = 0; j < jre1.length(); j++) {
                JSONObject jobject = jre1.getJSONObject(jre1.length()-1);
                holder.txt_d_loc.setText(jobject.getString("address"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void ridehistoryList(List<AllActivity> getBookingModles) {
        this.getBookingModles = getBookingModles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return getBookingModles == null ? 0 : getBookingModles.size();
    }

    public interface RideHistoryClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

         AppCompatTextView invoice_id,text_date,txt_p_loc,txt_d_loc,userNameId,tv_price;
        CircleImageView  driverProifleId;
        public ViewHolder(View itemView) {
            super(itemView);
            invoice_id = itemView.findViewById(R.id.invoice_id);
            text_date = itemView.findViewById(R.id.text_date);
            txt_p_loc = itemView.findViewById(R.id.txt_p_loc);
            txt_d_loc =itemView.findViewById(R.id.txt_d_loc);
            userNameId = itemView.findViewById(R.id.userNameId);
            tv_price = itemView.findViewById(R.id.price);
            driverProifleId = itemView.findViewById(R.id.driverProifleId);
        }

        @Override
        public void onClick(View v) {
        }
    }
}