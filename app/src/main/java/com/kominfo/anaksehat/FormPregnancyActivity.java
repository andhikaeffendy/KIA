package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.ShowcaseHelper;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.Pregnancy;
import com.kominfo.anaksehat.models.User;

public class FormPregnancyActivity extends BaseActivity {

    private EditText etLastPeriodDate,etStartHeight,etStartWeight, etArmRound, etKekStatus, etKontrasepsi,
            etRiwayatPenyakit, etRiwayatAlergi, etPregnancyNumber, etBirthCount,
            etMisCariage, etGCount, etPCount, etACount, etChildrenCount, etDeadBirthCOunt,
            etPrematureChildrenCount, etDistance, etImmunizationStatus, etLastBirthHelper, etLastBirthWay,
            etLastGiveBirthDate;
    private Spinner spPeriodType;
    private AutoCompleteTextView actvMotherName;
    private Mother selectedMother;
    private User user;
    private boolean editMode = false;
    private Pregnancy pregnancy;
    private ImageView ivPickDate, ivLastGiveBirthDate;
    private ShowcaseHelper showcaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pregnancy);

        checkSession();
        user = getUserSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        setTitle(R.string.title_create_pregnancy);

        actvMotherName = findViewById(R.id.mother_name);
        spPeriodType = findViewById(R.id.period_type);
        etLastPeriodDate = findViewById(R.id.last_period_date);
        etStartHeight = findViewById(R.id.start_height);
        etStartWeight = findViewById(R.id.start_weight);
        ivPickDate = findViewById(R.id.birth_icon);
        etArmRound = findViewById(R.id.arm_round);
        etKekStatus = findViewById(R.id.kek_status);
        etKontrasepsi = findViewById(R.id.kontrasepsi);
        etRiwayatAlergi = findViewById(R.id.alergi_history);
        etRiwayatPenyakit = findViewById(R.id.desease_history);
        etPregnancyNumber = findViewById(R.id.pregnancy_number);
        etBirthCount = findViewById(R.id.birth_count);
        etMisCariage = findViewById(R.id.miscarriage_count);
        etGCount = findViewById(R.id.g_count);
        etPCount = findViewById(R.id.p_count);
        etACount = findViewById(R.id.a_count);
        etChildrenCount = findViewById(R.id.children_count);
        etDeadBirthCOunt = findViewById(R.id.dead_birth_count);
        etPrematureChildrenCount = findViewById(R.id.premature_children_count);
        etDistance = findViewById(R.id.distance);
        etImmunizationStatus = findViewById(R.id.immunization_status);
        etLastBirthHelper = findViewById(R.id.last_birth_helper);
        etLastBirthWay = findViewById(R.id.last_birth_way);
        etLastGiveBirthDate = findViewById(R.id.last_give_birth_date);
        ivLastGiveBirthDate = findViewById(R.id.last_give_birth_date_icon);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.period_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeriodType.setAdapter(adapter);

        etLastPeriodDate.setInputType(InputType.TYPE_NULL);
        etLastPeriodDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPickDate.performClick();
            }
        });
        etLastGiveBirthDate.setInputType(InputType.TYPE_NULL);
        etLastGiveBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivLastGiveBirthDate.performClick();
            }
        });
        etStartWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if(validateData())
                        submitData();
                    handled = true;
                }
                return handled;
            }
        });

        showcaseHelper = new ShowcaseHelper(context, ShowcaseHelper.FORM_PREGNANCY_ID);

        editMode = getIntent().getBooleanExtra("edit_mode", false);
        if(!editMode) {
            initCreateData();
        } else initEditData();

        //startShowcase();
    }

