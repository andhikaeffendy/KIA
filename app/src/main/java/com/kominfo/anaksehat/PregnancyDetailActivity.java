package com.kominfo.anaksehat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.GiveBirth;
import com.kominfo.anaksehat.models.Pregnancy;
import com.kominfo.anaksehat.models.PregnancyHistory;

import java.util.ArrayList;
import java.util.List;

public class PregnancyDetailActivity extends BaseActivity {

    private TextView tvMotherName, tvStartHeight, tvStartWeight, tvWeightGain, tvLastPeriodDate,
            tvHplDate, tvPeriodType, tvImt, tvMotherWeightIndex, tvMotherNutritionCategory, tvMotherHeightCategory,
            tvArmRound, tvKekStatus, tvPregnancyNumber, tvMiscariage, tvGCount, tvPCount,tvKontrasepsi,tvDesease,
            tvACount, tvChildrenCount, tvDeadBirthCount, tvPrematureChildrenCount,tvRiwayatAlergi,
            tvBirthCount, tvDistance, tvImunisasi, tvLastBirthHelper, tvLastBirthWay, tvLastGiveBirthDate,
            tvAgeStatus, tvBloodingStatus, tvInfectionStatus, tvHbStatus, tvArmStatus, tvOtherRiskStatus,
            tvBloodPressureStatus, tvLastPregnancyStatus;
    private ImageView ivMotherWeightIndex, ivMotherNutritionCategory, ivMotherHeightCategory, ivAgeStatus,
            ivBloodingStatus, ivInfectionStatus, ivHbStatus, ivArmStatus, ivOtherRiskStatus, ivBloodPressureStatus, ivLastPregnancyStatus;
    private Button btnNext, btnNext2, btnNext3, btnNext4, btnNext5;
    private Pregnancy pregnancy;
    private LineChart mChart;

    private final static int RED = 0;
    private final static int ORANGE = 1;
    private final static int GREEN = 2;

