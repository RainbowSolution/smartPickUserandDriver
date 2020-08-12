package com.alpha.smartpick.driver.paymentHistoryPkg.adapterPkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.alpha.smartpick.driver.R;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {
    private Context context;
    private PaymentHistoryClickListener paymentHistoryClickListener;

    public PaymentHistoryAdapter(Context context, PaymentHistoryClickListener rideHistoryClickListener) {
        this.context = context;
        this.paymentHistoryClickListener = paymentHistoryClickListener;
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
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public interface PaymentHistoryClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public ViewHolder( View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
        }
    }
}