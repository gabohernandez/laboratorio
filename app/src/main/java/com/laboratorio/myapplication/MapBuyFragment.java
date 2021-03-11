package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;


import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laboratorio.myapplication.gps.GPSTracker;
import com.laboratorio.myapplication.model.Node;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapBuyFragment extends Fragment {

    private Context context;
    private MapView map;
    private List<Node> nodes;
    private GeoPoint userPoint;

    public MapBuyFragment(){

    }

    public void setNodes(List<Node> nodes){
        this.nodes = nodes;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.map_buy, container, false);

        this.context = container.getContext();

        Configuration.getInstance().load(context,PreferenceManager.getDefaultSharedPreferences(context));

        map = view.findViewById(R.id.mapView);

        map.setTileSource(TileSourceFactory.MAPNIK);

        final MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay (new GpsMyLocationProvider(context), map);
        map.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableMyLocation();

        IMapController mapController = map.getController();
        mapController.setZoom(15.00);
        map.setMultiTouchControls(true);

        GPSTracker gpstracker = new GPSTracker(context);

        double latitude = gpstracker.getLatitude();
        double longitude = gpstracker.getLongitude();

        this.setNodes();

        userPoint = new GeoPoint(latitude,longitude);

        Marker userMarker = new Marker(map);
        userMarker.setPosition(userPoint);
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(userMarker);

        mapController.setCenter(userPoint);

        return view;
    }

    private void setNodes(){
        if (context instanceof MainActivity) {
            nodes.forEach(node -> {
                double latitude = node.getAddress().getLatitude();
                double longitude = node.getAddress().getLongitude();
                GeoPoint nodePoint = new GeoPoint(latitude,longitude);
                Marker nodeMarker = new Marker(map);
                nodeMarker.setPosition(nodePoint);
                nodeMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                nodeMarker.setOnMarkerClickListener((marker, mapView) -> {
/*                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);*/
                    double[] distance;
                    distance = constructRoad(userPoint,nodePoint);
                    ((MainActivity) context).showLastStep(node,distance);
                    return false;
                });
                map.getOverlays().add(nodeMarker);
            });
        }
    }

    public double[] constructRoad(GeoPoint user, GeoPoint node){
        final double[] distanceandtime = {0.0,0.0};
        ArrayList<GeoPoint> wayPoints= new ArrayList<GeoPoint>();
        wayPoints.add(user);
        wayPoints.add(node);
        ExecutorService executor= Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            RoadManager roadManager= new OSRMRoadManager(context);
            Road road=  roadManager.getRoad(wayPoints);
            if(road.mStatus!= Road.STATUS_OK)
                Log.e("Fragment", "Error al Calcular Ruta!!");
            Polyline roadOverlay= RoadManager.buildRoadOverlay(road);
            distanceandtime[0] = road.mDuration;
            distanceandtime[1] = road.mLength;
        });
        return distanceandtime;
    }

}
