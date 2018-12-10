package navi.com.columbus.Service;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

public class SharedPreferencesClass {

private SharedPreferences prefs;

public SharedPreferencesClass(SharedPreferences prefs){
    this.prefs = prefs;
}

public void changeprefs(String save, String string){
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(save, string);
    editor.apply();
    }

    public Map<String, ?> getAllInfo(){
        Map<String, ?> map = prefs.getAll();
        return map;
    }
}
