package com.kominfo.anaksehat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppLog;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.adapters.AdapterListener;
import com.kominfo.anaksehat.helpers.adapters.ImmunizationsAdapter;
import com.kominfo.anaksehat.models.ApiData;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.Immunization;
import com.kominfo.anaksehat.models.Vaccine;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ImmunizationsActivity extends BaseActivity implements AdapterListener<Immunization>{

    private RecyclerView recyclerView;
    private EditText etSearch;
    private ImmunizationsAdapter mAdapter;
    private List<Immunization> dataList;
    private Child child;
    private List<Vaccine> vaccines;
    private ArrayAdapter<Vaccine> adapter;
    private boolean updateSuccess=false;
    private String vaccineName = "[nama vaksin]";

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immunizations);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_menu_immunizations);

        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<Immunization>();
        mAdapter = new ImmunizationsAdapter(context, dataList, this);
        dialog = new Dialog(context);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 0));
        recyclerView.setAdapter(mAdapter);

        whiteNotificationBar(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForm();
            }
        });

        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.immunizations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickMenu(item.getItemId());
        return true;
    }

    public void onClickMenu(int resId){
        switch (resId){
            case android.R.id.home:
                finish();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        getImmunsApi();
    }

    private void getImmunsApi(){
        showProgressBar(true);
        mApiService.getImmunizations(appSession.getData(AppSession.TOKEN), child.getId())
                .enqueue(getCallback.getCallback());
    }

    private void getVaccinesApi(){
        showProgressBar(true);
        mApiService.getVaccines(appSession.getData(AppSession.TOKEN))
                .enqueue(getVaccineCallback.getCallback());
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                @Override
                public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                        throws JsonParseException {
                    try {
                        return df.parse(json.getAsString());
                    } catch (ParseException e) {
                        return null;
                    }
                }
            }).create();
            ApiData<Immunization> apiData = gson.fromJson(result,
                    new TypeToken<ApiData<Immunization>>(){}.getType());
            AppLog.d(new Gson().toJson(apiData));
            mAdapter.setData(apiData.getData());

            getVaccinesApi();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();

            getVaccinesApi();
        }
    };

    ApiCallback getVaccineCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiData<Vaccine> vaccineApiData = new Gson().fromJson(result,
                    new TypeToken<ApiData<Vaccine>>(){}.getType());
            vaccines = vaccineApiData.getData();
            adapter = new ArrayAdapter<Vaccine>(context, android.R.layout.simple_spinner_item, vaccines);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            if(updateSuccess)
                shareForm();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback formCallback = new ApiCallback(){
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, R.string.submit_ok, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            updateSuccess=true;
            initData();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onItemSelected(Immunization data) {
        showEditForm(data);
    }

    @Override
    public void onItemLongSelected(Immunization data) {

    }

    private void showForm(){
        dialog.setContentView(R.layout.form_immunization);
        TextView close = dialog.findViewById(R.id.close);
        final Spinner spVaccine = dialog.findViewById(R.id.vaccine);
        final ImageView iv_immunization = dialog.findViewById(R.id.immunization_icon);
        final EditText etImmunization = dialog.findViewById(R.id.immunization_date);
        etImmunization.setInputType(InputType.TYPE_NULL);
        iv_immunization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.holder = etImmunization;
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        etImmunization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_immunization.performClick();
            }
        });
        Button submit = dialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etImmunization.getText().toString().isEmpty()){
                    etImmunization.setError(getString(R.string.error_immunization_date));
                    etImmunization.requestFocus();
                } else {
                    Vaccine selectedItem = (Vaccine) spVaccine.getSelectedItem();
                    vaccineName = selectedItem.getName();
                    showProgressBar(true);
                    mApiService.createImmunization(appSession.getData(AppSession.TOKEN),
                            etImmunization.getText().toString(), selectedItem.getId(),
                            child.getId()).enqueue(formCallback.getCallback());
                }
            }
        });
        spVaccine.setAdapter(adapter);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showEditForm(final Immunization data){
        dialog.setContentView(R.layout.form_immunization);
        TextView close = dialog.findViewById(R.id.close);
        final Spinner spVaccine = dialog.findViewById(R.id.vaccine);
        ImageView iv_immunization = dialog.findViewById(R.id.immunization_icon);
        final EditText etImmunization = dialog.findViewById(R.id.immunization_date);
        iv_immunization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.holder = etImmunization;
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        Button submit = dialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etImmunization.getText().toString().isEmpty()){
                    etImmunization.setError(getString(R.string.error_immunization_date));
                    etImmunization.requestFocus();
                } else {
                    showProgressBar(true);
                    vaccineName = data.getVaccine_name();
                    mApiService.updateImmunization(data.getId(), data.getId(), appSession.getData(AppSession.TOKEN),
                            etImmunization.getText().toString()).enqueue(formCallback.getCallback());
                }
            }
        });
        List<String> selected = Arrays.asList(data.getVaccine_name());
        ArrayAdapter<String> singleAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, selected);
        singleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVaccine.setAdapter(singleAdapter);
        spVaccine.setEnabled(false);
        if(data.getImmunization_date()!=null)
            etImmunization.setText(DateHelper.getDateServer(data.getImmunization_date()));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void shareForm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.share)
                .setMessage(createEditor(R.string.share_immunization));
        builder.setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                updateSuccess=false;
                doShare(R.string.share_immunization);
            }
        });

        builder.setNegativeButton(R.string.close_2, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateSuccess=false;
                // User clicked OK button
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
        updateSuccess=false;
    }

    @Override
    public String createEditor(int stringId) {
        String editor = getString(stringId);
        editor = editor.replace("[link download apps stunting]", link2App);
        editor = editor.replace("[nama vaksin]", vaccineName);
        editor = editor.replace("[nama anak]", child.getName());
        return editor;
    }
}
