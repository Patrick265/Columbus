package navi.com.columbus.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import java.util.Map;

import navi.com.columbus.R;
import navi.com.columbus.Service.SharedPreferencesClass;

public class HomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences prefs = getSharedPreferences("Application", MODE_PRIVATE);
        SharedPreferencesClass sharedPreferencesClass = new SharedPreferencesClass(prefs);
        sharedPreferencesClass.changeprefs("like fuck", "ali");

        Map<String, ?> keyValues = sharedPreferencesClass.getAllInfo();
        Log.i("AAAAAAAAAAAAA", keyValues.toString());

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GpsActivity.class);
                startActivity(intent);
            }
        });
    }
}
