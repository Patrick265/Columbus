package navi.com.columbus.Service;

public class DatabaseQuery
{
    // TABLE NAMES
    public static final String TABLE_HEADER_MONUMENT = "MONUMENT";
    public static final String TABLE_HEADER_ROUTE = "ROUTE";
    public static final String TABLE_HEADER_MAIN = "ROUTEMONUMENT";


    // COLUMNS IN MONUMENTS
    public static final String COL_MONUMENT_MONUMENTNAME = "MonumentName";
    public static final String COL_MONUMENT_DESCRIPTION = "Description";
    public static final String COL_MONUMENT_CREATOR = "Creator";
    public static final String COL_MONUMENT_SOUNDFILEURL = "SoundFileURL";
    public static final String COL_MONUMENT_IMAGEURL = "ImageURL";
    public static final String COL_MONUMENT_LATITUDE = "Latitude";
    public static final String COL_MONUMENT_LONGITUDE = "Longitude";
    public static final String COL_MONUMENT_CONSTRUCTIONYEAR = "ConstructionYear";
    public static final String COL_MONUMENT_ISVISITED = "IsVisited";

    // COLUMNS IN ROUTE
    public static final String COL_ROUTE_ROUTENAME = "RouteName";
    public static final String COL_ROUTE_ROUTEMONUMENTID = "RouteMonumentID";
    public static final String COL_ROUTE_DESCRIPTION = "Description";
    public static final String COL_ROUTE_LENGTH = "Length";
    public static final String COL_ROUTE_FINSIHED = "Finished";

    // MAIN TABEL COLUMNS
    public static final String COL_MAIN_ROUTEID = "RouteID";
    public static final String COL_MAIN_ROUTENAME = "RouteName";
    public static final String COL_MAIN_MONUMENTNAME = "MonumentName";
    public static final String COL_MAIN_ORDERMONUMENTS = "OrderMonuments";




    // BUILDING THE DATABASE WITH THESE QUERY'S
    public static final String CREATE_TABLE_MONUMENT = "CREATE TABLE IF NOT EXISTS MONUMENT (\n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tMonumentName VARCHAR(50),\n" +
            "    Description VARCHAR(50),\n" +
            "    Creator VARCHAR(50),\n" +
            "    SoundFileURL VARCHAR(50),\n" +
            "    ImageURL VARCHAR(50),\n" +
            "    Latitude VARCHAR(50),\n" +
            "    Longitude VARCHAR(50),\n" +
            "    ConstructionYear INTEGER,\n" +
            "    IsVisited INTEGER\n" +
            ");";

    public static final String CREATE_TABLE_ROUTE = "CREATE TABLE IF NOT EXISTS ROUTE (\n" +
            "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tRouteName VARCHAR(50),\n" +
            "    RouteMonumentID INTEGER,\n" +
            "    Description VARCHAR(50),\n" +
            "    Length DOUBLE,\n" +
            "    Finished BOOLEAN\n" +
            ");\n";

    public static final String CREATE_TABLE_MAIN = "CREATE TABLE IF NOT EXISTS ROUTEMONUMENT (\n" +
            "    RouteID INTEGER,\n" +
            "    MonumentID INTEGER,\n" +
            "    OrderMonuments INTEGER,\n" +
            "\tCONSTRAINT RouteID\n" +
            "\t\tFOREIGN KEY (RouteID)\n" +
            "\t\tREFERENCES ROUTE (id),\n" +
            "        \n" +
            "\tCONSTRAINT MonumentID\n" +
            "\t\tFOREIGN KEY (MonumentID)\n" +
            "\t\tREFERENCES MONUMENT (id)\n" +
            ");";
}
