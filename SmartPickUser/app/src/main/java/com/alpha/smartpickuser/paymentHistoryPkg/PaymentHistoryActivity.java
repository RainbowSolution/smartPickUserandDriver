package com.alpha.smartpickuser.paymentHistoryPkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.HomeActivity;
import com.alpha.smartpickuser.paymentHistoryPkg.adapterPkg.PaymentHistoryAdapter;
import com.alpha.smartpickuser.utility.RecyclerItemClickListener;

public class PaymentHistoryActivity extends AppCompatActivity implements PaymentHistoryAdapter.PaymentHistoryClickListener,View.OnClickListener {


    private RecyclerView rvpaymentHistoryId;
    private PaymentHistoryAdapter rideHistoryAdapter;
    private AppCompatImageView ivbackId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        init();

    }

    private void init() {
        ivbackId = findViewById(R.id.ivbackId);
        rvpaymentHistoryId = findViewById(R.id.rvpaymentHistoryId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvpaymentHistoryId.setLayoutManager(layoutManager);
        rideHistoryAdapter = new PaymentHistoryAdapter(this, this);
        rvpaymentHistoryId.setAdapter(rideHistoryAdapter);
        ivbackId.setOnClickListener(this);
        rvpaymentHistoryId.addOnItemTouchListener(new RecyclerItemClickListener(PaymentHistoryActivity.this, rvpaymentHistoryId, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PaymentHistoryActivity.this, PaymentDetailsActivityMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                finish();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View view, int position) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivbackId:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PaymentHistoryActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }
}
