package com.alpha.smartpick.driver.paymentDetailsPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.alpha.smartpick.driver.R;
import com.alpha.smartpick.driver.utility.AppSession;
import com.alpha.smartpick.driver.utility.Constants;

public class PaymentDetailsActivity  extends AppCompatActivity implements View.OnClickListener {

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
        payment_type = AppSession.getStringPreferences(PaymentDetailsActivity.this, Constants.PAYMENT_STATUS);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivbackId:
                onBackPressed();
                break;
            case R.id.viewpaymentdetailsId:
               onBackPressed();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            finish();
    }
}
