package com.kominfo.anaksehat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.ChildHistory;
import com.kominfo.anaksehat.models.PemeriksaanBayi;

public class FormPemeriksaanBayiActivity extends BaseActivity {

    private EditText etDate, etWeight, etLength, etTemp, etRepiratory, etHeartBeat,
            etInfection, etIkterus, etDiare, etLowWeight, etKVitamin, etHbBcgPolio,
            etShk, etShkConfirm, etTreatment;
    private ImageView ivDateIcon;
    private Button btnSubmit, btnCancel;
    private PemeriksaanBayi pemeriksaanBayi;
    private Child child;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pemeriksaan_bayi);

        etDate = findViewById(R.id.et_date);
        etWeight = findViewById(R.id.et_weight);
        etLength = findViewById(R.id.et_length);
        etTemp = findViewById(R.id.et_temp);
        etRepiratory = findViewById(R.id.et_repiratory);
        etHeartBeat = findViewById(R.id.et_heart_beat);
        etInfection = findViewById(R.id.et_infection);
        etIkterus = findViewById(R.id.et_ikterus);
        etDiare = findViewById(R.id.et_diare);
        etLowWeight = findViewById(R.id.et_k_vitamin);
        etKVitamin = findViewById(R.id.et_k_vitamin);
        etHbBcgPolio = findViewById(R.id.et_hb_bcg_polio);
        etShk = findViewById(R.id.et_shk);
        etShkConfirm = findViewById(R.id.et_shk_confirmation);
        etTreatment = findViewById(R.id.et_treatment);
        ivDateIcon = findViewById(R.id.date_icon);
        btnSubmit = findViewById(R.id.submit);
        btnCancel = findViewById(R.id.cancel);

        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);

        etDate.setInputType(InputType.TYPE_NULL);
//        ivDateIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BaseActivity.DatePickerFragment newFragment = new BaseActivity.DatePickerFragment();
//                newFragment.minDate = child.getBirth_date();
//                newFragment.holder = etDate;
//                newFragment.show(getSupportFragmentManager(),"datePicker");
//            }
//        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivDateIcon.performClick();
            }
        });

        editMode = getIntent().getBooleanExtra("edit_mode",false);
        //if (editMode)

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.children, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//    }
//
//    public void onClickMenu(int resId){
//        switch (resId){
//            case android.R.id.home:
//                back
//        }
//    }

    private void submitData(){

        String historyDate = etDate.getText().toString();
        long weight = Long.parseLong(etWeight.getText().toString());
        int length = Integer.parseInt(etLength.getText().toString());
        float temp = Float.parseFloat(etTemp.getText().toString());
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

        if (editMode){
            int id = pemeriksaanBayi.getId();
            mApiService.updatePemeriksaanBayi(id, id, appSession.getData(AppSession.TOKEN), historyDate, weight,length,
                    temp,repiratory,heartBeat,infection,ikterus,diare,lowWeight,kVItamin,hbBcgPolio,shk,shkConfirm,treatment,userId).enqueue(formCallback.getCallback());
        }else {
            int id = pemeriksaanBayi.getId();
            mApiService.createPemeriksaanBayi(appSession.getData(AppSession.TOKEN), id, historyDate, weight,length,temp,repiratory,heartBeat,infection,
                    ikterus,diare,lowWeight,kVItamin,hbBcgPolio,shk,shkConfirm,treatment,userId);

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

}