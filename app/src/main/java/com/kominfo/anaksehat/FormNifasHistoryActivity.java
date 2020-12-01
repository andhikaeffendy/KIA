package com.kominfo.anaksehat;

import android.app.TimePickerDialog;
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
import com.kominfo.anaksehat.models.NifasHistory;
import com.kominfo.anaksehat.models.NifasHistoryType;
import com.kominfo.anaksehat.models.Pregnancy;

import java.util.Calendar;

public class FormNifasHistoryActivity extends BaseActivity {

    private EditText etBirthDate,etMotherCondition,etBloodTempTespiration,etPerineum,
            etBloodingPervaginam,etInfection,etUteri,etFundusUteri,etLokhia,etSuggestion,
            etBirthCanal,etBreast,etAsi,etVitaminA,etKontrasepsi,etHighRisk,etBab,
            etBak, etGoodFood, etDrink, etCleanliness, etCaesarTakeCare, etTakeARest,
            etBreastfeeding, etBabyTreatment, etBabyCry, etBabyCommunication, etKbConsultation;
    private Spinner spNifasHistoryType;
    private ImageView ivDate;
    private GiveBirth giveBirth;
    private NifasHistory nifasHistory;
    private SpinAdapter<NifasHistoryType> nifasHistoryTypeSpinAdapter;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_nifas_history);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Input Data Persalinan");

        etBirthDate = findViewById(R.id.history_date);
        ivDate = findViewById(R.id.history_icon);
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
        spNifasHistoryType = findViewById(R.id.nifas_history_type_id);

        giveBirth = new Gson().fromJson(getIntent().getStringExtra("data"), GiveBirth.class);

        setTitle("Catatan Hasil Pelayanan Ibu Nifas");

        etBirthDate.setInputType(InputType.TYPE_NULL);
        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.minDate = giveBirth.getBirth_date();
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

        mApiService.getNifasHistoryTypes().enqueue(nifasHistoryTypesCallback.getCallback());

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
        nifasHistory = new Gson().fromJson(getIntent().getStringExtra("edit_data"), NifasHistory.class);
        setTitle("Edit Data Catatan Hasil Pelayanan Ibu Nifas");

        etBirthDate.setText(DateHelper.getDateServer(nifasHistory.getHistory_date()));
        etMotherCondition.setText(nifasHistory.getMother_condition());
        etBloodTempTespiration.setText(nifasHistory.getBlood_temp_respiration());
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
    }

    private boolean validateData(){
        String birthDate = etBirthDate.getText().toString();

        if(birthDate.isEmpty()){
            etBirthDate.setError("Tanggal Persalinan harus diisi");
            etBirthDate.requestFocus();
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
        NifasHistoryType selected = (NifasHistoryType) spNifasHistoryType.getSelectedItem();
        long nifasHistoryTypeId = selected.getId();
        long giveBirthId = giveBirth.getId();

        String birtDate = etBirthDate.getText().toString();
        String motherCondition = etMotherCondition.getText().toString();
        String bloodTempTespiration = etBloodTempTespiration.getText().toString();
        String perineum = etPerineum.getText().toString();
        String bloodingPervaginam = etBloodingPervaginam.getText().toString();
        String infection = etInfection.getText().toString();
        String uteri = etUteri.getText().toString();
        String fundusUteri = etFundusUteri.getText().toString();
        String lokhia = etLokhia.getText().toString();
        String suggestion = etSuggestion.getText().toString();
        String birthCanal = etBirthCanal.getText().toString();
        String breast = etBreast.getText().toString();
        String asi = etAsi.getText().toString();
        String vitaminA = etVitaminA.getText().toString();
        String kontrasepsi = etKontrasepsi.getText().toString();
        String highRisk = etHighRisk.getText().toString();
        String bab = etBab.getText().toString();
        String bak = etBak.getText().toString();
        String goodFood = etGoodFood.getText().toString();
        String drink = etDrink.getText().toString();
        String cleanliness = etCleanliness.getText().toString();
        String caesarTakeCare = etCaesarTakeCare.getText().toString();
        String takeARest = etTakeARest.getText().toString();
        String breastfeeding = etBreastfeeding.getText().toString();
        String babyTreatment = etBabyTreatment.getText().toString();
        String babyCry = etBabyCry.getText().toString();
        String babyCommunication = etBabyCommunication.getText().toString();
        String kbConsultation = etKbConsultation.getText().toString();

        showProgressBar(true);
        if(editMode){
            long id = nifasHistory.getId();
            mApiService.updateNifasHistory(id,id,appSession.getData(AppSession.TOKEN),giveBirthId,
                    birtDate,nifasHistoryTypeId,motherCondition,bloodTempTespiration,
                    bloodingPervaginam,perineum,infection,uteri,fundusUteri,lokhia,birthCanal,breast,
                    asi,vitaminA,kontrasepsi,highRisk,bab,bak,goodFood,drink,cleanliness,takeARest,
                    caesarTakeCare,breastfeeding,babyTreatment,babyCry,babyCommunication,kbConsultation)
                    .enqueue(formCallback.getCallback());
        } else
            mApiService.createNifasHistory(appSession.getData(AppSession.TOKEN), giveBirthId,
                    birtDate,nifasHistoryTypeId,motherCondition,bloodTempTespiration,
                    bloodingPervaginam,perineum,infection,uteri,fundusUteri,lokhia,birthCanal,breast,
                    asi,vitaminA,kontrasepsi,highRisk,bab,bak,goodFood,drink,cleanliness,takeARest,
                    caesarTakeCare,breastfeeding,babyTreatment,babyCry,babyCommunication,kbConsultation)
                    .enqueue(formCallback.getCallback());
    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                nifasHistory = gson.fromJson(result, NifasHistory.class);
                AppLog.d(new Gson().toJson(nifasHistory));
                Intent i = new Intent(context, NifasHistoryDetailActivity.class);
                i.putExtra("parent_data", new Gson().toJson(giveBirth));
                i.putExtra("data", new Gson().toJson(nifasHistory));
                startActivity(i);
                finish();
            } else {
                mApiService.getNifasHistory(nifasHistory.getId(), appSession.getData(AppSession.TOKEN))
                        .enqueue(nifasHistoryCallback.getCallback());
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

    ApiCallback nifasHistoryCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            nifasHistory = gson.fromJson(result, NifasHistory.class);
            AppLog.d(new Gson().toJson(nifasHistory));
            Intent i = new Intent(context, NifasHistoryDetailActivity.class);
            i.putExtra("parent_data", new Gson().toJson(giveBirth));
            i.putExtra("data", new Gson().toJson(nifasHistory));
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
            Intent intent = new Intent(context, GiveBirthDetailActivity.class);
            intent.putExtra("parent_data", new Gson().toJson(giveBirth));
            intent.putExtra("data", new Gson().toJson(nifasHistory));
            showWarning(intent, R.string.warning, R.string.warning_edit_give_birth, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_give_birth, R.string.ok, R.string.cancel);
        }
    }

    ApiCallback nifasHistoryTypesCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            ApiData<NifasHistoryType> nifasHistoryTypeApiData =
                    new Gson().fromJson(result, new TypeToken<ApiData<NifasHistoryType>>(){}.getType());
            AppLog.d(new Gson().toJson(nifasHistoryTypeApiData));
            nifasHistoryTypeSpinAdapter = new SpinAdapter<NifasHistoryType>(context,
                    android.R.layout.simple_spinner_item, nifasHistoryTypeApiData.getData());
            spNifasHistoryType.setAdapter(nifasHistoryTypeSpinAdapter);
            if(editMode){
                for(int i=0;i<nifasHistoryTypeApiData.getData().size();i++){
                    if(nifasHistoryTypeApiData.getData().get(i).getId()==nifasHistory.getNifas_history_type_id()){
                        spNifasHistoryType.setSelection(i);
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
