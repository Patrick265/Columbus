package navi.com.columbus.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import navi.com.columbus.DataModel.Monument;
import navi.com.columbus.R;

public class NotificationFragment extends DialogFragment
{
    private TextView monumentName, makers, constructionYear, description;
    private ImageView image;
    private Button okButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        monumentName = view.findViewById(R.id.fn_MonumentName);
        makers = view.findViewById(R.id.fn_Makers);
        constructionYear = view.findViewById(R.id.fn_ConstructionYear);
        description = view.findViewById(R.id.fn_Description);
        image = view.findViewById(R.id.fn_Image);
        okButton = view.findViewById(R.id.fn_OkButton);
        Bundle args = getArguments();
        Monument monument = (Monument) args.getSerializable("monument");
        okButton.setOnClickListener(view1 -> getDialog().dismiss());

        String imageURL = monument.getImageURL();
        Picasso.get().load(imageURL).into(image);

        monumentName.setText(monument.getName());
        makers.setText(monument.getCreator());
        constructionYear.setText(monument.getConstructionYear()+"");
        description.setText(monument.getDescription());

        return view;
    }

    @Override
    public void onResume()
    {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }
}
