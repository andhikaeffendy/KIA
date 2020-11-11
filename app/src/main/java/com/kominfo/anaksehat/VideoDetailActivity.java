package com.kominfo.anaksehat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.BitmapDrawablePlaceHolder;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Video;

import java.util.List;

public class VideoDetailActivity extends BaseActivity implements
        YouTubePlayer.OnInitializedListener {
    private TextView tvTitle,tvNews,tvTitle1,tvTitle2,tvSummary1,tvSummary2;
    private ImageView ivImage,ivImage1,ivImage2;
    private LinearLayout llOthers;
    private Picasso picasso;
    private Video video;
    private String dataVideos;
    private boolean getData = false;
    private YouTubePlayerFragment youTubePlayerFragment;
    private YouTubePlayer player;
    private boolean playerReady = false;

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

//        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_video);

        tvTitle = findViewById(R.id.title);
        tvNews = findViewById(R.id.news);
        tvTitle1 = findViewById(R.id.title_1);
        tvTitle2 = findViewById(R.id.title_2);
        tvSummary1 = findViewById(R.id.summary_1);
        tvSummary2 = findViewById(R.id.summary_2);
        ivImage = findViewById(R.id.image);
        ivImage1 = findViewById(R.id.image_1);
        ivImage2 = findViewById(R.id.image_2);
        llOthers = findViewById(R.id.other_videos);
        youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(UtilsApi.GOOGLE_API_KEY, this);

        initData();
    }

    private void initData(){
        long id = getIntent().getLongExtra("id",0);
        dataVideos = getIntent().getStringExtra("data");
        if(id==0){
            Toast.makeText(context, R.string.error_article_not_found, Toast.LENGTH_SHORT).show();
            finish();
        }

        picasso = Picasso.get();

        showProgressBar(true);
        mApiService.getVideo(id)
                .enqueue(getCallback.getCallback());
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            video = gson.fromJson(result, Video.class);
            tvTitle.setText(video.getTitle());
            tvNews.setText(Html.fromHtml(video.getBody(), new Html.ImageGetter() {
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
            if(playerReady)player.cueVideo(video.getYoutube_id());
            initOthers();
//            picasso.load(UtilsApi.BASE_URL+article.getImage_url())
//                    .fit()
//                    .transform(new CropSquareTransformation())
//                    .error(R.drawable.default_image)
//                    .into(ivImage);
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
            ApiData<Video> apiData = gson.fromJson(result, new TypeToken<ApiData<Video>>(){}.getType());
            dataVideos = new Gson().toJson(apiData.getData());
            initOthers();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            getData = true;
            showProgressBar(false);
        }
    };

    private void initOthers(){
        AppLog.d(dataVideos);
        if(dataVideos==null) {
            if(!getData)
                mApiService.getVideos()
                        .enqueue(getsCallback.getCallback());
            return;
        }
        List<Video> videos = new Gson().fromJson(dataVideos, new TypeToken<List<Video>>(){}.getType());
        AppLog.d(new Gson().toJson(videos));
        if(videos.size()>0){
            llOthers.setVisibility(View.VISIBLE);
            LinearLayout llArticle1 = findViewById(R.id.video1);
            LinearLayout llArticle2 = findViewById(R.id.video2);
            Video video1 = videos.get(0);
            if(video1.getId()==video.getId()){
                if(videos.size()>1){
                    video1 = videos.get(1);
                    tvTitle1.setText(video1.getTitle());
                    tvSummary1.setText(video1.getSummary());
                    ivImage1.setVisibility(View.VISIBLE);
//                    picasso.load(UtilsApi.BASE_URL+video1.getImage_url())
////                            .fit()
//                            .transform(new CropSquareTransformation())
//                            .error(R.drawable.default_image)
//                            .into(ivImage1);
                    setOnclick(llArticle1, video1, videos);
                    if(videos.size()>2){
                        Video video2 = videos.get(2);
                        tvTitle2.setText(video2.getTitle());
                        tvSummary2.setText(video2.getSummary());
                        ivImage2.setVisibility(View.VISIBLE);
//                        picasso.load(UtilsApi.BASE_URL+video2.getImage_url())
////                                .fit()
//                                .transform(new CropSquareTransformation())
//                                .error(R.drawable.default_image)
//                                .into(ivImage2);
                        setOnclick(llArticle2, video2, videos);
                    }
                }
            } else {
                video1 = videos.get(0);
                tvTitle1.setText(video1.getTitle());
                tvSummary1.setText(video1.getSummary());
                ivImage1.setVisibility(View.VISIBLE);
//                picasso.load(UtilsApi.BASE_URL+video1.getImage_url())
//                        .fit()
//                        .transform(new CropSquareTransformation())
//                        .error(R.drawable.default_image)
//                        .into(ivImage1);
                setOnclick(llArticle1, video1, videos);
                if(videos.size()>1){
                    Video video2 = videos.get(1);
                    if(video2.getId()==video.getId()){
                        if(videos.size()>2){
                            video2 = videos.get(2);
                            tvTitle2.setText(video2.getTitle());
                            tvSummary2.setText(video2.getSummary());
                            ivImage2.setVisibility(View.VISIBLE);
//                            picasso.load(UtilsApi.BASE_URL+video2.getImage_url())
////                                    .fit()
//                                    .transform(new CropSquareTransformation())
//                                    .error(R.drawable.default_image)
//                                    .into(ivImage2);
                            setOnclick(llArticle2, video2, videos);
                        }
                    } else {
                        video2 = videos.get(1);
                        tvTitle2.setText(video2.getTitle());
                        tvSummary2.setText(video2.getSummary());
                        ivImage2.setVisibility(View.VISIBLE);
//                        picasso.load(UtilsApi.BASE_URL+video2.getImage_url())
////                                .fit()
//                                .transform(new CropSquareTransformation())
//                                .error(R.drawable.default_image)
//                                .into(ivImage2);
                        setOnclick(llArticle2, video2, videos);
                    }
                }
            }
        }
    }

    private void setOnclick(View v, final Video video, final List<Video> videos){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ArticleActivity.class);
                i.putExtra("data", new Gson().toJson(videos));
                i.putExtra("id", video.getId());
                startActivity(i);

            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        this.player = player;
        if (!wasRestored) {
            playerReady=true;
            if(video!=null)
                player.cueVideo(video.getYoutube_id());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
