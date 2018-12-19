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
import java.util.List;
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
import navi.com.columbus.Service.DataStorage;
import navi.com.columbus.Service.RecyclerItemClickListener;

public class RouteListActivity extends AppCompatActivity implements BlindWallsListener
{
    private ArrayList<Route> routes;
    private ArrayList<Monument> monumentsBlindwall;
    private DataStorage storage;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView routesTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        initalise();


    }


    private void initalise() {
        this.storage = new DataStorage(getApplicationContext());
        this.routes = new ArrayList<>();


        //region ANDROID COMPONENTS



        routesTitle = findViewById(R.id.rl_Title);
        routesTitle.setText(R.string.routeList_title);

        mRecyclerView = findViewById(R.id.rl_RecyclerView);
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


        //endregion


        //region DATA LOGIC


        HistorischeKMFactory historischeKMFactory = new HistorischeKMFactory();

        BlindWallsListener listener = this;
        BlindWallsDataHandler handler = new BlindWallsDataHandler(this, listener);

        storage.retrieveAllRoutes().size();
        if(storage.retrieveAllRoutes().size() == 0) {
            handler.getWalls();
            routes.add(historischeKMFactory.getHistorischeKilometer(this));
            Log.i("DB", "ADDED FROM VOLLEY");
        } else {
            Log.i("DB", "ADDED FROM DATABASE");

            this.routes.addAll(this.storage.retrieveAllRoutes());
            this.mAdapter.notifyDataSetChanged();
        }

        //endregion


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
                new Route.Builder().description(getResources().getString(R.string.bw_description))
                        .name(getResources().getString(R.string.bw_shortdescription))
                        .routeList(monumentsBlindwall)
                        .build()
        );


        for(Route route : this.routes) {
            this.storage.addRoute(route);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMonumentError(String err)
    {

    }
}
