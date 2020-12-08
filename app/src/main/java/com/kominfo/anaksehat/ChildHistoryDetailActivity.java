package com.kominfo.anaksehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.ChildHistory;

public class ChildHistoryDetailActivity extends BaseActivity {
    private TextView tvHistoryDate,tvNote,tvBbuIndex,tvPbuIndex,tvBbpbIndex,tvImtuIndex,
            tvName,tvHeight,tvWeight,tvHeadRound,tvTemperature,tvImt;
    private ImageView ivBbuIndex,ivPbuIndex,ivBbpbIndex,ivImtuIndex;
    private ChildHistory childHistory;
    private Child child;

    private final static int RED = 0;
    private final static int ORANGE = 1;
    private final static int GREEN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_history_detail);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_child_history_detail);

        tvName=findViewById(R.id.name);
        tvHeight=findViewById(R.id.height);
        tvWeight=findViewById(R.id.weight);
        tvHeadRound=findViewById(R.id.head_round);
        tvTemperature=findViewById(R.id.temperature);
        tvImt=findViewById(R.id.imt);
        tvBbuIndex=findViewById(R.id.bbu_index);
        ivBbuIndex=findViewById(R.id.bbu_index_icon);

        tvPbuIndex=findViewById(R.id.pbu_index);
        ivPbuIndex=findViewById(R.id.pbu_index_icon);

        tvBbpbIndex=findViewById(R.id.bbpb_index);
        ivBbpbIndex=findViewById(R.id.bbpb_index_icon);

        tvImtuIndex=findViewById(R.id.imtu_index);
        ivImtuIndex=findViewById(R.id.imtu_index_icon);

        tvHistoryDate=findViewById(R.id.history_date);
        tvNote=findViewById(R.id.note);

        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit, menu);
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
            case R.id.edit:
                Intent i = new Intent(context, FormChildHistoryActivity.class);
                i.putExtra("data", new Gson().toJson(child));
                i.putExtra("edit_data", new Gson().toJson(childHistory));
                i.putExtra("edit_mode", true);
                startActivity(i);
                finish();
                break;
            default:
//                Toast.makeText(context, getString(R.string.error_coming_soon), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initData(){
        child = new Gson().fromJson(getIntent().getStringExtra("parent_data"),
                Child.class);
        childHistory = new Gson().fromJson(getIntent().getStringExtra("data"),
                ChildHistory.class);

        tvName.setText(childHistory.getChild_name());
        tvHeight.setText(""+childHistory.getHeight()+" cm");
        tvWeight.setText(replaceDotToComma(""+childHistory.getWeight())+" Gram");
        tvHeadRound.setText(""+childHistory.getHead_round()+" cm");
        tvTemperature.setText(""+childHistory.getTemperature()+" °C");
        tvHistoryDate.setText(DateHelper.getDateWithNameDay(childHistory.getHistory_date()));
        tvNote.setText(childHistory.getNote());
        tvImt.setText(replaceDotToComma(""+childHistory.getImt()));

        tvBbuIndex.setText(childHistory.getBbu_index());
        if(childHistory.getBbu_index().compareToIgnoreCase("Gizi Buruk")==0){
            setImage(ivBbuIndex, RED);
        } else if (childHistory.getBbu_index().compareToIgnoreCase("Gizi Baik")==0 ){
            setImage(ivBbuIndex, GREEN);
        } else {
            setImage(ivBbuIndex, ORANGE);
        }

        tvPbuIndex.setText(childHistory.getPbu_index());
        if(childHistory.getPbu_index().compareToIgnoreCase("Sangat Pendek")==0){
            setImage(ivPbuIndex, RED);
        } else if (childHistory.getPbu_index().compareToIgnoreCase("Normal")==0 ){
            setImage(ivPbuIndex, GREEN);
        } else {
            setImage(ivPbuIndex, ORANGE);
        }

        tvBbpbIndex.setText(childHistory.getBbpb_index());
        if(childHistory.getBbpb_index().compareToIgnoreCase("Sangat Kurus")==0){
            setImage(ivBbpbIndex, RED);
        } else if (childHistory.getBbpb_index().compareToIgnoreCase("Normal")==0 ){
            setImage(ivBbpbIndex, GREEN);
        } else {
            setImage(ivBbpbIndex, ORANGE);
        }

        tvImtuIndex.setText(childHistory.getImtu_index());
        if(childHistory.getImtu_index().compareToIgnoreCase("Sangat Kurus")==0){
            setImage(ivImtuIndex, RED);
        } else if (childHistory.getImtu_index().compareToIgnoreCase("Normal")==0 ){
            setImage(ivImtuIndex, GREEN);
        } else {
            setImage(ivImtuIndex, ORANGE);
        }

    }

    /*
    *   0 = red
    *   1 = orange
    *   2 = green
     */
    private void setImage(ImageView v, int color){
        switch (color){
            case 0:
                v.setImageResource(R.drawable.sangatpendek);
                break;
            case 1:
                v.setImageResource(R.drawable.kurus);
                break;
            case 2:
                v.setImageResource(R.drawable.baik);
                break;
        }
    }

    @Override
    public String createEditor(int stringId) {
        String editor = getString(stringId);
        editor = editor.replace("[link download apps stunting]", link2App);
        editor = editor.replace("[nama anak]", child.getName());
        return editor;
    }
}
