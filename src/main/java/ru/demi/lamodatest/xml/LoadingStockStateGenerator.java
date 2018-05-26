package ru.demi.lamodatest.xml;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class LoadingStockStateGenerator {

    private Random random = new Random();

    public List<LoadingStockStateItem> getLoadingStockStateItems(int amount) {
        List<LoadingStockStateItem> items = new ArrayList<>();

        for (int i = 1; i <= amount; i++) {
            items.add(new LoadingStockStateItem(i, UUID.randomUUID().toString(), random.nextInt(100) + 1));
        }

        return items;
    }

    public static void main(String[] args) {
        String fileName = "/tmp/file.xml";
        LoadingStockStateGenerator generator = new LoadingStockStateGenerator();
//        generator.writeXml(fileName, 10_000_000); // ~ 1GB
//        generator.writeXml(fileName, 1_000_000); // ~100MB
//        generator.writeXml(fileName, 4_000_000); // ~400MB
        generator.writeXml(fileName, 100);
    }

    public void writeXml(String fileName, int amount){
        String rootTagName = "Envelope";
        String headerTagName = "Header";
        String soapPrefix = "soap";
        String soapEnvPrefix = "SOAP-ENV";
        String bodyTagName = "Body";
        String responseTagName = "LoadingStockStateResponse";
        String soapNameSpace = "http://schemas.xmlsoap.org/soap/envelope/";
        String lamodaNameSpace = "http://lamoda.ru/xsd/wms/stock-state";

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        System.out.println("Start:");

        try {
            XMLEventWriter xmlEventWriter = xmlOutputFactory
                .createXMLEventWriter(new FileOutputStream(fileName), "UTF-8");
            //For Debugging - below code to print XML to Console
//            xmlEventWriter = xmlOutputFactory.createXMLEventWriter(System.out);
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();

            StartDocument startDocument = eventFactory.createStartDocument();
            XMLEvent newLine = eventFactory.createDTD("\n");
            XMLEvent tab = eventFactory.createDTD("\t");
            xmlEventWriter.add(startDocument);
            xmlEventWriter.add(newLine);

            StartElement rootStartElement = eventFactory
                .createStartElement(soapPrefix, soapNameSpace, rootTagName, null, null);
            xmlEventWriter.add(rootStartElement);
            xmlEventWriter.add(eventFactory.createAttribute("xmlns", soapNameSpace, "soap", soapNameSpace));
            xmlEventWriter.add(newLine);

            StartElement headerStartElement = eventFactory
                .createStartElement(soapEnvPrefix, soapNameSpace, headerTagName);
            xmlEventWriter.add(tab);
            xmlEventWriter.add(headerStartElement);
            xmlEventWriter.add(eventFactory.createAttribute("xmlns", soapNameSpace, "SOAP-ENV", soapNameSpace));
            xmlEventWriter.add(eventFactory.createEndElement(soapEnvPrefix, soapNameSpace, headerTagName));
            xmlEventWriter.add(newLine);

            StartElement bodyStartElement = eventFactory
                .createStartElement(soapPrefix, "", bodyTagName);
            xmlEventWriter.add(bodyStartElement);
            xmlEventWriter.add(newLine);

            StartElement responseStartElement = eventFactory
                .createStartElement("", lamodaNameSpace, responseTagName);
            xmlEventWriter.add(responseStartElement);
            xmlEventWriter.add(eventFactory.createAttribute("xmlns", lamodaNameSpace));
            xmlEventWriter.add(newLine);

            for (int i = 1; i <= amount; i++) {
                createItem(xmlEventWriter, i);
                System.out.printf("%.1f%%%n", ((float) i / amount) * 100);
            }

            xmlEventWriter.add(eventFactory.createEndElement("", "", responseTagName));
            xmlEventWriter.add(newLine);

            xmlEventWriter.add(eventFactory.createEndElement(soapPrefix, "", bodyTagName));
            xmlEventWriter.add(newLine);

            xmlEventWriter.add(eventFactory.createEndElement(soapPrefix, "", rootTagName));
            xmlEventWriter.add(newLine);
            xmlEventWriter.add(eventFactory.createEndDocument());
            xmlEventWriter.close();

            System.out.println("Complete!");

        } catch (FileNotFoundException | XMLStreamException e) { //
            throw new RuntimeException(e);
        }
    }

    private void createItem(XMLEventWriter eventWriter, int i) throws XMLStreamException {
        String element = "item";
        String sku = UUID.randomUUID().toString();
        int count = random.nextInt(100) + 1;

        XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
        XMLEvent newLine = xmlEventFactory.createDTD("\n");
        XMLEvent tab = xmlEventFactory.createDTD("\t");

        StartElement sElement = xmlEventFactory.createStartElement("", "", element);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        eventWriter.add(newLine);

        createNode(eventWriter, "id", String.valueOf(i));
        createNode(eventWriter, "sku", sku);
        createNode(eventWriter, "count", String.valueOf(count));

        EndElement eElement = xmlEventFactory.createEndElement("", "", element);
        eventWriter.add(tab);
        eventWriter.add(eElement);
        eventWriter.add(newLine);
    }

    private void createNode(XMLEventWriter eventWriter, String element, String value) throws XMLStreamException {
        createNode(eventWriter, element, value, "", "");
    }

    private void createNode(XMLEventWriter eventWriter, String element, String value, String prefix, String nameSpace) throws XMLStreamException {
        XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
        XMLEvent newLine = xmlEventFactory.createDTD("\n");
        XMLEvent tab = xmlEventFactory.createDTD("\t");
        //Create Start node
        StartElement sElement = xmlEventFactory.createStartElement("", "", element);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        //Create Content
        Characters characters = xmlEventFactory.createCharacters(value);
        eventWriter.add(characters);
        // Create End node
        EndElement eElement = xmlEventFactory.createEndElement("", "", element);
        eventWriter.add(eElement);
        eventWriter.add(newLine);
    }
}
