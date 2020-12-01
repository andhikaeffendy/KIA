package com.kominfo.anaksehat;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.kominfo.anaksehat.controllers.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsPregnancyActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private List<LatLng> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pregnancy);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
//        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(12);
        UiSettings uiSettings = gMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        LatLng starting = new LatLng(-7.021719, 110.962585);

        list = new ArrayList<>();

        mApiService.getPemetaanAnak().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("Maps Ibu: " , response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i<jsonArray.length(); i++){
                            double lat = Double.parseDouble(jsonArray.getJSONObject(i).getString("lat"));
                            double mLong = Double.parseDouble(jsonArray.getJSONObject(i).getString("long"));
                            list.add(new LatLng(lat, mLong));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                            .data(list)
                            .build();

                    TileOverlay mOverlay = gMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

                }else {
                    Log.d("Gagal", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        //--> List RS


        gMap.moveCamera(CameraUpdateFactory.newLatLng(starting));
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(starting,5.0f));

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        // Add a tile overlay to the map, using the heat map tile provider.

    }
}
