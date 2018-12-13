package navi.com.columbus.DataModel;

import org.junit.Test;

import static org.junit.Assert.*;

public class HistorischeKMFactoryTest
{
    HistorischeKMFactory kmTest = new HistorischeKMFactory();

    @Test
    public void getHistKm()
    {
        assertEquals(45, kmTest.getHistorischeKilometer().getMonumentList().size());
    }
}