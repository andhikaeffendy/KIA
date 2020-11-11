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
import com.kominfo.anaksehat.models.Article;

import java.util.List;

public class ArticleActivity extends BaseActivity {
    private TextView tvTitle,tvNews,tvTitle1,tvTitle2,tvSummary1,tvSummary2;
    private ImageView ivImage,ivImage1,ivImage2;
    private Picasso picasso;
    private LinearLayout llOthers;
    private Article article;
    private String dataArticles;
    private boolean getData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

//        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_Article);

        tvTitle = findViewById(R.id.title);
        tvNews = findViewById(R.id.news);
        tvTitle1 = findViewById(R.id.title_1);
        tvTitle2 = findViewById(R.id.title_2);
        tvSummary1 = findViewById(R.id.summary_1);
        tvSummary2 = findViewById(R.id.summary_2);
        ivImage = findViewById(R.id.image);
        ivImage1 = findViewById(R.id.image_1);
        ivImage2 = findViewById(R.id.image_2);
        llOthers = findViewById(R.id.other_articles);

        initData();
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
                doShare(R.string.share_article);
                break;
            default:
                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }

        if(i!=null)
            startActivity(i);
    }

    private void initData(){
        long id = getIntent().getLongExtra("id",0);
        dataArticles = getIntent().getStringExtra("data");
        if(id==0){
            Toast.makeText(context, R.string.error_article_not_found, Toast.LENGTH_SHORT).show();
            finish();
        }

        picasso = Picasso.get();

        showProgressBar(true);
        mApiService.getArticle(id)
                .enqueue(articleCallback.getCallback());
    }

    ApiCallback articleCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            article = gson.fromJson(result, Article.class);
            tvTitle.setText(article.getTitle());
            tvNews.setText(Html.fromHtml(article.getBody(), new Html.ImageGetter() {
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
            picasso.load(UtilsApi.BASE_URL+article.getImage_url())
//                    .fit()
//                    .transform(new CropSquareTransformation())
                    .error(R.drawable.default_image)
                    .into(ivImage);

        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            getData = true;
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Article> apiData = gson.fromJson(result, new TypeToken<ApiData<Article>>(){}.getType());
            dataArticles = new Gson().toJson(apiData.getData());
            initOthers();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            getData = true;
            showProgressBar(false);
        }
    };

    private void initOthers(){
        AppLog.d(dataArticles);
        if(dataArticles==null) {
            if(!getData)
                mApiService.getArticles()
                    .enqueue(getCallback.getCallback());
            return;
        }
        List<Article> articles = new Gson().fromJson(dataArticles, new TypeToken<List<Article>>(){}.getType());
        AppLog.d(new Gson().toJson(articles));
        if(articles.size()>0){
            llOthers.setVisibility(View.VISIBLE);
            LinearLayout llArticle1 = findViewById(R.id.article1);
            LinearLayout llArticle2 = findViewById(R.id.article2);
            Article article1 = articles.get(0);
            if(article1.getId()==article.getId()){
                if(articles.size()>1){
                    article1 = articles.get(1);
                    tvTitle1.setText(article1.getTitle());
                    tvSummary1.setText(article1.getSummary());
                    picasso.load(UtilsApi.BASE_URL+article1.getImage_url())
//                            .fit()
                            .transform(new CropSquareTransformation())
                            .error(R.drawable.default_image)
                            .into(ivImage1);
                    setOnclick(llArticle1, article1, articles);
                    if(articles.size()>2){
                        Article article2 = articles.get(2);
                        tvTitle2.setText(article2.getTitle());
                        tvSummary2.setText(article2.getSummary());
                        picasso.load(UtilsApi.BASE_URL+article2.getImage_url())
//                                .fit()
                                .transform(new CropSquareTransformation())
                                .error(R.drawable.default_image)
                                .into(ivImage2);
                        setOnclick(llArticle2, article2, articles);
                    }
                }
            } else {
                article1 = articles.get(0);
                tvTitle1.setText(article1.getTitle());
                tvSummary1.setText(article1.getSummary());
                picasso.load(UtilsApi.BASE_URL+article1.getImage_url())
                        .fit()
                        .transform(new CropSquareTransformation())
                        .error(R.drawable.default_image)
                        .into(ivImage1);
                setOnclick(llArticle1, article1, articles);
                if(articles.size()>1){
                    Article article2 = articles.get(1);
                    if(article2.getId()==article.getId()){
                        if(articles.size()>2){
                            article2 = articles.get(2);
                            tvTitle2.setText(article2.getTitle());
                            tvSummary2.setText(article2.getSummary());
                            picasso.load(UtilsApi.BASE_URL+article2.getImage_url())
//                                    .fit()
                                    .transform(new CropSquareTransformation())
                                    .error(R.drawable.default_image)
                                    .into(ivImage2);
                            setOnclick(llArticle2, article2, articles);
                        }
                    } else {
                        article2 = articles.get(1);
                        tvTitle2.setText(article2.getTitle());
                        tvSummary2.setText(article2.getSummary());
                        picasso.load(UtilsApi.BASE_URL+article2.getImage_url())
//                                .fit()
                                .transform(new CropSquareTransformation())
                                .error(R.drawable.default_image)
                                .into(ivImage2);
                        setOnclick(llArticle2, article2, articles);
                    }
                }
            }
        }
    }

    private void setOnclick(View v, final Article article, final List<Article> articles){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ArticleActivity.class);
                i.putExtra("data", new Gson().toJson(articles));
                i.putExtra("id", article.getId());
                startActivity(i);

            }
        });
    }

    @Override
    public String createEditor(int stringId) {
        String editor = getString(stringId);
        editor = editor.replace("[judul artikel]", article.getTitle());
        editor = editor.replace("[id]", ""+article.getId());
        return editor;
    }
}
