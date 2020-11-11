package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.ShowcaseHelper;
import com.kominfo.anaksehat.models.Pregnancy;
import com.kominfo.anaksehat.models.PregnancyHistory;

public class FormPregnancyHistoryActivity extends BaseActivity {

    private EditText etHistoryDate,etWeight,etBabyWeight,etNote,
            etBloodPressureTop,etBloodPressureBottom;
    private RadioButton rbGenderMale,rbGenderFemale,rbAmnioticCondition,rbAmnioticCondition2;
    private ImageView ivDate;
    private Pregnancy pregnancy;
    private boolean editMode = false;
    private PregnancyHistory pregnancyHistory;
    private ShowcaseHelper showcaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pregnancy_history);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_create_pregnancy_history);

        etHistoryDate = findViewById(R.id.history_date);
        etBabyWeight = findViewById(R.id.baby_weight);
        etWeight = findViewById(R.id.weight);
        rbAmnioticCondition = findViewById(R.id.amniotic_condition);
        rbAmnioticCondition2 = findViewById(R.id.amniotic_condition_2);
        etBloodPressureTop = findViewById(R.id.blood_pressure_top);
        etBloodPressureBottom = findViewById(R.id.blood_pressure_bottom);
        etNote = findViewById(R.id.note);
        rbGenderMale = findViewById(R.id.gender_prediction);
        rbGenderFemale = findViewById(R.id.gender_prediction_female);
        ivDate = findViewById(R.id.history_icon);

        pregnancy = new Gson().fromJson(getIntent().getStringExtra("data"), Pregnancy.class);

        setTitle(pregnancy.getName());

        etHistoryDate.setInputType(InputType.TYPE_NULL);
        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.minDate = pregnancy.getLast_period_date();
                newFragment.holder = etHistoryDate;
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        etHistoryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivDate.performClick();
            }
        });

        showcaseHelper = new ShowcaseHelper(context, ShowcaseHelper.FORM_PREGNANCY_HISTORY_ID);

        editMode = getIntent().getBooleanExtra("edit_mode", false);
        if(editMode) initEditData();

        startShowcase();
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
        pregnancyHistory = new Gson().fromJson(getIntent().getStringExtra("edit_data"), PregnancyHistory.class);
        setTitle(R.string.title_edit_pregnancy_history);

        etHistoryDate.setText(DateHelper.getDateServer(pregnancyHistory.getHistory_date()));
        etBabyWeight.setText(replaceDotToComma(""+pregnancyHistory.getBaby_weight()));
        etWeight.setText(replaceDotToComma(""+pregnancyHistory.getWeight()));
        if(pregnancyHistory.getAmniotic_condition().compareToIgnoreCase("Kurang Baik")==0)
            rbAmnioticCondition2.setChecked(true);
        etBloodPressureTop.setText(""+pregnancyHistory.getBlood_pressure_top());
        etBloodPressureBottom.setText(""+pregnancyHistory.getBlood_pressure_bottom());
        etNote.setText(pregnancyHistory.getNote());
        if(pregnancyHistory.getGender_prediction().compareToIgnoreCase("Female")==0)
            rbGenderFemale.setChecked(true);
    }

    private boolean validateData(){
        String historyDate = etHistoryDate.getText().toString();
        String baby_weight = etBabyWeight.getText().toString();
        String weight = etWeight.getText().toString();
        String blood_pressure_top = etBloodPressureTop.getText().toString();
        String blood_pressure_bottom = etBloodPressureBottom.getText().toString();

        if(historyDate.isEmpty()){
            etHistoryDate.setError(getString(R.string.error_birth_date));
            etHistoryDate.requestFocus();
            return false;
        }
        if(baby_weight.isEmpty()){
            etBabyWeight.setError(getString(R.string.error_baby_weight));
            etBabyWeight.requestFocus();
            return false;
        }
        if(weight.isEmpty()){
            etWeight.setError(getString(R.string.error_weight));
            etWeight.requestFocus();
            return false;
        }
        if(blood_pressure_top.isEmpty()){
            etBloodPressureTop.setError(getString(R.string.error_blood_pressure_top));
            etBloodPressureTop.requestFocus();
            return false;
        }
        if(blood_pressure_bottom.isEmpty()){
            etBloodPressureBottom.setError(getString(R.string.error_blood_pressure_bottom));
            etBloodPressureBottom.requestFocus();
            return false;
        }
        return true;
    }

    public void onClickForm(View v){
        switch (v.getId()){
            case R.id.add:
                startActivity(new Intent(context, FormPregnancyActivity.class));
                finish();
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
        String historyDate = etHistoryDate.getText().toString();
        double baby_weight = Double.parseDouble(replaceCommaToDot(etBabyWeight.getText().toString()));
        double weight = Double.parseDouble(replaceCommaToDot(etWeight.getText().toString()));
        int blood_pressure_top = Integer.parseInt(etBloodPressureTop.getText().toString());
        int blood_pressure_bottom = Integer.parseInt(etBloodPressureBottom.getText().toString());
//        String note = etNote.getText().toString();
        long pregnancyId = pregnancy.getId();
        String genderPrediction = "Male";
        int amniotic_condition = 1;

        if(!rbGenderMale.isChecked())genderPrediction="Female";
        if(!rbAmnioticCondition.isChecked())amniotic_condition=0;

        showProgressBar(true);
        if(editMode){
            long id = pregnancyHistory.getId();
            mApiService.updatePregnancyHistory(id, id, appSession.getData(AppSession.TOKEN),
                    historyDate, weight, blood_pressure_top, blood_pressure_bottom, baby_weight,
                    genderPrediction, amniotic_condition, null, pregnancyId)
                    .enqueue(formCallback.getCallback());
        } else
            mApiService.createPregnancyHistory(appSession.getData(AppSession.TOKEN), historyDate,
                    weight, blood_pressure_top, blood_pressure_bottom, baby_weight,
                    genderPrediction, amniotic_condition, null, pregnancyId).enqueue(formCallback.getCallback());
    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                pregnancyHistory = gson.fromJson(result, PregnancyHistory.class);
            }
            mApiService.getPregnancyHistory(pregnancyHistory.getId(), appSession.getData(AppSession.TOKEN))
                    .enqueue(pregnancyHistoryCallback.getCallback());
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

    ApiCallback pregnancyHistoryCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            PregnancyHistory pregnancyHistory = gson.fromJson(result, PregnancyHistory.class);
            AppLog.d(new Gson().toJson(pregnancyHistory));
            Intent i = new Intent(context, PregnancyHistoryDetailActivity.class);
            i.putExtra("parent_data", new Gson().toJson(pregnancy));
            i.putExtra("data", new Gson().toJson(pregnancyHistory));
            startActivity(i);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private void backFunction(){
        if(editMode){
            Intent intent = new Intent(context, PregnancyHistoryDetailActivity.class);
            intent.putExtra("parent_data", new Gson().toJson(pregnancy));
            intent.putExtra("data", new Gson().toJson(pregnancyHistory));
            showWarning(intent, R.string.warning, R.string.warning_edit_pregnancy_history, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_pregnancy_history, R.string.ok, R.string.cancel);
        }
    }

    private void startShowcase(){
        showcaseHelper.addShowcaseView(etHistoryDate, getString(R.string.guide_form_pregnancy_history_date));
        showcaseHelper.addShowcaseView(etWeight, getString(R.string.guide_form_pregnancy_history_weight));
        showcaseHelper.addShowcaseView(etBabyWeight, getString(R.string.guide_form_pregnancy_history_baby_weight));
        showcaseHelper.addShowcaseView(findViewById(R.id.layout_amniotic), getString(R.string.guide_form_pregnancy_history_amniotic));
        showcaseHelper.addShowcaseView(findViewById(R.id.layout_gender), getString(R.string.guide_form_pregnancy_history_gender));
        showcaseHelper.addShowcaseView(findViewById(R.id.layout_pressure), getString(R.string.guide_form_pregnancy_history_pressure));
        showcaseHelper.startGuide();
    }
}
