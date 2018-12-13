package navi.com.columbus.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class ApiHandler {
    RequestQueue queue;
    HUEListener listener;
    String API_KEY = "6f7a0330-2e63-428c-9cdb-f314dc942ae2";
    SharedPreferences prefs;
    int attempt;
    Context context;

    //Constructor
    public ApiHandler(Context context, HUEListener listener, String username, SharedPreferences prefs) {
        this.listener = listener;
        queue = Volley.newRequestQueue(context);
        this.context = context;
        this.username = username;
        this.attempt = 0;
        this.prefs = prefs;
        if(username.equals("")) {
            this.deviceType = UUID.randomUUID().toString();
            newConnection();
        }
        else
        {this.deviceType = UUID.randomUUID().toString();
            newConnection();
            //connect(); //connect instead
        }
    }

    public void newConnection()
    {
        //TODO: check local network
        String url = ip;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("VOLLEY_TAG", response.toString());
                        try {

                            key = response.getJSONObject(0).keys().next();
                            if (!key.equals("error")) {
                                Toast.makeText(context, "Connected.", Toast.LENGTH_LONG).show();
                                username = response.getJSONObject(0).getJSONObject("success").getString("username");
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("username", username);
                                editor.apply();
                                listener.onConnectionSucces();

                            } else if (attempt <= 25) {
                                Log.d("VOLLEY_TAG", "PUSH the link button within 25 seconds to link the HUE Bridge to this app");
                                try {
                                    sleep(1000);
                                    newConnection();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                Toast.makeText(context, "Connection failed.", Toast.LENGTH_LONG).show();
                                listener.onConnectionFailed("");
                            }
                        } catch (JSONException e) {
                            Log.d("VOLLEY_TAG", e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG",error.toString() );
                        listener.onConnectionFailed("");
                    }
                }
        ){
            @Override
            public byte[] getBody() {
                String your_string_json = "{\"devicetype\":\""+ deviceType + "\"}"; // put your json
                return your_string_json.getBytes();
            };
        };

        attempt++;
        this.queue.add(request);
    }

    public void connect() {
        Log.d("VOLLEY_TAG", "USER FOUND : " + username);
        listener.onConnectionSucces();
    }

    public void getLights()
    {
        String url = ip + "/" + username + "/lights";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VOLLEY_TAG",response.toString());
                        ArrayList<Light> lights = new ArrayList<>();
                        for (int i=1; i <= response.names().length(); i++) {
                            try {
                                int id = i;
                                String modelid = response.getJSONObject(i+"").getString("modelid");
                                String name = response.getJSONObject(i+"").getString("name");
                                String SWversion = response.getJSONObject(i+"").getString("swversion");

                                JSONObject obj = response.getJSONObject(i+"").getJSONObject("state");
                                boolean on = obj.getBoolean("on");
                                int bri = obj.getInt("bri");
                                int hue = obj.getInt("hue");
                                int sat = obj.getInt("sat");
                                int ct = obj.getInt("ct");
                                LightState state = new LightState(on, bri, hue, sat, ct);

                                Light light = new Light(modelid, name, SWversion, state, id);
                                lights.add(light);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        listener.onLightsAvailable(lights);
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_TAG",error.toString() );
                        listener.onLightError("");
                    }
                }
        );

        this.queue.add(request);
    }

    public void changeState(int id,boolean on, int hue, int saturation, int brightness)
    {
        String url = ip + "/" + username + "/lights/"+id +"/state";
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("on", on);
            jsonObject.put("hue", hue);
            jsonObject.put("sat", saturation);
            jsonObject.put("bri", brightness);
        } catch (JSONException e) {

        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        this.queue.add(request);
    }

    public String getUsername() {
        return username;
    }
}