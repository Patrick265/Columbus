package navi.com.columbus.Service;

import navi.com.columbus.DataModel.Monument;

public interface BlindWallsListener {
    public void onMonumentAvailable(Monument monument);

    public void onMonumentError(String err);

}
