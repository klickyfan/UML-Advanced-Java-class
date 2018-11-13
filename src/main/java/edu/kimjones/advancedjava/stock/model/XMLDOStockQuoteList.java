package edu.kimjones.advancedjava.stock.model;

import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
  Note: This class was originally generated from stock_info.xsd within IntelliJ using Tools->JAXB->Generate Xml...
  It has subsequently been modified as follows: the class has been made to implement the interface
  {@code XMLDomainObject}, the class was renamed {@class XMLDOStockQuoteList} to better reflect its nature, and
  comments have been altered/added.
 */

/**
 * This class models a list of stock quotes. It was originally generated from the anonymous complex type in
 * stock_info.xsd with the schema shown below:
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stock" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="symbol" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="price" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="time" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stock"
})
@XmlRootElement(name = "stocks")
public class XMLDOStockQuoteList implements XMLDomainObject {

    protected List<XMLDOStockQuote> stock;

    /**
     * This method gets the value of the stockList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a {@code set} method for the
     * {@code stock} property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStockQuoteList().add(newItem);
     * </pre>
     *
     * <p>
     * Objects of the following type(s) are allowed in the list: {@link XMLDOStockQuote}
     */
    public List<XMLDOStockQuote> getStockQuoteList() {
        if (stock == null) {
            stock = new ArrayList<XMLDOStockQuote>();
        }
        return this.stock;
    }

    @Override
    public String toString() {
        return "XMLStockQuoteList{" + stock + "%n" +
                '}';
    }
}
