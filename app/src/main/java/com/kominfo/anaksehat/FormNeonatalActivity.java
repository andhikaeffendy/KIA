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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.adapters.SpinAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.Neonatal;
import com.kominfo.anaksehat.models.NeonatalVisitType;

public class FormNeonatalActivity extends BaseActivity {

    private EditText etHistoryDate,etWeight,etHeight,etTemperature,etRespiratory,
        etHeartBeat,etInfection,etIkterus,etDiare,etLowWeight,etKVitamin, etHbBcgPolio,etShk,
        etShkConfirmation,etTreatment;
    private ImageView ivDate;
    private Spinner spNeonatalVisitType;
    private SpinAdapter<NeonatalVisitType> neonatalVisitTypeSpinAdapter;
    private Child child;
    private boolean editMode = false;
    private Neonatal neonatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_neonatal);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_create_child_history);

        etHistoryDate = findViewById(R.id.history_date);
        ivDate = findViewById(R.id.history_icon);
        etWeight = findViewById(R.id.weight);
        etHeight = findViewById(R.id.height);
        etTemperature = findViewById(R.id.temperature);
        etRespiratory = findViewById(R.id.respiratory);
        etHeartBeat = findViewById(R.id.heart_beat);
        etInfection = findViewById(R.id.infection);
        etIkterus = findViewById(R.id.ikterus);
        etDiare = findViewById(R.id.diare);
        etLowWeight = findViewById(R.id.low_weight);
        etKVitamin = findViewById(R.id.k_vitamin);
        etHbBcgPolio = findViewById(R.id.hb_bcg_polio);
        etShk = findViewById(R.id.shk);
        etShkConfirmation = findViewById(R.id.shk_confirmation);
        etTreatment = findViewById(R.id.treatment);
        spNeonatalVisitType = findViewById(R.id.neonatal_visit_type_id);

        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);

        setTitle(child.getName());

        etHistoryDate.setInputType(InputType.TYPE_NULL);
        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.minDate = child.getBirth_date();
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

        mApiService.getNeonatalVisitTypes().enqueue(neonatalVisitTypesCallback.getCallback());

        editMode = getIntent().getBooleanExtra("edit_mode", false);
        if(editMode) initEditData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.children, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickMenu(item.getItemId());
        return true;
    }

    public void onClickMenu(int resId){
        switch (resId){
            case R.id.add:
                startActivity(new Intent(context, FormChildActivity.class));
                finish();
                break;
            case android.R.id.home:
                backFunction();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initEditData(){
        neonatal = new Gson().fromJson(getIntent().getStringExtra("edit_data"), Neonatal.class);
        setTitle("Edit Neonatal");

        etHistoryDate.setText(DateHelper.getDateServer(neonatal.getHistory_date()));
        etWeight.setText(""+neonatal.getWeight());
        etHeight.setText(replaceDotToComma(""+neonatal.getHeight()));
        etTemperature.setText(replaceDotToComma(""+neonatal.getTemperature()));
        etRespiratory.setText(""+neonatal.getRespiratory());
        etHeartBeat.setText(""+neonatal.getHeart_beat());
        etInfection.setText(neonatal.getInfection());
        etIkterus.setText(neonatal.getIkterus());
        etDiare.setText(neonatal.getDiare());
        etLowWeight.setText(neonatal.getLow_weight());
        etKVitamin.setText(neonatal.getK_vitamin());
        etHbBcgPolio.setText(neonatal.getHb_bcg_polio());
        etShk.setText(neonatal.getShk());
        etShkConfirmation.setText(neonatal.getShk_confirmation());
        etTreatment.setText(neonatal.getTreatment());
    }

    private boolean validateData(){
        String historyDate = etHistoryDate.getText().toString();

        if(historyDate.isEmpty()){
            etHistoryDate.setError(getString(R.string.error_birth_date));
            etHistoryDate.requestFocus();
            return false;
        }
        return true;
    }

    public void onClickForm(View v){
        switch (v.getId()){
            case R.id.add:
//                startActivity(new Intent(context, FormChildActivity.class));
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
        String historyDate = etHistoryDate.getText().toString();
        long childId = child.getId();
        double weight = Double.parseDouble(replaceCommaToDot(etWeight.getText().toString()));
        double temperature = Double.parseDouble(replaceCommaToDot(etTemperature.getText().toString()));
        int height = Integer.parseInt(etHeight.getText().toString());
        String respiratory = etRespiratory.getText().toString();
        String heartBeat = etHeartBeat.getText().toString();
        String infection = etInfection.getText().toString();
        String ikterus = etIkterus.getText().toString();
        String diare = etDiare.getText().toString();
        String low_weight = etLowWeight.getText().toString();
        String kVitaminetHbBcgPolio = etKVitamin.getText().toString();
        String hbBcgPolio = etHbBcgPolio.getText().toString();
        String shk = etShk.getText().toString();
        String shkConfirmation = etShkConfirmation.getText().toString();
        String treatment = etTreatment.getText().toString();
        NeonatalVisitType selected = (NeonatalVisitType) spNeonatalVisitType.getSelectedItem();
        long neonatalVisitTypeId = selected.getId();

        showProgressBar(true);
        if(editMode){
            long id = neonatal.getId();
            mApiService.updateNeonatal(id, id, appSession.getData(AppSession.TOKEN), child.getId(),
                    neonatalVisitTypeId, historyDate, weight, height, temperature, respiratory,
                    heartBeat, infection, ikterus, diare, low_weight, kVitaminetHbBcgPolio,
                    hbBcgPolio, shk, shkConfirmation,treatment).enqueue(formCallback.getCallback());
        } else
            mApiService.createNeonatal(appSession.getData(AppSession.TOKEN),child.getId(),
                    neonatalVisitTypeId, historyDate, weight, height, temperature, respiratory,
                    heartBeat, infection, ikterus, diare, low_weight, kVitaminetHbBcgPolio,
                    hbBcgPolio, shk, shkConfirmation,treatment).enqueue(formCallback.getCallback());
    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                neonatal = gson.fromJson(result, Neonatal.class);
            }
            mApiService.getNeonatal(neonatal.getId(), appSession.getData(AppSession.TOKEN))
                    .enqueue(neonatalCallback.getCallback());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback neonatalCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            neonatal = gson.fromJson(result, Neonatal.class);
            Intent intent = new Intent(context, DetailNeonatalActivity.class);
            intent.putExtra("data", new Gson().toJson(neonatal));
            startActivity(intent);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        backFunction();
    }

    private void backFunction(){
        if(editMode){
            Intent intent = new Intent(context, DetailNeonatalActivity.class);
            intent.putExtra("parent_data", new Gson().toJson(child));
            intent.putExtra("data", new Gson().toJson(neonatal));
            showWarning(intent, R.string.warning, R.string.warning_edit_neonatal, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_neonatal, R.string.ok, R.string.cancel);
        }
    }

    ApiCallback neonatalVisitTypesCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            ApiData<NeonatalVisitType> neonatalVisitTypeApiData =
                    new Gson().fromJson(result, new TypeToken<ApiData<NeonatalVisitType>>(){}.getType());
            AppLog.d(new Gson().toJson(neonatalVisitTypeApiData));
            neonatalVisitTypeSpinAdapter = new SpinAdapter<NeonatalVisitType>(context,
                    android.R.layout.simple_spinner_item, neonatalVisitTypeApiData.getData());
            spNeonatalVisitType.setAdapter(neonatalVisitTypeSpinAdapter);
            if(editMode){
                for(int i=0;i<neonatalVisitTypeApiData.getData().size();i++){
                    if(neonatalVisitTypeApiData.getData().get(i).getName().equalsIgnoreCase(neonatal.getNeonatal_visit_type())){
                        spNeonatalVisitType.setSelection(i);
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
