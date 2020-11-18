package com.kominfo.anaksehat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.kominfo.anaksehat.helpers.adapters.ChildrenAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChildrenActivity extends BaseActivity implements
        AdapterListener<Child> {

    private RecyclerView recyclerView;
    private EditText etSearch;
    private ChildrenAdapter mAdapter;
    private List<Child> dataList;
    private User user;
    private Mother mother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);

        checkSession();
        checkMotherData();
        user = getUserSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        setTitle(R.string.title_menu_child);

        etSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<Child>();
        mAdapter = new ChildrenAdapter(context, dataList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 0));
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = getIntent().getLongExtra("id", 0);
                Log.d("ID", "ID MOTHER " +id);
                if (id > 0) {
                    mApiService.getMother(id, appSession.getData(AppSession.TOKEN))
                            .enqueue(motherCallback.getCallback());
                } else {
                    Intent intent = new Intent(context, FormChildActivity.class );
                    startActivityForResult(intent, 99);
                    finish();
                }
//                Intent intent = new Intent(context, FormChildActivity.class );
//                startActivityForResult(intent, 99);
            }
        });
    }

    ApiCallback motherCallback = new ApiCallback() {

        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
//            Gson gson = createGsonDate();
            mother = gson.fromJson(result, Mother.class);
            if(user.getPosyandu()==0){
                user.setMotherId(mother.getId());
                appSession.setData(AppSession.USER, new Gson().toJson(user));
                appSession.setData(AppSession.MOTHER, new Gson().toJson(mother));
            }
            Intent intent = new Intent(context, FormChildActivity.class );
            intent.putExtra("mother", mother.getName());
            intent.putExtra("birth_date", dateToString(mother.getBirth_date()));
            intent.putExtra("mother_id", mother.getId());
            startActivityForResult(intent, 99);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    String dateToString(Date date) {
        String _date = "";
        if (date != null) {
            _date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(mother.getBirth_date());
        } else {
            _date = "";
        }
        return _date;
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
        long id = getIntent().getLongExtra("id", 0);
        if(user.getPosyandu()==0&&user.getMotherId()>0){
            mApiService.getChildren(appSession.getData(AppSession.TOKEN), user.getMotherId())
                    .enqueue(childrenCallback.getCallback());
        } else if (id > 0) {
            mApiService.getChildren(appSession.getData(AppSession.TOKEN), id)
                    .enqueue(childrenCallback.getCallback());
        } else
            mApiService.getChildren(appSession.getData(AppSession.TOKEN))
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

    @Override
    public void onItemSelected(Child data) {
        Intent i = new Intent(context, ChildDetailActivity.class);
        i.putExtra("data", new Gson().toJson(data));
        startActivity(i);
    }

    @Override
    public void onItemLongSelected(Child data) {
        Intent i = new Intent(context, FormChildActivity.class);
        i.putExtra("edit_data", new Gson().toJson(data));
        i.putExtra("edit_mode", true);
        startActivity(i);
    }
}
