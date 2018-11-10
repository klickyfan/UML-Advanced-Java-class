package edu.kimjones.advancedjava.stock.model.xml;

import edu.kimjones.advancedjava.stock.model.database.DAOStockQuote;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * This class is for testing the class {@code XMLDOStockQuoteList}.
 *
 * @author Kim Jones
 */
public class XMLDOStockQuoteListTest {

    private static XMLDOStockQuoteList stockQuoteList;

    private static XMLDOStockQuote firstStockQuoteExpected;
    private static XMLDOStockQuote secondStockQuoteExpected;
    private static XMLDOStockQuote thirdStockQuoteExpected;

    @BeforeClass
    public static void setUp() {
        stockQuoteList = new XMLDOStockQuoteList();

        firstStockQuoteExpected = new XMLDOStockQuote();
        firstStockQuoteExpected.setSymbol("GOOG");
        firstStockQuoteExpected.setPrice("150.00");
        firstStockQuoteExpected.setTime("2015-02-10 00:00:01");

        stockQuoteList.getStockQuoteList().add(firstStockQuoteExpected);

        secondStockQuoteExpected = new XMLDOStockQuote();
        secondStockQuoteExpected.setSymbol("AAPL");
        secondStockQuoteExpected.setPrice("165.01");
        secondStockQuoteExpected.setTime("2015-02-10 00:00:01");

        stockQuoteList.getStockQuoteList().add(secondStockQuoteExpected);

        thirdStockQuoteExpected = new XMLDOStockQuote();
        thirdStockQuoteExpected.setSymbol("DIS");
        thirdStockQuoteExpected.setPrice("118.90");
        thirdStockQuoteExpected.setTime("2015-02-10 00:00:01");

        stockQuoteList.getStockQuoteList().add(thirdStockQuoteExpected);
    }

    @Test
    public void testGetStockQuoteListPositive() {
        List<XMLDOStockQuote> list = stockQuoteList.getStockQuoteList();

        assertNotNull("stock quote list is not null", list);
        assertEquals("stock quote list has 3 items", list.size(), 3);
        assertEquals("first stock quote obtained equals stock quote expected", list.get(0), firstStockQuoteExpected);
        assertEquals("second stock quote obtained equals stock quote expected", list.get(1), secondStockQuoteExpected);
        assertEquals("third stock quote obtained equals stock quote expected", list.get(2), thirdStockQuoteExpected);
    }

    @Test
    public void testGetStockQuoteListNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", stockQuoteList.getStockQuoteList(), is(not(new ArrayList<DAOStockQuote>())));
        assertThat("first stock quote obtained does not equal tenth stock quote expected", stockQuoteList.getStockQuoteList().get(0), is(not(secondStockQuoteExpected)));
    }
}