package navi.com.columbus.ViewModel;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import navi.com.columbus.DataModel.Route;
import navi.com.columbus.R;

import static java.security.AccessController.getContext;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    private ArrayList<Route> mDataset;
    private View contactView;

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView background;
        TextView routeName;
        TextView description;
        TextView routeLength;

        MyViewHolder(View v)
        {
            super(v);

            background = v.findViewById(R.id.rli_Background);
            routeName = v.findViewById(R.id.rli_RouteName);
            description = v.findViewById(R.id.rli_Description);
            routeLength = v.findViewById(R.id.rli_RouteLength);
        }
    }

    public RecyclerViewAdapter(ArrayList<Route> mDataset)
    {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        contactView = inflater.inflate(R.layout.route_list_item, parent, false);

        return new MyViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        if (position % 2 != 0)
            holder.background.setBackgroundColor(Color.parseColor("#1693ff"));

        holder.routeName.setText(mDataset.get(position).getName());
        holder.description.setText(mDataset.get(position).getDescription());
        holder.routeLength.setText(mDataset.get(position).getLength() + " km");
    }

    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }
}
