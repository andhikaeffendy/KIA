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
import android.widget.Toast;

import com.google.gson.Gson;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.Pregnancy;
import com.kominfo.anaksehat.models.BirthPlanning;

import java.util.Calendar;

public class FormBirthPlanningActivity extends BaseActivity {

    private EditText etMeetingDate,etBirthPlanningDate,etLocation,etBirthPlanningPlace,
            etBirthHelperMother,etBirthHelperFamily,etDangerNotice,etBirthNotice,
            etTransportationProblem,etMotherKeeper,etCostProblem,etBloodGiver,etBirthPartner,
            etChildrenKeeper,etKbMethod,etHelperDiscussion,etHomeCondition,etHomeEquipment;
    private ImageView ivDate, ivDate2;
    private Pregnancy pregnancy;
    private BirthPlanning birthPlanning;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_birth_planning);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Input Data Perencanaan Persalinan");

        etMeetingDate = findViewById(R.id.meeting_date);
        ivDate = findViewById(R.id.meeting_date_icon);
        etBirthPlanningDate = findViewById(R.id.birth_planning_date);
        ivDate2 = findViewById(R.id.birth_planning_date_icon);
        etLocation = findViewById(R.id.location);
        etBirthPlanningPlace = findViewById(R.id.birth_planning_place);
        etBirthHelperMother = findViewById(R.id.birth_helper_mother);
        etBirthHelperFamily = findViewById(R.id.birth_helper_family);
        etDangerNotice = findViewById(R.id.danger_notice);
        etBirthNotice = findViewById(R.id.birth_notice);
        etTransportationProblem = findViewById(R.id.transportation_problem);
        etMotherKeeper = findViewById(R.id.mother_keeper);
        etCostProblem = findViewById(R.id.cost_problem);
        etBloodGiver = findViewById(R.id.blood_giver);
        etBirthPartner = findViewById(R.id.birth_partner);
        etChildrenKeeper = findViewById(R.id.children_keeper);
        etKbMethod = findViewById(R.id.kb_method);
        etHelperDiscussion = findViewById(R.id.helper_discussion);
        etHomeCondition = findViewById(R.id.home_condition);
        etHomeEquipment = findViewById(R.id.home_equipment);

        pregnancy = new Gson().fromJson(getIntent().getStringExtra("data"), Pregnancy.class);

        etMeetingDate.setInputType(InputType.TYPE_NULL);
        etBirthPlanningDate.setInputType(InputType.TYPE_NULL);
        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.minDate = pregnancy.getLast_period_date();
                newFragment.holder = etMeetingDate;
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        etMeetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivDate.performClick();
            }
        });
        ivDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.minDate = pregnancy.getLast_period_date();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 10);
                newFragment.maxDate = cal.getTime();
                newFragment.holder = etBirthPlanningDate;
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        etBirthPlanningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivDate2.performClick();
            }
        });

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
        birthPlanning = new Gson().fromJson(getIntent().getStringExtra("edit_data"), BirthPlanning.class);
        setTitle("Edit Data Perencanaan Persalinan");

        etMeetingDate.setText(DateHelper.getDateServer(birthPlanning.getMeeting_date()));
        etBirthPlanningDate.setText(DateHelper.getDateServer(birthPlanning.getBirth_planning_date()));
        etLocation.setText(birthPlanning.getLocation());
        etBirthPlanningPlace.setText(birthPlanning.getBirth_planning_place());
        etBirthHelperMother.setText(birthPlanning.getBirth_helper_mother());
        etBirthHelperFamily.setText(birthPlanning.getBirth_helper_family());
        etDangerNotice.setText(birthPlanning.getDanger_notice());
        etBirthNotice.setText(birthPlanning.getBirth_notice());
        etTransportationProblem.setText(birthPlanning.getTransportation_problem());
        etMotherKeeper.setText(birthPlanning.getMother_keeper());
        etCostProblem.setText(birthPlanning.getCost_problem());
        etBloodGiver.setText(birthPlanning.getBlood_giver());
        etBirthPartner.setText(birthPlanning.getBirth_partner());
        etChildrenKeeper.setText(birthPlanning.getChildren_keeper());
        etKbMethod.setText(birthPlanning.getKb_method());
        etHelperDiscussion.setText(birthPlanning.getHelper_discussion());
        etHomeCondition.setText(birthPlanning.getHome_condition());
        etHomeEquipment.setText(birthPlanning.getHome_equipment());
    }

    private boolean validateData(){
        String birthDate = etMeetingDate.getText().toString();

        if(birthDate.isEmpty()){
            etMeetingDate.setError("Tanggal Perencanaan Persalinan harus diisi");
            etMeetingDate.requestFocus();
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
        long pregnancyId = pregnancy.getId();
        long motherId = pregnancy.getMother_id();

        String meetindDate = etMeetingDate.getText().toString();
        String birthPlanningDate = etBirthPlanningDate.getText().toString();
        String location = etLocation.getText().toString();
        String birthPlanningPlace = etBirthPlanningPlace.getText().toString();
        String birthHelperMother = etBirthHelperMother.getText().toString();
        String birthHelperFamily = etBirthHelperFamily.getText().toString();
        String dangerNotice = etDangerNotice.getText().toString();
        String birthNotice = etBirthNotice.getText().toString();
        String transportationProblem = etTransportationProblem.getText().toString();
        String motherKeeper = etMotherKeeper.getText().toString();
        String costProblem = etCostProblem.getText().toString();
        String bloodGiver = etBloodGiver.getText().toString();
        String birthPartner = etBirthPartner.getText().toString();
        String childrenKeeper = etChildrenKeeper.getText().toString();
        String kbMethod = etKbMethod.getText().toString();
        String helperDiscussion = etHelperDiscussion.getText().toString();
        String homeCondition = etHomeCondition.getText().toString();
        String homeEquipment = etHomeEquipment.getText().toString();

        showProgressBar(true);
        if(editMode){
            long id = birthPlanning.getId();
            mApiService.updateBirthPlanning(id,id,appSession.getData(AppSession.TOKEN),pregnancyId,
                    motherId,meetindDate,birthPlanningDate,location,birthPlanningPlace,
                    birthHelperMother,birthHelperFamily,birthNotice,dangerNotice,
                    transportationProblem,motherKeeper,costProblem,bloodGiver,birthPartner,
                    childrenKeeper,kbMethod,helperDiscussion,homeCondition,homeEquipment)
                    .enqueue(formCallback.getCallback());
        } else
            mApiService.createBirthPlanning(appSession.getData(AppSession.TOKEN), pregnancyId,
                    motherId,meetindDate,birthPlanningDate,location,birthPlanningPlace,
                    birthHelperMother,birthHelperFamily,birthNotice,dangerNotice,
                    transportationProblem,motherKeeper,costProblem,bloodGiver,birthPartner,
                    childrenKeeper,kbMethod,helperDiscussion,homeCondition,homeEquipment)
                    .enqueue(formCallback.getCallback());
    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                birthPlanning = gson.fromJson(result, BirthPlanning.class);
                AppLog.d(new Gson().toJson(birthPlanning));
                Intent i = new Intent(context, BirthPlanningDetailActivity.class);
                i.putExtra("parent_data", new Gson().toJson(pregnancy));
                i.putExtra("data", new Gson().toJson(birthPlanning));
                startActivity(i);
                finish();
            } else {
                mApiService.getBirthPlanning(birthPlanning.getId(), appSession.getData(AppSession.TOKEN))
                        .enqueue(birthPlanningCallback.getCallback());
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

    ApiCallback birthPlanningCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            birthPlanning = gson.fromJson(result, BirthPlanning.class);
            AppLog.d(new Gson().toJson(birthPlanning));
            Intent i = new Intent(context, BirthPlanningDetailActivity.class);
            i.putExtra("parent_data", new Gson().toJson(pregnancy));
            i.putExtra("data", new Gson().toJson(birthPlanning));
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
            Intent intent = new Intent(context, BirthPlanningDetailActivity.class);
            intent.putExtra("parent_data", new Gson().toJson(pregnancy));
            intent.putExtra("data", new Gson().toJson(birthPlanning));
            showWarning(intent, R.string.warning, R.string.warning_edit_birth_planning, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_birth_planning, R.string.ok, R.string.cancel);
        }
    }
}
