package ru.demi.lamodatest.controller;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.demi.lamodatest.entity.StockStateItem;
import ru.demi.lamodatest.service.StockStateItemsService;
import ru.demi.lamodatest.xml.XmlProcessor;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@Controller
public class XmlLoaderController {

    private static final Logger LOG = LoggerFactory.getLogger(XmlLoaderController.class);

    private final XmlProcessor xmlProcessor;
    private final StockStateItemsService stockStateItemsService;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Autowired
    public XmlLoaderController(XmlProcessor xmlProcessor, StockStateItemsService stockStateItemsService) {
        this.xmlProcessor = xmlProcessor;
        this.stockStateItemsService = stockStateItemsService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello world!";
    }

    @PostMapping("/load")
    public ResponseEntity loadData(HttpServletRequest request) {

        ServletFileUpload upload = new ServletFileUpload();

        try {
            FileItemIterator iterator = upload.getItemIterator(request);
            if (iterator.hasNext()) {
                FileItemStream item = iterator.next();

                if (!item.isFormField()) {
                    try (InputStream inputStream = item.openStream()) {
                        File xsdFile = new File(request.getServletContext().getRealPath("WEB-INF/classes/response.xsd"));
                        List<StockStateItem> items = new ArrayList<>(batchSize);
                        xmlProcessor.process(inputStream, xsdFile, loadingStockStateItem -> {
                            StockStateItem stockStateItem = new StockStateItem(loadingStockStateItem.getSku(), loadingStockStateItem.getCount());
                            items.add(stockStateItem);
                            if (items.size() == batchSize) {
                                stockStateItemsService.saveAll(items);
                                items.clear();
                            }
                        });

                        if (!items.isEmpty()) {
                            stockStateItemsService.saveAll(items);
                        }
                    }
                }
            }
        } catch (Exception e) {
            stockStateItemsService.deleteAll();
            LOG.error("Ошибка при обработке файла.", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
