package navi.com.columbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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


        okButton.setOnClickListener(view1 -> getDialog().dismiss());

        String imageURL = args.getString("imageURL");
        Picasso.get().load(imageURL).into(image);

        monumentName.setText(args.getString("monumentName"));
        makers.setText(args.getString("makers"));
        constructionYear.setText(args.getString("constructionYear"));
        description.setText(args.getString("description"));

        return view;
    }
}
