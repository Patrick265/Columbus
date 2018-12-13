package navi.com.columbus.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import navi.com.columbus.R;

public class HelpActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView text = findViewById(R.id.help_Tekst);
        text.setText(R.string.help_info);
        text.setMovementMethod(new ScrollingMovementMethod());
    }
}
