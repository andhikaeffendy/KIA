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
import com.kominfo.anaksehat.helpers.adapters.ChildHistoriesAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.ChildHistory;

import java.util.ArrayList;
import java.util.List;

public class ChildHistoriesActivity extends BaseActivity
        implements AdapterListener<ChildHistory> {

    private RecyclerView recyclerView;
    private EditText etSearch;
    private ChildHistoriesAdapter mAdapter;
    private List<ChildHistory> dataList;
    private Child child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_histories);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_menu_child_history);

        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<ChildHistory>();
        mAdapter = new ChildHistoriesAdapter(context, dataList, this);

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

        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormChildHistoryActivity.class );
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

    private void initData(){
        mApiService.getChildHistories(appSession.getData(AppSession.TOKEN), child.getId())
                .enqueue(childHistoriesCallback.getCallback());
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    ApiCallback childHistoriesCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<ChildHistory> stateApiData = gson.fromJson(result, new TypeToken<ApiData<ChildHistory>>(){}.getType());
            AppLog.d(new Gson().toJson(stateApiData));
            mAdapter.setData(stateApiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onItemSelected(ChildHistory data) {
        Intent i = new Intent(context, ChildHistoryDetailActivity.class);
        i.putExtra("data", new Gson().toJson(data));
        i.putExtra("parent_data", new Gson().toJson(child));
        startActivity(i);
    }

    @Override
    public void onItemLongSelected(ChildHistory data) {
        Intent i = new Intent(context, FormChildHistoryActivity.class);
        i.putExtra("data", new Gson().toJson(child));
        i.putExtra("edit_data", new Gson().toJson(data));
        i.putExtra("edit_mode", true);
        startActivity(i);
    }
}
