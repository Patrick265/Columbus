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

import navi.com.columbus.DataModel.Monument;
import navi.com.columbus.R;
import navi.com.columbus.Service.ApiHandler;
import navi.com.columbus.Service.BlindWallsDataHandler;
import navi.com.columbus.Service.BlindWallsListener;
import navi.com.columbus.Service.MapsListener;

public class GpsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, MapsListener, BlindWallsListener {

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
        lineOptions = null;
        BlindWallsDataHandler handler = new BlindWallsDataHandler(this, this);
        handler.getWalls();

        setContentView(R.layout.activity_gps);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }


        mapView = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gps_Map));
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
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
            List<LatLng> legs = PolyUtil.decode(jsonObject.getJSONArray("retrieveAllRoutes").getJSONObject(0).getJSONObject("overview_polyline").getString("points"));

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

    @Override
    public void onAllMonumentsAvailable(ArrayList<Monument> monuments) {
        ApiHandler test = new ApiHandler(this, listener);
        ArrayList<LatLng> path = new ArrayList<>();
        for(Monument monument: monuments)
        {
            path.add(new LatLng(monument.getLatitude(), monument.getLongitude()));
        }

        test.getDirections(new LatLng(51.594112, 4.779417),
                new LatLng(monuments.get(monuments.size()-1).getLatitude(),
                        monuments.get(monuments.size()-1).getLongitude()), path);
    }

    @Override
    public void onMonumentError(String err) {

    }
}
