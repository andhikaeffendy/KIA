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
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.Pregnancy;
import com.kominfo.anaksehat.models.PregnancyHistory;

public class PregnancyHistoryDetailActivity extends BaseActivity {

    private TextView tvPregnancyWeightGain, tvHistoryDate, tvMotherWeightIndex, tvWeight,
            tvBabyWeight, tvMotherNutritionCategory, tvAmnioticCondition,
            tvBloodPressureTop, tvBloodPressureBottom, tvNote;
    private ImageView ivMotherWeightIndex, ivMotherNutritionCategory, ivPregnancyWeightGain,
            ivAmnioticCondition;
    private PregnancyHistory pregnancyHistory;
    private Pregnancy pregnancy;

    private final static int RED = 0;
    private final static int ORANGE = 1;
    private final static int GREEN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_history_detail);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_pregnancy_history);

        tvBabyWeight = findViewById(R.id.baby_weight);
        tvWeight = findViewById(R.id.weight);
        tvHistoryDate = findViewById(R.id.history_date);
        tvNote = findViewById(R.id.note);
        tvBloodPressureTop = findViewById(R.id.blood_pressure_top);
        tvBloodPressureBottom = findViewById(R.id.blood_pressure_bottom);
//        tvGenderPrediction = findViewById(R.id.gender_prediction);
        tvAmnioticCondition = findViewById(R.id.amniotic_condition);
        tvPregnancyWeightGain = findViewById(R.id.pregnancy_weight_gain);
        tvMotherWeightIndex = findViewById(R.id.mother_weight_index);
        tvMotherNutritionCategory = findViewById(R.id.mother_nutrition_category);
        ivMotherWeightIndex = findViewById(R.id.mother_weight_index_icon);
        ivMotherNutritionCategory= findViewById(R.id.mother_nutrition_category_icon);
        ivAmnioticCondition= findViewById(R.id.amniotic_condition_icon);
        ivPregnancyWeightGain= findViewById(R.id.pregnancy_weight_gain_icon);

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
                Intent i = new Intent(context, FormPregnancyHistoryActivity.class);
                i.putExtra("data", new Gson().toJson(pregnancy));
                i.putExtra("edit_data", new Gson().toJson(pregnancyHistory));
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
        pregnancy = new Gson().fromJson(getIntent().getStringExtra("parent_data"),
                Pregnancy.class);
        pregnancyHistory = new Gson().fromJson(getIntent().getStringExtra("data"),
                PregnancyHistory.class);

        String gender = "Laki-laki";
//        if(pregnancyHistory.getGender_prediction().compareToIgnoreCase("female")==0)
//            gender="Perempuan";

        tvHistoryDate.setText(DateHelper.getDate(pregnancyHistory.getHistory_date()));
        tvWeight.setText(replaceDotToComma(""+pregnancyHistory.getWeight())+" Kg");
        tvBabyWeight.setText(replaceDotToComma(""+pregnancyHistory.getBaby_weight())+" gram");
        tvNote.setText(pregnancyHistory.getNote());
        tvBloodPressureTop.setText(""+pregnancyHistory.getBlood_pressure_top());
        tvBloodPressureBottom.setText(""+pregnancyHistory.getBlood_pressure_bottom());
        tvMotherNutritionCategory.setText(pregnancyHistory.getMother_nutrition_category());
//        tvGenderPrediction.setText(gender);
        tvAmnioticCondition.setText(pregnancyHistory.getAmniotic_condition());
        tvPregnancyWeightGain.setText(pregnancyHistory.getPregnancy_weight_gain());
        tvMotherWeightIndex.setText(pregnancyHistory.getMother_weight_index());

        if(pregnancyHistory.getMother_weight_index().compareToIgnoreCase("Gemuk")==0 ||
                pregnancyHistory.getMother_weight_index().compareToIgnoreCase("Sangat Gemuk")==0 ){
            setImage(ivMotherWeightIndex, RED);
        } else if (pregnancyHistory.getMother_weight_index().compareToIgnoreCase("Berat Badan Ideal")==0 ){
            setImage(ivMotherWeightIndex, GREEN);
        } else {
            setImage(ivMotherWeightIndex, ORANGE);
        }

        if(pregnancyHistory.getMother_nutrition_category().compareToIgnoreCase("Gizi Kurang Tingkat Tinggi")==0 ){
            setImage(ivMotherNutritionCategory, RED);
        } else if (pregnancyHistory.getMother_nutrition_category().compareToIgnoreCase("Gizi Baik")==0 ){
            setImage(ivMotherNutritionCategory, GREEN);
        } else {
            setImage(ivMotherNutritionCategory, ORANGE);
        }

        if (pregnancyHistory.getPregnancy_weight_gain().compareToIgnoreCase("Normal")==0 ){
            setImage(ivPregnancyWeightGain, GREEN);
        } else {
            setImage(ivPregnancyWeightGain, ORANGE);
        }

//        if(pregnancyHistory.getAmniotic_condition().compareToIgnoreCase("Kurang Baik")==0 ){
//            setImage(ivAmnioticCondition, RED);
//        } else if (pregnancyHistory.getAmniotic_condition().compareToIgnoreCase("Baik")==0 ){
//            setImage(ivAmnioticCondition, GREEN);
//        } else {
//
//        }

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

}