    private final int NEW_GIVE_BIRTH = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_detail);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_pregnancies);

        tvMotherName = findViewById(R.id.mother_name);
        tvStartHeight = findViewById(R.id.start_height);
        tvStartWeight = findViewById(R.id.start_weight);
        tvWeightGain = findViewById(R.id.weight_gain);
        tvLastPeriodDate = findViewById(R.id.last_period_date);
        tvHplDate = findViewById(R.id.hpl_date);
        tvPeriodType = findViewById(R.id.period_type);
        tvImt = findViewById(R.id.imt);
        tvMotherHeightCategory = findViewById(R.id.mother_height_category);
        ivMotherHeightCategory = findViewById(R.id.mother_height_category_icon);
        tvAgeStatus = findViewById(R.id.pregnancy_age_status);
        ivAgeStatus = findViewById(R.id.pregnancy_age_status_icon);
        tvBloodingStatus = findViewById(R.id.pregnancy_blooding_status);
        ivBloodingStatus = findViewById(R.id.pregnancy_blooding_status_icon);
        tvInfectionStatus = findViewById(R.id.pregnancy_infection_status);
        ivInfectionStatus = findViewById(R.id.pregnancy_infection_status_icon);
        tvHbStatus = findViewById(R.id.pregnancy_hb_status);
        ivHbStatus = findViewById(R.id.pregnancy_hb_status_icon);
        tvArmStatus = findViewById(R.id.pregnancy_arm_status);
        ivArmStatus = findViewById(R.id.pregnancy_arm_status_icon);
        tvOtherRiskStatus = findViewById(R.id.pregnancy_other_risk_status);
        ivOtherRiskStatus = findViewById(R.id.pregnancy_other_risk_status_icon);
        tvBloodPressureStatus = findViewById(R.id.pregnancy_blood_pressure_status);
        ivBloodPressureStatus = findViewById(R.id.pregnancy_blood_pressure_status_icon);
        tvLastPregnancyStatus = findViewById(R.id.pregnancy_last_pregnancy_status);
        ivLastPregnancyStatus = findViewById(R.id.pregnancy_last_pregnancy_status_icon);
        tvMotherWeightIndex = findViewById(R.id.mother_weight_index);
        tvMotherNutritionCategory = findViewById(R.id.mother_nutrition_category);
        ivMotherWeightIndex = findViewById(R.id.mother_weight_index_icon);
        ivMotherNutritionCategory= findViewById(R.id.mother_nutrition_category_icon);
        mChart = findViewById(R.id.chart1);
        tvArmRound = findViewById(R.id.arm_round);
        tvKekStatus = findViewById(R.id.kek_status);
        tvKontrasepsi = findViewById(R.id.kontrasepsi);
        tvRiwayatAlergi = findViewById(R.id.alergi_history);
        tvPregnancyNumber = findViewById(R.id.pregnancy_number);
        tvBirthCount = findViewById(R.id.birth_count);
        tvMiscariage = findViewById(R.id.miscarriage_count);
        tvGCount = findViewById(R.id.g_count);
        tvPCount = findViewById(R.id.p_count);
        tvACount = findViewById(R.id.a_count);
        tvChildrenCount = findViewById(R.id.children_count);
        tvDeadBirthCount = findViewById(R.id.dead_birth_count);
        tvPrematureChildrenCount = findViewById(R.id.premature_children_count);
        tvDistance = findViewById(R.id.distance);
        tvImunisasi = findViewById(R.id.immunization_status);
        tvLastBirthHelper = findViewById(R.id.last_birth_helper);
        tvLastBirthWay = findViewById(R.id.last_birth_way);
        tvDesease = findViewById(R.id.desease_history);
        tvLastGiveBirthDate = findViewById(R.id.last_give_birth_date);

        btnNext = findViewById(R.id.submit);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PregnancyHistoriesActivity.class);
                i.putExtra("data", new Gson().toJson(pregnancy));
                startActivity(i);
            }
        });

        btnNext2 = findViewById(R.id.submit2);
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FormPregnancyHistoryActivity.class);
                i.putExtra("data", new Gson().toJson(pregnancy));
                startActivity(i);
            }
        });

        btnNext3 = findViewById(R.id.submit3);
        btnNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pregnancy.getGive_birth_id()>0){
                    mApiService.getGiveBirth(pregnancy.getGive_birth_id(), appSession.getData(AppSession.TOKEN))
                            .enqueue(givebirthCallback.getCallback());
                } else {
                    Intent i = new Intent(context, FormGiveBirthActivity.class);
                    i.putExtra("data", new Gson().toJson(pregnancy));
                    startActivityForResult(i,NEW_GIVE_BIRTH);
                }
            }
        });

        btnNext4 = findViewById(R.id.submit4);
        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BirthPlanningsActivity.class);
                i.putExtra("data", new Gson().toJson(pregnancy));
                startActivity(i);
            }
        });

        btnNext5 = findViewById(R.id.submit5);
        btnNext5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FormBirthPlanningActivity.class);
                i.putExtra("data", new Gson().toJson(pregnancy));
                startActivity(i);
            }
        });

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
                Intent i = new Intent(context, FormPregnancyActivity.class);
                i.putExtra("edit_data", new Gson().toJson(pregnancy));
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
        pregnancy = new Gson().fromJson(getIntent().getStringExtra("data"), Pregnancy.class);
        initMPChart();

        tvLastPeriodDate.setText(""+DateHelper.getDate(pregnancy.getLast_period_date()));
        tvHplDate.setText(""+DateHelper.getDate(pregnancy.getHpl_date()));
        tvStartHeight.setText(""+pregnancy.getStart_height()+" cm");
        tvStartWeight.setText(replaceDotToComma(""+pregnancy.getStart_weight())+" Kg");
        tvWeightGain.setText(replaceDotToComma(""+pregnancy.getWeight_gain())+" Kg");
        tvPeriodType.setText(pregnancy.getPeriod_type());
        tvImt.setText(replaceDotToComma(""+pregnancy.getImt()));
        tvMotherWeightIndex.setText(pregnancy.getMother_weight_index());
        tvMotherNutritionCategory.setText(pregnancy.getMother_nutrition_category());
        tvMotherName.setText(pregnancy.getMother_name());
        tvArmRound.setText(""+pregnancy.getArm_round()+" cm");
        tvKekStatus.setText(""+pregnancy.getKek_status()+" ");
        tvPregnancyNumber.setText(""+pregnancy.getPregnancy_number()+" ");
        tvMiscariage.setText(""+pregnancy.getMiscariage_count()+" ");
        tvGCount.setText(""+pregnancy.getG_count()+" ");
        tvPCount.setText(""+pregnancy.getP_count()+" ");
        tvACount.setText(""+pregnancy.getA_count()+" ");
        tvChildrenCount.setText(""+pregnancy.getChildren_count()+" Anak");
        tvDeadBirthCount.setText(""+pregnancy.getDead_birth_count()+" Anak");
        tvPrematureChildrenCount.setText(""+pregnancy.getPremature_children_count()+" Anak");
        tvBirthCount.setText(""+pregnancy.getBirth_count()+" ");
        tvDistance.setText(pregnancy.getDistance());
        tvKontrasepsi.setText(pregnancy.getKontrasepsi());
        tvDesease.setText(pregnancy.getDisease_histories());
        tvRiwayatAlergi.setText(pregnancy.getAllergy_histories());
        tvImunisasi.setText(pregnancy.getImmunization_status());
        tvLastBirthHelper.setText(pregnancy.getLast_birth_helper());
        tvLastBirthWay.setText(pregnancy.getLast_birth_way());
        tvLastGiveBirthDate.setText(DateHelper.getDate(pregnancy.getLast_give_birth_date()));

        if(pregnancy.getMother_weight_index().compareToIgnoreCase("Gemuk")==0 ||
                pregnancy.getMother_weight_index().compareToIgnoreCase("Sangat Gemuk")==0 ){
            setImage(ivMotherWeightIndex, RED);
        } else if (pregnancy.getMother_weight_index().compareToIgnoreCase("Berat Badan Ideal")==0 ){
            setImage(ivMotherWeightIndex, GREEN);
        } else {
            setImage(ivMotherWeightIndex, ORANGE);
        }

        if(pregnancy.getMother_nutrition_category().compareToIgnoreCase("Gizi Kurang Tingkat Tinggi")==0 ){
            setImage(ivMotherNutritionCategory, RED);
        } else if (pregnancy.getMother_nutrition_category().compareToIgnoreCase("Gizi Baik")==0 ){
            setImage(ivMotherNutritionCategory, GREEN);
        } else {
            setImage(ivMotherNutritionCategory, ORANGE);
        }

        if (pregnancy.getHeight_status()==0){
            setImage(ivMotherHeightCategory, GREEN);
            tvMotherHeightCategory.setText("Aman");
            Log.d("Height Status",""+pregnancy.getHeight_status());
            Log.d("Age Status",""+pregnancy.getAge_status());
        } else{
            setImage(ivMotherHeightCategory, RED);
            tvMotherHeightCategory.setText("Risiko Tinggi");
        }

        if (pregnancy.getAge_status()==0){
            setImage(ivAgeStatus, GREEN);
            tvAgeStatus.setText("Aman");
        } else if (pregnancy.getAge_status()==1){
            Log.d("Ini 1","Aman");
            setImage(ivAgeStatus, RED);
            tvAgeStatus.setText("Risiko Tinggi");
        } else {
            setImage(ivAgeStatus, ORANGE);
        }
