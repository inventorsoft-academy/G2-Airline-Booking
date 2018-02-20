package com.inventorsoft.xml;

import com.inventorsoft.model.ticket.Ticket;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class WriteTicketInXML {

    /**
     * Запись настроек в XML файл
     */
    public  WriteTicketInXML(Ticket ticket, String fileName) throws TransformerException, IOException {
        //Инициализация XML
        DocumentBuilder builder = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        // запись
        Document doc = builder.newDocument();
        Element RootElement = doc.createElement("ticket");

        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(ticket.getName()));
        RootElement.appendChild(name);

        Element offerId = doc.createElement("offerId");
        offerId.appendChild(doc.createTextNode(String.valueOf(ticket.getOfferId())));
        RootElement.appendChild(offerId);

        Element departureCity = doc.createElement("departureCity");
        departureCity.appendChild(doc.createTextNode(ticket.getDepartureCity()));
        RootElement.appendChild(departureCity);

        Element arrivalCity = doc.createElement("arrivalCity");
        arrivalCity.appendChild(doc.createTextNode(ticket.getArrivalCity()));
        RootElement.appendChild(arrivalCity);

        Element departureDate = doc.createElement("departureDate");
        departureDate.appendChild(doc.createTextNode(String.valueOf(ticket.getDepartureDate())));
        RootElement.appendChild(departureDate);

        Element arrivalDate = doc.createElement("arrivalDate");
        arrivalDate.appendChild(doc.createTextNode(String.valueOf(ticket.getArrivalDate())));
        RootElement.appendChild(arrivalDate);

        Element number = doc.createElement("number");
        number.appendChild(doc.createTextNode(String.valueOf(ticket.getNumber())));
        RootElement.appendChild(number);

        Element price = doc.createElement("price");
        price.appendChild(doc.createTextNode(String.valueOf(ticket.getPrice())));
        RootElement.appendChild(price);

        doc.appendChild(RootElement);

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");

        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileName)));
    }
}
