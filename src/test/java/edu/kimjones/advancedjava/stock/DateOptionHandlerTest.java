package edu.kimjones.advancedjava.stock;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import static org.hamcrest.Matcher.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.AllOf.allOf;


/**
 * This class is for testing the DateOptionHandler class.
 *
 * @author Kim Jones
 */
@RunWith(JUnitParamsRunner.class)
public class DateOptionHandlerTest {

    private static class DateOption {
        @Option(name = "-d", aliases = "--duration", handler = DateOptionHandler.class)
        public Date date;
    }

    private DateOption option = new DateOption();

    @SuppressWarnings("unused")
    @Parameters
    private Object[] parsableOptions() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DateOptionHandler.DATE_FORMAT);

        return new Object[][] {
                { new String[] {"-d", "3/11/1968"}, dateFormat.parse("3/11/1968")},
                { new String[] {"-d", "09/30/2018"}, dateFormat.parse("09/30/2018")}
        };
    }

    @SuppressWarnings("unused")
    private Object[] notParsableOptions() {
        return new Object[][] {
                {new String[] {"-d", "not a date"}, allOf(containsString("not a date"))}
         /*       ,
                {new String[] {"--duration=1h"}, allOf(containsString("--duration"), containsString("1/1/1"))},*/
        };
    }

    @Test
    @Parameters(method = "parsableOptions")
    public void testDateOptionHandlerPositive(String[] args, Date expected) throws CmdLineException {

        new CmdLineParser(option).parseArgument(args);

        assertThat(option.date, equalTo(expected));

    }

    @Test
    @Parameters(method = "notParsableOptions")
    public void testDateOptionHandlerNegative(String[] args, Matcher<String> check) {

        try {
            new CmdLineParser(option).parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(check);
            assertThat(e.getMessage(), check);
        }
    }
}
