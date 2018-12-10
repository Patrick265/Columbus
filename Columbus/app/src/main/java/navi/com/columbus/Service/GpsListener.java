package navi.com.columbus.Service;

import android.location.Location;
import android.os.Bundle;

public interface GpsListener {
    public void onLocationChanged(Location location);
    public void onProviderDisabled(String arg0);
    public void onProviderEnabled(String arg0);
    public void onStatusChanged(String arg0, int arg1, Bundle bundle);
}
