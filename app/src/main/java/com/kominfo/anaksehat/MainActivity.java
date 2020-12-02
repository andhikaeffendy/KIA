package com.kominfo.anaksehat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.ShowcaseHelper;
import com.kominfo.anaksehat.helpers.adapters.AdapterListener;
import com.kominfo.anaksehat.helpers.adapters.ContentsAdapter;
import com.kominfo.anaksehat.helpers.adapters.MenusAdapter;
import com.kominfo.anaksehat.helpers.adapters.RecyclerContentAdapter;
import com.kominfo.anaksehat.helpers.adapters.ViewPagerAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.Content;
import com.kominfo.anaksehat.models.NavMenu;
import com.kominfo.anaksehat.models.Pregnancy;
import com.kominfo.anaksehat.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity
//        implements NavigationView.OnNavigationItemSelectedListener,
        implements AdapterListener<Content> {

    private RecyclerView recyclerView, navRecyclerView;
    private LinearLayout sliderDotspanel;
    private int dotscount=0;
    private ImageView[] dots;
    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private RecyclerContentAdapter adapter;
    private ScrollView scrollView;
    private ImageView iv_left,iv_right;
    private Button btnLogout;
    private ShowcaseHelper showcaseHelper;
    private GridView gridView;
    private ContentsAdapter aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout2();
            }
        });

