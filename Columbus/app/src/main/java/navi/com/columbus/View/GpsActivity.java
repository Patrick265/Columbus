package navi.com.columbus.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import navi.com.columbus.DataModel.Monument;
import navi.com.columbus.DataModel.Route;
import navi.com.columbus.R;
import navi.com.columbus.Service.ApiHandler;
import navi.com.columbus.Service.DataStorage;
import navi.com.columbus.Service.LocationCallBackListener;
import navi.com.columbus.Service.LocationCallbackHandler;
import navi.com.columbus.Service.MapsListener;
import navi.com.columbus.Service.NotificationService;
import navi.com.columbus.Service.SharedPreferencesClass;

public class GpsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, MapsListener, LocationCallBackListener, OnMarkerClickListener
{
    private GoogleMap mMap;
    private SupportMapFragment mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private PolylineOptions lineOptions;
    private MapsListener listener;
    private Location lastLocation;
    private GpsActivity gpsActivity;
    private ArrayList<Monument> monuments;
    private List<LatLng> legs;
    private PolylineOptions lineOptions2;
    private TextView distanceLeft;
    private ConstraintLayout bottomBar;
    private int totalDistance;
    private Polyline mPolyLine;
    private Polyline mPolyLine2;
    private DataStorage storage;
    private Dialog dMessage;
    private Route route;
    private ArrayList<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        markers = new ArrayList<>();
        storage = new DataStorage(getApplicationContext());
        dMessage = new Dialog(this);
        distanceLeft = findViewById(R.id.gps_Distance);
        bottomBar = findViewById(R.id.gps_BottomBar);
        bottomBar.setVisibility(View.INVISIBLE);
        lastLocation = null;
        gpsActivity = this;
        monuments = new ArrayList<>();

        this.route = (Route)getIntent().getExtras().get("ROUTE");

        LocationCallbackHandler loc = new LocationCallbackHandler();
        loc.addListener(this);

        listener = this;
        lineOptions = null;

