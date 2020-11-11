package com.kominfo.anaksehat;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.controllers.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private List<LatLng> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
//        list.add(new LatLng(-6.914744, 107.609810));
//        list.add(new LatLng(-6.714744, 107.609810));
//        list.add(new LatLng(-6.814744, 107.609810));
//        list.add(new LatLng(-6.814744, 106.609810));
//        list.add(new LatLng(-6.814744, 107.009810));

        //--> List RS

        list.add(new LatLng(-7.169225, 111.221447));
        list.add(new LatLng(-7.083177, 110.915055));
        list.add(new LatLng(-7.093826, 110.918230));
        list.add(new LatLng(-7.060393, 110.667558));
        list.add(new LatLng(-7.084657, 110.920177));

        //--> List Poliklinik
        list.add(new LatLng(-7.094637, 110.909302));
        list.add(new LatLng(-7.080077, 110.915303));
        list.add(new LatLng(-7.095933, 110.913146));
        list.add(new LatLng(-7.074137, 110.885962));
        list.add(new LatLng(-7.089336, 110.705310));
        list.add(new LatLng(-7.031985, 110.920226));


        gMap.moveCamera(CameraUpdateFactory.newLatLng(starting));
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(starting,5.0f));

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        TileOverlay mOverlay = gMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

    }
}
