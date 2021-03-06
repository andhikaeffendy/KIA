package com.kominfo.anaksehat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.PemeriksaanBayi;

public class DetailPemeriksaanBayiActivity extends BaseActivity {

    private TextView tvWeight, tvHeight, tvTemp, tvRepiratory, tvHeatBeat, tvInfection,
            tvIkterus, tvDiare, tvLowWeight, tvKVitamin, tvHbBcgPolio, tvShk,
            tvShkConfirm, tvTreatment;
    PemeriksaanBayi pemeriksaanBayi;
    Child child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemeriksaan_bayi);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvWeight = findViewById(R.id.tv_weight);
        tvHeight = findViewById(R.id.tv_height);
        tvTemp = findViewById(R.id.tv_temp);
        tvRepiratory = findViewById(R.id.tv_repiratory);
        tvHeatBeat = findViewById(R.id.tv_heat_beat);
        tvInfection = findViewById(R.id.tv_infection);
        tvIkterus = findViewById(R.id.tv_ikterus);
        tvDiare = findViewById(R.id.tv_diare);
        tvLowWeight = findViewById(R.id.tv_low_weight);
        tvKVitamin = findViewById(R.id.tv_k_vitamin);
        tvHbBcgPolio = findViewById(R.id.tv_hb_bcg_polio);
        tvShk = findViewById(R.id.tv_shk);
        tvShkConfirm = findViewById(R.id.tv_shk_confrimation);
        tvTreatment = findViewById(R.id.tv_treatment);

        initData();
    }

    private void initData(){
        pemeriksaanBayi = new Gson().fromJson(getIntent().getStringExtra("data"), PemeriksaanBayi.class);
        child = new Gson().fromJson(getIntent().getStringExtra("parent_data"), Child.class);

        tvWeight.setText(""+pemeriksaanBayi.getWeight() +" Gram");
        tvHeight.setText(""+pemeriksaanBayi.getHeight() + " Cm");
        tvTemp.setText(""+pemeriksaanBayi.getTemperature() +" C");
        tvRepiratory.setText(""+pemeriksaanBayi.getRespiratory() +" Minute");
        tvHeatBeat.setText(""+pemeriksaanBayi.getHeartBeat()+" Minute");
        tvInfection.setText(pemeriksaanBayi.getInfection());
        tvIkterus.setText(pemeriksaanBayi.getIkterus());
        tvDiare.setText(pemeriksaanBayi.getDiare());
        tvLowWeight.setText(pemeriksaanBayi.getLowWeight());
        tvKVitamin.setText(pemeriksaanBayi.getkVitamin());
        tvHbBcgPolio.setText(pemeriksaanBayi.gethBP());
        tvShk.setText(pemeriksaanBayi.getShk());
        tvShkConfirm.setText(pemeriksaanBayi.getShkConfirmation());
        tvTreatment.setText(pemeriksaanBayi.getTreatment());
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
                Intent i = new Intent(this, FormPemeriksaanBayiActivity.class);
                i.putExtra("data", new Gson().toJson(
                        i.putExtra("edit_data", new Gson().toJson(pemeriksaanBayi))));
                i.putExtra("edit_mode", true);
                startActivity(i);
                finish();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    ApiCallback detailPemeriksaanBayiCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            Gson gson = createGsonDate();
            child = gson.fromJson(result, Child.class);
        }

        @Override
        public void onApiFailure(String errorMessage) {

        }
    };

}