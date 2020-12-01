package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.Pregnancy;
import com.kominfo.anaksehat.models.BirthPlanning;

public class BirthPlanningDetailActivity extends BaseActivity {

    private TextView etMeetingDate,etBirthPlanningDate,etLocation,etBirthPlanningPlace,
            etBirthHelperMother,etBirthHelperFamily,etDangerNotice,etBirthNotice,
            etTransportationProblem,etMotherKeeper,etCostProblem,etBloodGiver,etBirthPartner,
            etChildrenKeeper,etKbMethod,etHelperDiscussion,etHomeCondition,etHomeEquipment;
    private Pregnancy pregnancy;
    private BirthPlanning birthPlanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_planning_detail);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Riwayat Perencanaan Persalinan");

        etMeetingDate = findViewById(R.id.meeting_date);
        etBirthPlanningDate = findViewById(R.id.birth_planning_date);
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

        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit, menu);
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
                finish();
                break;
            case R.id.edit:
                Intent i = new Intent(context, FormBirthPlanningActivity.class);
                i.putExtra("data", new Gson().toJson(pregnancy));
                i.putExtra("edit_data", new Gson().toJson(birthPlanning));
                i.putExtra("edit_mode", true);
                startActivity(i);
                finish();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initData(){
        pregnancy = new Gson().fromJson(getIntent().getStringExtra("parent_data"), Pregnancy.class);
        birthPlanning = new Gson().fromJson(getIntent().getStringExtra("data"), BirthPlanning.class);

        etMeetingDate.setText("Tanggal pertemuan : "+DateHelper.getDateServer(birthPlanning.getMeeting_date()));
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
}
