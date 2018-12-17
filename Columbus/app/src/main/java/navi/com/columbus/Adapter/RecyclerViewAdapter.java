package navi.com.columbus.Adapter;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import navi.com.columbus.DataModel.Route;
import navi.com.columbus.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<Route> mDataset;
    private OnRouteClickListener listener;

    public RecyclerViewAdapter(ArrayList<Route> mDataset, OnRouteClickListener listener) {
        this.mDataset = mDataset;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Route route = mDataset.get(position);
        if (position % 2 != 0)
            holder.background.setBackgroundColor(Color.parseColor("#1693ff"));

        holder.getRouteName().setText(route.getName());
        holder.getDescription().setText(route.getDescription());
        holder.getRouteLength().setText(route.getLength() + " km");
        holder.bindActivity(route, listener);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public ArrayList<Route> getmDataset()
    {
        return mDataset;
    }

    public OnRouteClickListener getListener()
    {
        return listener;
    }
}
