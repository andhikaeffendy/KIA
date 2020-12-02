package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.Neonatal;

public class DetailNeonatalActivity extends BaseActivity {
    private TextView etHistoryDate,etWeight,etHeight,etTemperature,etRespiratory,etNeonatalVisitType,
            etHeartBeat,etInfection,etIkterus,etDiare,etLowWeight,etKVitamin, etHbBcgPolio,etShk,
            etShkConfirmation,etTreatment,tvBbuIndex,tvPbuIndex,tvBbpbIndex,tvImtuIndex,tvImt;
    private ImageView ivBbuIndex,ivPbuIndex,ivBbpbIndex,ivImtuIndex;
    private Neonatal neonatal;
    private Child child;

    private final static int RED = 0;
    private final static int ORANGE = 1;
    private final static int GREEN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neonatal);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Detail Pemeriksaan Neonatal");

        etNeonatalVisitType = findViewById(R.id.neonatal_visit_type_id);
        etHistoryDate = findViewById(R.id.history_date);
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
        tvImt=findViewById(R.id.imt);
        tvBbuIndex=findViewById(R.id.bbu_index);
        ivBbuIndex=findViewById(R.id.bbu_index_icon);

        tvPbuIndex=findViewById(R.id.pbu_index);
        ivPbuIndex=findViewById(R.id.pbu_index_icon);

        tvBbpbIndex=findViewById(R.id.bbpb_index);
        ivBbpbIndex=findViewById(R.id.bbpb_index_icon);

        tvImtuIndex=findViewById(R.id.imtu_index);
        ivImtuIndex=findViewById(R.id.imtu_index_icon);

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
                Intent i = new Intent(context, FormNeonatalActivity.class);
                i.putExtra("data", new Gson().toJson(child));
                i.putExtra("edit_data", new Gson().toJson(neonatal));
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
        child = new Gson().fromJson(getIntent().getStringExtra("parent_data"),
                Child.class);
        neonatal = new Gson().fromJson(getIntent().getStringExtra("data"),
                Neonatal.class);

        etHistoryDate.setText(DateHelper.getDateServer(neonatal.getHistory_date()));
        etWeight.setText(replaceDotToComma(""+neonatal.getWeight()));
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
        tvImt.setText(replaceDotToComma(""+neonatal.getImt()));
        etNeonatalVisitType.setText(neonatal.getNeonatal_visit_type());

        tvBbuIndex.setText(neonatal.getChild_bbu_index());
        if(neonatal.getChild_bbu_index().compareToIgnoreCase("Gizi Buruk")==0){
            setImage(ivBbuIndex, RED);
        } else if (neonatal.getChild_bbu_index().compareToIgnoreCase("Gizi Baik")==0 ){
            setImage(ivBbuIndex, GREEN);
        } else {
            setImage(ivBbuIndex, ORANGE);
        }

        tvPbuIndex.setText(neonatal.getChild_pbu_index());
        if(neonatal.getChild_pbu_index().compareToIgnoreCase("Sangat Pendek")==0){
            setImage(ivPbuIndex, RED);
        } else if (neonatal.getChild_pbu_index().compareToIgnoreCase("Normal")==0 ){
            setImage(ivPbuIndex, GREEN);
        } else {
            setImage(ivPbuIndex, ORANGE);
        }

        tvBbpbIndex.setText(neonatal.getChild_bbpb_index());
        if(neonatal.getChild_bbpb_index().compareToIgnoreCase("Sangat Kurus")==0){
            setImage(ivBbpbIndex, RED);
        } else if (neonatal.getChild_bbpb_index().compareToIgnoreCase("Normal")==0 ){
            setImage(ivBbpbIndex, GREEN);
        } else {
            setImage(ivBbpbIndex, ORANGE);
        }

        tvImtuIndex.setText(neonatal.getChild_imtu_index());
        if(neonatal.getChild_imtu_index().compareToIgnoreCase("Sangat Kurus")==0){
            setImage(ivImtuIndex, RED);
        } else if (neonatal.getChild_imtu_index().compareToIgnoreCase("Normal")==0 ){
            setImage(ivImtuIndex, GREEN);
        } else {
            setImage(ivImtuIndex, ORANGE);
        }

    }

    /*
     *   0 = red
     *   1 = orange
     *   2 = green
     */
    private void setImage(ImageView v, int color){
        switch (color){
            case 0:
                v.setImageResource(R.drawable.sangatpendek);
                break;
            case 1:
                v.setImageResource(R.drawable.kurus);
                break;
            case 2:
                v.setImageResource(R.drawable.baik);
                break;
        }
    }

    @Override
    public String createEditor(int stringId) {
        String editor = getString(stringId);
        editor = editor.replace("[link download apps KIA]", link2App);
        editor = editor.replace("[nama anak]", child.getName());
        return editor;
    }
}
