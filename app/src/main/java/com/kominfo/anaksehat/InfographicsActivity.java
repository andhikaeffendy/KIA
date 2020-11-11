package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.adapters.InfographicsAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Infographic;

import java.util.List;

public class InfographicsActivity extends BaseActivity {
    private GridView gridView;
    private InfographicsAdapter adapter;
    private List<Infographic> infographics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infografis);

//        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        setTitle(R.string.title_infographic);

        gridView = findViewById(R.id.gridview);

        showProgressBar(true);
        mApiService.getInfographics().enqueue(getCallback.getCallback());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, InfographicDetailActivity.class);
                i.putExtra("id", infographics.get(position).getId());
                i.putExtra("data", new Gson().toJson(infographics));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickMenu(item.getItemId());
        return true;
    }

    public void onClickMenu(int resId){
        Intent i = null ;
        switch (resId){
            case android.R.id.home:
                finish();
                break;
            case R.id.logout:
                logout2();
                break;
            default:
                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }

        if(i!=null)
            startActivity(i);
    }

    private void initData(List<Infographic> infographics){
        this.infographics = infographics;
        Picasso picasso = Picasso.get();

        adapter = new InfographicsAdapter(context, this.infographics ,picasso);
        gridView.setAdapter(adapter);
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Infographic> apiData = gson.fromJson(result, new TypeToken<ApiData<Infographic>>(){}.getType());
            AppLog.d(new Gson().toJson(apiData));
            initData(apiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
        }
    };
}
