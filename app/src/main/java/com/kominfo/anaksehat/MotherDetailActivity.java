package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kominfo.anaksehat.R;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.CropSquareTransformation;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.Mother;

public class MotherDetailActivity extends BaseActivity {
    private ImageView ivAvatar;
    private TextView tvName, tvBirthDate, tvBloodType, tvHeight, tvWeight, tvSpouseName, tvAddress,
            tvDistrictName, tvBloodPressureTop, getTvBloodPressureBottom, tvKkName, tvNik,
            tvJamStatus, tvSubDistrict, tvVillages;
//    private TextView tvStateName;
    private Button btnChild, btnPregnancy;
    private Mother mother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_detail);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setTitle(R.string.title_mother);

        ivAvatar = findViewById(R.id.avatar);
        tvName = findViewById(R.id.name);
        tvBirthDate = findViewById(R.id.birth_date);
        tvBloodType = findViewById(R.id.blood_type);
        tvHeight = findViewById(R.id.height);
        tvWeight = findViewById(R.id.weight);
        tvSpouseName = findViewById(R.id.spouse_name);
        tvAddress = findViewById(R.id.address);
//        tvStateName =findViewById(R.id.state_name);
        tvDistrictName = findViewById(R.id.district_name);
        tvBloodPressureTop = findViewById(R.id.blood_pressure_top);
        getTvBloodPressureBottom = findViewById(R.id.blood_pressure_bottom);
        btnPregnancy = findViewById(R.id.submit);
        btnChild = findViewById(R.id.submit_child);
        tvKkName = findViewById(R.id.kk_name);
        tvNik = findViewById(R.id.nik);
        tvJamStatus = findViewById(R.id.jampersal_status);
        tvSubDistrict = findViewById(R.id.sub_district_name);
        tvVillages = findViewById(R.id.village);

        btnChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChildrenActivity.class);
                i.putExtra("id", mother.getId());
                startActivity(i);
            }
        });

        btnPregnancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PregnanciesActivity.class);
                i.putExtra("id", mother.getId());
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
                Intent i = new Intent(context, FormMotherActivity.class);
                i.putExtra("edit_data", new Gson().toJson(mother));
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
        mother = new Gson().fromJson(getIntent().getStringExtra("data"), Mother.class);

        tvName.setText(mother.getName());
        tvBirthDate.setText(DateHelper.getDate(mother.getBirth_date()));
        tvBloodType.setText(mother.getBlood_type());
        tvHeight.setText(""+mother.getHeight()+" cm");
        tvWeight.setText(replaceDotToComma(""+mother.getWeight())+" Kg");
        tvSpouseName.setText(mother.getSpouse_name());
        tvAddress.setText(mother.getAddress());
//        tvStateName.setText(mother.getState_name());
        tvDistrictName.setText(mother.getDistrict_name());
        tvBloodPressureTop.setText(""+mother.getBlood_pressure_top());
        getTvBloodPressureBottom.setText(""+mother.getBlood_pressure_bottom());
        tvKkName.setText(mother.getKk_name());
        tvNik.setText(mother.getNik());
        tvJamStatus.setText(mother.getJampersal_status());
        tvSubDistrict.setText(mother.getSub_district_name());
        tvVillages.setText(mother.getVillage_name());

        Picasso.get().load(UtilsApi.BASE_URL+mother.getPhoto_url())
                .transform(new CropSquareTransformation())
                .placeholder(R.drawable.menumother)
                .error(R.drawable.menumother)
                .into(ivAvatar);
    }
}
