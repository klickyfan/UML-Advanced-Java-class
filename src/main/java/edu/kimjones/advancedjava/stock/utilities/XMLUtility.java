package edu.kimjones.advancedjava.stock.utilities;

import edu.kimjones.advancedjava.stock.model.XMLDomainObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

/**
 * This class encapsulates XML related utility methods, specifically those needed for marshalling and unmarshalling XML
 * instances.
 *
 * @author Spencer Marks and Kim Jones
 */
public class XMLUtility {

    /**
     * Puts the provided XML String into the give {@code XMLDomainObject} using JAXB without using schema validation.
     *
     * @param xmlInstance           an XML instance that matches the XML domain object specified by {@code T}
     * @param tClass                an XML domain object class which corresponds the given XML instance
     * @return                      an XML domain object of type {@code T} populated with values in the
     *                              {@code xmlInstance} String provided
     * @throws InvalidXMLException  if the {@code xmlInstance} provided cannot be successfully parsed
     */
    public static  <T extends XMLDomainObject> T unmarshal(String xmlInstance, Class <T> tClass) throws InvalidXMLException {
        T returnValue;
        try {
            Unmarshaller unmarshaller = createUnmarshaller(tClass);
            returnValue = tClass.cast(unmarshaller.unmarshal(new StringReader(xmlInstance)));
        } catch (JAXBException e) {
            throw new InvalidXMLException("JAXBException occurred. " + e.getMessage(), e);
        }
        return returnValue;
    }

    /**
     * Serializes the given {@code domainClass} into an XML instance which is returned as a String.
     *
     * @param domainClass           the XML domain object class
     * @return                      a String containing a valid XML instance for the class provided
     * @throws InvalidXMLException  if the object can't be serialized into XML
     */
    public static String marshall(XMLDomainObject domainClass) throws InvalidXMLException {
       try {
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           JAXBContext context = JAXBContext.newInstance(domainClass.getClass());
           Marshaller marshaller = context.createMarshaller();
           marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
           marshaller.marshal(domainClass, byteArrayOutputStream);
           return byteArrayOutputStream.toString();
       } catch (JAXBException e) {
           throw new InvalidXMLException("JAXBException occurred. " + e.getMessage(), e);
       }
    }

    private static Unmarshaller createUnmarshaller(Class T) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(T);
        return jaxbContext.createUnmarshaller();
    }
}
