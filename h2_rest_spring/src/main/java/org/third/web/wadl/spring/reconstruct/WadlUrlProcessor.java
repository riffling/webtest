package org.third.web.wadl.spring.reconstruct;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.util.xml.DomUtils;
import org.third.util.XmlUtil;
import org.third.web.entity.SpringRestConfig;
import org.third.web.entity.WebXmlServlet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.util.DOMUtil;

public class WadlUrlProcessor {
    public static final String IDM_WEB_DIR = "C:/Users/gongyo/workspace/idmsh/idm-service/target/idm-service-1.8.0-SNAPSHOT";
    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    static DocumentBuilder domBuilder = null;

    {
        try {
            domBuilder = domFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Document createDocument(String file) throws SAXException, IOException {
        return domBuilder.parse(file);
    }

    public static void main(String[] args) {

        WadlUrlProcessor processor = new WadlUrlProcessor();
        try {
            Map<String, WebXmlServlet> servletUrlMap = WadlUrlGenerator
                    .parseServletPrefix(IDM_WEB_DIR + "/WEB-INF/web.xml");
            Map<String, SpringRestConfig> methodSpringRestConfig = new HashMap<String, SpringRestConfig>();
            for (Map.Entry<String, WebXmlServlet> servlet : servletUrlMap.entrySet()) {
                WadlUrlGenerator.parseSpringRestController(servlet, methodSpringRestConfig);
            }
            processor.parseIdmWadl(IDM_WEB_DIR + "/wadl/wadl.xml", methodSpringRestConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void parseIdmWadl(String wadlFile, Map<String, SpringRestConfig> methodSpringRestConfig)
            throws SAXException, IOException {
        Document wadlDoc = WadlUrlGenerator.domBuilder.parse(wadlFile);
        Element wadlRootApplication = wadlDoc.getDocumentElement();
        List<Element> resourcesList = DomUtils.getChildElementsByTagName(wadlRootApplication, "resources");
        for (Element resources : resourcesList) {
            List<Element> resourceList = DomUtils.getChildElementsByTagName(resources, "resource");
            for (Element resource : resourceList) {
                String resourcePath = resource.getAttribute("path");
                Element method = DOMUtil.getFirstChildElement(resource, "method");
                String methodName = null, methodHttpMethod = null;
                if (method != null) {
                    methodName = method.getAttribute("id");
                    methodHttpMethod = method.getAttribute("name");
                }
                String restMethodKey = WadlUrlGenerator.getRestMethod(resourcePath, methodName, methodHttpMethod);

                SpringRestConfig restConfig = methodSpringRestConfig.get(restMethodKey);
                if (null != restConfig) {
                    System.out.println("Matched: " + restMethodKey);
                    resource.setAttribute("path", restConfig.getUrl());
                } else {
                    System.out.println("Unmatched: " + restMethodKey);
                }
            }
        }
        System.out.println(XmlUtil.toString(wadlRootApplication));

    }

}
