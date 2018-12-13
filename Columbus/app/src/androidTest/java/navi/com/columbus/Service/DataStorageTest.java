package navi.com.columbus.Service;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import navi.com.columbus.DataModel.Monument;

import static org.junit.Assert.*;

public class DataStorageTest
{
    private DataStorage storage = new DataStorage(InstrumentationRegistry.getTargetContext());
    private  Monument monument = new Monument.Builder()
            .name("TestNaam")
            .constructionYear(1995)
            .creator("CreatorName")
            .description("Test description for monument")
            .imageURL("URL")
            .soundURL("SOUND URL")
            .isVisited(true)
            .latitude(123123)
            .longitude(219312)
            .build();

    @Test
    public void onUpgrade()
    {
        storage.onUpgrade(storage.getReadableDatabase(), 0, 1);
    }

    @Test
    public void onCreate()
    {
        storage.onCreate(storage.getReadableDatabase());
    }

    @Test
    public void addMonument()
    {


        storage.addMonument(monument);
    }

    @Test
    public void retrieveAllMonument()
    {
        assertEquals(1, storage.retrieveAllMonument().size());
    }

    @Test
    public void retrieveMonument()
    {
        Monument m = storage.retrieveMonument("TestNaam", 1995);
        assertEquals(monument.getName(), m.getName());
        assertEquals(monument.getConstructionYear(), m.getConstructionYear());
        assertEquals(monument.getCreator(), m.getCreator());
        assertEquals(monument.getDescription(), m.getDescription());
        assertEquals(monument.getImageURL(), m.getImageURL());
        assertEquals(monument.getLatitude(), m.getLatitude(), 0.005);
        assertEquals(monument.getLongitude(), m.getLongitude(), 0.005);
        assertEquals(monument.getSoundURL(), m.getSoundURL());

    }
}