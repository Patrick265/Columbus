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
    private ArrayList<Monument> monumentList;

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

    private ArrayList<Monument> getHistKmList()
    {
        ArrayList<Monument> sightseeingslist = new ArrayList<>();
        ArrayList<Monument> tempList = new ArrayList<>();

            try
            {
                final CSVParser parser = new CSVParserBuilder()
                        .withSeparator(';')
                        .build();
                //region don't look
                final  CSVReader reader = new CSVReaderBuilder(
                        new StringReader("1;51.594111;4.779416;VVV;Beginpunt\n" +
                        "2;51.593277;4.779388;Liefdeszuster;\n" +
                        "3;51.592500;4.779695;Nassau Baronie Monument;\n" +
                        "4;51.592500;4.779388;;Pad ten westen van monument\n" +
                        "5;51.592833;4.778471;The Light House;\n" +
                        "6;51.592666;4.777916;;\n" +
                        "7;51.590611;4.777000;;Einde park\n" +
                        "8;51.590611;4.776166;Kasteel van Breda;Kasteelplein\n" +
                        "9;51.589695;4.776138;Stadhouderspoort;\n" +
                        "10;51.590333;4.776000;;Kruising Kasteelplein/Cingelstraat\n" +
                        "11;51.590388;4.775000;;Bocht Cingelstraat\n" +
                        "12;51.590028;4.774361;Huis van Brecht (rechter zijde);\n" +
                        "13;51.590195;4.773445;Spanjaardsgat (rechter zijde);\n" +
                        "14;51.589833;4.773333;Begin Vismarkt;\n" +
                        "15;51.589361;4.774445;Begin Havermarkt;\n" +
                        "16;51.588778;4.774888; ;Kruising Torenstraat/Kerkplein\n" +
                        "17;51.588833;4.775278;Grote Kerk;\n" +
                        "18;51.588778;4.774888;;Kruising Torenstraat/Kerkplein\n" +
                        "19;51.588195;4.775138;Het Poortje;\n" +
                        "20;51.587083;4.775750;Ridderstraat;\n" +
                        "21;51.587416;4.776555;Grote Markt;Zuidpunt Grote Markt\n" +
                        "22;51.588028;4.776333;Bevrijdingsmonument;\n" +
                        "23;51.588750;4.776111;Stadhuis;\n" +
                        "24;51.587971;4.776361;;Terug naar begin Grote Markt\n" +
                        "25;51.587500;4.776555;;Zuidpunt Grote Markt\n" +
                        "26;51.587638;4.777516;Antonius van Paduakerk;\n" +
                        "27;51.588278;4.778500;;Kruising St.Janstraat/Molenstraat\n" +
                        "28;51.588000;4.778945;Bibliotheek;\n" +
                        "29;51.587361;4.780221;;Kruising Molenstraat/Kloosterplein\n" +
                        "30;51.587721;4.781028;Kloosterkazerne;\n" +
                        "31;51.587750;4.782000;Chasse theater;\n" +
                        "32;51.587750;4.781250;;Kruising Kloosterplein/Vlaszak\n" +
                        "33;51.588611;4.780888;Binding van Isaac;\n" +
                        "34;51.589500;4.780445;;Kruising Vlaszak/Boschstraat\n" +
                        "35;51.589666;4.781000;Beyerd;\n" +
                        "36;51.589500;4.780445;;Kruising Vlaszak/Boschstraat\n" +
                        "37;51.589555;4.780000;Gasthuispoort;\n" +
                        "38;51.589416;4.779861;;Ingang Veemarktstraat\n" +
                        "39;51.589028;4.779695;;1e bocht Veemarktstraat\n" +
                        "40;51.588555;4.778333;;Kruising Veemarktstraat/St.Annastraat\n" +
                        "41;51.589111;4.777945;Willem Merkxtuin;Ingang Willem Merkxtuin\n" +
                        "42;51.589666;4.777805;;Kruising St.Annastraat/Catharinastraat\n" +
                        "43;51.589695;4.778361;Begijnenhof;Ingang Begijnenhof\n" +
                        "44;51.589666;4.777805;;Kruising St.Annastraat/Catharinastraat\n" +
                        "45;51.589500;4.77625;Einde stadswandeling;Eindpunt\n")).withCSVParser(parser).build();
                //endregion
                String[] nextLine;
                int count = 0;
                while((nextLine = reader.readNext()) != null)
                {
                    Monument monument = new Monument.Builder().longitude(Double.valueOf(nextLine[2]))
                            .latitude(Double.valueOf(nextLine[1]))
                            .name(nextLine[3])
                            .description(nextLine[4])
                            .id(Integer.valueOf(nextLine[0]))
                            .build();
                    tempList.add(monument);
                    if(monument.getName() != null)
                    {
                        sightseeingslist.add(monument);
                    }
                    count++;
                    if(count == 24)
                        break;
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return sightseeingslist;
    }



}