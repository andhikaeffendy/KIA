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
import com.kominfo.anaksehat.models.GiveBirth;
import com.kominfo.anaksehat.models.NifasHistory;

public class NifasHistoryDetailActivity extends BaseActivity {

    private TextView etBirthDate,etMotherCondition,etBloodTempTespiration,etPerineum,
            etBloodingPervaginam,etInfection,etUteri,etFundusUteri,etLokhia,etSuggestion,
            etBirthCanal,etBreast,etAsi,etVitaminA,etKontrasepsi,etHighRisk,etBab,
            etBak, etGoodFood, etDrink, etCleanliness, etCaesarTakeCare, etTakeARest,
            etBreastfeeding, etBabyTreatment, etBabyCry, etBabyCommunication, etKbConsultation,
            etNifasHistoryType, tvBloodPressureTop, tvBloodPressureBottom, tvTemperature, tvRespiration, tvPulse;
    private GiveBirth giveBirth;
    private NifasHistory nifasHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nifas_history_detail);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Riwayat Pemeriksaan Nifas");

        tvBloodPressureTop = findViewById(R.id.tv_blood_pressure_top);
        tvBloodPressureBottom = findViewById(R.id.tv_blood_pressure_bottom);
        tvTemperature = findViewById(R.id.tv_temp);
        tvRespiration = findViewById(R.id.tv_respiration);
        tvPulse = findViewById(R.id.tv_pulse);
        etBirthDate = findViewById(R.id.history_date);
        etMotherCondition = findViewById(R.id.mother_condition);
        etBloodTempTespiration = findViewById(R.id.blood_temp_respiration);
        etPerineum = findViewById(R.id.perineum);
        etBloodingPervaginam = findViewById(R.id.blooding_pervaginam);
        etInfection = findViewById(R.id.infection);
        etUteri = findViewById(R.id.uteri);
        etFundusUteri = findViewById(R.id.fundus_uteri);
        etLokhia = findViewById(R.id.lokhia);
        etSuggestion = findViewById(R.id.suggestion);
        etBirthCanal = findViewById(R.id.birth_canal);
        etBreast = findViewById(R.id.breast);
        etAsi = findViewById(R.id.asi);
        etVitaminA = findViewById(R.id.vitamin_a);
        etKontrasepsi = findViewById(R.id.kontrasepsi);
        etHighRisk = findViewById(R.id.high_risk);
        etBab = findViewById(R.id.bab);
        etBak = findViewById(R.id.bak);
        etGoodFood = findViewById(R.id.good_food);
        etDrink = findViewById(R.id.drink);
        etCleanliness = findViewById(R.id.cleanliness);
        etCaesarTakeCare = findViewById(R.id.caesar_take_care);
        etTakeARest = findViewById(R.id.take_a_rest);
        etBreastfeeding = findViewById(R.id.breastfeeding);
        etBabyTreatment = findViewById(R.id.baby_treatment);
        etBabyCry = findViewById(R.id.baby_cry);
        etBabyCommunication = findViewById(R.id.baby_communication);
        etKbConsultation = findViewById(R.id.kb_consultation);
        etNifasHistoryType = findViewById(R.id.nifas_history_type_id);

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
                Intent i = new Intent(context, FormNifasHistoryActivity.class);
                i.putExtra("data", new Gson().toJson(giveBirth));
                i.putExtra("edit_data", new Gson().toJson(nifasHistory));
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
        giveBirth = new Gson().fromJson(getIntent().getStringExtra("parent_data"), GiveBirth.class);
        nifasHistory = new Gson().fromJson(getIntent().getStringExtra("data"), NifasHistory.class);

        etBirthDate.setText(DateHelper.getDateServer(nifasHistory.getHistory_date()));
        etMotherCondition.setText(nifasHistory.getMother_condition());
//        etBloodTempTespiration.setText(nifasHistory.getBlood_temp_respiration());
        etPerineum.setText(nifasHistory.getPerineum());
        etBloodingPervaginam.setText(nifasHistory.getBlooding_pervaginam());
        etInfection.setText(nifasHistory.getInfection());
        etUteri.setText(nifasHistory.getUteri());
        etFundusUteri.setText(nifasHistory.getFundus_uteri());
        etLokhia.setText(nifasHistory.getLokhia());
        etSuggestion.setText(nifasHistory.getSuggestion());
        etBirthCanal.setText(nifasHistory.getBirth_canal());
        etBreast.setText(nifasHistory.getBreast());
        etAsi.setText(nifasHistory.getAsi());
        etVitaminA.setText(nifasHistory.getVitamin_a());
        etKontrasepsi.setText(nifasHistory.getKontrasepsi());
        etHighRisk.setText(nifasHistory.getHigh_risk());
        etBab.setText(nifasHistory.getBab());
        etBak.setText(nifasHistory.getBak());
        etGoodFood.setText(nifasHistory.getGood_food());
        etDrink.setText(nifasHistory.getDrink());
        etCleanliness.setText(nifasHistory.getCleanliness());
        etCaesarTakeCare.setText(nifasHistory.getCaesar_take_care());
        etTakeARest.setText(nifasHistory.getTake_a_rest());
        etBreastfeeding.setText(nifasHistory.getBreastfeeding());
        etBabyTreatment.setText(nifasHistory.getBaby_treatment());
        etBabyCry.setText(nifasHistory.getBaby_cry());
        etBabyCommunication.setText(nifasHistory.getBaby_communication());
        etKbConsultation.setText(nifasHistory.getKb_consultation());
        etNifasHistoryType.setText(nifasHistory.getNifas_history_type_name());
        tvBloodPressureTop.setText("" + nifasHistory.getBlood_pressure_top());
        tvBloodPressureBottom.setText("" + nifasHistory.getBlood_pressure_top());
        tvTemperature.setText(replaceCommaToDot("" + nifasHistory.getTemp()));
        tvRespiration.setText(replaceCommaToDot("" + nifasHistory.getRespiration()));
        tvPulse.setText(replaceCommaToDot("" + nifasHistory.getPulse()));
    }
}
