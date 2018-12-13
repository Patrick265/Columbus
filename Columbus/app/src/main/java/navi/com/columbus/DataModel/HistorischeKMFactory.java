package navi.com.columbus.DataModel;

import android.content.res.AssetManager;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HistorischeKMFactory
{
    private Map<String, String> values;

    public HistorischeKMFactory()
    {
        this.values = new HashMap<>();
    }

    public int getHistKmMap()
    {
        if(values == null)
        {
            try
            {


                CSVReader reader = new CSVReader(new FileReader("D:\\Documenten\\Technische Informatica\\2. Jaar 2\\2. Periode 2\\1. AGS A5\\4. Fase 3 - Implementatie\\Code\\Columbus\\Columbus\\app\\src\\main\\java\\navi\\com\\columbus\\Assets\\HistKm.csv"),';');
                String[] nextLine;
                while((nextLine = reader.readNext()) != null)
                {
                    System.out.println("HISTKM----"+ nextLine[0]);
                    System.out.println("HISTKM----"+ nextLine[1]);
                    System.out.println("HISTKM----"+ nextLine[2]);
                    System.out.println("HISTKM----"+ nextLine[3]);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return 1;
    }

}


/*
 try
            {
                values = new CSVReaderHeaderAware(new FileReader("navi/com/columbus/Assets/HistKm.csv")).readMap();
                Log.i("HistKMvalue", String.valueOf(values.size()));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
 */
