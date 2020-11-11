package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kominfo.anaksehat.controllers.BaseActivity;

public class AccountActivationActivity extends BaseActivity {

    private EditText etPhoneNumber;
    private Button btnSubmit;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activation);

        checkSession2();

        etPhoneNumber = findViewById(R.id.phone_number);
        btnSubmit = findViewById(R.id.submit);

        phoneNumber = getIntent().getStringExtra("phone_number");
        if(phoneNumber!=null)
            etPhoneNumber.setText(phoneNumber);

        etPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activationNow();
            }
        });
    }

    private void activationNow(){
        phoneNumber = etPhoneNumber.getText().toString();

        if(validateData()) {
            showProgressBar(true);
            mApiService.resendCode(phoneNumber)
                    .enqueue(accountActivationCallback.getCallback());
        }
    }

    private boolean validateData(){
        if(!Patterns.PHONE.matcher(phoneNumber).matches()){
            etPhoneNumber.setError(getString(R.string.error_phone_number));
            etPhoneNumber.requestFocus();
            return false;
        }
        return true;
    }

    ApiCallback accountActivationCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.resend_code_done),
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(context, ActivationActivity.class);
            i.putExtra("phone_number", phoneNumber);
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
