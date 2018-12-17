package navi.com.columbus.View;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import navi.com.columbus.Adapter.RecyclerViewAdapter;
import navi.com.columbus.DataModel.HistorischeKMFactory;
import navi.com.columbus.DataModel.Monument;
import navi.com.columbus.DataModel.Route;
import navi.com.columbus.R;
import navi.com.columbus.Service.ApiHandler;
import navi.com.columbus.Service.BlindWallsDataHandler;
import navi.com.columbus.Service.BlindWallsListener;
import navi.com.columbus.Service.RecyclerItemClickListener;

public class RouteListActivity extends AppCompatActivity implements BlindWallsListener
{
    private ArrayList<Route> routes;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView routesTitle;
    private Dialog dMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);



        dMessage = new Dialog(this);
        Button errorMessageOpen = findViewById(R.id.rl_ErrorTest);
        errorMessageOpen.setOnClickListener(v -> showMessage("Je moeder", "Dit is een kort test tekstje. De kat krabt de krullen van de kanker trap."));



        routesTitle = findViewById(R.id.rl_Title);
        routesTitle.setText(R.string.routeList_title);

        routes = new ArrayList<>();
        HistorischeKMFactory historischeKMFactory = new HistorischeKMFactory();
        ArrayList<Monument> blindWallMonuments = new ArrayList<>();


        routes.add(historischeKMFactory.getHistorischeKilometer());
        BlindWallsListener listener = this;
        BlindWallsDataHandler handler = new BlindWallsDataHandler(this, listener);
        handler.getWalls();

        mRecyclerView = findViewById(R.id.rl_RecyclerView);
        //mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(routes, route1 -> {
            Intent intent = new Intent(getApplicationContext(), GpsActivity.class);
            intent.putExtra("ROUTE", route1);

            startActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);

        ImageButton helpButton = findViewById(R.id.rl_HelpButton);

        helpButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(v.getContext(), HelpActivity.class);
            startActivity(intent);
        });
    }

    private void showMessage(String title, String message)
    {
        try
        {
            dMessage.setContentView(R.layout.notification);
            dMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dMessage.show();

            TextView titleView = dMessage.findViewById(R.id.not_Title);
            TextView messageView = dMessage.findViewById(R.id.not_Message);
            Button okButton = dMessage.findViewById(R.id.not_OkButton);

            titleView.setText(title);
            messageView.setText(message);

            okButton.setOnClickListener(v1 -> dMessage.dismiss());

        } catch(Exception e){
            Log.d("ERROR", e.toString());
        }
    }

    @Override
    public void onAllMonumentsAvailable(ArrayList<Monument> monuments)
    {
        ArrayList<Monument> monumentsBlindwall = new ArrayList<>();
        int i = 0;
        for (Monument monument: monuments)
        {
            if (i < 23)
            {
                monumentsBlindwall.add(monument);
            }
            i++;
        }

        routes.add(
                new Route.Builder().description("BlindWalls")
                        .name("De route van Blind walls")
                        .routeList(monumentsBlindwall)
                        .build()
        );
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMonumentError(String err) {

    }
}
