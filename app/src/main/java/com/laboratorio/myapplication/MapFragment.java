package com.laboratorio.myapplication;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;


import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.core.content.ContextCompat;

import com.laboratorio.myapplication.gps.GPSTracker;
import com.laboratorio.myapplication.model.Node;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;

public class MapFragment extends Fragment {

    private Context context;
    private MapView map;

    public MapFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_test, container, false);

        this.context = container.getContext();

        Configuration.getInstance().load(context,PreferenceManager.getDefaultSharedPreferences(context));

        map = (MapView) view.findViewById(R.id.mapView);

        map.setTileSource(TileSourceFactory.MAPNIK);

        final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay (new GpsMyLocationProvider(context), map);
        map.getOverlays().add(myLocationoverlay);
        myLocationoverlay.enableMyLocation();

        IMapController mapController = map.getController();
        mapController.setZoom(15.00);

        GPSTracker gpstracker = new GPSTracker(context);

        Double latitude = gpstracker.getLatitude();
        Double longitude = gpstracker.getLongitude();

        this.setNodes();

        GeoPoint userPoint = new GeoPoint(latitude,longitude);

        Marker userMarker = new Marker(map);
        userMarker.setPosition(userPoint);
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(userMarker);

        mapController.setCenter(userPoint);


        return view;
    }

    private void setNodes(){
        if (context instanceof MainActivity) {
            List<Node> nodes = ((MainActivity) context).getNodes();
            nodes.forEach(node -> {
                Double latitude = node.getAddress().getLatitude();
                Double longitude = node.getAddress().getLongitude();
                GeoPoint nodePoint = new GeoPoint(latitude,longitude);
                Marker nodeMarker = new Marker(map);
                nodeMarker.setPosition(nodePoint);
                nodeMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                map.getOverlays().add(nodeMarker);
            });
        }
    }

}
