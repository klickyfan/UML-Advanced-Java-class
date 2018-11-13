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
    private static final String SERVICE_TYPE_PARAMETER_KEY = "service";
    private static final String INTERVAL_PARAMETER_KEY = "interval";

    private static final String HTML_INPUT_DATE_FORMAT = "yyyy-MM-dd";

    private static final String RESULTS_JSP = "/stockQuoteResults.jsp";

    /**
    Does the work of the Stock Quote Application (on post).
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        if (session!= null) {
            session.invalidate();
        }

        try {

            /* get {@code stockQuoteList} */

            List<StockQuote> stockQuoteList = getStockQuotes(request);

            /* put {@code stockQuoteList} in the session */

            session = request.getSession();
            session.setAttribute("stockQuoteList", stockQuoteList);

            /* return to results jsp */

            forward(request, response);

        } catch (BadRequestException exception) {

            session = request.getSession();
            session.setAttribute("errorMessage", exception.getMessage());

            forward(request, response);
        }
    }

    /**
     * Gets the list of stock quotes requested.
     *
     * @param request               an instance of {@code HttpServletRequest} that encapsulates all the parameters
     *                              passed to the servlet
     * @return                      a list of stock quotes that satisfy the parameters in {@code request}
     * @throws BadRequestException  if an error occurs while trying to get the stock quotes
     */
    private List<StockQuote> getStockQuotes(HttpServletRequest request) throws BadRequestException {

        List<StockQuote> stockQuoteList;

        try {

             /* process parameters */

            String symbol = request.getParameter(SYMBOL_PARAMETER_KEY);
            String fromDatePassed = request.getParameter(FROM_PARAMETER_KEY);
            String untilDatePassed = request.getParameter(UNTIL_PARAMETER_KEY);
            String serviceTypePassed = request.getParameter(SERVICE_TYPE_PARAMETER_KEY);
            String intervalPassed = request.getParameter(INTERVAL_PARAMETER_KEY);

            Calendar fromDate = Calendar.getInstance();
            fromDate.setTime(new SimpleDateFormat(HTML_INPUT_DATE_FORMAT).parse(fromDatePassed));

            Calendar untilDate = Calendar.getInstance();
            untilDate.setTime(new SimpleDateFormat(HTML_INPUT_DATE_FORMAT).parse(untilDatePassed));

            ServiceFactory.ServiceType serviceType = ServiceFactory.ServiceType.fromString(serviceTypePassed);
            StockQuoteInterval interval = StockQuoteInterval.fromString(intervalPassed);

            /* get stock quotes */

            stockQuoteList = getStockQuotes(symbol, fromDate, untilDate, serviceType, interval);

        } catch (ParseException exception) {

            LOGGER.error("Invalid arguments. " + exception.getMessage());
            throw new BadRequestException("You selected invalid arguments.");

        } catch (StockServiceException exception) {

            LOGGER.error(exception.getMessage());
            throw new BadRequestException(exception.getMessage());
        }

        return stockQuoteList;
    }

    /**
     * Gets a list of stock quotes for the company indicated by the given symbol, one for each period in the given
     * interval in the given date range.

     * @param symbol                    a stock symbol of a company, e.g. "APPL" for Apple
     * @param from                      the date of the first stock quote
     * @param until                     the date of the last stock quote
     * @param interval                  the interval between which stock quotes should be obtained, i.e. if DAILY, then
     *                                  one per day
     * @return                          a list of stock quotes for the company with the given symbol, one for each
     *                                  period in the given interval in the given date range
     * @throws StockServiceException    if an error occurs while trying to get the stock quotes
     * @throws BadRequestException      if unable to find any stock quotes matching the request
     */
    private List<StockQuote> getStockQuotes(String symbol, Calendar from, Calendar until, ServiceFactory.ServiceType serviceType, StockQuoteInterval interval) throws StockServiceException, BadRequestException {

        List<StockQuote> stockQuoteList;
        StockService stockService = ServiceFactory.getStockService(serviceType);

        stockQuoteList = stockService.getStockQuoteList(symbol, from, until, interval);

        if (stockQuoteList.isEmpty()) {
            String serviceName = serviceType == ServiceFactory.ServiceType.DATABASE ? "The database" : "Yahoo";
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            String message =
                    String.format("%s has no %s stock data for symbol %s from %s to %s.",
                                   serviceName, interval.toString().toLowerCase(), symbol, format.format(from.getTime()), format.format(until.getTime()));
            throw new BadRequestException(message);
        }
        return stockQuoteList;
    }

    private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher =
                servletContext.getRequestDispatcher(RESULTS_JSP);
        dispatcher.forward(request, response);
    }
}
