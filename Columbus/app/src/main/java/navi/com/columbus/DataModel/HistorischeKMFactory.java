package navi.com.columbus.DataModel;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistorischeKMFactory
{
    private List<Monument> values;

    public HistorischeKMFactory()
    {
        this.values = new ArrayList<>();
    }

    public List<Monument> getHistKmMap()
    {
        if(values.isEmpty())
        {
            try
            {
                CSVReader reader = new CSVReader(new FileReader("D:\\Documenten\\Technische Informatica\\2. Jaar 2\\2. Periode 2\\1. AGS A5\\4. Fase 3 - Implementatie\\Code\\Columbus\\Columbus\\app\\src\\main\\java\\navi\\com\\columbus\\Assets\\HistKm.csv"),';');
                String[] nextLine;
                while((nextLine = reader.readNext()) != null)
                {
                    Monument monument = new Monument.Builder().longitude(Double.valueOf(nextLine[1]))
                            .latitude(Double.valueOf(nextLine[2]))
                            .name(nextLine[3])
                            .description(nextLine[4])
                            .build();
                    values.add(monument);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return this.values;
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
