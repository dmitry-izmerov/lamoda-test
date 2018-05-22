package ru.demi.lamodatest.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class XmlProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(XmlProcessor.class);

//    @Transactional
    public void process(InputStream inputStream, File xsdFile, StockStateItemProcessor processor) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = schemaFactory.newSchema(new File("/resources/response.xsd"));
            Schema schema = schemaFactory.newSchema(xsdFile);

            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            saxFactory.setNamespaceAware(true);
            saxFactory.setSchema(schema);

            SAXParser parser = saxFactory.newSAXParser();
            StockStateItemHandler handler = new StockStateItemHandler(processor);
            parser.parse(inputStream, handler);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            LOG.error("Ошибка обработки входящего потока.", e);
            throw new RuntimeException(e);
        }
    }
}
