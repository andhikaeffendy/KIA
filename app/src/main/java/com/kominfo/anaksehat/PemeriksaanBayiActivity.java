package com.kominfo.anaksehat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.adapters.AdapterListener;
import com.kominfo.anaksehat.helpers.adapters.BabyAdapter;
import com.kominfo.anaksehat.helpers.adapters.MothersAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.PemeriksaanBayi;
import com.kominfo.anaksehat.models.User;

import java.util.ArrayList;
import java.util.List;

public class PemeriksaanBayiActivity extends BaseActivity implements AdapterListener<PemeriksaanBayi> {

    private RecyclerView recyclerView;
    private BabyAdapter mAdapter;
    private List<PemeriksaanBayi> dataList;
    private PemeriksaanBayi pemeriksaanBayi;
    private Child child;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_bayi);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Catatan Neonatal");

        recyclerView = (RecyclerView) findViewById(R.id.rv_pemeriksaan_bayi);
        dataList = new ArrayList<PemeriksaanBayi>();
        mAdapter = new BabyAdapter(context, dataList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        whiteNotificationBar(recyclerView);
        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormPemeriksaanBayiActivity.class );
                intent.putExtra("data", new Gson().toJson(child));
                startActivityForResult(intent, 99);
            }
        });
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
            case android.R.id.home:
                finish();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }


    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onItemSelected(PemeriksaanBayi data) {
        editMode = false;
        mApiService.getPemeriksaanBayi(data.getId(),appSession.getData(AppSession.TOKEN))
                .enqueue(detailPemeriksaanBayiCallback.getCallback());
    }

    @Override
    public void onItemLongSelected(PemeriksaanBayi data) {
        editMode = true;
        mApiService.getPemeriksaanBayi(data.getId(),appSession.getData(AppSession.TOKEN))
                .enqueue(detailPemeriksaanBayiCallback.getCallback());
    }

    ApiCallback babyCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<PemeriksaanBayi> stateApiData = gson.fromJson(result, new TypeToken<ApiData<PemeriksaanBayi>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            mAdapter.setData(stateApiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    public void initData() {
        mApiService.getListPemeriksaanBayi(appSession.getData(AppSession.TOKEN), child.getId())
                .enqueue(babyCallback.getCallback());
    }

    @Override
    public void onBackPressed() {
        backFunction();
    }

    private void backFunction(){
        if(editMode){
            Intent intent = new Intent(context, ChildHistoryDetailActivity.class);
            intent.putExtra("parent_data", new Gson().toJson(child));
            intent.putExtra("data", new Gson().toJson(pemeriksaanBayi));
            showWarning(intent, R.string.warning, R.string.warning_edit_baby_histories, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_child_history, R.string.ok, R.string.cancel);
        }
    }

    ApiCallback detailPemeriksaanBayiCallback = new ApiCallback() {

        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            PemeriksaanBayi pemeriksaanBayi = gson.fromJson(result, PemeriksaanBayi.class);
            if(!editMode){
                Intent i = new Intent(context, DetailPemeriksaanBayiActivity.class);
                i.putExtra("data", new Gson().toJson(pemeriksaanBayi));
                i.putExtra("parent_data", new Gson().toJson(child));
                startActivity(i);
            } else {
                Intent i = new Intent(context, FormPemeriksaanBayiActivity.class);
                i.putExtra("data", new Gson().toJson(child));
                i.putExtra("edit_data", new Gson().toJson(pemeriksaanBayi));
                i.putExtra("edit_mode", true);
                startActivity(i);
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
    };
}