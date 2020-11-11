package com.kominfo.anaksehat;

import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity {
    private EditText etPhoneNumber;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etPhoneNumber = findViewById(R.id.phone_number);
        btnSend = findViewById(R.id.submit);

        etPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendPassword();
                    handled = true;
                }
                return handled;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPassword();
            }
        });
    }

    private boolean validateData(){
        String phoneNumber = etPhoneNumber.getText().toString();
        if(!Patterns.PHONE.matcher(phoneNumber).matches()){
            etPhoneNumber.setError(getString(R.string.error_phone_number));
            etPhoneNumber.requestFocus();
            return false;
        }
        return true;
    }

    private void sendPassword(){
        String phoneNumber = etPhoneNumber.getText().toString();

        if(validateData()){
            showProgressBar(true);
            mApiService.forgotPassword(phoneNumber)
                    .enqueue(forgotCallback.getCallback());
        }
    }

    @Override
    public void onBackPressed() {
        showWarning(R.string.warning, R.string.warning_forgot, R.string.ok, R.string.cancel);
    }

    ApiCallback forgotCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.forgot_done),
                    Toast.LENGTH_LONG).show();
            finish();
        }
        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };
}