//
        if (pregnancy.getBlood_status()==0){
            setImage(ivBloodingStatus, GREEN);
            tvBloodingStatus.setText("Ya");
        } else if (pregnancy.getBlood_status()==1){
            setImage(ivBloodingStatus, RED);
            tvBloodingStatus.setText("Tidak");
        } else {
            setImage(ivBloodingStatus, ORANGE);
        }

        if (pregnancy.getBlood_status()==0){
            setImage(ivBloodingStatus, GREEN);
            tvBloodingStatus.setText("Ya");
        } else if (pregnancy.getBlood_status()==1){
            setImage(ivBloodingStatus, RED);
            tvBloodingStatus.setText("Tidak");
        } else {
            setImage(ivBloodingStatus, ORANGE);
        }

        if (pregnancy.getInfection_status()==0){
            setImage(ivInfectionStatus, GREEN);
            tvInfectionStatus.setText("Aman");
        } else if (pregnancy.getInfection_status()==1){
            setImage(ivInfectionStatus, RED);
            tvInfectionStatus.setText("Risiko Tinggi");
        } else {
            setImage(ivInfectionStatus, ORANGE);
        }

        if (pregnancy.getHb_status()==0){
            setImage(ivHbStatus, GREEN);
            tvHbStatus.setText("Aman");
        } else if (pregnancy.getHb_status()==1){
            setImage(ivHbStatus, RED);
            tvHbStatus.setText("Risiko Tinggi");
        } else {
            setImage(ivHbStatus, ORANGE);
        }

        if (pregnancy.getArm_status()==0){
            setImage(ivArmStatus, GREEN);
            tvArmStatus.setText("Aman");
        } else if (pregnancy.getArm_status()==1){
            setImage(ivArmStatus, RED);
            tvArmStatus.setText("Risiko Tinggi");
        } else {
            setImage(ivArmStatus, ORANGE);
        }

        if (pregnancy.getOther_risks_status()==0){
            setImage(ivOtherRiskStatus, GREEN);
            tvOtherRiskStatus.setText("Aman");
        } else if (pregnancy.getOther_risks_status()==1){
            setImage(ivOtherRiskStatus, RED);
            tvOtherRiskStatus.setText("Risiko Tinggi");
        } else {
            setImage(ivOtherRiskStatus, ORANGE);
        }

        if (pregnancy.getBlood_pressure_status()==0){
            setImage(ivBloodPressureStatus, GREEN);
            tvBloodPressureStatus.setText("Aman");
        } else if (pregnancy.getBlood_pressure_status()==1){
            setImage(ivBloodPressureStatus, RED);
            tvBloodPressureStatus.setText("Risiko Tinggi");
        } else {
            setImage(ivBloodPressureStatus, ORANGE);
        }

        if (pregnancy.getLast_pregancy_status()==0){
            setImage(ivLastPregnancyStatus, GREEN);
            tvLastPregnancyStatus.setText("Aman");
        } else if (pregnancy.getLast_pregancy_status()==1){
            setImage(ivLastPregnancyStatus, RED);
            tvLastPregnancyStatus.setText("Risiko Tinggi");
        } else {
            setImage(ivLastPregnancyStatus, ORANGE);
        }

        mApiService.getPregnancyHistories(appSession.getData(AppSession.TOKEN), pregnancy.getId())
                .enqueue(pregnancyHistoriesCallback.getCallback());
    }

    ApiCallback pregnancyHistoriesCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<PregnancyHistory> stateApiData = gson.fromJson(result, new TypeToken<ApiData<PregnancyHistory>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            initChartData(stateApiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

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

    private void initMPChart(){
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(false);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawMarkers(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getAxisRight().setEnabled(false);
//        mChart.getAxisLeft().setAxisMaximum(18);
        mChart.getAxisLeft().setAxisMinimum(0);
//        mChart.getXAxis().setAxisMaximum(36);
        mChart.getXAxis().setAxisMinimum(0);
        mChart.setBackgroundColor(Color.TRANSPARENT);
        mChart.setDrawGridBackground(false);
//        mChart.setVisibleXRange(0,36);
//        mChart.setVisibleYRange(0,18, YAxis.AxisDependency.LEFT);
    }

    private void initChartData(List<PregnancyHistory> pregnancyHistories){
        LinearLayout chartZone = findViewById(R.id.chart_zone);
        if(pregnancyHistories.size()<2){
            chartZone.setVisibility(View.GONE);
            return;
        }
        chartZone.setVisibility(View.VISIBLE);

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        for(int i=pregnancyHistories.size()-1;i>=0;i--){
            PregnancyHistory pregnancyHistory = pregnancyHistories.get(i);
            Entry entry = new Entry(DateHelper.getRangeMonth(pregnancy.getLast_period_date(), pregnancyHistory.getHistory_date()), (float) pregnancyHistory.getWeight());
            AppLog.d(entry.toString());
            lineEntries.add(entry);
        }

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Berat Badan");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.parseColor("#0000FF"));
        lineDataSet.setCircleColor(Color.parseColor("#C3C3C3"));
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return " ";
            }
        });
        LineData lineData = new LineData(lineDataSet);
        mChart.setData(lineData);
        mChart.invalidate();
        mChart.notifyDataSetChanged();
    }

    ApiCallback givebirthCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            GiveBirth giveBirth = gson.fromJson(result, GiveBirth.class);
            AppLog.d(new Gson().toJson(giveBirth));
            Intent i = new Intent(context, GiveBirthDetailActivity.class);
            i.putExtra("parent_data", new Gson().toJson(pregnancy));
            i.putExtra("data", new Gson().toJson(giveBirth));
            startActivity(i);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_GIVE_BIRTH && resultCode == RESULT_OK) {
            GiveBirth giveBirth = new Gson().fromJson(data.getStringExtra("result"), GiveBirth.class);
            pregnancy.setGive_birth_id(giveBirth.getId());
            Intent i = new Intent(context, GiveBirthDetailActivity.class);
            i.putExtra("parent_data", new Gson().toJson(pregnancy));
            i.putExtra("data", new Gson().toJson(giveBirth));
            startActivity(i);
        }
    }

}
