package navi.com.columbus.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

import navi.com.columbus.DataModel.Monument;

public interface BlindWallsListener {
    public void onAllMonumentsAvailable(ArrayList<Monument> monuments);

    public void onMonumentError(String err);

}
