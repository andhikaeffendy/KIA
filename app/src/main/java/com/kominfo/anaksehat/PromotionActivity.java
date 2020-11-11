package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;

public class PromotionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

//        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        setTitle(R.string.title_promotion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(appSession.isLogin())
            inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickMenu(item.getItemId());
        return true;
    }

    public void onClickMenu(View v){
        onClickMenu(v.getId());
    }

    public void onClickMenu(int resId){
        Intent i = null ;
        switch (resId){
            case R.id.video:
                i = new Intent(context, VideoActivity.class);
                break;
            case R.id.video_icon:
                i = new Intent(context, VideoActivity.class);
                break;
            case R.id.infografis:
                i = new Intent(context, InfographicsActivity.class);
                break;
            case R.id.infografis_icon:
                i = new Intent(context, InfographicsActivity.class);
                break;
            case R.id.stuntingmap:
                i = new Intent(context, MapsActivity.class);
                break;
            case R.id.stuntingmap_icon:
                i = new Intent(context, MapsActivity.class);
                break;
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
}
