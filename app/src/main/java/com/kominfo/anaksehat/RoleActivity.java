package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;

public class RoleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        checkSession2();

    }

    public void onClickRole(View v){
        switch (v.getId()){
            case R.id.mother:
                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.worker:
                Intent i = new Intent(context, LoginActivity.class);
                startActivity(i);
                break;
        }
    }
}
