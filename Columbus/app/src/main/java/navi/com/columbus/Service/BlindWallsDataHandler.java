package navi.com.columbus.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

import navi.com.columbus.DataModel.Monument;

public class BlindWallsDataHandler {

    RequestQueue queue;
    BlindWallsListener listener;

    //Constructor
    public BlindWallsDataHandler(Context context, BlindWallsListener listener) {
        this.listener = listener;
        queue = Volley.newRequestQueue(context);
    }

    // alle murals ophalen
    public void getWalls(){
        String url = "https://api.blindwalls.gallery/apiv2/murals";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("VOLLEY_TAG",response.toString());
                        for (int i=0; i < response.length(); i++) {
                            try {
                                String name = response.getJSONObject(i).getString("");
                                String creator = response.getJSONObject(i).getString("author");
                                String desc = response.getJSONObject(i).getJSONObject("description").getString("nl");
                                JSONArray images = response.getJSONObject(i).getJSONArray("images");
                                int index = new Random().nextInt(images.length());
                                String imageURL = "https://api.blindwalls.gallery/" + images.getJSONObject(index).getString("url");
                                Monument monument = new Monument.Builder().description(desc).build();
                                listener.onMonumentAvailable(monument);
                                Log.d("VOLLEY_TAG", monument.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG",error.toString() );
                        listener.onMonumentError("");
                    }
                }
        );

        this.queue.add(request);
    }


}
