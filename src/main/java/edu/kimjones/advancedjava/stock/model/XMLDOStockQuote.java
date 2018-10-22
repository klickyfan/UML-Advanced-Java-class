package edu.kimjones.advancedjava.stock.model;

import javax.xml.bind.annotation.*;

/*
  Note: This class was originally generated from stock_info.xsd within IntelliJ using Tools->JAXB->Generate Xml...
  It has subsequently been modified as follows: the class has been made to implement the interface
  {@code XMLDomainObject}, the class has been renamed {@class XMLDOStockQuote}, and comments have been altered/added.
 */

/**
 * Like {@code DAOStockQuote} this class models a stock quote. It was originally generated from the anonymous complex type
 * in stock_info.xsd with the schema shown below:
 *
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="symbol" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="price" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="time" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "content"
})
public class XMLDOStockQuote implements XMLDomainObject {

    @XmlValue
    protected String content;
    @XmlAttribute(name = "symbol")
    protected String symbol;
    @XmlAttribute(name = "price")
    protected String price;
    @XmlAttribute(name = "time")
    protected String time;

    /**
     * @return the value of the content property
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     *
     * @param value the new content
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * @return the value of the symbol property (that is, the symbol of the company associated with this stock)
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the stock quote's symbol.
     *
     * @param value the new symbol (of the company to be associated with this stock)
     */
    public void setSymbol(String value) {
        this.symbol = value;
    }

    /**
     * @return the value of the price property (that is, the price of this stock)
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the stock quote's price.
     *
     * @param value the new price
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * @return the value of the time property (the time at which the stock had the set price)
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the stock quote's time.
     *
     * @param value the new time
     */
    public void setTime(String value) {
        this.time = value;
    }

    @Override
    public String toString() {
        return "XMLStockQuote{" +
                "symbol='" + symbol + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

