package com.kominfo.anaksehat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.ImageFilePath;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.District;
import com.kominfo.anaksehat.models.State;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignupActivity extends BaseActivity {
    static final int REQUEST_CHOOSE_FILE = 2;
    static final int MY_PERMISSIONS_REQUEST_STORAGE = 100;

    private EditText etUsername, etName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
    private AutoCompleteTextView actvState, actvDistrict;
    private TextView tvLegalFile;
    private Spinner spRole;
    private State selectedState;
    private District selectedDistrict;
    private String mCurrentFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        checkSession2();

        etUsername = findViewById(R.id.username);
        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etPhoneNumber = findViewById(R.id.phone_number);
        etPassword = findViewById(R.id.password);
        etConfirmPassword = findViewById(R.id.confirm_password);
        spRole = findViewById(R.id.role);
        actvDistrict = findViewById(R.id.district_name);
        actvState = findViewById(R.id.state_name);
        tvLegalFile = findViewById(R.id.legal_file);

        etUsername.setOnFocusChangeListener(tipsFocus);
        etName.setOnFocusChangeListener(tipsFocus);
        etEmail.setOnFocusChangeListener(tipsFocus);
        etPhoneNumber.setOnFocusChangeListener(tipsFocus);
        etPassword.setOnFocusChangeListener(tipsFocus);
        etConfirmPassword.setOnFocusChangeListener(tipsFocus);
        actvState.setOnFocusChangeListener(tipsFocus);
        actvDistrict.setOnFocusChangeListener(tipsFocus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRole.setAdapter(adapter);

        spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position==0){
//                    tvLegalFile.setVisibility(View.VISIBLE);
//                } else tvLegalFile.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    signupnow();
                    handled = true;
                }
                return handled;
            }
        });

//        showProgressBar(true);
//        mApiService.getStates(appSession.getData(AppSession.TOKEN))
//                .enqueue(statecallback.getCallback());
    }

    private void signupnow(){
        String username = etUsername.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etPassword.getText().toString();
//        String confirmPassword = etConfirmPassword.getText().toString();
        int posyandu = 1;
//        if("Akun Ibu".compareToIgnoreCase(spRole.getSelectedItem().toString())==0)
//            posyandu=0;

        if(validateData(posyandu)) {
            if(posyandu==0) {
                showProgressBar(true);
                mApiService.signinRequest(email, phoneNumber, posyandu, password, confirmPassword)
                        .enqueue(signinCallback.getCallback());
            } else {

//                File file= new File(mCurrentFilePath);
//
//                // create RequestBody instance from file
//                RequestBody requestFile =
//                        RequestBody.create(
//                                MediaType.parse("multipart/form-data"),
//                                file
//                        );
//
//                // MultipartBody.Part is used to send also the actual file name
//                MultipartBody.Part body =
//                        MultipartBody.Part.createFormData("official_letter", file.getName(), requestFile);

//                RequestBody rb_username =
//                        RequestBody.create(
//                                MediaType.parse("text/plain"), username);
//
//                RequestBody rb_name =
//                        RequestBody.create(
//                                MediaType.parse("text/plain"), name);

                RequestBody rb_phone_number =
                        RequestBody.create(
                                MediaType.parse("text/plain"), phoneNumber);

                RequestBody rb_email =
                        RequestBody.create(
                                MediaType.parse("text/plain"), email);

                RequestBody rb_posyandu =
                        RequestBody.create(
                                MediaType.parse("text/plain"), ""+posyandu);

                RequestBody rb_password =
                        RequestBody.create(
                                MediaType.parse("text/plain"), password);

//                RequestBody rb_password2 =
//                        RequestBody.create(
//                                MediaType.parse("text/plain"), confirmPassword);
//
//                RequestBody rb_state =
//                        RequestBody.create(
//                                MediaType.parse("text/plain"), ""+selectedState.getId());
//
//                RequestBody rb_district =
//                        RequestBody.create(
//                                MediaType.parse("text/plain"), ""+selectedDistrict.getId());

                showProgressBar(true);
//                mApiService.signinRequest(rb_email, rb_phone_number,
//                        rb_posyandu, rb_password)
//                        .enqueue(signinCallback.getCallback());

                mApiService.signinRequest(email, phoneNumber, posyandu, password, confirmPassword)
                        .enqueue(signinCallback.getCallback());

            }
        }
    }

    private boolean validateData(int posyandu){
//        String username = etUsername.getText().toString();
//        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String password = etPassword.getText().toString();
//        String confirmPassword = etConfirmPassword.getText().toString();

//        if(name.length()<4){
//            etName.setError(getString(R.string.error_name));
//            etName.requestFocus();
//            return false;
//        }
//        if(username.length()<4){
//            etUsername.setError(getString(R.string.error_username_short));
//            etUsername.requestFocus();
//            return false;
//        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError(getString(R.string.error_email));
            etEmail.requestFocus();
            return false;
        }
        if(!Patterns.PHONE.matcher(phoneNumber).matches()){
            etPhoneNumber.setError(getString(R.string.error_phone_number));
            etPhoneNumber.requestFocus();
            return false;
        }
        if(password.length()<8){
            etPassword.setError(getString(R.string.error_password_short));
            etPassword.requestFocus();
            return false;
        }