//    private void initCreateData(){
//
////        if(user.getPosyandu()==0&&user.getMotherId()>0){
////            selectedMother = new Mother();
////            selectedMother.setId(user.getMotherId());
////            actvMotherName.setVisibility(View.GONE);
////            actvMotherName.setText(selectedMother.getName());
////            Log.d("Ibu", "Nama ibu "+ selectedMother.getName());
////        } else
////            mApiService.getMothers(appSession.getData(AppSession.TOKEN))
////                    .enqueue(mothersCallback.getCallback());
//
//        if(user.getPosyandu()==0&&user.getMotherId()>0){
//            String mother = getIntent().getStringExtra("mother");
//            String motherBirthDate = getIntent().getStringExtra("birth_date");
//            selectedMother = new Mother();
//            selectedMother.setId(user.getId());
//            if (mother != null || !mother.isEmpty()) {
//                actvMotherName.setText(mother + "("+motherBirthDate+")");
//                actvMotherName.setEnabled(false);
//            }
////            actvMotherName.setVisibility(View.GONE);
//        }
//        mApiService.getMothers(appSession.getData(AppSession.TOKEN))
//                .enqueue(mothersCallback.getCallback());
//
//    }

    private void initCreateData(){
        if(user.getPosyandu()==0&&user.getMotherId()>0){
            selectedMother = new Mother();
            selectedMother.setId(user.getMotherId());
//            actvMotherName.setVisibility(View.GONE);
        } else {
            String mother = getIntent().getStringExtra("mother");
            String motherBirthDate = getIntent().getStringExtra("birth_date");
            long motherId = getIntent().getLongExtra("mother_id", 0);
            if (motherId > 0) {
                Log.d("DEBUG", "MOTHER "+mother);
                selectedMother = new Mother();
                selectedMother.setId(motherId);
//                selectedMother.setId(motherId);
                actvMotherName.setText(mother + "("+motherBirthDate+")");
                actvMotherName.setEnabled(false);
            }
//            else {
//                Log.d("DEBUG", "MASUK ELSE");
//                selectedMother.setId(user.getMotherId());
//            }
            mApiService.getMothers(appSession.getData(AppSession.TOKEN))
                    .enqueue(mothersCallback.getCallback());
        }
    }

    private void initEditData(){
        pregnancy = new Gson().fromJson(getIntent().getStringExtra("edit_data"), Pregnancy.class);
        if(user.getPosyandu()==0&&user.getMotherId()>0){
            selectedMother = getMotherSession();
            actvMotherName.setVisibility(View.GONE);
        } else
            mApiService.getMothers(appSession.getData(AppSession.TOKEN))
                    .enqueue(mothersCallback.getCallback());

        //setTitle(R.string.title_edit_pregnancy);

        switch (pregnancy.getPeriod_type()){
            case "Pendek":
                spPeriodType.setSelection(0);
                break;
            case "Normal":
                spPeriodType.setSelection(1);
                break;
            case "Panjang":
                spPeriodType.setSelection(2);
                break;
            default:
                spPeriodType.setSelection(1);
                break;
        }
        actvMotherName.setText(pregnancy.getMother_name());
        etLastPeriodDate.setText(DateHelper.getDateServer(pregnancy.getLast_period_date()));
        etStartHeight.setText(""+pregnancy.getStart_height());
        etStartWeight.setText(replaceDotToComma(""+pregnancy.getStart_weight()));
        etArmRound.setText(""+pregnancy.getArm_round());
        etKekStatus.setText(""+pregnancy.getKek_status());
        etPregnancyNumber.setText(""+pregnancy.getPregnancy_number());
        etMisCariage.setText(""+pregnancy.getMiscariage_count());
        etGCount.setText(""+pregnancy.getG_count());
        etPCount.setText(""+pregnancy.getP_count());
        etACount.setText(""+pregnancy.getA_count());
        etChildrenCount.setText(""+pregnancy.getChildren_count());
        etDeadBirthCOunt.setText(""+pregnancy.getDead_birth_count());
        etPrematureChildrenCount.setText(""+pregnancy.getPremature_children_count());
        etBirthCount.setText(""+pregnancy.getBirth_count());
        etDistance.setText(pregnancy.getDistance());
        etKontrasepsi.setText(pregnancy.getKontrasepsi());
        etRiwayatPenyakit.setText(pregnancy.getDisease_histories());
        etRiwayatAlergi.setText(pregnancy.getAllergy_histories());
        etImmunizationStatus.setText(pregnancy.getImmunization_status());
        etLastBirthHelper.setText(pregnancy.getLast_birth_helper());
        etLastBirthWay.setText(pregnancy.getLast_birth_way());
        etLastGiveBirthDate.setText(DateHelper.getDateServer(pregnancy.getLast_give_birth_date()));
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

    public void onClickForm(View v){
        switch (v.getId()){
            case R.id.submit:
                if(validateData())
                    submitData();
                break;
            case R.id.cancel:
                backFunction();
                break;
        }
    }

    private boolean validateData(){
        String last_period_date = etLastPeriodDate.getText().toString();
        String start_height = etStartHeight.getText().toString();
        String start_weight = etStartWeight.getText().toString();

        if(last_period_date.isEmpty()){
            etLastPeriodDate.setError(getString(R.string.error_last_period_date));
            etLastPeriodDate.requestFocus();
            return false;
        }
        if(start_height.isEmpty()){
            etStartHeight.setError(getString(R.string.error_start_height));
            etStartHeight.requestFocus();
            return false;
        }

        if(start_weight.isEmpty()){
            etStartWeight.setError(getString(R.string.error_start_weight));
            etStartWeight.requestFocus();
            return false;
        }
        if(selectedMother==null){
            actvMotherName.setError(getString(R.string.error_mother_data));
            actvMotherName.requestFocus();
            return false;
        }
        return true;
    }

    private void submitData(){
        String auth_token = appSession.getData(AppSession.TOKEN);
        String last_period_date = etLastPeriodDate.getText().toString();
        int period_type = spPeriodType.getSelectedItemPosition();//.getSelectedItem().toString();
        int start_height = Integer.parseInt(etStartHeight.getText().toString());
        double start_weight = Double.parseDouble(replaceCommaToDot(etStartWeight.getText().toString()));
        long mother_id = selectedMother.getId();
        int arm_round = Integer.parseInt(etArmRound.getText().toString());
        String kek_status = etKekStatus.getText().toString();
        String kontrasepsi = etKontrasepsi.getText().toString();
        String riwayat_alergi = etRiwayatAlergi.getText().toString();
        String riwayat_penyakit = etRiwayatPenyakit.getText().toString();
//        int pregnancy_number = Integer.parseInt(etPregnancyNumber.getText().toString());
//        int birth_count = Integer.parseInt(etBirthCount.getText().toString());
//        int miscariage = Integer.parseInt(etMisCariage.getText().toString());
        int pregnancy_number = 0;
        int birth_count = 0;
        int miscariage = 0;
        int g_count = Integer.parseInt(etGCount.getText().toString());
        int p_count = Integer.parseInt(etPCount.getText().toString());
        int a_count = Integer.parseInt(etACount.getText().toString());
        int children_count = Integer.parseInt(etChildrenCount.getText().toString());
        int dead_birth_count = Integer.parseInt(etDeadBirthCOunt.getText().toString());
        int premature_children = Integer.parseInt(etPrematureChildrenCount.getText().toString());
        String distance = etDistance.getText().toString();
        String immunisasi_status = etImmunizationStatus.getText().toString();
        String last_birth_helper = etLastBirthHelper.getText().toString();
        String last_birth_way = etLastBirthWay.getText().toString();
        String last_give_birth_date = etLastGiveBirthDate.getText().toString();

//        int period_number = 2;
//
//        if(period_type.compareToIgnoreCase("Singkat")==0)period_number=0;
//        if(period_type.compareToIgnoreCase("Normal")==0)period_number=1;

        showProgressBar(true);
        if(editMode){
            long id = pregnancy.getId();
            mApiService.updatePregnancy(id, id, auth_token, last_period_date, start_height,
                    start_weight, period_type, mother_id, arm_round, kek_status,kontrasepsi,riwayat_penyakit,riwayat_alergi,
                    pregnancy_number,birth_count,miscariage,g_count,p_count,a_count,children_count,
                    dead_birth_count,premature_children,distance,immunisasi_status,last_birth_helper,last_birth_way,
                    last_give_birth_date).enqueue(formCallback.getCallback());
        } else
            mApiService.createPregnancy(auth_token, last_period_date, start_height, start_weight,
                    period_type, mother_id,arm_round, kek_status,kontrasepsi, riwayat_penyakit,riwayat_alergi,
                    pregnancy_number,birth_count,miscariage,g_count,p_count,a_count,children_count,
                    dead_birth_count,premature_children,distance,immunisasi_status,last_birth_helper,last_birth_way,
                    last_give_birth_date).enqueue(formCallback.getCallback());
    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                pregnancy = gson.fromJson(result, Pregnancy.class);
            }
            mApiService.getPregnancy(pregnancy.getId(), appSession.getData(AppSession.TOKEN))
                    .enqueue(pregnancyCallback.getCallback());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback mothersCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Mother> stateApiData = gson.fromJson(result, new TypeToken<ApiData<Mother>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            ArrayAdapter<Mother> adapter = new ArrayAdapter<Mother>(context,
                    android.R.layout.simple_dropdown_item_1line, stateApiData.getData());
            actvMotherName.setAdapter(adapter);
            actvMotherName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedMother = (Mother) parent.getAdapter().getItem(position);
                    AppLog.d(new Gson().toJson(selectedMother));
                }
            });
            if(editMode){
                for(int i=0;i<stateApiData.getData().size();i++){
                    if(pregnancy.getMother_id()==stateApiData.getData().get(i).getId()){
                        actvMotherName.setText(stateApiData.getData().get(i).getName());
                        selectedMother = stateApiData.getData().get(i);
                        break;
                    }
                }
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback pregnancyCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            pregnancy = gson.fromJson(result, Pregnancy.class);
            Intent intent = new Intent(context, PregnancyDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(pregnancy));
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
            Intent intent = new Intent(context, PregnancyDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(pregnancy));
            showWarning(intent, R.string.warning, R.string.warning_edit_pregnancy, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_pregnancy, R.string.ok, R.string.cancel);
        }
    }

    private void startShowcase(){
        if(actvMotherName.getVisibility()==View.VISIBLE)
            showcaseHelper.addShowcaseView(actvMotherName, getString(R.string.guide_form_pregnancy_mother_name));
        showcaseHelper.addShowcaseView(etLastPeriodDate, getString(R.string.guide_form_pregnancy_period_date));
        showcaseHelper.addShowcaseView(spPeriodType, getString(R.string.guide_form_pregnancy_period_type));
        showcaseHelper.addShowcaseView(etStartHeight, getString(R.string.guide_form_pregnancy_height));
        showcaseHelper.addShowcaseView(etStartWeight, getString(R.string.guide_form_pregnancy_weight));
        showcaseHelper.startGuide();
    }
}
