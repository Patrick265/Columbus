package navi.com.columbus.Service;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface MapsListener {
    public void onRouteAvailable(JSONObject object);

    public void onRouteError(VolleyError error);
}
