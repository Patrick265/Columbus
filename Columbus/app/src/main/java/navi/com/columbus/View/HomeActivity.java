package navi.com.columbus.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

import navi.com.columbus.R;
import navi.com.columbus.Service.ApiHandler;
import navi.com.columbus.Service.MapsListener;
import navi.com.columbus.Service.SharedPreferencesClass;

public class HomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v ->
        {
            Intent intent = new Intent(v.getContext(), GpsActivity.class);
            startActivity(intent);
        });

        ImageButton helpButton = findViewById(R.id.home_HelpButton);
        helpButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(v.getContext(), HelpActivity.class);
            startActivity(intent);
        });

        Button routesButton = findViewById(R.id.home_RoutesButton);
        routesButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(v.getContext(), RouteListActivity.class);
            startActivity(intent);
        });

        ImageView vvvLogo = findViewById(R.id.home_VVVLogo);
        vvvLogo.setOnClickListener(v ->
        {
            Uri uriUrl = Uri.parse("https://vvvbreda.nl/");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        });
    }
}
