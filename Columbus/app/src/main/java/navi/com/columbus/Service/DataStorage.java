package navi.com.columbus.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import navi.com.columbus.DataModel.Monument;
import navi.com.columbus.DataModel.Route;

public class DataStorage extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ROUTE";


    public DataStorage(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DatabaseQuery.CREATE_TABLE_MONUMENT);
        db.execSQL(DatabaseQuery.CREATE_TABLE_ROUTE);
        db.execSQL(DatabaseQuery.CREATE_TABLE_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS '" + DatabaseQuery.TABLE_HEADER_MAIN + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + DatabaseQuery.TABLE_HEADER_ROUTE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + DatabaseQuery.TABLE_HEADER_MONUMENT + "'");

        onCreate(db);
    }


    /**
     * It will search through the database with the two parameters and will return the monument.
     * If it cannot find a monument it will throw a new Error message.
     * @param monumentName the monument name of the searched monument
     * @param constructionyear the construction year of the searched monument
     * @return The monument from the database
     */
    public Monument retrieveMonument(@NonNull String monumentName, @NonNull int constructionyear) {
        String query = "SELECT * FROM MONUMENT WHERE MONUMENT.ConstructionYear = " + constructionyear +" AND MONUMENT.MonumentName = \"" + monumentName + "\" LIMIT 1;";
        Log.d("QUERYMONUMENT", query);

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor!= null){
            cursor.moveToFirst();
        }

        if(cursor.getCount() > 0) {
            Monument monument = new Monument.Builder()
                    .name(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_MONUMENTNAME)))
                    .description(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_DESCRIPTION)))
                    .creator(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_CREATOR)))
                    .soundURL(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_SOUNDFILEURL)))
                    .imageURL(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_IMAGEURL)))
                    .latitude(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_LATITUDE)))
                    .longitude(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_LONGITUDE)))
                    .constructionYear(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_CONSTRUCTIONYEAR)))
                    .build();

            if(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_ISVISITED)) == 1) {
                monument.setVisited(true);
            } else
            {
                monument.setVisited(false);
            }

            cursor.close();
            return monument;
        }
        else {
            cursor.close();
            throw new Error("MONUMENT COULD NOT BE FOUND BECAUSE EITHER IT DOES NOT EXIST OR INVALID PARAMETERS!");
        }
    }

    public List<Monument> retrieveAllMonument() {
        List<Monument> monuments = new ArrayList<>();
        String query = "SELECT * FROM MONUMENT;";
        Log.d("QUERYALLMONUMENT", query);

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);

        if(cursor.getCount() > 0 ) {
            if(cursor.moveToFirst()) {
                do {
                    Monument monument = new Monument.Builder()
                                           .name(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_MONUMENTNAME)))
                                           .description(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_DESCRIPTION)))
                                           .creator(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_CREATOR)))
                                           .soundURL(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_SOUNDFILEURL)))
                                           .imageURL(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_IMAGEURL)))
                                           .latitude(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_LATITUDE)))
                                           .longitude(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_LONGITUDE)))
                                           .constructionYear(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_CONSTRUCTIONYEAR)))
                                            .build();

                    if(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_MONUMENT_ISVISITED)) == 1) {
                        monument.setVisited(true);
                    } else
                    {
                        monument.setVisited(false);
                    }
                    monuments.add(monument);
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
       return monuments;
    }

    public void addMonument(@NonNull Monument monument) {
        ContentValues values = new ContentValues();
        values.put(DatabaseQuery.COL_MONUMENT_MONUMENTNAME, monument.getName());
        values.put(DatabaseQuery.COL_MONUMENT_DESCRIPTION, monument.getDescription());
        values.put(DatabaseQuery.COL_MONUMENT_CREATOR, monument.getCreator());
        values.put(DatabaseQuery.COL_MONUMENT_SOUNDFILEURL, monument.getSoundURL());
        values.put(DatabaseQuery.COL_MONUMENT_IMAGEURL, monument.getImageURL());
        values.put(DatabaseQuery.COL_MONUMENT_LATITUDE, monument.getLatitude());
        values.put(DatabaseQuery.COL_MONUMENT_LONGITUDE, monument.getLongitude());
        values.put(DatabaseQuery.COL_MONUMENT_CONSTRUCTIONYEAR, monument.getConstructionYear());

        if(monument.isVisited()) {
            values.put(DatabaseQuery.COL_MONUMENT_ISVISITED, 1);
        } else {
            values.put(DatabaseQuery.COL_MONUMENT_ISVISITED, 0);
        }

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DatabaseQuery.TABLE_HEADER_MONUMENT, null, values);
        Log.i("INSERTEDDB", "INSERTED MONUMENT IN DB");
    }

    public void addRoute(@NonNull Route route) {
        ContentValues values = new ContentValues();
        values.put(DatabaseQuery.COL_ROUTE_ROUTENAME, route.getName());
        values.put(DatabaseQuery.COL_ROUTE_DESCRIPTION, route.getDescription());
        values.put(DatabaseQuery.COL_ROUTE_LENGTH, route.getLength());
        if(route.isFinished()) {
            values.put(DatabaseQuery.COL_ROUTE_FINSIHED, 1);
        } else {
            values.put(DatabaseQuery.COL_ROUTE_FINSIHED, 0);
        }
        getWritableDatabase().insert(DatabaseQuery.TABLE_HEADER_ROUTE, null, values);
        Log.i("INSERTEDDB", "INSERTED ROUTE IN DB");
    }

    public List<Route> retrieveAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT * FROM ROUTE;";
        Log.d("QUERYALLROUTE", query);

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);

        if(cursor.getCount() > 0 ) {
            if(cursor.moveToFirst()) {
                do {
                    Route route = new Route.Builder()
                            .name(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_ROUTENAME)))
                            .description(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_DESCRIPTION)))
                            .length(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_LENGTH)))
                            .build();
                    if(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_FINSIHED)) == 1) {
                        route.setFinished(true);
                    } else
                    {
                        route.setFinished(false);
                    }
                    routes.add(route);
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        return routes;
    }


    /**
     * It will search through the database with the one parameters and will return the route.
     * If it cannot find a route it will throw a new Error message.
     * @param routename The name of the route
     * @return The route
     */
    public Route retrieveRoute(String routename) {
        String query = "SELECT * FROM ROUTE WHERE ROUTE.RouteName = \"" + routename +"\" LIMIT 1;";
        Log.d("QUERYROUTE", query);

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor!= null){
            cursor.moveToFirst();
        }

        if(cursor.getCount() > 0) {
            Route route = new Route.Builder()
                    .name(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_ROUTENAME)))
                    .description(cursor.getString(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_DESCRIPTION)))
                    .length(cursor.getDouble(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_LENGTH)))
                    .build();
            if(cursor.getInt(cursor.getColumnIndex(DatabaseQuery.COL_ROUTE_FINSIHED)) == 1) {
                route.setFinished(true);
            } else
            {
                route.setFinished(false);
            }
            cursor.close();
            return route;
        }
        else {
            cursor.close();
            throw new Error("ROUTE COULD NOT BE FOUND BECAUSE EITHER IT DOES NOT EXIST OR INVALID PARAMETERS!");
        }
    }

}
