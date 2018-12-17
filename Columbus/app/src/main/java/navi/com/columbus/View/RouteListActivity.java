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

import java.util.ArrayList;

import navi.com.columbus.Adapter.RecyclerViewAdapter;
import navi.com.columbus.DataModel.Route;
import navi.com.columbus.R;
import navi.com.columbus.Service.RecyclerItemClickListener;

public class RouteListActivity extends AppCompatActivity
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

        Route route = new Route.Builder()
                .name("yeut")
                .description("yeuter meteut")
                .length(10.52)
                .build();
        routes.add(route);


        mRecyclerView = findViewById(R.id.rl_RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(routes);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mRecyclerView.getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {

                    }

                    @Override
                    public void onLongItemClick(View view, int position)
                    {

                    }
                })
        );

        ImageButton helpButton = findViewById(R.id.rl_HelpButton);

        helpButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(v.getContext(), HelpActivity.class);
            startActivity(intent);
        });

        routes.add(
                new Route.Builder().description("Gerdtinus is autisch")
                        .name("TESTTTT")
                        .build()
        );
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
}
