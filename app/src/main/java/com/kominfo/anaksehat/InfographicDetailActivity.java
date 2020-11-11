package com.kominfo.anaksehat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.BitmapDrawablePlaceHolder;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.CropSquareTransformation;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Infographic;

import java.util.List;

public class InfographicDetailActivity extends BaseActivity {
    private TextView tvTitle,tvNews,tvTitle1,tvTitle2,tvSummary1,tvSummary2;
    private ImageView ivImage,ivImage1,ivImage2;
    private LinearLayout llOthers;
    private Picasso picasso;
    private Infographic infographic;
    private String dataInfographics;
    private boolean getData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geografis_detail);

//        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_infographic);

        tvTitle = findViewById(R.id.title);
        tvNews = findViewById(R.id.news);
        tvTitle1 = findViewById(R.id.title_1);
        tvTitle2 = findViewById(R.id.title_2);
        tvSummary1 = findViewById(R.id.summary_1);
        tvSummary2 = findViewById(R.id.summary_2);
        ivImage = findViewById(R.id.image);
        ivImage1 = findViewById(R.id.image_1);
        ivImage2 = findViewById(R.id.image_2);
        llOthers = findViewById(R.id.other_infographics);

        initData();
    }

    private void initData(){
        long id = getIntent().getLongExtra("id",0);
        dataInfographics = getIntent().getStringExtra("data");
        if(id==0){
            Toast.makeText(context, R.string.error_infographic_not_found, Toast.LENGTH_SHORT).show();
            finish();
        }

        picasso = Picasso.get();

        showProgressBar(true);
        mApiService.getInfographic(id)
                .enqueue(getCallback.getCallback());
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            infographic = gson.fromJson(result, Infographic.class);
            tvTitle.setText(infographic.getTitle());
            tvNews.setText(Html.fromHtml(infographic.getBody(), new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String s) {
                    String source = s;
                    if(!s.substring(0,4).equalsIgnoreCase("http"))
                        source = UtilsApi.BASE_URL+s;
                    BitmapDrawablePlaceHolder placeHolder = new BitmapDrawablePlaceHolder(context, tvNews);
                    picasso.load(source)
                            .into(placeHolder);
                    return placeHolder;
                }
            },null));
            initOthers();
            picasso.load(UtilsApi.BASE_URL+infographic.getImage_url())
//                    .fit()
                    .error(R.drawable.background_geografi_detail)
                    .placeholder(R.drawable.background_geografi_detail)
                    .into(ivImage);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback getsCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            getData = true;
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Infographic> apiData = gson.fromJson(result, new TypeToken<ApiData<Infographic>>(){}.getType());
            dataInfographics = new Gson().toJson(apiData.getData());
            initOthers();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            getData = true;
            showProgressBar(false);
        }
    };

    private void initOthers(){
        AppLog.d(dataInfographics);
        if(dataInfographics==null) {
            if(!getData)
                mApiService.getInfographics()
                        .enqueue(getsCallback.getCallback());
            return;
        }
        List<Infographic> infographics = new Gson().fromJson(dataInfographics, new TypeToken<List<Infographic>>(){}.getType());
        AppLog.d(new Gson().toJson(infographics));
        if(infographics.size()>0){
            llOthers.setVisibility(View.VISIBLE);
            LinearLayout llArticle1 = findViewById(R.id.infographic1);
            LinearLayout llArticle2 = findViewById(R.id.infographic2);
            Infographic infographic1 = infographics.get(0);
            if(infographic1.getId()==infographic.getId()){
                if(infographics.size()>1){
                    infographic1 = infographics.get(1);
                    tvTitle1.setText(infographic1.getTitle());
                    tvSummary1.setText(infographic1.getSummary());
                    picasso.load(UtilsApi.BASE_URL+infographic1.getImage_url())
//                            .fit()
                            .transform(new CropSquareTransformation())
                            .error(R.drawable.default_image)
                            .into(ivImage1);
                    setOnclick(llArticle1, infographic1, infographics);
                    if(infographics.size()>2){
                        Infographic infographic2 = infographics.get(2);
                        tvTitle2.setText(infographic2.getTitle());
                        tvSummary2.setText(infographic2.getSummary());
                        picasso.load(UtilsApi.BASE_URL+infographic2.getImage_url())
//                                .fit()
                                .transform(new CropSquareTransformation())
                                .error(R.drawable.default_image)
                                .into(ivImage2);
                        setOnclick(llArticle2, infographic2, infographics);
                    }
                }
            } else {
                infographic1 = infographics.get(0);
                tvTitle1.setText(infographic1.getTitle());
                tvSummary1.setText(infographic1.getSummary());
                picasso.load(UtilsApi.BASE_URL+infographic1.getImage_url())
                        .fit()
                        .transform(new CropSquareTransformation())
                        .error(R.drawable.default_image)
                        .into(ivImage1);
                setOnclick(llArticle1, infographic1, infographics);
                if(infographics.size()>1){
                    Infographic infographic2 = infographics.get(1);
                    if(infographic2.getId()==infographic.getId()){
                        if(infographics.size()>2){
                            infographic2 = infographics.get(2);
                            tvTitle2.setText(infographic2.getTitle());
                            tvSummary2.setText(infographic2.getSummary());
                            picasso.load(UtilsApi.BASE_URL+infographic2.getImage_url())
//                                    .fit()
                                    .transform(new CropSquareTransformation())
                                    .error(R.drawable.default_image)
                                    .into(ivImage2);
                            setOnclick(llArticle2, infographic2, infographics);
                        }
                    } else {
                        infographic2 = infographics.get(1);
                        tvTitle2.setText(infographic2.getTitle());
                        tvSummary2.setText(infographic2.getSummary());
                        picasso.load(UtilsApi.BASE_URL+infographic2.getImage_url())
//                                .fit()
                                .transform(new CropSquareTransformation())
                                .error(R.drawable.default_image)
                                .into(ivImage2);
                        setOnclick(llArticle2, infographic2, infographics);
                    }
                }
            }
        }
    }

    private void setOnclick(View v, final Infographic infographic, final List<Infographic> infographics){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ArticleActivity.class);
                i.putExtra("data", new Gson().toJson(infographics));
                i.putExtra("id", infographic.getId());
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share, menu);
        return true;
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
            case R.id.share:
                doShare(R.string.share_infograph);
                break;
            default:
                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }

        if(i!=null)
            startActivity(i);
    }

    @Override
    public String createEditor(int stringId) {
        String editor = getString(stringId);
        editor = editor.replace("[judul infografis]", infographic.getTitle());
        editor = editor.replace("[id]", ""+infographic.getId());
        return editor;
    }
}
