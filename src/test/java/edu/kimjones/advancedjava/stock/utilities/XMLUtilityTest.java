package edu.kimjones.advancedjava.stock.utilities;

import com.google.common.io.CharStreams;
import edu.kimjones.advancedjava.stock.model.xml.XMLDOStockQuoteList;

import org.junit.BeforeClass;
import org.junit.Test;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import java.io.*;

import static org.junit.Assert.*;

public class XMLUtilityTest {

    private static final String XML_FILE = "./src/main/xml/stock_info.xml";

    private static String xmlInstanceFromFile;
    private static XMLDOStockQuoteList stockQuoteList;
    private static String xmlInstanceAfterMarshall;

    @BeforeClass
    public static void setUp() throws Exception {

        InputStream inputStream = new FileInputStream(new File(XML_FILE));

        xmlInstanceFromFile = null;

        try (final Reader reader = new InputStreamReader(inputStream)) {
            xmlInstanceFromFile = CharStreams.toString(reader);
        }

        stockQuoteList = XMLUtility.unmarshal(xmlInstanceFromFile, XMLDOStockQuoteList.class);

        xmlInstanceAfterMarshall = XMLUtility.marshall(stockQuoteList);
    }

    @Test
    public void testUnmarshal() {
        assertNotNull("stockQuoteList is not null", stockQuoteList);
        assertNotNull("stockQuoteList's list is not null", stockQuoteList.getStockQuoteList());
        assertEquals("stockQuoteList's list has 50 elements in it", 49, stockQuoteList.getStockQuoteList().size());
        assertEquals("stockQuoteList's first element has symbol VNET", "VNET", stockQuoteList.getStockQuoteList().get(0).getSymbol());
        assertEquals("stockQuoteList's second element has price 120.10", "120.10", stockQuoteList.getStockQuoteList().get(1).getPrice());
    }

    @Test
    public void testMarshal() {
        final Diff documentDiff = DiffBuilder.compare(xmlInstanceFromFile).withTest(xmlInstanceAfterMarshall).ignoreWhitespace().build();
        assertFalse("xmlInstanceFromFile is the same as xmlInstanceAfterMarshal", documentDiff.hasDifferences());
    }
}