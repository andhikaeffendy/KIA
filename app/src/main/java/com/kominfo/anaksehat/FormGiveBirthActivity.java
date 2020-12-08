package com.kominfo.anaksehat;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.adapters.SpinAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.BirthWay;
import com.kominfo.anaksehat.models.GiveBirth;
import com.kominfo.anaksehat.models.MotherCondition;
import com.kominfo.anaksehat.models.Pregnancy;

import java.util.Calendar;
import java.util.Date;

public class FormGiveBirthActivity extends BaseActivity {

    private EditText etBirthDate,etBirthTime,etPregnancyAge,etBirthHelper,etRemarks, etTreatment;
    private LinearLayout lnTreatment;
    private Spinner spMotherCondition, spBirthWay;
    private ImageView ivDate;
    private Pregnancy pregnancy;
    private GiveBirth giveBirth;
    private SpinAdapter<MotherCondition> motherConditionSpinAdapter;
    private SpinAdapter<BirthWay> birthWaySpinAdapter;
    private TimePickerDialog picker;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_give_birth_history);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Input Data Persalinan");

        lnTreatment = findViewById(R.id.layout_treatment);
        etTreatment = findViewById(R.id.treatment);
        etBirthDate = findViewById(R.id.birth_date);
        etBirthTime = findViewById(R.id.birth_time);
        etPregnancyAge = findViewById(R.id.pregnancy_age);
        etBirthHelper = findViewById(R.id.bitrh_helper);
        etRemarks = findViewById(R.id.remarks);
        ivDate = findViewById(R.id.history_icon);
        spBirthWay = findViewById(R.id.birth_way_id);
        spMotherCondition = findViewById(R.id.mother_condition_id);
        etBirthTime.setInputType(InputType.TYPE_NULL);
        etBirthTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                picker = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                etBirthTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        pregnancy = new Gson().fromJson(getIntent().getStringExtra("data"), Pregnancy.class);

        setTitle(pregnancy.getName());

        etBirthDate.setInputType(InputType.TYPE_NULL);
        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                cal.add(Calendar.YEAR,1);
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.minDate = pregnancy.getLast_period_date();
                newFragment.maxDate = new Date(cal.getTimeInMillis());
                newFragment.holder = etBirthDate;
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivDate.performClick();
            }
        });

        mApiService.getBirthWays().enqueue(birthWayCallback.getCallback());
        mApiService.getMotherConditions().enqueue(motherConditionCallback.getCallback());

        editMode = getIntent().getBooleanExtra("edit_mode", false);
        if(editMode) initEditData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pregnancies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickMenu(item.getItemId());
        return true;
    }

    public void onClickMenu(int resId){
        switch (resId){
            case android.R.id.home:
                backFunction();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initEditData(){
        giveBirth = new Gson().fromJson(getIntent().getStringExtra("edit_data"), GiveBirth.class);
        setTitle("Edit Data Persalinan");

        etBirthDate.setText(DateHelper.getDateServer(giveBirth.getBirth_date()));
        etBirthTime.setText(giveBirth.getBirth_time());
        etPregnancyAge.setText(""+giveBirth.getPregnancy_age());
        etBirthHelper.setText(giveBirth.getBitrh_helper());
        etRemarks.setText(giveBirth.getRemarks());
        if (giveBirth.getTreatment() != null) etTreatment.setText(giveBirth.getTreatment());
    }

    private boolean validateData(){
        String birthDate = etBirthDate.getText().toString();
        String birth_time = etBirthTime.getText().toString();
        String pregnancy_age = etPregnancyAge.getText().toString();
        String birth_helper = etBirthHelper.getText().toString();

        if(birthDate.isEmpty()){
            etBirthDate.setError("Tanggal Persalinan harus diisi");
            etBirthDate.requestFocus();
            return false;
        }
        if(birth_time.isEmpty()){
            etBirthTime.setError("Jam Persalinan harus diisi ");
            etBirthTime.requestFocus();
            return false;
        }
        if(pregnancy_age.isEmpty()){
            etPregnancyAge.setError("Umur Kehamilan harus diisi");
            etPregnancyAge.requestFocus();
            return false;
        }
        if(birth_helper.isEmpty()){
            etBirthHelper.setError("Penolong Persalinan harus diisi");
            etBirthHelper.requestFocus();
            return false;
        }
        return true;
    }

    public void onClickForm(View v){
        switch (v.getId()){
            case R.id.add:
//                startActivity(new Intent(context, FormPregnancyActivity.class));
//                finish();
                break;
            case R.id.submit:
                if(validateData())
                    submitData();
                break;
            case R.id.cancel:
                backFunction();
                break;
        }
    }

    private void submitData(){
        String birthDate = etBirthDate.getText().toString();
        String birthTime = etBirthTime.getText().toString();
        int pregnancyAge = Integer.parseInt(etPregnancyAge.getText().toString());
        String birthHelper = etBirthHelper.getText().toString();
        String remarks = etRemarks.getText().toString();
        MotherCondition selected = (MotherCondition) spMotherCondition.getSelectedItem();
        long motherConditionId = selected.getId();
        String birthWay = spBirthWay.getSelectedItem().toString();
        String treatment = etTreatment.getText().toString();

        long pregnancyId = pregnancy.getId();
        long motherId = pregnancy.getMother_id();

        showProgressBar(true);
        if(editMode){
            long id = giveBirth.getId();
            mApiService.updateGiveBirth(id, id, appSession.getData(AppSession.TOKEN),
                    pregnancyId, motherId, birthDate, birthTime, pregnancyAge, birthHelper,
                    birthWay, motherConditionId, remarks, treatment)
                    .enqueue(formCallback.getCallback());
        } else
            mApiService.createGiveBirth(appSession.getData(AppSession.TOKEN), pregnancyId, motherId,
                    birthDate, birthTime, pregnancyAge, birthHelper, birthWay, motherConditionId,
                    remarks,treatment)
                    .enqueue(formCallback.getCallback());
    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                giveBirth = gson.fromJson(result, GiveBirth.class);
                AppLog.d(new Gson().toJson(giveBirth));
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",new Gson().toJson(giveBirth));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
//                Intent i = new Intent(context, GiveBirthDetailActivity.class);
//                i.putExtra("parent_data", new Gson().toJson(pregnancy));
//                i.putExtra("data", new Gson().toJson(giveBirth));
//                startActivity(i);
//                finish();
            } else {
                mApiService.getGiveBirth(giveBirth.getId(), appSession.getData(AppSession.TOKEN))
                        .enqueue(givebirthCallback.getCallback());
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        backFunction();
    }

    ApiCallback givebirthCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            giveBirth = gson.fromJson(result, GiveBirth.class);
            AppLog.d(new Gson().toJson(giveBirth));
            Intent i = new Intent(context, GiveBirthDetailActivity.class);
            i.putExtra("parent_data", new Gson().toJson(pregnancy));
            i.putExtra("data", new Gson().toJson(giveBirth));
            startActivity(i);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    private void backFunction(){
        if(editMode){
            Intent intent = new Intent(context, GiveBirthDetailActivity.class);
            intent.putExtra("parent_data", new Gson().toJson(pregnancy));
            intent.putExtra("data", new Gson().toJson(giveBirth));
            showWarning(intent, R.string.warning, R.string.warning_edit_give_birth, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_give_birth, R.string.ok, R.string.cancel);
        }
    }

    ApiCallback motherConditionCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            ApiData<MotherCondition> motherConditionApiData =
                    new Gson().fromJson(result, new TypeToken<ApiData<MotherCondition>>(){}.getType());
            AppLog.d(new Gson().toJson(motherConditionApiData));
            motherConditionSpinAdapter = new SpinAdapter<MotherCondition>(context,
                    android.R.layout.simple_spinner_item, motherConditionApiData.getData());
            spMotherCondition.setAdapter(motherConditionSpinAdapter);
            if(editMode){
                for(int i=0;i<motherConditionApiData.getData().size();i++){
                    if(motherConditionApiData.getData().get(i).getId()==giveBirth.getMother_condition_id()){
                        spMotherCondition.setSelection(i);
                        break;
                    }
                }
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {

        }
    };

    ApiCallback birthWayCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            ApiData<BirthWay> birthWayApiData =
                    new Gson().fromJson(result, new TypeToken<ApiData<BirthWay>>(){}.getType());
            AppLog.d(new Gson().toJson(birthWayApiData));
            birthWaySpinAdapter = new SpinAdapter<BirthWay>(context,
                    android.R.layout.simple_spinner_item, birthWayApiData.getData());
            spBirthWay.setAdapter(birthWaySpinAdapter);
            spBirthWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 1:
                            Log.d("satu", ""+spBirthWay.getItemAtPosition(position));
                            lnTreatment.setVisibility(View.VISIBLE);
                            if (giveBirth.getTreatment() != null) etTreatment.setText(giveBirth.getTreatment());
                            break;
                        case 0:
                            Log.d("nol", ""+spBirthWay.getItemAtPosition(position));
                            lnTreatment.setVisibility(View.GONE);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    etTreatment.setText(giveBirth.getTreatment());
                }
            });
            if(editMode){
                for(int i=0;i<birthWayApiData.getData().size();i++){
                    if(birthWayApiData.getData().get(i).getName().equalsIgnoreCase(giveBirth.getBirth_way_id())){
                        spBirthWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position){
                                    case 1:
                                        Log.d("satu", ""+spBirthWay.getItemAtPosition(position));
                                        lnTreatment.setVisibility(View.VISIBLE);
                                        if (giveBirth.getTreatment() != null) etTreatment.setText(giveBirth.getTreatment());
                                        break;
                                    case 0:
                                        Log.d("default", ""+spBirthWay.getItemAtPosition(position));
                                        lnTreatment.setVisibility(View.GONE);
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        spBirthWay.setSelection(i);
                        break;
                    }
                }
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {

        }
    };
}
