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
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteCityInXML {

    /**
     * Запись настроек в XML файл
     */
    public WriteCityInXML(String code, String city, String fileName) throws TransformerException, IOException {
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
        Element RootElement = doc.createElement("cities");

        Element name = doc.createElement("code");
        name.appendChild(doc.createTextNode(code));
        RootElement.appendChild(name);

        Element offerId = doc.createElement("city");
        offerId.appendChild(doc.createTextNode(city));
        RootElement.appendChild(offerId);

        doc.appendChild(RootElement);

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");

        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileName)));
    }
}
