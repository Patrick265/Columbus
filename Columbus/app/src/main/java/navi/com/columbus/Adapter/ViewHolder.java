package navi.com.columbus.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import navi.com.columbus.DataModel.Route;
import navi.com.columbus.R;
import navi.com.columbus.View.GpsActivity;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView background;
    TextView routeName;
    TextView description;
    TextView routeLength;

    public ViewHolder(@NonNull View v) {
        super(v);

        this.background = v.findViewById(R.id.rli_Background);
        this.routeName = v.findViewById(R.id.rli_RouteName);
        this.description = v.findViewById(R.id.rli_Description);
        this.routeLength = v.findViewById(R.id.rli_RouteLength);
    }


    public void bindActivity(final Route route, final OnRouteClickListener listener) {
        super.itemView.setOnClickListener(v -> {
            listener.onItemClick(route);
        });
    }
    public ImageView getBackground()
    {
        return background;
    }

    public TextView getRouteName()
    {
        return routeName;
    }

    public TextView getDescription()
    {
        return description;
    }

    public TextView getRouteLength()
    {
        return routeLength;
    }
}

