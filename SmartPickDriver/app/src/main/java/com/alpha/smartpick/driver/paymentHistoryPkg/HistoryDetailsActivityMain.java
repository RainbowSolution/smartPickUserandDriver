package com.alpha.smartpick.driver.paymentHistoryPkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.alpha.smartpick.driver.R;

public class HistoryDetailsActivityMain extends AppCompatActivity implements View.OnClickListener {

    LinearLayout viewpaymentdetailsId;
    private String payment_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
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
                Intent intent = new Intent(HistoryDetailsActivityMain.this, PaymentDetailsActivityMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HistoryDetailsActivityMain.this, PaymentDetailsActivityMain.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();

    }
}
