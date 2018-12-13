package navi.com.columbus.Service;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import navi.com.columbus.DataModel.Monument;

import static org.junit.Assert.*;

public class BlindWallsDataHandlerTest {
    Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void getWalls() {
        BlindWallsListener listener = new BlindWallsListener() {
            @Override
            public void onMonumentAvailable(Monument monument) {

            }

            @Override
            public void onMonumentError(String err) {

            }
        };
        BlindWallsDataHandler blindWallsDataHandler = new BlindWallsDataHandler(context, listener);
        assertEquals(true, blindWallsDataHandler.getWalls());
    }
}