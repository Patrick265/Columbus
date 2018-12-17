package navi.com.columbus.DataModel;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import navi.com.columbus.R;

public class HistorischeKMFactory
{
    private List<Monument> monumentList;

    public HistorischeKMFactory()
    {
        this.monumentList = new ArrayList<>();

    }

    public Route getHistorischeKilometer()
    {
        if(monumentList.isEmpty())
        {
            monumentList = getHistKmList();
        }
        Route histKmRoute = new Route.Builder().routeList(monumentList).name("De historische kilometer").description("Een wandelroute langs de historie van Breda. Een korte wandeling door het centrum die u kennis laat maken met de diepgaande cultuur en geschiedenis van deze mooie stad.").build();
        System.out.println(histKmRoute.toString());
        return histKmRoute;
    }

    private List<Monument> getHistKmList()
    {
        List<Monument> tempList = new ArrayList<>();

            try
            {
                final CSVParser parser = new CSVParserBuilder()
                        .withSeparator(';')
                        .build();
                final  CSVReader reader = new CSVReaderBuilder(new StringReader("D:\\Documenten\\Technische Informatica\\2. Jaar 2\\2. Periode 2\\1. AGS A5\\4. Fase 3 - Implementatie\\Code\\Columbus\\Columbus\\app\\src\\main\\java\\navi\\com\\columbus\\Assets\\HistKm.csv")).withCSVParser(parser).build();

                String[] nextLine;
                while((nextLine = reader.readNext()) != null)
                {
                    Monument monument = new Monument.Builder().longitude(Double.valueOf(nextLine[1]))
                            .latitude(Double.valueOf(nextLine[2]))
                            .name(nextLine[3])
                            .description(nextLine[4])
                            .build();
                    tempList.add(monument);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return tempList;
    }



}