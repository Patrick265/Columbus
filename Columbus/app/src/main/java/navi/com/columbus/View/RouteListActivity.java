package navi.com.columbus.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import navi.com.columbus.DataModel.Route;
import navi.com.columbus.R;
import navi.com.columbus.ViewModel.RecyclerViewAdapter;

public class RouteListActivity extends AppCompatActivity
{
    ArrayList<Route> routes;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView routesTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        routesTitle = findViewById(R.id.rl_Title);
        routesTitle.setText(R.string.routeList_title);

        routes = new ArrayList<>();

        mRecyclerView = findViewById(R.id.rl_RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(routes);
        mRecyclerView.setAdapter(mAdapter);


        ImageButton helpButton = findViewById(R.id.rl_HelpButton);

        helpButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(v.getContext(), HelpActivity.class);
            startActivity(intent);
        });
    }
}
