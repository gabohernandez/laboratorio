package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;


import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapFragment extends Fragment {

    private Context context;

    public MapFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_test, container, false);

        this.context = container.getContext();

        Configuration.getInstance().load(context,PreferenceManager.getDefaultSharedPreferences(context));

        //setContentView(R.layout.activity_main);

        //MapView map = new MapView(context);

        MapView map = (MapView) view.findViewById(R.id.mapView);

        map.setTileSource(TileSourceFactory.MAPNIK);

        final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay (new GpsMyLocationProvider(context), map);
        map.getOverlays().add(myLocationoverlay);
        myLocationoverlay.enableMyLocation();

        IMapController mapController=map.getController();
        mapController.setZoom(10.5);
        GeoPoint startPoint = new GeoPoint(myLocationoverlay.getMyLocation().getLatitude(),myLocationoverlay.getMyLocation().getLongitude());
        mapController.setCenter(startPoint);

        return view;
    }


}
