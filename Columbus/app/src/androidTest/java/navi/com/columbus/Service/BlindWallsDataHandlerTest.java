package navi.com.columbus.Service;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;

import navi.com.columbus.DataModel.Monument;

import static org.junit.Assert.*;

public class BlindWallsDataHandlerTest {
    Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void getWalls() {
        BlindWallsListener listener = new BlindWallsListener() {

            @Override
            public void onAllMonumentsAvailable(ArrayList<Monument> monuments)
            {

            }

            @Override
            public void onMonumentError(String err) {

            }
        };
        BlindWallsDataHandler blindWallsDataHandler = new BlindWallsDataHandler(context, listener);
        assertEquals(true, blindWallsDataHandler.getWalls());
    }
}