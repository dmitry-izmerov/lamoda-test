package ru.demi.lamodatest.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Objects;

public class StockStateItemHandler extends DefaultHandler {
    private final StockStateItemProcessor processor;
    private LoadingStockStateItem item;
    private StringBuilder stringBuilder;

    public StockStateItemHandler(StockStateItemProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        stringBuilder = new StringBuilder();

        if (qName.equals("item")){
            item = new LoadingStockStateItem();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        stringBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (Objects.nonNull(item)) {
            switch (qName) {
                case "id":
                    item.setId(Integer.parseInt(stringBuilder.toString()));
                    break;
                case "sku":
                    item.setSku(stringBuilder.toString());
                    break;
                case "count":
                    item.setCount(Integer.parseInt(stringBuilder.toString()));
                    break;
                case "item":
                    processor.process(item);
                    item = null;
                    break;
            }
        }
    }

    @Override
    public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
        throw new RuntimeException(String.format("Entity declaration with name=%s is not allowed.", name));
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        throw new RuntimeException(String.format("Entity with name=%s was skipped.", name));
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        throw new RuntimeException(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        throw new RuntimeException(e);
    }
}