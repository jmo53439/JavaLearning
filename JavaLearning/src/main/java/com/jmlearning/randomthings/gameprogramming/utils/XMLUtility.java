package com.jmlearning.randomthings.gamingprogramming.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class XMLUtility {

    public static Document parseDoc(InputStream inputStream) throws
            ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(inputStream));

        return document;
    }

    public static Document parseDoc(Reader reader) throws
            ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(reader));

        return document;
    }

    public static List<Element> getAllElements(Element element, String tagName) {

        ArrayList<Element> elements = new ArrayList <>();
        NodeList nodes = element.getElementsByTagName(tagName);

        for(int i = 0; i < nodes.getLength(); i++) {

            elements.add((Element) nodes.item(i));
        }

        return elements;
    }

    public static List<Element> getElements(Element element, String tagName) {

        ArrayList<Element> elements = new ArrayList <>();
        NodeList children = element.getChildNodes();

        for(int i = 0; i < children.getLength(); i++) {

            Node node = children.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {

                String nodeName = node.getNodeName();

                if(nodeName != null && nodeName.equals(tagName)) {

                    elements.add((Element) node);
                }
            }
        }

        return elements;
    }
}
