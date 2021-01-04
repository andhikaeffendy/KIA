package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.AppSession;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.helpers.ShowcaseHelper;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.ChildHistory;

public class FormChildHistoryActivity extends BaseActivity {

    private EditText etHistoryDate,etHeight,etWeight,etHeadRound,etTemperature,etNote;
    private ImageView ivDate;
    private Child child;
    private boolean editMode = false;
    private ChildHistory childHistory;
    private ShowcaseHelper showcaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_child_history);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_create_child_history);

        etHistoryDate = findViewById(R.id.history_date);
        etHeight = findViewById(R.id.height);
        etWeight = findViewById(R.id.weight);
        etHeadRound = findViewById(R.id.head_round);
        etTemperature = findViewById(R.id.temperature);
        etNote = findViewById(R.id.note);
        ivDate = findViewById(R.id.history_icon);

        child = new Gson().fromJson(getIntent().getStringExtra("data"), Child.class);

        setTitle(child.getName());

        etHistoryDate.setInputType(InputType.TYPE_NULL);
        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.minDate = child.getBirth_date();
                newFragment.holder = etHistoryDate;
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        etHistoryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivDate.performClick();
            }
        });

        showcaseHelper = new ShowcaseHelper(context, ShowcaseHelper.FORM_CHILD_HISTORY_ID);

        editMode = getIntent().getBooleanExtra("edit_mode", false);
        if(editMode) initEditData();

        //startShowcase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.children, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickMenu(item.getItemId());
        return true;
    }

    public void onClickMenu(int resId){
        switch (resId){
            case R.id.add:
                startActivity(new Intent(context, FormChildActivity.class));
                finish();
                break;
            case android.R.id.home:
                backFunction();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initEditData(){
        childHistory = new Gson().fromJson(getIntent().getStringExtra("edit_data"), ChildHistory.class);
        setTitle(R.string.title_edit_child_history);

        etHistoryDate.setText(DateHelper.getDateServer(childHistory.getHistory_date()));
        etHeight.setText(""+childHistory.getHeight());
        etWeight.setText(replaceDotToComma(""+childHistory.getWeight()));
        etHeadRound.setText(""+childHistory.getHead_round());
        etTemperature.setText(""+childHistory.getTemperature());
        etNote.setText(childHistory.getNote());
    }

    private boolean validateData(){
        String historyDate = etHistoryDate.getText().toString();
        String height = etHeight.getText().toString();
        String weight = etWeight.getText().toString();
        String headRound = etHeadRound.getText().toString();
        String temperature = etTemperature.getText().toString();

        if(historyDate.isEmpty()){
            etHistoryDate.setError(getString(R.string.error_birth_date));
            etHistoryDate.requestFocus();
            return false;
        }
        if(height.isEmpty()){
            etHeight.setError(getString(R.string.error_height));
            etHeight.requestFocus();
            return false;
        }
        if(weight.isEmpty()){
            etWeight.setError(getString(R.string.error_weight));
            etWeight.requestFocus();
            return false;
        }
//        if(headRound.isEmpty()){
//            etHeadRound.setError(getString(R.string.error_head_round));
//            etHeadRound.requestFocus();
//            return false;
//        }
//        if(temperature.isEmpty()){
//            etTemperature.setError(getString(R.string.error_temperature));
//            etTemperature.requestFocus();
//            return false;
//        }
        return true;
    }

    public void onClickForm(View v){
        switch (v.getId()){
            case R.id.add:
                startActivity(new Intent(context, FormChildActivity.class));
                finish();
                break;
            case R.id.submit:
                if(validateData())
                    submitData();
                break;
            case R.id.cancel:
                backFunction();
                break;
        }
    }

    private void submitData(){
        String historyDate = etHistoryDate.getText().toString();
        int height = Integer.parseInt(etHeight.getText().toString());
        double weight = Double.parseDouble(replaceCommaToDot(etWeight.getText().toString()));
//        int headRound = Integer.parseInt(etHeadRound.getText().toString());
//        int temperature = Integer.parseInt(etTemperature.getText().toString());
//        String note = etNote.getText().toString();
        long childId = child.getId();

        showProgressBar(true);
        if(editMode){
            long id = childHistory.getId();
            mApiService.updateChildHistory(id, id, appSession.getData(AppSession.TOKEN),
                    historyDate, height, weight, childId)
                    .enqueue(formCallback.getCallback());
        } else
            mApiService.createChildHistory(appSession.getData(AppSession.TOKEN),historyDate, height,
                weight, childId).enqueue(formCallback.getCallback());
    }

    ApiCallback formCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Toast.makeText(context, getString(R.string.submit_ok), Toast.LENGTH_SHORT).show();
            if(!editMode) {
                Gson gson = createGsonDate();
                childHistory = gson.fromJson(result, ChildHistory.class);
            }
//            mApiService.getChildHistory(childHistory.getId(), appSession.getData(AppSession.TOKEN))
//                    .enqueue(childHistoryCallback.getCallback());
            mApiService.getChild(child.getId(), appSession.getData(AppSession.TOKEN))
                    .enqueue(childCallback.getCallback());
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    ApiCallback childHistoryCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            childHistory = gson.fromJson(result, ChildHistory.class);
            Intent intent = new Intent(context, ChildHistoryDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(childHistory));
            startActivity(intent);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    ApiCallback childCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = createGsonDate();
            child = gson.fromJson(result, Child.class);
            Intent intent = new Intent(context, ChildDetailActivity.class);
            intent.putExtra("data", new Gson().toJson(child));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        backFunction();
    }

    private void backFunction(){
        if(editMode){
            Intent intent = new Intent(context, ChildHistoryDetailActivity.class);
            intent.putExtra("parent_data", new Gson().toJson(child));
            intent.putExtra("data", new Gson().toJson(childHistory));
            showWarning(intent, R.string.warning, R.string.warning_edit_child_history, R.string.ok, R.string.cancel);
        } else {
            showWarning(R.string.warning, R.string.warning_create_child_history, R.string.ok, R.string.cancel);
        }
    }

    private void startShowcase(){
        showcaseHelper.addShowcaseView(etHistoryDate, getString(R.string.guide_form_child_history_date));
        showcaseHelper.addShowcaseView(etHeight, getString(R.string.guide_form_child_history_height));
        showcaseHelper.addShowcaseView(etWeight, getString(R.string.guide_form_child_history_weight));
        showcaseHelper.startGuide();
    }
}
