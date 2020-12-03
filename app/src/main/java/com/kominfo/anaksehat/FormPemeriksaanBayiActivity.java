package com.kominfo.anaksehat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.ChildHistory;
import com.kominfo.anaksehat.models.PemeriksaanBayi;

public class FormPemeriksaanBayiActivity extends BaseActivity {

    private EditText etDate, etWeight, etLength, etTemp, etRepiratory, etHeartBeat,
            etInfection, etIkterus, etDiare, etLowWeight, etKVitamin, etHbBcgPolio,
            etShk, etShkConfirm, etTreatment;
    private ImageView ivDateIcon;
    //private Button btnSubmit, btnCancel;
    private PemeriksaanBayi pemeriksaanBayi;
    private Child child;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pemeriksaan_bayi);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_create_child_history);

        etDate = findViewById(R.id.et_date);
        etWeight = findViewById(R.id.et_weight);
        etLength = findViewById(R.id.et_length);
        etTemp = findViewById(R.id.et_temp);
        etRepiratory = findViewById(R.id.et_repiratory);
        etHeartBeat = findViewById(R.id.et_heart_beat);
        etInfection = findViewById(R.id.et_infection);
        etIkterus = findViewById(R.id.et_ikterus);
        etDiare = findViewById(R.id.et_diare);
        etLowWeight = findViewById(R.id.et_low_weight);
        etKVitamin = findViewById(R.id.et_k_vitamin);
        etHbBcgPolio = findViewById(R.id.et_hb_bcg_polio);
        etShk = findViewById(R.id.et_shk);
        etShkConfirm = findViewById(R.id.et_shk_confirmation);
        etTreatment = findViewById(R.id.et_treatment);
        ivDateIcon = findViewById(R.id.date_icon);
//        btnSubmit = findViewById(R.id.submit);
//        btnCancel = findViewById(R.id.cancel);

        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);
//        Log.d("DEBUG", "ID CHILD : " + child.getId());

        etDate.setInputType(InputType.TYPE_NULL);
        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);
        ivDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.DatePickerFragment newFragment = new BaseActivity.DatePickerFragment();
                newFragment.minDate = child.getBirth_date();
                newFragment.holder = etDate;
                newFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivDateIcon.performClick();
            }
        });

        editMode = getIntent().getBooleanExtra("edit_mode",false);
        if (editMode) {
            initEditData();
        }

    }

    private void initEditData(){
        pemeriksaanBayi = new Gson().fromJson(getIntent().getStringExtra("edit_data"), PemeriksaanBayi.class);
        setTitle("Edit Pemeriksaan");

        etDate.setText(DateHelper.getDateServer(pemeriksaanBayi.getHistory_date()));
        etWeight.setText(""+pemeriksaanBayi.getWeight());
        etLength.setText(replaceDotToComma(""+pemeriksaanBayi.getHeight()));
        etTemp.setText(replaceDotToComma(""+pemeriksaanBayi.getTemperature()));
        etRepiratory.setText(""+pemeriksaanBayi.getRespiratory());
        etHeartBeat.setText(""+pemeriksaanBayi.getHeartBeat());
        etInfection.setText(pemeriksaanBayi.getInfection());
        etIkterus.setText(pemeriksaanBayi.getIkterus());
        etDiare.setText(pemeriksaanBayi.getDiare());
        etLowWeight.setText(pemeriksaanBayi.getLowWeight());
        etKVitamin.setText(pemeriksaanBayi.getkVitamin());
        etHbBcgPolio.setText(pemeriksaanBayi.gethBP());
        etShk.setText(pemeriksaanBayi.getShk());
        etShkConfirm.setText(pemeriksaanBayi.getShkConfirmation());
        etTreatment.setText(pemeriksaanBayi.getTreatment());
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

    private void submitData(){

        String historyDate = etDate.getText().toString();

        int length = Integer.parseInt(etLength.getText().toString());
        double weight = Double.parseDouble(replaceCommaToDot(etWeight.getText().toString()));
        double temperature = Double.parseDouble(replaceCommaToDot(etTemp.getText().toString()));
        int repiratory = Integer.parseInt(etRepiratory.getText().toString());
        int heartBeat = Integer.parseInt(etHeartBeat.getText().toString());
        String infection = etInfection.getText().toString();
        String ikterus = etIkterus.getText().toString();
        String diare = etDiare.getText().toString();
        String lowWeight = etLowWeight.getText().toString();
        String kVItamin = etKVitamin.getText().toString();
        String hbBcgPolio = etHbBcgPolio.getText().toString();
        String shk = etShk.getText().toString();
        String shkConfirm = etShkConfirm.getText().toString();
        String treatment = etTreatment.getText().toString();
        long userId = getUserSession().getId();

        showProgressBar(true);
        if (editMode){
            long id = pemeriksaanBayi.getId();
            mApiService.updatePemeriksaanBayi(id, id, appSession.getData(AppSession.TOKEN), historyDate, weight,length,
                    temperature,repiratory,heartBeat,infection,ikterus,diare,lowWeight,kVItamin,hbBcgPolio,shk,shkConfirm,treatment,userId).enqueue(formCallback.getCallback());
        }else {
            mApiService.createPemeriksaanBayi(appSession.getData(AppSession.TOKEN), child.getId(), historyDate, weight,length,temperature,repiratory,heartBeat,infection,
                    ikterus,diare,lowWeight,kVItamin,hbBcgPolio,shk,shkConfirm,treatment,userId).enqueue(formCallback.getCallback());

        }

    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                pemeriksaanBayi = gson.fromJson(result, PemeriksaanBayi.class);
            }
//            mApiService.getChildHistory(childHistory.getId(), appSession.getData(AppSession.TOKEN))
//                    .enqueue(childHistoryCallback.getCallback());
            mApiService.getChild(child.getId(), appSession.getData(AppSession.TOKEN))
                    .enqueue(childCallback.getCallback());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback childCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            child = gson.fromJson(result, Child.class);
            Intent intent = new Intent(context, ChildDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(child));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    private boolean validateData(){
        String historyDate = etDate.getText().toString();

        if(historyDate.isEmpty()){
            etDate.setError(getString(R.string.error_birth_date));
            etDate.requestFocus();
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

    @Override
    public void onBackPressed() {
        backFunction();
    }

    private void backFunction(){
        if(editMode){
            Intent intent = new Intent(context, DetailPemeriksaanBayiActivity.class);
            intent.putExtra("parent_data", new Gson().toJson(child));
            intent.putExtra("data", new Gson().toJson(pemeriksaanBayi));
            showWarning(intent, R.string.warning, R.string.warning_edit_pemeriksaan, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_pemeriksaan, R.string.ok, R.string.cancel);
        }
    }

}