        monuments = route.getMonumentList();
        if(route.getName().equals(getResources().getString(R.string.bw_shortdescription)))
        {
            callAPI(route);
        }
        else if(route.getName().equals(getResources().getString(R.string.histkm_shortdescription)))
        {
            callAPI(route);
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null)
        {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        ImageButton helpButton = findViewById(R.id.gps_HelpButton);
        helpButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(v.getContext(), HelpActivity.class);

            intent.putExtra("HELP_TEXT", getResources().getString(R.string.gps_info));

            startActivity(intent);
        });


        mapView = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gps_Map));
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        Context context = getApplicationContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            startForegroundService(new Intent(context, NotificationService.class));
        }
        else
        {
            context.startService(new Intent(context, NotificationService.class));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap)
    {
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setMapToolbarEnabled(false);
        settings.setCompassEnabled(true);
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        mMap.setMinZoomPreference(12);
        LatLng currentLocation = new LatLng(51.585843, 4.792213);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
        mMap.setMyLocationEnabled(true);
        settings.setMyLocationButtonEnabled(true);
    }

    @Override
    public void onLocationChanged(Location location)
    {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }

    @Override
    public void onProviderEnabled(String s)
    {

    }

    @Override
    public void onProviderDisabled(String s)
    {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRouteAvailable(JSONObject object)
    {
        try
        {
            legs = PolyUtil.decode(object.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points"));

            lineOptions2 = new PolylineOptions();
            lineOptions2.addAll(legs);
            lineOptions2.width(15);
            lineOptions2.color(Color.GRAY);
            lineOptions = new PolylineOptions();
            lineOptions.addAll(legs);
            lineOptions.width(15);
            lineOptions.color(getResources().getColor(R.color.colorPrimary));
            if(mMap != null)
            {
                mMap.addPolyline(lineOptions2);
                mMap.addPolyline(lineOptions);
                totalDistance = (int)SphericalUtil.computeLength(lineOptions2.getPoints());
                distanceLeft.setText("± "+totalDistance/10*10+ "/" + totalDistance/10*10 + " meter");

                TextView distanceLeftTitle = findViewById(R.id.gps_DistanceLeftText);
                distanceLeftTitle.setText(getResources().getString(R.string.distance_left_title));

                ImageButton cancelRoute = findViewById(R.id.gps_CancelRouteButton);
                cancelRoute.setOnClickListener(v -> this.finish());

                bottomBar.setVisibility(View.VISIBLE);

                ConstraintLayout c = findViewById(R.id.gps_ConstraintLayout);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(c);
                constraintSet.connect(R.id.gps_Map, ConstraintSet.BOTTOM, R.id.gps_BottomBar, ConstraintSet.TOP,0);
                constraintSet.applyTo(c);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (LatLng latLng : lineOptions2.getPoints())
                {
                    builder.include(latLng);
                }

                final LatLngBounds bounds = builder.build();

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                mMap.animateCamera(cu);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRouteError(VolleyError error)
    {

    }

    public void callAPI(Route route)
    {
        ApiHandler test = new ApiHandler(this, listener);
        ArrayList<LatLng> path = new ArrayList<>();

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if (lastLocation != null && mMap != null)
                {
                    timer.cancel();
                    gpsActivity.runOnUiThread(() ->
                    {
                        int i = 0;
                        for (Monument monument: route.getMonumentList())
                        {
                            if (i < 24)
                            {
                                LatLng m = new LatLng(monument.getLatitude(), monument.getLongitude());

                                if(monument.isVisited())
                                    markers.add(mMap.addMarker(new MarkerOptions().position(m).title(monument.getName()).icon(BitmapDescriptorFactory.fromBitmap(resizeIcon(R.drawable.location_pin_visited)))));
                                else
                                    markers.add(mMap.addMarker(new MarkerOptions().position(m).title(monument.getName()).icon(BitmapDescriptorFactory.fromBitmap(resizeIcon(R.drawable.location_pin)))));

                                markers.get(markers.size()-1).setTag(monument);

                                if(i < 23)
                                    path.add(new LatLng(monument.getLatitude(), monument.getLongitude()));
                            }

                            i++;
                        }

                        test.getDirections(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()),
                                new LatLng(route.getMonumentList().get(route.getMonumentList().size() - 1).getLatitude(),
                                        route.getMonumentList().get(route.getMonumentList().size() - 1).getLongitude()), path, route, getResources());
                    });
                }
            }
        },0, 1000);
    }

    public Bitmap resizeIcon(int imagelocation)
    {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), imagelocation);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 43, 70, false);
        return resizedBitmap;
    }

    private void checkInternetAvailable()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme);
        dialogBuilder
                .setTitle(R.string.internet_title)
                .setMessage(R.string.requires_internet)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    if(!isConnected())
                    {
                        checkInternetAvailable();
                    }
                })
                .create()
                .show();
    }

    private boolean isConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        return connected;
    }

    @Override
    public void onLocationAvailable(Location location)
    {
        boolean connected = isConnected();
        if(!connected)
        {
            checkInternetAvailable();
        }
        this.lastLocation = location;
        float notificationDistance = 20.0f;
        Monument closestMonument = null;
        float distance;
        for (Monument monument : monuments)
        {
            Location monumentLocation = new Location("Monument");
            monumentLocation.setLatitude(monument.getLatitude());
            monumentLocation.setLongitude(monument.getLongitude());

            distance = location.distanceTo(monumentLocation);
            if (distance < notificationDistance && !monument.isVisited())
            {
                notificationDistance = distance;
                closestMonument = monument;
            }
        }

        if (closestMonument != null)
        {
            closestMonument.setVisited(true);
            this.storage.updateMonument(closestMonument);

            showMessage(closestMonument);

            for(Marker marker: markers)
            {
                if(marker.getPosition().latitude == closestMonument.getLatitude() && marker.getPosition().longitude == closestMonument.getLongitude() )
                {
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizeIcon(R.drawable.location_pin_visited)));
                    break;
                }
            }
        }

        int counter = 0;
        for (Monument monument : monuments)
        {
            if (monument.isVisited())
            {
                counter++;
            }
            if (counter == monuments.size())
            {
                this.route.setFinished(true);
                this.storage.updateRoute(this.route);
            }
        }


        float killDistance = 50.0f;
        if (legs != null)
        {
            boolean onPath = PolyUtil.isLocationOnPath(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), legs, false, 50);
            if (onPath)
            {
                List<LatLng> points = new ArrayList<>(lineOptions.getPoints());
                for (LatLng point : points)
                {
                    Location legLoc = new Location("wow");
                    legLoc.setLatitude(point.latitude);
                    legLoc.setLongitude(point.longitude);

                    distance = location.distanceTo(legLoc);
                    if (distance < killDistance)
                    {
                        lineOptions.getPoints().remove(point);
                        int d1 = (int) SphericalUtil.computeLength(lineOptions.getPoints());
                        distanceLeft.setText("± " + d1 / 100 * 100 + "/" + totalDistance / 100 * 100 + " meter");
                        if (mPolyLine != null)
                        {
                            mPolyLine.remove();
                            mPolyLine2.remove();
                        }
                        mPolyLine = this.mMap.addPolyline(lineOptions2);
                        mPolyLine2 = this.mMap.addPolyline(lineOptions);
                    }
                }
            }
        }
    }

    private void showMessage(Monument monument)
    {
        try
        {
            dMessage.setContentView(R.layout.notification);
            dMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dMessage.show();

            TextView titleView = dMessage.findViewById(R.id.not_Title);
            TextView messageView = dMessage.findViewById(R.id.not_Message);
            TextView tapMessageView = dMessage.findViewById(R.id.not_TapMessage);
            TextView monNameView = dMessage.findViewById(R.id.not_monName);

            titleView.setText(R.string.not_titleString);
            messageView.setText(R.string.not_messString_start);
            tapMessageView.setText(R.string.not_TapMessString);
            monNameView.setText(monument.getName());

        } catch(Exception e){
            Log.d("ERROR", e.toString());
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        NotificationFragment dialog = new NotificationFragment();
        if (marker.getTag() != null)
        {
            Monument clickedMonument = (Monument) marker.getTag();
            Bundle args = new Bundle();
//            args.putString("monumentName", clickedMonument.getName());
//            args.putString("makers", clickedMonument.getCreator());
//            args.putString("constructionYear", String.valueOf(clickedMonument.getConstructionYear()));
//            args.putString("imageURL", clickedMonument.getImageURL());
//            args.putString("description", clickedMonument.getDescription());
            args.putSerializable("monument", clickedMonument);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "MyCustomDialog");
        }
        return true;
    }
}

class MonumentVisited implements Serializable {
    private Monument monument;
    private boolean isVisited;

    public MonumentVisited(Monument monument, boolean isVisited) {
        this.monument = monument;
        this.isVisited = isVisited;
    }

    public Monument getMonument() {
        return monument;
    }

    public void setMonument(Monument monument) {
        this.monument = monument;
    }

    public boolean getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }
}
