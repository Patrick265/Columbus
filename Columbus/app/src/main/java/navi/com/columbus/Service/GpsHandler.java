package navi.com.columbus.Service;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class GpsHandler {
    private final int PERMISSION_REQUEST_CODE = 698;
    public static final String PERMISSION_STRING = android.Manifest.permission.ACCESS_FINE_LOCATION;
    final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    GpsListener listener;
    LocationListener locationListener;
    private LocationManager locManager;
    private Activity activity;

    public GpsHandler(Activity activity, GpsListener listener) {
        this.listener = listener;
        this.activity = activity;
        setLocationListener();
        setPermissions();
    }

    private void setLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                listener.onLocationChanged(location);
            }

            @Override
            public void onProviderDisabled(String arg0) {
                listener.onProviderDisabled(arg0);
            }

            @Override
            public void onProviderEnabled(String arg0) {
                listener.onProviderEnabled(arg0);
            }

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle bundle) {
                onStatusChanged(arg0, arg1, bundle);
            }
        };

        locManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    public void setPermissions() {
        if (ContextCompat.checkSelfPermission(activity, PERMISSION_STRING)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{PERMISSION_STRING}, PERMISSION_REQUEST_CODE);
            return;
        }

        locManager.requestLocationUpdates(LOCATION_PROVIDER, 1000, 1, locationListener);
    }
}
