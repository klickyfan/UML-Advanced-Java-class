package edu.kimjones.advancedjava.stock.model.xml;

import edu.kimjones.advancedjava.stock.model.database.DAOStockQuote;

import org.junit.Before;
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

    private XMLDOStockQuoteList stockQuoteList;

    private XMLDOStockQuote firstStockQuoteExpected;
    private XMLDOStockQuote secondStockQuoteExpected;
    private XMLDOStockQuote thirdStockQuoteExpected;

    @Before
    public void setUp() {
        this.stockQuoteList = new XMLDOStockQuoteList();

        this.firstStockQuoteExpected = new XMLDOStockQuote();
        this.firstStockQuoteExpected.setSymbol("GOOG");
        this.firstStockQuoteExpected.setPrice("150.00");
        this.firstStockQuoteExpected.setTime("2015-02-10 00:00:01");

        stockQuoteList.getStockQuoteList().add(this.firstStockQuoteExpected);

        this.secondStockQuoteExpected = new XMLDOStockQuote();
        this.secondStockQuoteExpected.setSymbol("AAPL");
        this.secondStockQuoteExpected.setPrice("165.01");
        this.secondStockQuoteExpected.setTime("2015-02-10 00:00:01");

        stockQuoteList.getStockQuoteList().add(this.secondStockQuoteExpected);

        this.thirdStockQuoteExpected = new XMLDOStockQuote();
        this.thirdStockQuoteExpected.setSymbol("DIS");
        this.thirdStockQuoteExpected.setPrice("118.90");
        this.thirdStockQuoteExpected.setTime("2015-02-10 00:00:01");

        stockQuoteList.getStockQuoteList().add(this.thirdStockQuoteExpected);
    }

    @Test
    public void testGetStockQuoteListPositive() {
        List<XMLDOStockQuote> list = this.stockQuoteList.getStockQuoteList();

        assertNotNull("stock quote list is not null", list);
        assertEquals("stock quote list has 3 items", list.size(), 3);
        assertEquals("first stock quote obtained equals stock quote expected", list.get(0), this.firstStockQuoteExpected);
        assertEquals("second stock quote obtained equals stock quote expected", list.get(1), this.secondStockQuoteExpected);
        assertEquals("third stock quote obtained equals stock quote expected", list.get(2), this.thirdStockQuoteExpected);
    }

    @Test
    public void testGetStockQuoteListNegative() {
        assertThat("stock quote list obtained does not equal empty stock quote list ", this.stockQuoteList.getStockQuoteList(), is(not(new ArrayList<DAOStockQuote>())));
        assertThat("first stock quote obtained does not equal tenth stock quote expected", this.stockQuoteList.getStockQuoteList().get(0), is(not(this.secondStockQuoteExpected)));
    }
}