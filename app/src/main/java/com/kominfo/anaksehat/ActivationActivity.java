package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;

public class ActivationActivity extends BaseActivity {
    private EditText etActivation;
    private TextView tvResend;
    private Button btnSubmit;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        checkSession2();

        phoneNumber = getIntent().getStringExtra("phone_number");

        etActivation = findViewById(R.id.activation_code);
        btnSubmit = findViewById(R.id.submit);
        tvResend = findViewById(R.id.resend);

        etActivation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    activationNow();
                    handled = true;
                }
                return handled;
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAccountActivation();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activationNow();
            }
        });
    }

    private boolean validateData(){
        String activationCode = etActivation.getText().toString();
        if(activationCode.length()<4){
            etActivation.setError(getString(R.string.error_activation));
            etActivation.requestFocus();
            return false;
        }
        return true;
    }

    private void activationNow(){
        String activationCode = etActivation.getText().toString();

        if(validateData()) {
            showProgressBar(true);
            mApiService.activation(phoneNumber, activationCode)
                    .enqueue(activationCallback.getCallback());
        }
    }

    private void gotoAccountActivation(){
        Intent i = new Intent(context, AccountActivationActivity.class);
        i.putExtra("phone_number", phoneNumber);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        showWarning(R.string.warning, R.string.warning_activation, R.string.ok, R.string.cancel);
    }

    ApiCallback activationCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.signup_done),
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };
}
