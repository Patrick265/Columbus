package navi.com.columbus.Service;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.List;

public class LocationCallbackHandler extends LocationCallback {
    private static LocationCallbackHandler instance;
    private List<Location> locationList;

    public LocationCallbackHandler()
    {
        instance = this;
    }

    public static LocationCallbackHandler getInstance() { return instance;}

    @Override
    public void onLocationResult(LocationResult locationResult) {
        locationList = locationResult.getLocations();

        if(locationList.size() > 0)
        {
            Location location = locationList.get(locationList.size() - 1);
            Log.i("AIDSCODE", locationResult.getLastLocation().getLatitude() + " " + locationResult.getLastLocation().getLongitude());
        }
    }
}
