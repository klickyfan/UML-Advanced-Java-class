package edu.kimjones.advancedjava.stock;

import edu.kimjones.advancedjava.stock.utilities.DatabaseInitializationException;
import edu.kimjones.advancedjava.stock.utilities.DatabaseUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/*
Note: to run this application, Tomcat must be running (as a service), and the constants CHROME_DRIVER_LOCATION and
WEB_APPLICATION_URL must be set correctly, as per your environment.
*/

// @Ignore("Before checking in I set this to be ignored as I do not know how to make Selenium work in circleci!")
public class StockQuoteWebApplicationAcceptanceTest {

    private static final boolean DEBUGGING = true;

    private static final Logger LOGGER = LogManager.getLogger(StockQuoteWebApplicationAcceptanceTest.class.getName());

    private static final String PATH_TO_CHROME_DRIVER = "C:/OneDrive/code/Java/chromedriver.exe";
    private static final String WEB_APPLICATION_URL = "http://localhost:8080/kimjones-app-1.0-SNAPSHOT/stockQuoteForm.jsp";

    private static WebDriver driver;

    private static final String[][] YAHOO_QUOTES_EXPECTED = {
            {"AAPL","10/08/18 00:00","223.77"},
            {"AAPL","10/09/18 00:00	","226.87"},
            {"AAPL","10/10/18 00:00","216.36"},
            {"AAPL","10/11/18 00:00	","214.45"},
            {"AAPL","10/12/18 00:00	","222.11"}
    };

    private static final String[] DATABASE_INITIALIZATION_FILES =
            { DatabaseUtility.INITIALIZATION_FILE, "./src/main/sql/add_AAPL_interday_stock_data.sql", "./src/main/sql/add_AAPL_intraday_stock_data.sql"};

    private static final String[][] DATABASE_QUOTES_EXPECTED = {
            {"AAPL","02/01/18 00:00","167.78"},
            {"AAPL","02/02/18 00:00	","160.50"},
            {"AAPL","02/05/18 00:00","156.49"},
            {"AAPL","02/06/18 00:00	","163.03"},
            {"AAPL","02/07/18 00:00	","159.54"}
    };

    @BeforeClass
    public static void setup() {
        LOGGER.debug("entering setup");

        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        LOGGER.debug("exiting setup");
    }

    /**
     * Verifies that the form has all the components expected.
     */
    @Test
    public void verifyFormContents() throws Exception {

        LOGGER.debug("entering verifyFormContents");

        driver.navigate().to(WEB_APPLICATION_URL);

        String title = driver.findElement(By.cssSelector("h1")).getText();
        assertThat("title is correct", title, is("Stock Quote Application: Home"));

        assertTrue("symbol input element is present", driver.findElement(By.id("symbol")).isDisplayed());
        assertTrue("from input element is present", driver.findElement(By.id("from")).isDisplayed());
        assertTrue("until input element is present", driver.findElement(By.id("until")).isDisplayed());

        List<WebElement> serviceTypeWebElements = driver.findElements(By.name("service"));
        assertThat("service type radio box is present and has two choices", serviceTypeWebElements.size(), is(2) );

        List<WebElement> intervalWebElements = driver.findElements(By.name("interval"));
        assertThat("interval radio box is present and has three choices", intervalWebElements.size(), is(3) );

        assertTrue("submit button is present", driver.findElement(By.name("submit")).isDisplayed());

        if (DEBUGGING) {
            Thread.sleep(5000);
        }

        LOGGER.debug("exiting verifyFormContents");
    }

    /**
     * Tests what the application does when asked for quotes from Yahoo.
     */
    @Test
    public void searchForStockWithYahoo() throws InterruptedException
    {
        LOGGER.debug("entering searchForStockWithYahoo");

        /*
        go to web application
         */

        driver.navigate().to(WEB_APPLICATION_URL);

        /*
        set all inputs as needed to perform test
         */

        setInputs("AAPL", "10/08/2018", "10/12/2018", 0, 0);

        /*
        allow time to eyeball things
         */

        if (DEBUGGING) {
            Thread.sleep(5000);
        }

        /*
        submit form
         */

        driver.findElement(By.name("submit")).click();

        if (DEBUGGING) {
            Thread.sleep(5000);
        }

        /*
        check results
         */

        assertTrue("title is as expected", driver.getTitle().contains("Stock Quote Application: Quotes Requested"));

        List<WebElement> stockQuotes = driver.findElements(By.xpath("//table/tbody/tr"));
        checkStockQuotes(YAHOO_QUOTES_EXPECTED, stockQuotes);

        LOGGER.debug("exiting searchForStockWithYahoo");
    }

