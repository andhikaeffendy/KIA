package com.kominfo.anaksehat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
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
            tvHplDate, tvPeriodType, tvImt, tvMotherWeightIndex, tvMotherNutritionCategory;
    private ImageView ivMotherWeightIndex, ivMotherNutritionCategory;
    private Button btnNext, btnNext2, btnNext3;
    private Pregnancy pregnancy;
    private LineChart mChart;

    private final static int RED = 0;
    private final static int ORANGE = 1;
    private final static int GREEN = 2;

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
        tvMotherWeightIndex = findViewById(R.id.mother_weight_index);
        tvMotherNutritionCategory = findViewById(R.id.mother_nutrition_category);
        ivMotherWeightIndex = findViewById(R.id.mother_weight_index_icon);
        ivMotherNutritionCategory= findViewById(R.id.mother_nutrition_category_icon);
        mChart = findViewById(R.id.chart1);

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
                    Intent i = new Intent(context, GiveBirthDetailActivity.class);
                    i.putExtra("data", new Gson().toJson(pregnancy));
                    startActivity(i);
                } else {
                    Intent i = new Intent(context, FormGiveBirthActivity.class);
                    i.putExtra("data", new Gson().toJson(pregnancy));
                    startActivity(i);
                }
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

        tvLastPeriodDate.setText("Menstruasi Terakhir : "+DateHelper.getDate(pregnancy.getLast_period_date()));
        tvHplDate.setText("HPL : "+DateHelper.getDate(pregnancy.getHpl_date()));
        tvStartHeight.setText(""+pregnancy.getStart_height()+" cm");
        tvStartWeight.setText(replaceDotToComma(""+pregnancy.getStart_weight())+" Kg");
        tvWeightGain.setText(replaceDotToComma(""+pregnancy.getWeight_gain())+" Kg");
        tvPeriodType.setText(pregnancy.getPeriod_type());
        tvImt.setText(replaceDotToComma(""+pregnancy.getImt()));
        tvMotherWeightIndex.setText(pregnancy.getMother_weight_index());
        tvMotherNutritionCategory.setText(pregnancy.getMother_nutrition_category());
        tvMotherName.setText(pregnancy.getMother_name());

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
