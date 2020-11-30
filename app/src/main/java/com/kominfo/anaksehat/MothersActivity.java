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
import com.kominfo.anaksehat.helpers.adapters.MothersAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.User;

import java.util.ArrayList;
import java.util.List;

public class MothersActivity extends BaseActivity implements
        AdapterListener<Mother> {

    private RecyclerView recyclerView;
    private EditText etSearch;
    private MothersAdapter mAdapter;
    private List<Mother> dataList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mothers);

        checkSession();
        user = getUserSession();
        if(user.getPosyandu()==0&&user.getMotherId()>0){
            Intent i = new Intent(context, MotherDetailActivity.class);
            i.putExtra("data", appSession.getData(AppSession.MOTHER));
            startActivity(i);
            finish();
        }
        if(user.getPosyandu()==0&&user.getMotherId()==0){
            Intent i = new Intent(context, FormMotherActivity.class);
            startActivity(i);
            finish();
        }

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        setTitle(R.string.title_menu_mother);

        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<Mother>();
        mAdapter = new MothersAdapter(context, dataList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVisibility(View.GONE);

        whiteNotificationBar(recyclerView);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormMotherActivity.class );
                startActivityForResult(intent, 99);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mothers, menu);
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
        mApiService.getMothers(appSession.getData(AppSession.TOKEN))
                .enqueue(childrenCallback.getCallback());
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    ApiCallback childrenCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Mother> stateApiData = gson.fromJson(result, new TypeToken<ApiData<Mother>>(){}.getType());
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
    public void onItemSelected(Mother data) {
        Intent i = new Intent(context, MotherDetailActivity.class);
        i.putExtra("data", new Gson().toJson(data));
        startActivity(i);
    }

    @Override
    public void onItemLongSelected(Mother data) {
        Intent i = new Intent(context, FormMotherActivity.class);
        i.putExtra("edit_data", new Gson().toJson(data));
        i.putExtra("edit_mode", true);
        startActivity(i);
    }
}
