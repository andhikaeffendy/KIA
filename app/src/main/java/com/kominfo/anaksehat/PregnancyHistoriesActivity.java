package com.kominfo.anaksehat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.adapters.AdapterListener;
import com.kominfo.anaksehat.helpers.adapters.PregnancyHistoriesAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Pregnancy;
import com.kominfo.anaksehat.models.PregnancyHistory;

import java.util.ArrayList;
import java.util.List;

public class PregnancyHistoriesActivity extends BaseActivity
        implements AdapterListener<PregnancyHistory> {

    private RecyclerView recyclerView;
    private EditText etSearch;
    private PregnancyHistoriesAdapter mAdapter;
    private List<PregnancyHistory> dataList;
    private Pregnancy pregnancy;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_histories);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_menu_pregnancy_history);

        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<PregnancyHistory>();
        mAdapter = new PregnancyHistoriesAdapter(context, dataList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        whiteNotificationBar(recyclerView);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });

        pregnancy = new Gson().fromJson(getIntent().getStringExtra("data"), Pregnancy.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormPregnancyHistoryActivity.class );
                intent.putExtra("data", new Gson().toJson(pregnancy));
                startActivityForResult(intent, 99);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pregnancies, menu);
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

    private void initData(){
        mApiService.getPregnancyHistories(appSession.getData(AppSession.TOKEN), pregnancy.getId())
                .enqueue(pregnancyHistoriesCallback.getCallback());
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    ApiCallback pregnancyHistoriesCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<PregnancyHistory> stateApiData = gson.fromJson(result, new TypeToken<ApiData<PregnancyHistory>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            mAdapter.setData(stateApiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback pregnancyHistoryCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            PregnancyHistory pregnancyHistory = gson.fromJson(result, PregnancyHistory.class);
            AppLog.d(new Gson().toJson(pregnancyHistory));
            if(editMode){
                Intent i = new Intent(context, FormPregnancyHistoryActivity.class);
                i.putExtra("data", new Gson().toJson(pregnancy));
                i.putExtra("edit_data", new Gson().toJson(pregnancyHistory));
                i.putExtra("edit_mode", true);
                startActivity(i);
            } else {
                Intent i = new Intent(context, PregnancyHistoryDetailActivity.class);
                i.putExtra("parent_data", new Gson().toJson(pregnancy));
                i.putExtra("data", new Gson().toJson(pregnancyHistory));
                startActivity(i);
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onItemSelected(PregnancyHistory data) {
        showProgressBar(true);
        editMode = false;
        mApiService.getPregnancyHistory(data.getId(), appSession.getData(AppSession.TOKEN))
                .enqueue(pregnancyHistoryCallback.getCallback());
    }

    @Override
    public void onItemLongSelected(PregnancyHistory data) {
        editMode = true;
        mApiService.getPregnancyHistory(data.getId(), appSession.getData(AppSession.TOKEN))
                .enqueue(pregnancyHistoryCallback.getCallback());
    }
}
