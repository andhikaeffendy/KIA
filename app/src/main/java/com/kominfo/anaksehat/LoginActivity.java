package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.User;

public class LoginActivity extends BaseActivity {
    private EditText etUsername, etPassword;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        checkSession2();

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    loginNow();
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void loginNow(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(validateData()) {
            showProgressBar(true);
            mApiService.loginRequest(username, password).enqueue(loginCallback.getCallback());
        }
    }

    private boolean validateData(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if(BuildConfig.DEBUG) {
            if (username.compareToIgnoreCase("aliali") == 0) return true;
            if (username.compareToIgnoreCase("alinur") == 0) return true;
            if (username.compareToIgnoreCase("reza") == 0) return true;
        }
        if(username.length()<4){
            etUsername.setError(getString(R.string.error_username_short));
            etUsername.requestFocus();
            return false;
        }
        if(password.length()<8){
            etPassword.setError(getString(R.string.error_password_short));
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    public void onClickLogin(View v){
        Intent i = null;
        switch (v.getId()){
            case R.id.submit:
                loginNow();
                break;
            case R.id.swap:
                i = new Intent(context, SignupActivity.class);
                break;
            case R.id.forgot:
                i = new Intent(context, ForgotPasswordActivity.class);
                break;
            case R.id.resend:
                i = new Intent(context, AccountActivationActivity.class);
                break;
        }

        if(i!=null){
            startActivity(i);
        }
    }

    ApiCallback loginCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            user = new Gson().fromJson(result, User.class);
            if(user.getToken()!=null&&user.getId()!=0) {
                if(user.getPosyandu()==0&&user.getMotherId()>0){
                    Log.d("Ini IF", "Posyandu = " + user.getPosyandu());
                    showProgressBar(true);
                    mApiService.getMother(user.getMotherId(),user.getToken())
                            .enqueue(motherCallback.getCallback());
                } else {
                    Log.d("Ini Else", "Posyandu = " + user.getPosyandu());
                    appSession.createSession(user.getToken(), user);
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback motherCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            Mother mother = gson.fromJson(result, Mother.class);
            appSession.createSession(user.getToken(), user);
            appSession.setData(AppSession.USER, new Gson().toJson(user));
            appSession.setData(AppSession.MOTHER, new Gson().toJson(mother));
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
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
