package navi.com.columbus.View;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppComponentFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import navi.com.columbus.R;
import navi.com.columbus.Service.ApiHandler;
import navi.com.columbus.Service.MapsListener;

public class GpsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, MapsListener {

    private GoogleMap mMap;
    private SupportMapFragment mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private Location mLastLocation;
    private JSONObject jsonObject;
    private PolylineOptions lineOptions;
    private MapsListener listener;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = findViewById(R.id.gps_Title);
        title.setText(R.string.GPS_title);

        listener = this;
        ApiHandler test = new ApiHandler(this, listener);
        ArrayList<LatLng> path = new ArrayList<>();
        path.add(new LatLng(51.593278, 4.779388));
        path.add(new LatLng(51.592500, 4.779695));
        path.add(new LatLng(51.585843, 4.792213));
        test.getDirections(new LatLng(51.594112, 4.779417), new LatLng(51.592500, 4.779388), path);

        lineOptions = null;
        setContentView(R.layout.activity_gps);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }


        mapView = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gps_Map));
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        try {
            this.onRouteAvailable (new JSONObject("{\"geocoded_waypoints\":[{\"geocoder_status\":\"OK\",\"place_id\":\"ChIJhVDSf4SfxkcRpeJ9mLW2ORI\",\"types\":[\"street_address\"]},{\"geocoder_status\":\"OK\",\"place_id\":\"ChIJ20MtiIOfxkcRCbODmuKJumQ\",\"types\":[\"street_address\"]},{\"geocoder_status\":\"OK\",\"place_id\":\"ChIJj1Bm8YOfxkcRig_s8BGMTgQ\",\"types\":[\"establishment\",\"point_of_interest\"]},{\"geocoder_status\":\"OK\",\"place_id\":\"ChIJj1Bm8YOfxkcRig_s8BGMTgQ\",\"types\":[\"establishment\",\"point_of_interest\"]}],\"routes\":[{\"bounds\":{\"northeast\":{\"lat\":51.5941123,\"lng\":4.7797342},\"southwest\":{\"lat\":51.5924412,\"lng\":4.7793873}},\"copyrights\":\"Map data ©2018 Google\",\"legs\":[{\"distance\":{\"text\":\"92 m\",\"value\":92},\"duration\":{\"text\":\"1 min\",\"value\":69},\"end_address\":\"Willemstraat 1, 4811 AH Breda, Netherlands\",\"end_location\":{\"lat\":51.5932922,\"lng\":4.7795478},\"start_address\":\"Willemstraat 19, 4811 AJ Breda, Netherlands\",\"start_location\":{\"lat\":51.5941123,\"lng\":4.779421399999999},\"steps\":[{\"distance\":{\"text\":\"92 m\",\"value\":92},\"duration\":{\"text\":\"1 min\",\"value\":69},\"end_location\":{\"lat\":51.5932922,\"lng\":4.7795478},\"html_instructions\":\"Head <b>south</b> on <b>Willemstraat</b> toward <b>Academiesingel</b>\",\"polyline\":{\"points\":\"e~{yHknd\\\\vAMn@EJADAHA\"},\"start_location\":{\"lat\":51.5941123,\"lng\":4.779421399999999},\"travel_mode\":\"WALKING\"}],\"traffic_speed_entry\":[],\"via_waypoint\":[]},{\"distance\":{\"text\":\"0.1 km\",\"value\":117},\"duration\":{\"text\":\"1 min\",\"value\":86},\"end_address\":\"Delpratsingel 1, 4811 AM Breda, Netherlands\",\"end_location\":{\"lat\":51.5924412,\"lng\":4.7796313},\"start_address\":\"Willemstraat 1, 4811 AH Breda, Netherlands\",\"start_location\":{\"lat\":51.5932922,\"lng\":4.7795478},\"steps\":[{\"distance\":{\"text\":\"25 m\",\"value\":25},\"duration\":{\"text\":\"1 min\",\"value\":19},\"end_location\":{\"lat\":51.5930657,\"lng\":4.7795611},\"html_instructions\":\"Head <b>south</b> on <b>Willemstraat</b> toward <b>Academiesingel</b>\",\"polyline\":{\"points\":\"ay{yHeod\\\\@?h@A\"},\"start_location\":{\"lat\":51.5932922,\"lng\":4.7795478},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"10 m\",\"value\":10},\"duration\":{\"text\":\"1 min\",\"value\":8},\"end_location\":{\"lat\":51.5930279,\"lng\":4.779693},\"html_instructions\":\"Turn <b>left</b> onto <b>Academiesingel</b>/<b>Delpratsingel</b>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"uw{yHgod\\\\FY\"},\"start_location\":{\"lat\":51.5930657,\"lng\":4.7795611},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"43 m\",\"value\":43},\"duration\":{\"text\":\"1 min\",\"value\":31},\"end_location\":{\"lat\":51.5926419,\"lng\":4.7797342},\"html_instructions\":\"Turn <b>right</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"mw{yHapd\\\\n@?\\\\G\"},\"start_location\":{\"lat\":51.5930279,\"lng\":4.779693},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"24 m\",\"value\":24},\"duration\":{\"text\":\"1 min\",\"value\":17},\"end_location\":{\"lat\":51.592517,\"lng\":4.7794484},\"html_instructions\":\"Turn <b>right</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"_u{yHipd\\\\Vv@\"},\"start_location\":{\"lat\":51.5926419,\"lng\":4.7797342},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"15 m\",\"value\":15},\"duration\":{\"text\":\"1 min\",\"value\":11},\"end_location\":{\"lat\":51.5924412,\"lng\":4.7796313},\"html_instructions\":\"Turn <b>left</b><div style=\\\"font-size:0.9em\\\">Destination will be on the left</div>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"gt{yHqnd\\\\FOFS\"},\"start_location\":{\"lat\":51.592517,\"lng\":4.7794484},\"travel_mode\":\"WALKING\"}],\"traffic_speed_entry\":[],\"via_waypoint\":[]},{\"distance\":{\"text\":\"20 m\",\"value\":20},\"duration\":{\"text\":\"1 min\",\"value\":15},\"end_address\":\"Delpratsingel 1, 4811 AM Breda, Netherlands\",\"end_location\":{\"lat\":51.592501,\"lng\":4.7793873},\"start_address\":\"Delpratsingel 1, 4811 AM Breda, Netherlands\",\"start_location\":{\"lat\":51.5924412,\"lng\":4.7796313},\"steps\":[{\"distance\":{\"text\":\"15 m\",\"value\":15},\"duration\":{\"text\":\"1 min\",\"value\":11},\"end_location\":{\"lat\":51.592517,\"lng\":4.7794484},\"html_instructions\":\"Head <b>northwest</b>\",\"polyline\":{\"points\":\"ws{yHuod\\\\GRGN\"},\"start_location\":{\"lat\":51.5924412,\"lng\":4.7796313},\"travel_mode\":\"WALKING\"},{\"distance\":{\"text\":\"5 m\",\"value\":5},\"duration\":{\"text\":\"1 min\",\"value\":4},\"end_location\":{\"lat\":51.592501,\"lng\":4.7793873},\"html_instructions\":\"Turn <b>left</b>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"gt{yHqnd\\\\BJ\"},\"start_location\":{\"lat\":51.592517,\"lng\":4.7794484},\"travel_mode\":\"WALKING\"}],\"traffic_speed_entry\":[],\"via_waypoint\":[]}],\"overview_polyline\":{\"points\":\"e~{yHknd\\\\xCWJAh@AFYn@?\\\\GVv@FOFSGRGNBJ\"},\"summary\":\"Willemstraat\",\"warnings\":[\"Walking directions are in beta.    Use caution – This route may be missing sidewalks or pedestrian paths.\"],\"waypoint_order\":[0,1]}],\"status\":\"OK\"}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setMapToolbarEnabled(false);
        settings.setCompassEnabled(true);
        mMap = googleMap;
        if(lineOptions != null)
            mMap.addPolyline(lineOptions);

        mMap.setMinZoomPreference(12);
        LatLng sydney = new LatLng(51.585843, 4.792213);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Avans Hogeschool Breda"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
        mMap.setMyLocationEnabled(true);
        settings.setMyLocationButtonEnabled(true);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        // use latitude and longitude given by
        // location.getLatitude(), location.getLongitude()
        // for updated location marker
        Log.d("aaaaaaaa===>", "" + location.getLatitude() + "\n" + location.getLongitude());
        // displayLocation();

        // to remove old markers
        mMap.clear();
        final LatLng loc = new LatLng(location.getLongitude(), location.getLongitude());

        Marker ham = mMap.addMarker(new MarkerOptions().position(loc).title("This is Me").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRouteAvailable(JSONObject object) {
        try {
            jsonObject = object;
            List<LatLng> legs = PolyUtil.decode(jsonObject.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points"));

            lineOptions = new PolylineOptions();
            lineOptions.addAll(legs);
            lineOptions.width(10);
            lineOptions.color(Color.RED);
            if(mMap != null)
                mMap.addPolyline(lineOptions);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRouteError(VolleyError error) {

    }
}
