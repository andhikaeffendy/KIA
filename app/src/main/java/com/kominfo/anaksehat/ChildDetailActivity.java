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
import com.kominfo.anaksehat.models.Checkbox;
import com.kominfo.anaksehat.models.Neonatal;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.CropSquareTransformation;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.ChildHistory;

import java.util.ArrayList;
import java.util.List;

public class ChildDetailActivity extends BaseActivity {
    private ImageView ivAvatar;
    private TextView tvName, tvBirthDate, tvBloodType, tvFirstLength, tvFirstWeight, tvHeight,
            tvWeight, tvGender, tvFirstHeadRound, tvMotherName, tvBabyCondition, tvBabyTreatment,
            tvNote, tvJampersalStatus, tvChildNumber;
    private Button btnNext, btnImun, btnNeo, btnPemBayi;
    private Child child;
    private LineChart mChart;

    private final static int RED = 0;
    private final static int ORANGE = 1;
    private final static int GREEN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_detail);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_child);

        tvChildNumber = findViewById(R.id.child_number);
        tvBabyCondition = findViewById(R.id.tv_nb_baby_condition_ids);
        tvBabyTreatment = findViewById(R.id.tv_nb_baby_treatment_ids);
        tvNote = findViewById(R.id.tv_note);
        tvJampersalStatus = findViewById(R.id.tv_jampersal_status);
        ivAvatar = findViewById(R.id.avatar);
        tvName = findViewById(R.id.name);
        tvBirthDate = findViewById(R.id.birth_date);
        tvBloodType = findViewById(R.id.blood_type);
        tvFirstLength = findViewById(R.id.first_length);
        tvFirstWeight = findViewById(R.id.first_weight);
        tvHeight = findViewById(R.id.height);
        tvWeight = findViewById(R.id.weight);
        tvGender = findViewById(R.id.gender);
        tvFirstHeadRound = findViewById(R.id.first_head_round);
        tvMotherName = findViewById(R.id.mother_name);
        btnNext = findViewById(R.id.submit);
        btnImun = findViewById(R.id.submit_immunization);
        btnNeo = findViewById(R.id.submit_neonatal);
        btnPemBayi = findViewById(R.id.submit_pemeriksaanBayi);
        mChart = findViewById(R.id.chart1);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChildHistoriesActivity.class);
                i.putExtra("data", new Gson().toJson(child));
                startActivity(i);
            }
        });

        btnImun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ImmunizationsActivity.class);
                i.putExtra("data", new Gson().toJson(child));
                startActivity(i);
            }
        });

        btnNeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NeonatalActivity.class);
                i.putExtra("data", new Gson().toJson(child));
                startActivity(i);
            }
        });

        btnPemBayi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PemeriksaanBayiActivity.class);
                i.putExtra("data", new Gson().toJson(child));
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
                Intent i = new Intent(context, FormChildActivity.class);
                i.putExtra("edit_data", new Gson().toJson(child));
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
        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);
        initMPChart();

        String gender = "Laki-laki";
        if(child.getGender().compareToIgnoreCase("female")==0)
            gender="Perempuan";

        tvName.setText(child.getName());
        tvBirthDate.setText(DateHelper.getDate(child.getBirth_date()));
        tvBloodType.setText(child.getBlood_type());
        tvFirstLength.setText(""+child.getFirst_length()+" cm");
        tvFirstWeight.setText(replaceDotToComma(""+child.getFirst_weight())+" Gram");
        tvHeight.setText(""+child.getHeight()+" cm");
        tvWeight.setText(replaceDotToComma(""+child.getWeight())+" Gram");
        tvGender.setText(gender);
        tvFirstHeadRound.setText(""+child.getFirst_head_round()+" cm");
        tvMotherName.setText(child.getMother_name());

        Picasso.get().load(UtilsApi.BASE_URL+child.getPhoto_url())
                .transform(new CropSquareTransformation())
                .placeholder(R.drawable.menuchild)
                .error(R.drawable.menuchild)
                .into(ivAvatar);

        mApiService.getChildHistories(appSession.getData(AppSession.TOKEN), child.getId())
                .enqueue(childHistoriesCallback.getCallback());

        tvChildNumber.setText(""+child.getChild_number());
        tvNote.setText(child.getNote());
        String conditions = "", treatments = "";
        tvJampersalStatus.setText(child.getJampersal_status());
        for (int i=0; i < child.getNb_baby_conditions().size();i++) {
            conditions = conditions.concat("- "+child.getNb_baby_conditions().get(i).getName());
            if( i+1 < child.getNb_baby_conditions().size()){
                conditions = conditions.concat("\n");
            }
        }
        tvBabyCondition.setText(conditions);
        for (int i=0; i < child.getNb_baby_treatments().size();i++) {
            treatments = treatments.concat("- "+child.getNb_baby_treatments().get(i).getName());
            if( i+1 < child.getNb_baby_treatments().size()){
                treatments = treatments.concat("\n");
            }
        }
        tvBabyTreatment.setText(treatments);
    }

    ApiCallback childHistoriesCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<ChildHistory> stateApiData = gson.fromJson(result, new TypeToken<ApiData<ChildHistory>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            initChartData(stateApiData.getData());
            initMeasureData(stateApiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

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
        mChart.getAxisLeft().setAxisMaximum(18);
        mChart.getAxisLeft().setAxisMinimum(0);
        mChart.getXAxis().setAxisMaximum(36);
        mChart.getXAxis().setAxisMinimum(0);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.setBackgroundColor(Color.TRANSPARENT);
        mChart.setDrawGridBackground(false);
        mChart.setVisibleXRange(0,36);
        mChart.setVisibleYRange(0,18, YAxis.AxisDependency.LEFT);
    }

    private void initChartData(List<ChildHistory> childHistories){
        LinearLayout chartZone = findViewById(R.id.chart_zone);
        if(childHistories.size()<2){
            chartZone.setVisibility(View.GONE);
            return;
        }
        chartZone.setVisibility(View.VISIBLE);
        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        for(int i=childHistories.size()-1;i>=0;i--){
            ChildHistory childHistory = childHistories.get(i);
            Entry entry = new Entry(DateHelper.getRangeMonth(child.getBirth_date(), childHistory.getHistory_date()), (float) childHistory.getWeight());
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

    private void initMeasureData(List<ChildHistory> childHistories){
        LinearLayout llmeasure_zone = findViewById(R.id.measure_zone);
        if ((childHistories.size()==0)) {
            llmeasure_zone.setVisibility(View.GONE);
            return;
        }

        llmeasure_zone.setVisibility(View.VISIBLE);
        ChildHistory childHistory = childHistories.get(0);

        TextView tvBbuIndex,tvPbuIndex,tvBbpbIndex,tvImtuIndex;
        ImageView ivBbuIndex,ivPbuIndex,ivBbpbIndex,ivImtuIndex;

        tvBbuIndex=findViewById(R.id.bbu_index);
        ivBbuIndex=findViewById(R.id.bbu_index_icon);

        tvPbuIndex=findViewById(R.id.pbu_index);
        ivPbuIndex=findViewById(R.id.pbu_index_icon);

        tvBbpbIndex=findViewById(R.id.bbpb_index);
        ivBbpbIndex=findViewById(R.id.bbpb_index_icon);

        tvImtuIndex=findViewById(R.id.imtu_index);
        ivImtuIndex=findViewById(R.id.imtu_index_icon);

        tvBbuIndex.setText(childHistory.getBbu_index());
        if(childHistory.getBbu_index().compareToIgnoreCase("Gizi Buruk")==0){
            setImage(ivBbuIndex, RED);
        } else if (childHistory.getBbu_index().compareToIgnoreCase("Gizi Baik")==0 ){
            setImage(ivBbuIndex, GREEN);
        } else {
            setImage(ivBbuIndex, ORANGE);
        }

        tvPbuIndex.setText(childHistory.getPbu_index());
        if(childHistory.getPbu_index().compareToIgnoreCase("Sangat Pendek")==0){
            setImage(ivPbuIndex, RED);
        } else if (childHistory.getPbu_index().compareToIgnoreCase("Normal")==0 ){
            setImage(ivPbuIndex, GREEN);
        } else {
            setImage(ivPbuIndex, ORANGE);
        }

        tvBbpbIndex.setText(childHistory.getBbpb_index());
        if(childHistory.getBbpb_index().compareToIgnoreCase("Sangat Kurus")==0){
            setImage(ivBbpbIndex, RED);
        } else if (childHistory.getBbpb_index().compareToIgnoreCase("Normal")==0 ){
            setImage(ivBbpbIndex, GREEN);
        } else {
            setImage(ivBbpbIndex, ORANGE);
        }

        tvImtuIndex.setText(childHistory.getImtu_index());
        if(childHistory.getImtu_index().compareToIgnoreCase("Sangat Kurus")==0){
            setImage(ivImtuIndex, RED);
        } else if (childHistory.getImtu_index().compareToIgnoreCase("Normal")==0 ){
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

}
