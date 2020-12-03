package com.kominfo.anaksehat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvCountChildren = findViewById(R.id.count_children);
        tvCountMother = findViewById(R.id.count_mothers);

        mApiService.getSummary().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
            }
        });

    }
}