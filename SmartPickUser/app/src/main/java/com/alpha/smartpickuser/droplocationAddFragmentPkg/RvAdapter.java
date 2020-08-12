package com.alpha.smartpickuser.droplocationAddFragmentPkg;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.droplocationAddFragmentPkg.Roomdatabase.DatabaseClient;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {
    Context context;
    List<PlaceModel> models;
    Onclick onclick;
    String recivername,recivercontactnumber,productId;


    public interface Onclick {
        void onEvent(View view, PlaceModel model, int pos);
    }

    public RvAdapter(Context context, List<PlaceModel> models, Onclick onclick) {
        this.context = context;
        this.models = models;
        this.onclick = onclick;
    }

    View view;

    @Override
    public RvAdapter.RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.from(parent.getContext()).inflate(R.layout.row_drop_point, parent, false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(RvAdapter.RvViewHolder holder, final int position) {
        final PlaceModel model = models.get(position);
        if (model.getDroplocation() != null) {
            holder.txt_drop_loc.setText(model.getDroplocation());

        }
        if (model.getReceivername().isEmpty()){

        }else {
            holder.reciver_number.setText(context.getResources().getString(R.string.reciver_nubmer)+" : "+model.getReceivernumber());
            holder.reciver_name.setText(context.getResources().getString(R.string.reciver_nubmer)+" : "+model.getReceivername());
        }

        holder.removeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models.remove(position);
                deleteTask();
                notifyDataSetChanged();
            }
        });
        holder.imgcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onEvent(v,model,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();

    }


    public void allproductlist(List<PlaceModel> taskList) {
        this.models = taskList;
        notifyDataSetChanged();
    }
        public class RvViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView txt_drop_loc,reciver_name,reciver_number;
        ImageView removeImg,imgcontact;
        LinearLayout llItem;

        public RvViewHolder(View itemView) {
            super(itemView);
            txt_drop_loc = itemView.findViewById(R.id.txt_drop_loc);
            removeImg = itemView.findViewById(R.id.img_remove);
            imgcontact = itemView.findViewById(R.id.img_contact);
            reciver_name = itemView.findViewById(R.id.reciver_name);
            reciver_number = itemView.findViewById(R.id.reciver_number);


        }


    }

    private void deleteTask() {
        class DeleteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(context).getAppDatabase()
                        .taskDao()
                        .delete();
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }
        DeleteTask dt = new DeleteTask();
        dt.execute();
    }


}



