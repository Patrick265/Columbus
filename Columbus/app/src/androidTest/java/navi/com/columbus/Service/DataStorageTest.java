package navi.com.columbus.Service;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;

import navi.com.columbus.DataModel.Monument;
import navi.com.columbus.DataModel.Route;

import static org.junit.Assert.*;

public class DataStorageTest
{
    private DataStorage storage = new DataStorage(InstrumentationRegistry.getTargetContext());
    private Monument monument = new Monument.Builder()
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

    private ArrayList<Monument> monuments = new ArrayList<>();


    public void initalise() {
        monuments.add(monument);
    }

    private Route route = new Route.Builder()
                    .name("Historische kilometer")
                    .length(5)
                    .description("Historische kilometer")
                    .finished(true)
                    .routeList(monuments)
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
        for(Monument m : this.storage.retrieveAllMonument()) {
            Log.i("M", m.toString());
        }
        //assertEquals(1, storage.retrieveAllMonument().size());
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

    @Test
    public void addRoute()
    {
        initalise();
        this.storage.addRoute(route);
    }

    @Test
    public void retrieveAllRoutes()
    {
        for(Route r : storage.retrieveAllRoutes()) {
            Log.i("ROUTE", r.toString());
        }
        assertEquals(1, storage.retrieveAllRoutes().size());
    }

    @Test
    public void retrieveRoute()
    {
        Route r = storage.retrieveRoute("Historische kilometer");
        assertEquals(route.getName(), r.getName());
        assertEquals(route.getDescription(), r.getDescription());
        assertEquals(route.getLength(), r.getLength(), 0.005);

    }

    @Test
    public void getRPrimaryKey()
    {
        int i = storage.getRPrimaryKey(route);
        for(Route r : storage.retrieveAllRoutes()) {
            Log.i("ROUTE", String.valueOf(storage.getRPrimaryKey(r)));
        }
        assertEquals(1, i);
    }

    @Test
    public void getMPrimaryKey()
    {
        int i = storage.getMPrimaryKey(monument);

        assertEquals(1, i);
    }

    @Test
    public void getMonumentCount()
    {
        Log.i("TAG", String.valueOf(storage.getMonumentCount()));
        assertEquals(1, storage.getMonumentCount());
    }

    @Test
    public void onConfigure()
    {
    }
}