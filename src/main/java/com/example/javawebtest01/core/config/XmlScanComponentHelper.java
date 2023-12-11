package com.example.javawebtest01.core.config;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author zhangshengji
 * @since 2023/12/11 15:47
 */
public class XmlScanComponentHelper {

    public static List<String> getNodeValues(URL xmlPath) {
        // 获取需要扫描的包
        List<String> packages = new ArrayList<>();

        SAXReader reader = new SAXReader();
        try {
            Document read = reader.read(xmlPath);
            Element rootElement = read.getRootElement();
            Iterator iterator = rootElement.elementIterator();
            while (iterator.hasNext()) {
                Element next = (Element)iterator.next();
                String value = next.attributeValue("base-package");
                if (value != null && !value.isEmpty()) {
                    packages.add(value);
                }
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        return packages;
    }
}
