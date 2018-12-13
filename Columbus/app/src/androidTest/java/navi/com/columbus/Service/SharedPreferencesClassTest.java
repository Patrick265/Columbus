package navi.com.columbus.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;

import java.util.Map;

import navi.com.columbus.DataModel.Monument;

import static org.junit.Assert.*;

public class SharedPreferencesClassTest {
    Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void changeprefs() {
        SharedPreferences prefs = context.getSharedPreferences("Application", context.MODE_PRIVATE);
        SharedPreferencesClass sharedPreferencesClass = new SharedPreferencesClass(prefs);

        assertEquals(true, sharedPreferencesClass.changeprefs("like fuck", "ali"));
    }

    @Test
    public void getAllInfo() {
        SharedPreferences prefs = context.getSharedPreferences("Application", context.MODE_PRIVATE);
        SharedPreferencesClass sharedPreferencesClass = new SharedPreferencesClass(prefs);
        sharedPreferencesClass.changeprefs("like fuck", "ali");
        Map<String, ?> keyValues = sharedPreferencesClass.getAllInfo();
        assertEquals("like fuck=ali", keyValues.toString());
    }
}