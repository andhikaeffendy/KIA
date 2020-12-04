package com.kominfo.anaksehat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kominfo.anaksehat.controllers.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends BaseActivity {

    private TextView tvCountChildren, tvCountMother;
    private ImageView ivStartDate, ivEndDate;
    private EditText etStartDate,etEndDate;
    private Button btnFilter, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvCountChildren = findViewById(R.id.count_children);
        tvCountMother = findViewById(R.id.count_mothers);

        etStartDate = findViewById(R.id.start_date);
        ivStartDate = findViewById(R.id.start_date_icon);
        etEndDate = findViewById(R.id.end_date);
        ivEndDate = findViewById(R.id.end_date_icon);
        btnFilter = findViewById(R.id.submit);
        btnReset = findViewById(R.id.cancel);

        etEndDate.setInputType(InputType.TYPE_NULL);
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivEndDate.performClick();
            }
        });

        etStartDate.setInputType(InputType.TYPE_NULL);
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivStartDate.performClick();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start_date = etStartDate.getText().toString();
                String end_date = etEndDate.getText().toString();
                if(!start_date.isEmpty()&&!end_date.isEmpty()){
                    updateData(start_date, end_date);
                } else Toast.makeText(context, "Tanggal Awal/Akhir masih kosong", Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEndDate.setText("");
                etStartDate.setText("");
                updateData();
            }
        });

        updateData();
    }

    private void updateData(){
        showProgressBar(true);
        mApiService.getSummary().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                showProgressBar(false);
                if (response.isSuccessful()) {
                    Log.d("Summary : ", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("Succes load : ", jsonObject.toString());
                        tvCountChildren.setText("Total Anak : " + jsonObject.getString("total_children"));
                        tvCountMother.setText("Total Ibu : " + jsonObject.getString("total_mothers"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Gagal : ", "gagal");
                showProgressBar(false);
            }
        });
    }

    private void updateData(String start_date, String end_date){
        showProgressBar(true);
        mApiService.getFilterSummary(start_date, end_date).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                showProgressBar(false);
                if (response.isSuccessful()){
                    Log.d("Summary : ", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("Succes load : ", jsonObject.toString());
                        tvCountChildren.setText("Total Anak : "+jsonObject.getString("total_children"));
                        tvCountMother.setText("Total Ibu : "+jsonObject.getString("total_mothers"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Gagal : ", "gagal");
                showProgressBar(false);
            }
        });
    }
}