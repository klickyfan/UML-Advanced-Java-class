package edu.kimjones.advancedjava.stock.servlets;

import edu.kimjones.advancedjava.stock.model.StockQuote;
import edu.kimjones.advancedjava.stock.services.ServiceFactory;
import edu.kimjones.advancedjava.stock.services.StockService;
import edu.kimjones.advancedjava.stock.services.StockService.StockQuoteInterval;
import edu.kimjones.advancedjava.stock.services.StockServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class performs the back end work of the web application Stock Quote Application.
 *
 * @author: Kim Jones
 */
public class StockSearch extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(StockSearch.class.getName());

    private static final String SYMBOL_PARAMETER_KEY = "symbol";
    private static final String FROM_PARAMETER_KEY = "from";
    private static final String UNTIL_PARAMETER_KEY = "until";
    private static final String INTERVAL_PARAMETER_KEY = "interval";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List<StockQuote> stockQuoteList = new ArrayList<>();

        try {

            /* get parameters */

            String symbol = request.getParameter(SYMBOL_PARAMETER_KEY);
            String fromDatePassed = request.getParameter(FROM_PARAMETER_KEY);
            String untilDatePassed = request.getParameter(UNTIL_PARAMETER_KEY);
            String intervalPassed = request.getParameter(INTERVAL_PARAMETER_KEY);

            Calendar fromDate = Calendar.getInstance();
            fromDate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(fromDatePassed));

            Calendar untilDate = Calendar.getInstance();
            untilDate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(untilDatePassed));

            StockQuoteInterval interval = StockQuoteInterval.fromString(intervalPassed);

            /* get stock quotes */

            StockService stockService = ServiceFactory.getStockService();
            stockQuoteList = new ArrayList<>();
            stockQuoteList = stockService.getStockQuoteList(symbol, fromDate, untilDate, interval);

        } catch (ParseException exception) {
            LOGGER.error("Invalid arguments. " + exception.getMessage());
        } catch (StockServiceException exception) {
            LOGGER.error("Unable to get quotes. " + exception.getMessage());
        }

        /* put stockQuoteList in the session */

        HttpSession session = request.getSession();
        session.setAttribute("stockQuoteList", stockQuoteList);

        /* forward the user to stockQuoteResults.jsp */

        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher =
                servletContext.getRequestDispatcher("/stockQuoteResults.jsp");
        dispatcher.forward(request, response);
    }
}
