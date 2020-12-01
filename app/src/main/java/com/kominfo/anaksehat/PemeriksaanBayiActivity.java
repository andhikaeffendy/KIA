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
import com.kominfo.anaksehat.models.User;

import java.util.ArrayList;
import java.util.List;

public class PemeriksaanBayiActivity extends BaseActivity implements AdapterListener<Child> {

    private RecyclerView recyclerView;
    private BabyAdapter mAdapter;

    private List<Child> dataList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_bayi);

        recyclerView = (RecyclerView) findViewById(R.id.rv_pemeriksaan_bayi);
        dataList = new ArrayList<Child>();
        mAdapter = new BabyAdapter(context, dataList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        whiteNotificationBar(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormPemeriksaanBayiActivity.class );
//                intent.putExtra("data", new Gson().toJson(mAdapter));
                startActivityForResult(intent, 99);
            }
        });

    }

    ApiCallback babyCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Child> stateApiData = gson.fromJson(result, new TypeToken<ApiData<Child>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            mAdapter.setData(stateApiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onItemSelected(Child data) {
        Intent i = new Intent(context, DetailPemeriksaanBayiActivity.class);
        i.putExtra("data", new Gson().toJson(data));
        startActivity(i);
    }

    @Override
    public void onItemLongSelected(Child data) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
        mApiService.getPemeriksaanBayi(appSession.getData(AppSession.TOKEN))
                .enqueue(babyCallback.getCallback());
    }
}