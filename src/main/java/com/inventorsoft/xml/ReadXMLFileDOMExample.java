package com.inventorsoft.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadXMLFileDOMExample {

    private static final String FILENAME = "src/main/resources/cityCodes.xml";

    private Map<String, String> cities = new HashMap<>();


    public Map<String, String> getCities() {

        // Строим объектную модель исходного XML файла
        final File xmlFile = new File(FILENAME);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = db.parse(xmlFile);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        // Выполнять нормализацию не обязательно, но рекомендуется
        doc.getDocumentElement().normalize();

        // Получаем все узлы с именем "city"
        NodeList nodeList = doc.getElementsByTagName("city");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                cities.put(element.getElementsByTagName("code").item(0).getTextContent(),
                        element.getElementsByTagName("name").item(0).getTextContent());
            }
        }
        return cities;
    }

}
