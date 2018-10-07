package edu.kimjones.advancedjava.stock;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class creates a handler for args4j to use to process date command line arguments. For more information see
 * https://grysz.com/2016/03/10/implement-custom-option-handler-in-args4j/
 */
public class DateOptionHandler extends OneArgumentOptionHandler<Date> {

    public static final String DATE_FORMAT = "MM/dd/yyyy";

    public DateOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Date> setter) {
        super(parser, option, setter);
    }

    @Override
    protected Date parse(String argument) throws NumberFormatException, CmdLineException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dateFormat.parse(argument);
        } catch (ParseException ex) {
            throw new CmdLineException(owner, ex);
        }
    }
}