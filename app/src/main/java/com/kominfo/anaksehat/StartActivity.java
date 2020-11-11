package com.kominfo.anaksehat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.adapters.AdapterListener;
import com.kominfo.anaksehat.helpers.adapters.RecyclerContentAdapter;
import com.kominfo.anaksehat.helpers.adapters.ViewPagerAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Content;
import com.kominfo.anaksehat.models.User;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends BaseActivity implements AdapterListener<Content>{

//    private GridView gridView;
    private RecyclerView recyclerView;
    private LinearLayout sliderDotspanel;
    private int dotscount=0;
    private ImageView[] dots;
    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
//    private ContentsAdapter aAdapter;
    private RecyclerContentAdapter adapter;
    private ScrollView scrollView;
    private ImageView iv_left,iv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        if(checkSession()) {
            initUserLayout();

            Toolbar myToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            viewPager = findViewById(R.id.viewPagerPos);
            sliderDotspanel = findViewById(R.id.SliderDotsPos);
//            gridView = findViewById(R.id.gridview);
            scrollView = findViewById(R.id.scroll_view);
            iv_left = findViewById(R.id.left);
            iv_right = findViewById(R.id.right);

            iv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back();
                }
            });

            iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next();
                }
            });

            recyclerView = findViewById(R.id.recycler_view);

            showProgressBar(true);
            mApiService.getContents().enqueue(getCallback.getCallback());
        }
    }

    private void initUserLayout(){
        User user = getUserSession();
        if(user.getPosyandu()==1) {
            LinearLayout llMenus = findViewById(R.id.layout_menu);
            LinearLayout llPregnancy = findViewById(R.id.pregnancies);
            LinearLayout llChild = findViewById(R.id.children);

            llPregnancy.setVisibility(View.GONE);
            llChild.setVisibility(View.GONE);
            llMenus.setWeightSum(3);
        }
    }

    private void back(){
        int current = viewPager.getCurrentItem();
        int next = current-1;
        if(next<0)next=vpAdapter.getCount()-1;
        viewPager.setCurrentItem(next);
    }

    private void next(){
        int current = viewPager.getCurrentItem();
        int next = current+1;
        if(next==vpAdapter.getCount())next=0;
        viewPager.setCurrentItem(next);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickMenu(item.getItemId());
        return true;
    }

    private void initData(final List<Content> contents){
        Picasso picasso = Picasso.get();
        vpAdapter = new ViewPagerAdapter(context, contents, picasso, this);
        viewPager.setAdapter(vpAdapter);

//        aAdapter = new ContentsAdapter(context, contents,picasso);
//        gridView.setAdapter(aAdapter);

        adapter = new RecyclerContentAdapter(context, contents, new AdapterListener<Content>() {
            @Override
            public void onItemSelected(Content data) {
                onSelectContent(data);
            }

            @Override
            public void onItemLongSelected(Content data) {
                onSelectContent(data);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getHeight(contents.size()));
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        dotscount = vpAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                onSelectContent(contents.get(position));
//            }
//        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0,0);
            }
        });
    }

    private int getHeight(int count){
        int height = 340;
        return height*((count/2)+(count%2));
    }

    public void onClickMenu(View v){
        onClickMenu(v.getId());
    }

    public void onClickMenu(int resId){
        Intent i = null ;
        switch (resId){
            case R.id.children:
                i = new Intent(context, ChildrenActivity.class);
                break;
            case R.id.children_icon:
                i = new Intent(context, ChildrenActivity.class);
                break;
            case R.id.mothers:
                i = new Intent(context, MothersActivity.class);
                break;
            case R.id.mothers_icon:
                i = new Intent(context, MothersActivity.class);
                break;
            case R.id.promotion:
                i = new Intent(context, PromotionActivity.class);
                break;
            case R.id.promotion_icon:
                i = new Intent(context, PromotionActivity.class);
                break;
            case R.id.pregnancies:
                i = new Intent(context, PregnanciesActivity.class);
                break;
            case R.id.pregnancies_icon:
                i = new Intent(context, PregnanciesActivity.class);
                break;
            case R.id.games:
                i = new Intent(context, GameActivity.class);
//                i = new Intent(context, UnityPlayerActivity.class);
//                i = getPackageManager().getLaunchIntentForPackage("com.Company.DefaultCompany");
                break;
            case R.id.games_icon:
                i = new Intent(context, GameActivity.class);
//                i = new Intent(context, UnityPlayerActivity.class);
//                i = getPackageManager().getLaunchIntentForPackage("com.Company.DefaultCompany");
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

    @Override
    public void onItemSelected(Content data) {
        onSelectContent(data);
    }

    @Override
    public void onItemLongSelected(Content data) {

    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    int current = viewPager.getCurrentItem();
                    int next = current+1;
                    if(next==vpAdapter.getCount())next=0;
                    viewPager.setCurrentItem(next);

                }
            });

        }
    }

    private void onSelectContent(Content content){
        Intent i ;
        if(content.getData_type().compareToIgnoreCase("article")==0){
            i = new Intent(context, ArticleActivity.class);
        } else if(content.getData_type().compareToIgnoreCase("video")==0){
            i = new Intent(context, VideoDetailActivity.class);
        } else i = new Intent(context, InfographicDetailActivity.class);
        i.putExtra("id", content.getId());
        startActivity(i);
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            ApiData<Content> apiData = gson.fromJson(result, new TypeToken<ApiData<Content>>(){}.getType());
            AppLog.d(new Gson().toJson(apiData));
            initData(apiData.getData());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
        }
    };

    @Override
    protected void onResume() {
        appSession = new AppSession(context);
        super.onResume();
    }
}
