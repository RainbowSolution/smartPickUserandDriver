package com.alpha.smartpickuser.bookPkg.goodtypesPkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.bookPkg.goodtypesPkg.cartmodelPkg.AllVehicle;
import com.alpha.smartpickuser.utility.PrefData;

import java.util.List;

public class GoodsTypesAdapter extends RecyclerView.Adapter<GoodsTypesAdapter.ViewHolder> {
    private Context context;
    private List<AllVehicle> personUtils;
    Context con;
    PrefData prefData;
    public GoodsTypesAdapter(Context context) {
        this.context = context;
        con = context;
        prefData = new PrefData(con);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.bottom_sheet_row                                                 , parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AllVehicle pu = personUtils.get(position);
        if (prefData.getCurrentLanguage().equals("ar")){
            holder.pJobProfile.setText(pu.getName());
        }else if (prefData.getCurrentLanguage().equals("eng")){
            holder.pJobProfile.setText(pu.getEnglish_name());
        }

    }

    @Override
    public int getItemCount() {
        return personUtils == null ? 0 : personUtils.size();

    }
    public void allvehicleType(List<AllVehicle> personUtils) {
        this.personUtils = personUtils;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public AppCompatTextView pJobProfile;
        public ViewHolder( View itemView) {
            super(itemView);
            pJobProfile = (AppCompatTextView) itemView.findViewById(R.id.goods_type_id);
        }

        @Override
        public void onClick(View v) {

        }
    }
}