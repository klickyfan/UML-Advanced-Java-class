package edu.kimjones.advancedjava.stock;

import com.google.common.io.CharStreams;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import edu.kimjones.advancedjava.stock.model.xml.XMLDOStockQuote;
import edu.kimjones.advancedjava.stock.model.xml.XMLDOStockQuoteList;
import edu.kimjones.advancedjava.stock.services.ServiceFactory;
import edu.kimjones.advancedjava.stock.services.StockService;
import edu.kimjones.advancedjava.stock.services.StockServiceException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseInitializationException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;
import edu.kimjones.advancedjava.stock.utilities.InvalidXMLException;
import edu.kimjones.advancedjava.stock.utilities.XMLUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This application was created for Assignment 7. It parses stock_info.xml into the JAXB XMLDOStockQuote and
 * XMLDOStockQuoteList classes. Then it converts that data into database ORM objects and persist to the database.
 * It takes no arguments.
 */
public class Assignment7StockApplication {

    private static final Logger LOGGER = LogManager.getLogger(Assignment7StockApplication.class.getName());

    private static final String xmlFile = "./src/main/xml/stock_info.xml";

    public static void main(String[] args)
            throws IOException, InvalidXMLException, DatabaseInitializationException, StockServiceException, ParseException {

        // try out log4j
        LOGGER.debug("Test DEBUG message logged !!!");
        LOGGER.info("Test INFO Message logged !!!");
        LOGGER.error("Test ERROR message logged !!!");

        // read xml
        XMLDOStockQuoteList stockQuoteList = getXMLDOStockQuoteList();

        System.out.println(stockQuoteList.toString());

        // prepare database
        DatabaseUtility.initializeDatabase(DatabaseUtility.INITIALIZATION_FILE);

        /*
         this application was created to test the DatabaseStockService, so ask the ServiceFactory for the
         database stock service
         */
        StockService stockService = ServiceFactory.getStockService(ServiceFactory.ServiceType.DATABASE);

        // put stocks from xml into database
        addStocksToDatabase(stockService, stockQuoteList);

        // get latest stock quote
        StockQuote latestStockQuote = stockService.getLatestStockQuote("LOOK");

        System.out.printf("%nThe latest price of stock %s is %.2f %n", "LOOK", latestStockQuote.getStockPrice());
    }

    private static XMLDOStockQuoteList getXMLDOStockQuoteList() throws IOException, InvalidXMLException {
        InputStream inputStream = new FileInputStream(new File(xmlFile));

        String xmlInstance = null;

        try (final Reader reader = new InputStreamReader(inputStream)) {
            xmlInstance = CharStreams.toString(reader);
        }

        return XMLUtility.unmarshal(xmlInstance, XMLDOStockQuoteList.class);
    }

    private static void addStocksToDatabase(StockService stockService, XMLDOStockQuoteList stockQuoteList) throws StockServiceException, ParseException {
        for (XMLDOStockQuote quote : stockQuoteList.getStockQuoteList()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date stockDate  = dateFormat.parse(quote.getTime());
            stockService.addOrUpdateStockQuote(quote.getSymbol(), new BigDecimal(quote.getPrice()), stockDate);
        }
    }
}
