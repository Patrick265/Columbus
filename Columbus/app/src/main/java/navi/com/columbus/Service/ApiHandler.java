package navi.com.columbus.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import navi.com.columbus.DataModel.Route;
import navi.com.columbus.R;

import static java.lang.Thread.sleep;

public class ApiHandler {
    RequestQueue queue;
    MapsListener listener;
    String URLDIRECTIONS = "https://maps.moviecast.io/directions?";
    String API_KEY = "6f7a0330-2e63-428c-9cdb-f314dc942ae2";
    Context context;

    //Constructor
    public ApiHandler(Context context, MapsListener listener) {
        this.listener = listener;
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public void getDirections(LatLng origin, LatLng destination, ArrayList<LatLng> path, Route route, Resources resources)
    {
        String waypointsPoly = PolyUtil.encode(path);
        String url = "";
        if(route.getName().equals(resources.getString(R.string.histkm_shortdescription)))
            url = URLDIRECTIONS + "origin=" + origin.latitude + "," + origin.longitude + "&destination=" + destination.latitude + "," + destination.longitude + "&waypoints=enc:" + waypointsPoly + ":&mode=walking&key=" + API_KEY;
        else
            url = URLDIRECTIONS + "origin=" + origin.latitude + "," + origin.longitude + "&destination=" + destination.latitude + "," + destination.longitude + "&waypoints=optimize:true|enc:" + waypointsPoly + ":&mode=walking&key=" + API_KEY;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRouteAvailable(response);
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRouteError(error);
                    }
                }
        );

        this.queue.add(request);
    }
}