//        if(confirmPassword.length()<8){
//            etConfirmPassword.setError(getString(R.string.error_password_short));
//            etConfirmPassword.requestFocus();
//            return false;
//        }
//        if(confirmPassword.compareTo(password)!=0){
//            etConfirmPassword.setError(getString(R.string.error_password_not_match));
//            etConfirmPassword.requestFocus();
//            return false;
//        }
//        if(selectedState==null){
//            actvState.setError(getString(R.string.error_state));
//            actvState.requestFocus();
//            return false;
//        }
//        if(selectedDistrict==null){
//            actvDistrict.setError(getString(R.string.error_district));
//            actvDistrict.requestFocus();
//            return false;
//        }
//        if(posyandu==1&&mCurrentFilePath==null){
//            Toast.makeText(context, R.string.error_legal_file, Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }

    public void onClickSignup(View v){
        switch (v.getId()){
            case R.id.submit:
                signupnow();
                break;
            case R.id.swap:
                finish();
                break;
            case R.id.legal_file:
                chooseFile();
                break;
        }
    }

    private void chooseFile(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkCameraPermission(MY_PERMISSIONS_REQUEST_STORAGE))
                dispatchChoosePictureIntent();
        } else
            dispatchChoosePictureIntent();
    }

    ApiCallback signinCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Intent i = new Intent(SignupActivity.this, ActivationActivity.class);
            i.putExtra("phone_number", etPhoneNumber.getText().toString());
            startActivity(i);
            finish();
//            User user = new Gson().fromJson(result, User.class);
//            if(user.getToken()!=null&&user.getId()!=0) {
//                AppSession appSession = new AppSession(context);
//                appSession.createSession(user.getToken(), user);
//                Intent i = new Intent(SigninActivity.this, StartActivity.class);
//                startActivity(i);
//                finish();
//            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        showWarning(R.string.warning, R.string.warning_signup, R.string.ok, R.string.cancel);
    }

    View.OnFocusChangeListener tipsFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText)v;
            if(hasFocus&&editText.getText().toString().isEmpty()){
                editText.setError(editText.getContentDescription().toString());
            }
        }
    };

    ApiCallback statecallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiData<State> stateApiData = new Gson().fromJson(result, new TypeToken<ApiData<State>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            ArrayAdapter<State> adapter = new ArrayAdapter<State>(context,
                    android.R.layout.simple_dropdown_item_1line, stateApiData.getData());
            actvState.setAdapter(adapter);
            actvState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedState = (State) parent.getAdapter().getItem(position);
                    AppLog.d(new Gson().toJson(selectedState));
                    showProgressBar(true);
                    mApiService.getDistrics(appSession.getData(AppSession.TOKEN),
                            selectedState.getId()).enqueue(districtcallback.getCallback());
                }
            });
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
        }
    };

    ApiCallback districtcallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiData<District> districtApiData = new Gson().fromJson(result, new TypeToken<ApiData<District>>(){}.getType());
            ArrayAdapter<District> adapter = new ArrayAdapter<District>(context,
                    android.R.layout.simple_dropdown_item_1line, districtApiData.getData());
            actvDistrict.setAdapter(adapter);
            actvDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedDistrict = (District) parent.getAdapter().getItem(position);
                    AppLog.d(new Gson().toJson(selectedDistrict));
                }
            });
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
        }
    };

    public boolean checkCameraPermission(int permission){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        permission);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        permission);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        dispatchChoosePictureIntent();
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(context, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    private void dispatchChoosePictureIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Choose a File"),
                REQUEST_CHOOSE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_FILE && resultCode == RESULT_OK && data != null){
            mCurrentFilePath = ImageFilePath.getPath(context, data.getData());
            AppLog.d(mCurrentFilePath);
            String[] path = mCurrentFilePath.split("/");
            tvLegalFile.setText(path[path.length-1]);
        }
    }
}
