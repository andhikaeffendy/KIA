package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.GiveBirth;
import com.kominfo.anaksehat.models.Pregnancy;

public class GiveBirthDetailActivity extends BaseActivity {

    private TextView tvBirthDate, tvBirthTime, tvPregnancyAge, tvBirthHelper, tvBirthWay,
            tvMotherCondition, tvRemarks, tvTreatment;
    private Button btnNext, btnNext2, btnNext3;
    private LinearLayout lnJenisPersalinan;
    private GiveBirth giveBirth;
    private Pregnancy pregnancy;

    private final int NEW_CHILD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_birth_detail);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Persalinan");

        lnJenisPersalinan = findViewById(R.id.layout_jenis);
        tvTreatment = findViewById(R.id.tv_treatment);
        tvBirthDate= findViewById(R.id.birth_date);
        tvBirthTime= findViewById(R.id.birth_time);
        tvPregnancyAge= findViewById(R.id.pregnancy_age);
        tvBirthHelper= findViewById(R.id.bitrh_helper);
        tvBirthWay= findViewById(R.id.birth_way_id);
        tvMotherCondition= findViewById(R.id.mother_condition_id);
        tvRemarks= findViewById(R.id.remarks);

        btnNext = findViewById(R.id.submit);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(giveBirth.getChild_id()>0) {
                    mApiService.getChild(giveBirth.getChild_id(),
                            appSession.getData(AppSession.TOKEN)).enqueue(childCallback.getCallback());
                } else {
                    Intent i = new Intent(context, FormChildActivity.class);
                    i.putExtra("give_birth_id", giveBirth.getId());
                    i.putExtra("mother_id", Long.parseLong(""+giveBirth.getMother_id()));
                    i.putExtra("mother", giveBirth.getMother_name());
                    i.putExtra("birth_date", "-");
                    startActivityForResult(i, NEW_CHILD);
                }
            }
        });

        btnNext2 = findViewById(R.id.submit2);
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NifasHistoriesActivity.class);
                i.putExtra("data", new Gson().toJson(giveBirth));
                startActivity(i);
            }
        });

        btnNext3 = findViewById(R.id.submit3);
        btnNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FormNifasHistoryActivity.class);
                i.putExtra("data", new Gson().toJson(giveBirth));
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
                Intent i = new Intent(context, FormGiveBirthActivity.class);
                i.putExtra("data", new Gson().toJson(pregnancy));
                i.putExtra("edit_data", new Gson().toJson(giveBirth));
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
        giveBirth = new Gson().fromJson(getIntent().getStringExtra("data"), GiveBirth.class);
        pregnancy = new Gson().fromJson(getIntent().getStringExtra("parent_data"), Pregnancy.class);
        tvBirthDate.setText("Tanggal Persalinan : "+DateHelper.getDate(giveBirth.getBirth_date()));
        tvBirthTime.setText(giveBirth.getBirth_time());
        tvBirthHelper.setText(giveBirth.getBitrh_helper());
        tvBirthWay.setText(giveBirth.getBirth_way_id());
        tvMotherCondition.setText(giveBirth.getMother_condition_name());
        tvPregnancyAge.setText(""+giveBirth.getPregnancy_age());
        tvRemarks.setText(""+giveBirth.getRemarks());
        if (giveBirth.getBirth_way_id().equals("Normal")){
            lnJenisPersalinan.setVisibility(View.GONE);
            Log.d("ini if ", giveBirth.getBirth_way_id());
        } else {
            lnJenisPersalinan.setVisibility(View.VISIBLE);
            tvTreatment.setText(giveBirth.getTreatment());
            Log.d("ini if ", giveBirth.getBirth_way_id());
        }
    }

    ApiCallback childCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            Child child = gson.fromJson(result, Child.class);
            AppLog.d(new Gson().toJson(child));
            Intent i = new Intent(context, ChildDetailActivity.class);
            i.putExtra("data", new Gson().toJson(child));
            startActivity(i);
        }
        //

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_CHILD && resultCode == RESULT_OK) {
            Child child = new Gson().fromJson(data.getStringExtra("result"), Child.class);
            giveBirth.setChild_id(child.getId());
            Intent i = new Intent(context, ChildDetailActivity.class);
            i.putExtra("parent_data", new Gson().toJson(giveBirth));
            i.putExtra("data", new Gson().toJson(child));
            startActivity(i);
        }
    }
}
