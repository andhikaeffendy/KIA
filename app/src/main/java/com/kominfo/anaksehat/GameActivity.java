package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;

import com.kominfo.anaksehat.controllers.BaseActivity;
import com.unity3d.player.UnityPlayerActivity;

public class GameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = new Intent(context, UnityPlayerActivity.class);
        startActivity(intent);
        finish();
    }
}
