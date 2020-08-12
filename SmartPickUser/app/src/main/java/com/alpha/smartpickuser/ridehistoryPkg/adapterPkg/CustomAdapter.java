package com.alpha.smartpickuser.ridehistoryPkg.adapterPkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.ridehistoryPkg.getbookingPkg.LocationItem;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    Context context;
    ArrayList<LocationItem> arrayList;

    public CustomAdapter(Context context, ArrayList<LocationItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_drop_location
                , parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.droplocation.setText(arrayList.get(position).getDroplocation());
        if (arrayList.get(position).getReciver_name().isEmpty()){
            holder.reciver_name.setVisibility(View.GONE);
        }else {
            holder.reciver_name.setText(context.getResources().getString(R.string.reciver_name)+" : "+arrayList.get(position).getReciver_name());
        }
        if (arrayList.get(position).getReciver_number().isEmpty()){
            holder.reciver_number.setVisibility(View.GONE);
        }else {
            holder.reciver_number.setText(context.getResources().getString(R.string.reciver_nubmer)+" : "+arrayList.get(position).getReciver_number());
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        AppCompatTextView droplocation, email,reciver_name,reciver_number;
        public ViewHolder( View itemView) {
            super(itemView);
            droplocation = (AppCompatTextView) itemView.findViewById(R.id.txt_d_loc);
            reciver_name =(AppCompatTextView) itemView.findViewById(R.id.reciver_name);
            reciver_number = (AppCompatTextView) itemView.findViewById(R.id.reciver_number);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
