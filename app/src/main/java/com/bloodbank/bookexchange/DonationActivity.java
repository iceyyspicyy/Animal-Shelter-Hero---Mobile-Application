package com.bloodbank.bookexchange;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class DonationActivity extends AppCompatActivity implements PaymentResultListener {

    private EditText writeMessage, donationAmount;
    private Button donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        writeMessage = findViewById(R.id.writeEt);

        donationAmount = findViewById(R.id.enterAmountEt);
//        String n = donationAmount.getText().toString();
//
//        int amount = Math.round(Float.parseFloat(n) * 100);

        donate = findViewById(R.id.donationBtn);

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_GCz0MgPWHt4hD2");
                checkout.setImage(R.drawable.rzp_logo);

                JSONObject object = new JSONObject();
                try {
                    object.put("name","Rescue Dog");
                    object.put("description","Test Payment");
                    object.put("theme.color","#0093DD");
                    object.put("currency","INR");
                    object.put("amount",donationAmount.getText().toString());
                    object.put("prefill.contact","8638853591");
                    object.put("prefill.email","barmangolap9@gmail.com");
                    checkout.open(DonationActivity.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    private void paymentGatewayStart(String amount, String desc) {
//        final EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder()
//                .with(this)
//                .setPayeeVpa("8638853591@upi")
//                .setPayeeName("Golap")
//                .setTransactionId("20190603022401")
//                .setTransactionRefId("0120192019060302240")
//                .setDescription(writeMessage.getText().toString())
//                .setAmount(donationAmount.getText().toString());
//
//        EasyUpiPayment easyUpiPayment = builder.build();
//        easyUpiPayment.startPayment();
//
//        easyUpiPayment.setPaymentStatusListener(this);
//
//    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}