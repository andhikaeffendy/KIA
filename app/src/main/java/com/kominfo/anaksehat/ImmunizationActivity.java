package com.kominfo.anaksehat;

import android.os.Bundle;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;

public class ImmunizationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immunization);

        checkSession();
    }
}
