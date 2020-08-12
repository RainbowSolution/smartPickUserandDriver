package com.alpha.smartpickuser.paymentHistoryPkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.alpha.smartpickuser.R;

public class PaymentDetailsActivityMain extends AppCompatActivity implements View.OnClickListener {
    LinearLayout viewpaymentdetailsId;
    private String payment_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        init();
    }

    private void init() {
        AppCompatImageView ivbackId = findViewById(R.id.ivbackId);
        viewpaymentdetailsId = findViewById(R.id.viewpaymentdetailsId);
        ivbackId.setOnClickListener(this);
        viewpaymentdetailsId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivbackId:
                onBackPressed();
                break;
            case R.id.viewpaymentdetailsId:
                Intent intent = new Intent(PaymentDetailsActivityMain.this, HistoryDetailsActivityMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PaymentDetailsActivityMain.this, PaymentHistoryActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }
}
