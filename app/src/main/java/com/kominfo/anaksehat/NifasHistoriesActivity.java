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
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.adapters.AdapterListener;
import com.kominfo.anaksehat.helpers.adapters.NifasHistoriesAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.GiveBirth;
import com.kominfo.anaksehat.models.NifasHistory;

import java.util.ArrayList;
import java.util.List;

public class NifasHistoriesActivity extends BaseActivity
        implements AdapterListener<NifasHistory> {

    private RecyclerView recyclerView;
    private EditText etSearch;
    private NifasHistoriesAdapter mAdapter;
    private List<NifasHistory> dataList;
    private GiveBirth giveBirth;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nifas_histories);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Riwayat Pemeriksaan Nifas");

        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<NifasHistory>();
        mAdapter = new NifasHistoriesAdapter(context, dataList, this);

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

        giveBirth = new Gson().fromJson(getIntent().getStringExtra("data"), GiveBirth.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormNifasHistoryActivity.class );
                intent.putExtra("data", new Gson().toJson(giveBirth));
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
        mApiService.getNifasHistories(appSession.getData(AppSession.TOKEN), giveBirth.getId())
                .enqueue(nifasHistoriesCallback.getCallback());
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    ApiCallback nifasHistoriesCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<NifasHistory> stateApiData = gson.fromJson(result, new TypeToken<ApiData<NifasHistory>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            mAdapter.setData(stateApiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback nifasHistoryCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            NifasHistory nifasHistory = gson.fromJson(result, NifasHistory.class);
            AppLog.d(new Gson().toJson(nifasHistory));
            if(editMode){
                Intent i = new Intent(context, FormNifasHistoryActivity.class);
                i.putExtra("data", new Gson().toJson(giveBirth));
                i.putExtra("edit_data", new Gson().toJson(nifasHistory));
                i.putExtra("edit_mode", true);
                startActivity(i);
            } else {
                Intent i = new Intent(context, NifasHistoryDetailActivity.class);
                i.putExtra("parent_data", new Gson().toJson(giveBirth));
                i.putExtra("data", new Gson().toJson(nifasHistory));
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
    public void onItemSelected(NifasHistory data) {
        showProgressBar(true);
        editMode = false;
        mApiService.getNifasHistory(data.getId(), appSession.getData(AppSession.TOKEN))
                .enqueue(nifasHistoryCallback.getCallback());
    }

    @Override
    public void onItemLongSelected(NifasHistory data) {
        editMode = true;
        mApiService.getNifasHistory(data.getId(), appSession.getData(AppSession.TOKEN))
                .enqueue(nifasHistoryCallback.getCallback());
    }
}
