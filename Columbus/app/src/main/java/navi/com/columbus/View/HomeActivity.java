package navi.com.columbus.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import navi.com.columbus.R;

public class HomeActivity extends AppCompatActivity
{
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private TextView homeTitle;
    private TextView appName;
    private TextView buttonText;
    private TextView possibleBy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeTitle = findViewById(R.id.home_Title);
        homeTitle.setText(R.string.home_title);

        appName = findViewById(R.id.home_AppName);
        appName.setText(R.string.app_name);

        buttonText = findViewById(R.id.home_RoutesButton);
        buttonText.setText(R.string.home_routeButton);

        possibleBy = findViewById(R.id.home_VVVMessage);
        possibleBy.setText(R.string.home_possibleBy);

        ImageButton helpButton = findViewById(R.id.home_HelpButton);
        helpButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(v.getContext(), HelpActivity.class);

            intent.putExtra("HELP_TEXT", getResources().getString(R.string.home_info));

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            }
            else
            {
                checkLocationPermission();
            }
        }

        boolean connected = isConnected();
        if(!connected)
        {
            checkInternetAvailable();
        }
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

    private void checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme);
                dialogBuilder
                        .setTitle(R.string.permission_title)
                        .setMessage(R.string.permission_message)
                        .setPositiveButton("Ok", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION );
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {

                    }

                }
                else
                {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme);
                    dialogBuilder
                            .setTitle(R.string.permission_title)
                            .setMessage(R.string.permission_message)
                            .setPositiveButton("Ok", (dialogInterface, i) -> {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(HomeActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            })
                            .create()
                            .show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