    /**
     * Tests what the application does when asked for quotes from the database.
     */
    @Test
    public void searchForStockInDatabase() throws DatabaseInitializationException, InterruptedException {

        LOGGER.debug("entering searchForStockInDatabase");

        /*
        ensure database has data
         */

        for(String file: DATABASE_INITIALIZATION_FILES) {
            DatabaseUtility.initializeDatabase(file);
        }

        /*
        go to web application
         */

        driver.navigate().to(WEB_APPLICATION_URL);

        /*
        set all inputs as needed to perform test
         */

        setInputs("AAPL", "02/01/2018", "02/07/2018", 1, 0);

        /*
        allow time to eyeball things
         */

        if (DEBUGGING) {
            Thread.sleep(5000);
        }

        /*
        submit form
         */

        driver.findElement(By.name("submit")).click();

        if (DEBUGGING) {
            Thread.sleep(5000);
        }

        /*
        check results
         */

        assertTrue("title is as expected", driver.getTitle().contains("Stock Quote Application: Quotes Requested"));

        List<WebElement> stockQuotes = driver.findElements(By.xpath("//table/tbody/tr"));
        checkStockQuotes(DATABASE_QUOTES_EXPECTED, stockQuotes);

        /*
         clean up
          */

        DatabaseUtility.initializeDatabase(DatabaseUtility.INITIALIZATION_FILE);

        LOGGER.debug("exiting searchForStockInDatabase");
    }

    /**
     * Tests what the application does when asked for data it can't get.
     */
    @Test
    public void checkForErrorMessage() throws InterruptedException {

        LOGGER.debug("entering searchForStockWithYahoo");

        /*
        go to web application
         */

        driver.navigate().to(WEB_APPLICATION_URL);

        /*
        set all inputs as needed to perform test
         */

        setInputs("AAPL", "10/01/2017", "10/10/2017", 1, 0);

        /*
        allow time to eyeball things
         */

        if (DEBUGGING) {
            Thread.sleep(5000);
        }

        /*
        submit form
         */

        driver.findElement(By.name("submit")).click();

        if (DEBUGGING) {
            Thread.sleep(5000);
        }

        /*
        check results
         */

        assertTrue("title is as expected", driver.getTitle().contains("Stock Quote Application: Quotes Requested"));

        String message = driver.findElement(By.className("errorMessage")).getText();
        assertTrue("error message is present", message.contains("Sorry"));
    }

    @Ignore
    @Test
    public void ignoredTest() {
        LOGGER.debug("This is an example of an ignored test!");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        LOGGER.debug("entering tearDown");
        driver.quit();
        LOGGER.debug("exiting tearDown");
    }

    private void setInputs(String symbol, String from, String until, int serviceType, int interval) {

        driver.findElement(By.id("symbol")).clear();
        driver.findElement(By.id("symbol")).sendKeys(symbol);

        driver.findElement(By.id("from")).clear();
        driver.findElement(By.id("from")).sendKeys(from);

        driver.findElement(By.id("until")).clear();
        driver.findElement(By.id("until")).sendKeys(until);

        List<WebElement> serviceTypeWebElements = driver.findElements(By.name("service"));
        serviceTypeWebElements.get(serviceType).click();

        List<WebElement> intervalWebElements = driver.findElements(By.name("interval"));
        intervalWebElements.get(interval).click();
    }

    private void checkStockQuotes(String[][] quotesExpected, List<WebElement>quotesFound) {

        int quoteNumber = 0;

        assertEquals("number of quotes expected equals number of quotes found", quotesExpected.length, quotesFound.size());

        for (WebElement quote : quotesFound) {

            String quoteParts[] = quote.getText().split(" ");
            String dateTime = (quoteParts[1] + " " + quoteParts[2]);

            assertEquals("symbol is as expected", quotesExpected[quoteNumber][0], quoteParts[0]);
            assertEquals("date is as expected", quotesExpected[quoteNumber][1].trim(), dateTime.trim());
            assertEquals("price is as expected", quotesExpected[quoteNumber][2], quoteParts[3]);

            quoteNumber++;
        }
    }
}