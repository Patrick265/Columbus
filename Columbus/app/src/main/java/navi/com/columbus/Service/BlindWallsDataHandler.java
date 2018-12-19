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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
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
    public boolean getWalls(){
        String url = "https://api.blindwalls.gallery/apiv2/murals";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response ->
                {
                    Log.d("VOLLEY_TAG",response.toString());
                    String language;
                    if(Locale.getDefault().getLanguage().equals("nl")){
                        language = "nl";
                    }
                    else{
                        language = "en";
                    }
                    ArrayList<Monument> monuments = new ArrayList<>();
                    for (int i=0; i < response.length(); i++) {
                        try {
                            JSONObject o = response.getJSONObject(i);
                            String name = o.getString("author");
                            String desc = o.getJSONObject("description").getString(language);
                            String creator = o.getString("author");
                            //Blind Walls hebben geen sound;
                            JSONArray images = o.getJSONArray("images");
                            int index = new Random().nextInt(images.length());
                            String imageURL = "https://api.blindwalls.gallery/" + images.getJSONObject(1).getString("url");

                            Double longitude = o.getDouble("longitude");
                            Double latitude = o.getDouble("latitude");
                            int constructionyear = o.getInt("year");
                            Monument monument = new Monument.Builder()
                                    .name(name)
                                    .description(desc)
                                    .creator(creator)
                                    .imageURL(imageURL)
                                    .longitude(longitude)
                                    .latitude(latitude)
                                    .constructionYear(constructionyear)
                                    .build();
                            monuments.add(monument);
                            Log.d("VOLLEY_TAG", monument.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listener.onAllMonumentsAvailable(monuments);

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
        return true;
    }


}
