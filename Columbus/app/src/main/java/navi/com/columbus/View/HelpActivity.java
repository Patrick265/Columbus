package navi.com.columbus.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import navi.com.columbus.R;

public class HelpActivity extends AppCompatActivity
{
    private TextView helpTitle;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        helpTitle = findViewById(R.id.help_Title);
        helpTitle.setText(R.string.help_title);

        info = findViewById(R.id.help_Tekst);
        info.setMovementMethod(new ScrollingMovementMethod());

        String helpText = (String) getIntent().getExtras().get("HELP_TEXT");
        info.setText(helpText);
    }
}
