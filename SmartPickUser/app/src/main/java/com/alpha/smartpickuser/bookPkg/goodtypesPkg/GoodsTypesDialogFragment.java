package com.alpha.smartpickuser.bookPkg.goodtypesPkg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpickuser.ApiPkg.ApiServices;
import com.alpha.smartpickuser.ApiPkg.RetrofitClient;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.bookPkg.goodtypesPkg.cartmodelPkg.AllVehicle;
import com.alpha.smartpickuser.bookPkg.goodtypesPkg.cartmodelPkg.CardModel;
import com.alpha.smartpickuser.utility.AppSession;
import com.alpha.smartpickuser.utility.Constants;
import com.alpha.smartpickuser.utility.PrefData;
import com.alpha.smartpickuser.utility.RecyclerItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodsTypesDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";
    private RecyclerView goods_type;
    private ItemClickListener mListener;
    private GoodsTypesAdapter customRecyclerAdapter;
    List<AllVehicle> allVehicles;
    private ApiServices apiServices;
    private String cart_id;
    private ProgressBar q_progress;
    private AppCompatTextView txt_v_type;
    Context con;
    PrefData prefData;
    public static GoodsTypesDialogFragment newInstance() {
        return new GoodsTypesDialogFragment();
    }
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        con = getActivity();
        prefData = new PrefData(con);
        init(view);
        cart_id = AppSession.getStringPreferences(getActivity(), Constants.VEHICLE_TYPE);
        ridehistory();
        return view;

    }

    private void init(View view) {
        allVehicles = new ArrayList<>();
        goods_type=view.findViewById(R.id.goods_type);
        txt_v_type = view.findViewById(R.id.txt_v_type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        goods_type.setLayoutManager(layoutManager);
        q_progress = view.findViewById(R.id.q_progress);
        customRecyclerAdapter = new GoodsTypesAdapter(getActivity());
        goods_type.setAdapter(customRecyclerAdapter);
        goods_type.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), goods_type, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (prefData.getCurrentLanguage().equals("ar")){
                    AllVehicle pu = allVehicles.get(position);
                    mListener.onItemClick(pu.getName());
                    dismiss();
                }else if (prefData.getCurrentLanguage().equals("eng")){
                    AllVehicle pu = allVehicles.get(position);
                    mListener.onItemClick(pu.getEnglish_name());
                    dismiss();
                }

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        mListener.onItemClick(tvSelected.getText().toString());
        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }

    private void ridehistory() {
        q_progress.setVisibility(View.VISIBLE);
            apiServices.goodId(cart_id).enqueue(new Callback<CardModel>() {
            @Override
            public void onResponse(Call<CardModel> call, Response<CardModel> response) {
                if (response.isSuccessful()) {
                    q_progress.setVisibility(View.GONE);
                    CardModel getBookingModle = response.body();
                    if (getBookingModle.getStatus().equals(true)) {
                        allVehicles = getBookingModle.getAllVehicle();
                        customRecyclerAdapter.allvehicleType(allVehicles);
                        txt_v_type.setVisibility(View.GONE);
                    }else {
                        txt_v_type.setVisibility(View.VISIBLE);
                        txt_v_type.setText(getBookingModle.getMessage());
                    }

                }
            }
            @Override
            public void onFailure(Call<CardModel> call, Throwable t) {
                q_progress.setVisibility(View.GONE);
            }
        });

    }
}