//        NavigationView navigationView = findViewById(R.id.nav_view);
        List<NavMenu> dataList = new ArrayList<>();
        if(!appSession.isLogin()){
            btnLogout.setVisibility(View.GONE);
            dataList.add(new NavMenu(5, R.drawable.nav_promotion, "Sign In"));
            //dataList.add(new NavMenu(6, R.drawable.nav_promotion, "Registrasi"));
        } else {
            dataList.add(new NavMenu(1, R.drawable.nav_mother, "Profil"));
//            dataList.add(new NavMenu(2, R.drawable.nav_child, "Anakku"));
//            dataList.add(new NavMenu(3, R.drawable.nav_pregnancy, "Kehamilanku"));
        }
        //dataList.add(new NavMenu(4, R.drawable.nav_promotion, "Info"));
        MenusAdapter mAdapter = new MenusAdapter(context, dataList, new AdapterListener<NavMenu>() {
            @Override
            public void onItemSelected(NavMenu data) {
                onClickSideMenu(data);
            }

            @Override
            public void onItemLongSelected(NavMenu data) {
                onClickSideMenu(data);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        navRecyclerView = findViewById(R.id.nav_recycler_view);
        navRecyclerView.setLayoutManager(mLayoutManager);
        navRecyclerView.setItemAnimator(new DefaultItemAnimator());
        navRecyclerView.setNestedScrollingEnabled(false);
//        navRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 5));
        navRecyclerView.setAdapter(mAdapter);
//        navigationView.setNavigationItemSelectedListener(this);
//        if(checkSession()) {
            initUserLayout();

            viewPager = findViewById(R.id.viewPagerPos);
            sliderDotspanel = findViewById(R.id.SliderDotsPos);
            scrollView = findViewById(R.id.scroll_view);
//            iv_left = findViewById(R.id.left);
//            iv_right = findViewById(R.id.right);
//
//            iv_left.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    back();
//                }
//            });
//
//            iv_right.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    next();
//                }
//            });

            //recyclerView = findViewById(R.id.recycler_view);

            gridView = findViewById(R.id.gridview);

            showcaseHelper = new ShowcaseHelper(context, ShowcaseHelper.HOME_ID);

            showProgressBar(true);
            mApiService.getContents().enqueue(getCallback.getCallback());
//        }
    }

    private void initUserLayout(){
//        User user = getUserSession();
//        if(user.getPosyandu()==1) {
//            LinearLayout llMenus = findViewById(R.id.layout_menu);
//            LinearLayout llPregnancy = findViewById(R.id.pregnancies);
//            LinearLayout llChild = findViewById(R.id.children);
//
//            llPregnancy.setVisibility(View.GONE);
//            llChild.setVisibility(View.GONE);
//            llMenus.setWeightSum(3);
//        }

//        LinearLayout llMenus = findViewById(R.id.layout_menu);
//        LinearLayout llPregnancy = findViewById(R.id.pregnancies);
//        LinearLayout llChild = findViewById(R.id.children);
//        if(!appSession.isLogin()){
//            llPregnancy.setVisibility(View.GONE);
//            llChild.setVisibility(View.GONE);
//            llMenus.setWeightSum(1);
//        } else {
//            llPregnancy.setVisibility(View.VISIBLE);
//            llChild.setVisibility(View.VISIBLE);
//            llMenus.setWeightSum(3);
//        }
    }

    private void back(){
        if(viewPager==null)return;
        int current = viewPager.getCurrentItem();
        int next = current-1;
        if(next<0)next=vpAdapter.getCount()-1;
        viewPager.setCurrentItem(next);
    }

    private void next(){
        if(viewPager==null)return;
        int current = viewPager.getCurrentItem();
        int next = current+1;
        if(next==vpAdapter.getCount())next=0;
        viewPager.setCurrentItem(next);
    }

    private void initData(final List<Content> contents){
        Picasso picasso = Picasso.get();
        vpAdapter = new ViewPagerAdapter(context, contents, picasso, this);
        viewPager.setAdapter(vpAdapter);

        aAdapter = new ContentsAdapter(context, contents,picasso);
        gridView.setAdapter(aAdapter);

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

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getHeight(contents.size()));
//        recyclerView.setLayoutParams(layoutParams);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);

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

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.MyTimerTask(), 2000, 4000);

//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.scrollTo(0,0);
//            }
//        });
    }

    private int getHeight(int count){
        int height = 340;
        return height*((count/2)+(count%2));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//
//        onClickMenu(item.getItemId());
//        return true;
//    }

    public void onClickMenu(View v){
        onClickMenu(v.getId());
    }

    public void onClickMenu(int resId){
        switch (resId){
            case android.R.id.home:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.pemetaan_ibu_hamil:
                startActivity(new Intent(context, MapsPregnancyActivity.class));
                break;
            case R.id.pemetaan_ibu_icon:
                startActivity(new Intent(context, MapsPregnancyActivity.class));
                break;
            case R.id.pemetaan_bayi_balita:
                startActivity(new Intent(context, MapsChildrenActivity.class));
                break;
            case R.id.pemetaan_bayi_icon:
                startActivity(new Intent(context, MapsChildrenActivity.class));
                break;
//            case R.id.nav_children:
//                gotoChildren();
//                break;
            case R.id.nav_mothers:
                gotoMothers();
                break;
            case R.id.mothers:
                gotoMothers();
                break;
            case R.id.mothers_icon:
                gotoMothers();
                break;
//            case R.id.nav_promotion:
//                gotoPromotion();
//                break;
//            case R.id.nav_pregnancies:
//                gotoPregnancies();
//                break;
            case R.id.nav_logout:
                logout2();
                break;
            case R.id.pregnancies:
                gotoLastPregnancy();
                break;
            case R.id.pregnancies_icon:
                gotoLastPregnancy();
                break;
            case R.id.games:
                gotoGames();
                break;
            case R.id.games_icon:
                gotoGames();
                break;
            case R.id.children:
                gotoChildren();
                break;
            case R.id.children_icon:
                gotoChildren();
                break;
            case R.id.submit_immunization:
                gotoImmunization();
                break;
            case R.id.immunization_icon:
                gotoImmunization();
                break;
            default:
                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
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
            //startShowcase();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onResume() {
        appSession = new AppSession(context);
        super.onResume();
    }

    private void gotoChildren(){
        if(appSession.isLogin()) {
            startActivity(new Intent(context, ChildrenActivity.class));
        } else startActivity(new Intent(context, LoginActivity.class));
    }

    private void gotoMothers(){
        if(appSession.isLogin()) {
            startActivity(new Intent(context, MothersActivity.class));
        } else startActivity(new Intent(context, LoginActivity.class));
    }

    private void gotoPromotion(){
        startActivity(new Intent(context, PromotionActivity.class));
    }

    private void gotoSignIn(){
        startActivity(new Intent(context, LoginActivity.class));
    }

    private void gotoRegistration(){
        startActivity(new Intent(context, SignupActivity.class));
    }

    private void gotoPregnancies(){
        startActivity(new Intent(context, PregnanciesActivity.class));
    }

    private void gotoGames(){
        startActivity(new Intent(context, GameActivity.class));
    }

    private void gotoLastChild(){
        if(!appSession.isLogin()){
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        User user = getUserSession();
        if(user.getPosyandu()==0&&user.getMotherId()==0) {
            startActivity(new Intent(context, FormMotherActivity.class));
        } else if(user.getPosyandu()==1) {
            gotoChildren();
        } else
            showProgressBar(true);
        mApiService.getLastChild(appSession.getData(AppSession.TOKEN), user.getMotherId())
                .enqueue(new ApiCallback() {
                    @Override
                    public void onApiSuccess(String result) {
                        showProgressBar(false);
                        Gson gson = createGsonDate();
                        Child child = gson.fromJson(result, Child.class);
                        AppLog.d(new Gson().toJson(child));
                        if(child.getId()>0){
                            Intent i = new Intent(context, FormChildHistoryActivity.class);
                            i.putExtra("data", new Gson().toJson(child));
                            startActivityForResult(i, 99);
                        } else {
                            Intent i = new Intent(context, FormChildActivity.class);
                            startActivityForResult(i, 99);
                        }
                    }

                    @Override
                    public void onApiFailure(String errorMessage) {
                        showProgressBar(false);
                        if(errorMessage.equalsIgnoreCase("data not available")){
                            Intent i = new Intent(context, FormChildActivity.class);
                            startActivityForResult(i, 99);
                        } else
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }.getCallback());
    }

    private void gotoLastPregnancy(){
        if(!appSession.isLogin()){
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        User user = getUserSession();
        if(user.getPosyandu()==0&&user.getMotherId()==0) {
            startActivity(new Intent(context, FormMotherActivity.class));
        } else if(user.getPosyandu()==1) {
            gotoMothers();
        } else
            showProgressBar(true);
            mApiService.getLastPregnancy(appSession.getData(AppSession.TOKEN), user.getMotherId())
                    .enqueue(new ApiCallback() {
                        @Override
                        public void onApiSuccess(String result) {
                            showProgressBar(false);
                            Gson gson = createGsonDate();
                            Pregnancy pregnancy = gson.fromJson(result, Pregnancy.class);
                            AppLog.d(new Gson().toJson(pregnancy));
                            if(pregnancy.getId()>0){
                                Intent i = new Intent(context, FormPregnancyHistoryActivity.class);
                                i.putExtra("data", new Gson().toJson(pregnancy));
                                startActivityForResult(i, 99);
                            } else {
                                Intent i = new Intent(context, FormPregnancyActivity.class);
                                startActivityForResult(i, 99);
                            }
                        }

                        @Override
                        public void onApiFailure(String errorMessage) {
                            showProgressBar(false);
                            if(errorMessage.equalsIgnoreCase("data not available")){
                                Intent i = new Intent(context, FormPregnancyActivity.class);
                                startActivityForResult(i, 99);
                            } else
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }.getCallback());
    }

    private void gotoImmunization(){
        User user = getUserSession();
        if(user.getPosyandu()==0&&user.getMotherId()==0) {
            startActivity(new Intent(context, FormMotherActivity.class));
        } else if(user.getPosyandu()==1) {
            gotoChildren();
        } else
            showProgressBar(true);
            mApiService.getLastChild(appSession.getData(AppSession.TOKEN), user.getMotherId())
                    .enqueue(new ApiCallback() {
                        @Override
                        public void onApiSuccess(String result) {
                            showProgressBar(false);
                            Gson gson = createGsonDate();
                            Child child = gson.fromJson(result, Child.class);
                            AppLog.d(new Gson().toJson(child));
                            if(child.getId()>0){
                                Intent i = new Intent(context, ImmunizationsActivity.class);
                                i.putExtra("data", new Gson().toJson(child));
                                startActivityForResult(i, 99);
                            } else {
                                Intent i = new Intent(context, FormChildActivity.class);
                                startActivityForResult(i, 99);
                            }
                        }

                        @Override
                        public void onApiFailure(String errorMessage) {
                            showProgressBar(false);
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }.getCallback());
    }

    private void startShowcase(){
        showcaseHelper.addShowcaseView(findViewById(R.id.pregnancies), getString(R.string.guide_menu_pregnancies));
        showcaseHelper.addShowcaseView(findViewById(R.id.children), getString(R.string.guide_menu_children));
        showcaseHelper.addShowcaseView(findViewById(R.id.games), getString(R.string.guide_menu_games));
        showcaseHelper.addShowcaseView(findViewById(R.id.top_corner), getString(R.string.guide_menu_other));
        showcaseHelper.startGuide();
    }

    private void onClickSideMenu(NavMenu data){
        switch ((int)data.getId()) {
            case 1:
                gotoMothers();
                break;
            case 2:
                gotoChildren();
                break;
            case 3:
                gotoPregnancies();
                break;
            case 4:
                gotoPromotion();
                break;
            case 5:
                gotoSignIn();
                break;
            case 6:
                gotoRegistration();
                break;
        }
    }
}
