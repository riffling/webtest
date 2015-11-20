package org.third.web.wadl.spring.reconstruct;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.xml.DomUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.third.web.entity.SpringRestConfig;
import org.third.web.entity.WebXmlServlet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class WadlUrlGenerator {
    public static final String IDM_WEB_DIR = "C:/Users/gongyo/workspace/idmsh/idm-service/target/idm-service-1.8.0-SNAPSHOT";

    static DocumentBuilder domBuilder = null;

    static final DocumentBuilder getDocumentBuilder() {
        if (null == domBuilder) {
            synchronized (WadlUrlGenerator.class) {
                if (null == domBuilder) {
                    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                    try {
                        domBuilder = domFactory.newDocumentBuilder();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return domBuilder;
    }

    public Document createDocument(String file) throws SAXException, IOException {
        return domBuilder.parse(file);
    }

    public static void main(String[] args) {

        WadlUrlGenerator processor = new WadlUrlGenerator();
        try {
            Map<String, WebXmlServlet> servletUrlMap = processor.parseServletPrefix(IDM_WEB_DIR + "/WEB-INF/web.xml");
            Map<String, SpringRestConfig> methodSpringRestConfig = new HashMap<String, SpringRestConfig>();
            for (Map.Entry<String, WebXmlServlet> servlet : servletUrlMap.entrySet()) {
                parseSpringRestController(servlet, methodSpringRestConfig);
            }
            // parseIdmWadl(IDM_WEB_DIR + "/wadl/wadl.xml",
            // methodSpringRestConfig);
            generateIdmWadl(IDM_WEB_DIR + "/wadl/wadl.xml", methodSpringRestConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void generateIdmWadl(String wadlOutput, Map<String, SpringRestConfig> methodSpringRestConfig)
            throws IOException {
        org.dom4j.Document wadlDoc = org.dom4j.DocumentHelper.createDocument();
        org.dom4j.Element applicationElement = addRootApplication(wadlDoc);
        addGrammars(applicationElement);
        /////
        org.dom4j.Element resourcesElement = applicationElement.addElement("resources");
        resourcesElement.addAttribute("base", "http://SGDLITVM0996.hpswlabs.adapps.hp.com:18080/idm");
        for (Map.Entry<String, SpringRestConfig> configEntry : methodSpringRestConfig.entrySet()) {
            org.dom4j.Element resourceElement = resourcesElement.addElement("resource");
            SpringRestConfig config = configEntry.getValue();
            String resourcePath = config.getServletUrl() + config.getControllerUrl() + config.getMethodUrl();
            resourceElement.addAttribute("path", resourcePath);

            org.dom4j.Element methodElement = resourceElement.addElement("method");
            methodElement.addAttribute("id", config.getMethodName());
            methodElement.addAttribute("name", config.getMethodHttpMethod());
            methodElement.addAttribute("class", config.getController());

            org.dom4j.Element requestElement = null;
            for (int i = 0, size = config.getMethod().getParameters().length; i < size; i++) {
                Parameter javaParam = config.getMethod().getParameters()[i];
                if (javaParam.isAnnotationPresent(org.springframework.web.bind.annotation.PathVariable.class)) {
                    PathVariable pathVariable = (PathVariable) javaParam.getAnnotation(PathVariable.class);

                    org.dom4j.Element paramElement = methodElement.addElement("param");
                    paramElement.addAttribute("name", pathVariable.value());
                    paramElement.addAttribute("style", "template");
                    paramElement.addAttribute("required", "true");

                    paramElement.addAttribute("type", getSchemaType(javaParam));
                } else if (javaParam.isAnnotationPresent(org.springframework.web.bind.annotation.RequestParam.class)) {
                    RequestParam requestParam = (RequestParam) javaParam.getAnnotation(RequestParam.class);
                    if (requestElement == null)
                        requestElement = methodElement.addElement("request");
                    org.dom4j.Element requestParamElement = requestElement.addElement("param");
                    requestParamElement.addAttribute("name", requestParam.value());
                    requestParamElement.addAttribute("style", "query");
                    requestParamElement.addAttribute("type", getSchemaType(javaParam));
                    requestParamElement.addAttribute("required", String.valueOf(requestParam.required()));
                    requestParamElement.addAttribute("defaultValue", requestParam.defaultValue());

                    // <param name="start" style="query" type="xs:integer"
                    // default="0" required="false" />

                } else if (javaParam.isAnnotationPresent(org.springframework.web.bind.annotation.RequestBody.class)) {
                    if (requestElement == null)
                        requestElement = methodElement.addElement("request");
                    requestElement.addAttribute("xmlns:idm",
                            "http://www.hp.com/schema/" + javaParam.getClass().getSimpleName());
                    requestElement.addAttribute("element", "idm:" + javaParam.getClass().getSimpleName());
                    requestElement.addAttribute("mediaType", "application/json");
                }
            }

            org.dom4j.Element responseElement = methodElement.addElement("response");
            Class returnType = config.getMethod().getReturnType();
            if (ResponseEntity.class == returnType) {
                responseElement.addAttribute("status", "200");
                org.dom4j.Element representationElement = responseElement.addElement("representation");
                representationElement.addAttribute("xmlns:rsp", "http://www.springframework.org/schema/responseEntity");
                representationElement.addAttribute("element", "rsp:responseEntity");
                representationElement.addAttribute("mediaType", "application/json");
            } else {
                responseElement.addAttribute("status", "200");
                if (void.class != returnType) {
                    org.dom4j.Element representationElement = responseElement.addElement("representation");
                    representationElement.addAttribute("xmlns:idm",
                            "http://www.hp.com/schema/" + returnType.getSimpleName());
                    representationElement.addAttribute("element", "idm:" + returnType.getSimpleName());
                    representationElement.addAttribute("mediaType", "application/json");
                }
            }

        }

        ////
        writeToFile(wadlDoc);
    }

    private static String getSchemaType(Parameter javaParam) {
        String type = "xs:stdring";
        if (javaParam.getType() == String.class) {
            type = "xs:string";
        } else if (javaParam.getType() == int.class || javaParam.getType() == Integer.class) {
            type = "xs:int";
        } else if (javaParam.getType() == long.class || javaParam.getType() == Long.class) {
            type = "xs:long";
        } else {
            type = "idm:" + javaParam.getClass().getSimpleName();
        }
        return type;
    }

    static void writeToFile(org.dom4j.Document wadlDoc) throws IOException {
        // org.dom4j.io.XMLWriter writer = new XMLWriter(new
        // FileWriter(IDM_WEB_DIR + "/WEB-INF/idm.wadl"));
        // writer.write(wadlDoc);
        // writer.close();

        // 美化格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        org.dom4j.io.XMLWriter writer = new XMLWriter(new FileWriter(IDM_WEB_DIR + "/WEB-INF/idm.wadl"), format);
        writer.write(wadlDoc);
        writer.close();
        //
        // // 缩减格式
        // format = OutputFormat.createCompactFormat();
        // writer = new XMLWriter(System.out, format);
        // writer.write(wadlDoc);

    }

    private static void addGrammars(org.dom4j.Element applicationElement) {
        org.dom4j.Element grammarsElement = applicationElement.addElement("grammars");
        String[] fileList = new String[] { "schema/metadata", "schema/role", "schema/organizationList",
                "schema/baseGroupRepresentationCollection", "schema/responseEntity",
                "schema/baseAuthenticationConfiguration", "schema/authenticationSuccessResponse",
                "schema/authenticationResponse", "schema/abstractUser", "schema/request_token",
                "schema/keystoneConfiguration", "schema/baseGroupRepresentation", "schema/samlConfig",
                "schema/metadataList", "schema/loginResponse", "schema/requestTokenDetails", "schema/org",
                "schema/organizationInfoView", "schema/versionInfo", "schema/roleList", "schema/version",
                "schema/token", "schema/keystoneConfigurationList", "schema/baseUserRepresentation",
                "schema/abstractGroup", "schema/organization", "schema/pagedResponse" };
        for (String schemaFile : fileList) {
            org.dom4j.Element includeElement = grammarsElement.addElement("include");
            includeElement.addAttribute("href", schemaFile);
        }
    }

    private static org.dom4j.Element addRootApplication(org.dom4j.Document wadlDoc) {
        org.dom4j.Element applicationElement = wadlDoc.addElement("application");
        applicationElement.addAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
        applicationElement.addAttribute("xmlns", "http://wadl.dev.java.net/2009/02");

        org.dom4j.Element docElement = applicationElement.addElement("doc");
        docElement.addAttribute("title", "IDM REST API WADL");

        return applicationElement;
    }

    public static final String getRestMethod(String methodUrl, String methodName, String methodHttpMethod) {
        return methodUrl + "#" + methodName + "#" + methodHttpMethod;
    }

    static void parseSpringRestController(Entry<String, WebXmlServlet> servlet,
            Map<String, SpringRestConfig> methodSpringRestConfig)
                    throws SAXException, IOException, ClassNotFoundException {
        Document springConfigDoc = domBuilder.parse(IDM_WEB_DIR + servlet.getValue().getSpringConfigFile());
        Element springConfigRootElement = springConfigDoc.getDocumentElement();

        List<Element> beanList = DomUtils.getChildElementsByTagName(springConfigRootElement, "bean");
        for (Element bean : beanList) {
            String beanClassName = bean.getAttribute("class");
            Class beanClass = Class.forName(beanClassName);
            Controller springRestController = (Controller) beanClass
                    .getAnnotation(org.springframework.stereotype.Controller.class);
            if (null != springRestController) {
                RequestMapping springRestClassRequestMapping = (RequestMapping) beanClass
                        .getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class);
                String classRequestMapping = "";
                if (null != springRestClassRequestMapping) {
                    classRequestMapping = springRestClassRequestMapping.value()[0];

                }
                System.out.println("Rest Controller: " + beanClass + "," + classRequestMapping);
                Method[] methods = beanClass.getDeclaredMethods();
                for (Method method : methods) {
                    RequestMapping springRestMethodRequestMapping = method
                            .getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class);

                    if (null != springRestMethodRequestMapping) {

                        System.out.println("\t" + servlet.getKey() + ",  " + classRequestMapping
                                + springRestMethodRequestMapping.value()[0] + ",  " + method.getName() + ",  "
                                + springRestMethodRequestMapping.method()[0].name());

                        SpringRestConfig config = new SpringRestConfig();
                        config.setServletUrl(servlet.getKey());
                        config.setControllerUrl(classRequestMapping);
                        config.setMethod(method);
                        config.setMethodUrl(springRestMethodRequestMapping.value()[0]);
                        config.setMethodName(method.getName());
                        config.setMethodHttpMethod(springRestMethodRequestMapping.method()[0].name());
                        config.setController(beanClass.getName());

                        methodSpringRestConfig.put(getRestMethod(classRequestMapping + config.getMethodUrl(),
                                config.getMethodName(), config.getMethodHttpMethod()), config);
                    }
                }

            }
        }

    }

    /**
     * {/v2.0={restservices,/v2.0,/WEB-INF/spring/applicationContext.xml},
     * /idm/v0={authv0,/idm/v0,/WEB-INF/spring/applicationContext-v0.xml},
     * /api/scim={scim,/api/scim,/WEB-INF/spring/applicationContext-scim.xml},
     * /api={api,/api,/WEB-INF/spring/applicationContext-api.xml}}
     * 
     * @param _webxml
     * @return
     * @throws SAXException
     * @throws IOException
     */
    static final Map<String, WebXmlServlet> parseServletPrefix(String _webxml) throws SAXException, IOException {

        Map<String, String> servletSpringConfigMap = new HashMap<String, String>();

        Document webxmlDoc = getDocumentBuilder().parse(_webxml);
        Element webxmlRootElement = webxmlDoc.getDocumentElement();

        List<Element> servletList = DomUtils.getChildElementsByTagName(webxmlRootElement, "servlet");
        for (Element servletElement : servletList) {
            String servletName = DomUtils.getChildElementValueByTagName(servletElement, "servlet-name");
            String servletClass = DomUtils.getChildElementValueByTagName(servletElement, "servlet-class");
            String paramName = null, paramValue = null;
            List<Element> initParamList = DomUtils.getChildElementsByTagName(servletElement, "init-param");
            for (Element initParamElement : initParamList) {
                paramName = DomUtils.getChildElementValueByTagName(initParamElement, "param-name");
                if ("contextConfigLocation".equals(paramName)) {
                    paramValue = DomUtils.getChildElementValueByTagName(initParamElement, "param-value").trim();
                }
            }

            if ("org.springframework.web.servlet.DispatcherServlet".equals(servletClass) && null != paramValue
                    && paramValue.length() > 0) {
                servletSpringConfigMap.put(servletName, paramValue);
            }

        }

        Map<String, WebXmlServlet> servletUrlMap = new HashMap<String, WebXmlServlet>();
        List<Element> servletMappingList = DomUtils.getChildElementsByTagName(webxmlRootElement, "servlet-mapping");
        for (Element servletElement : servletMappingList) {
            String servletName = DomUtils.getChildElementValueByTagName(servletElement, "servlet-name");
            String urlPattern = DomUtils.getChildElementValueByTagName(servletElement, "url-pattern");
            String springContextConfig = servletSpringConfigMap.get(servletName);
            if (null != springContextConfig && null != urlPattern) {
                urlPattern.trim();
                if (urlPattern.endsWith("/*")) {
                    urlPattern = urlPattern.substring(0, urlPattern.length() - 2);
                }
                WebXmlServlet servlet = new WebXmlServlet();
                servlet.setName(servletName);
                servlet.setSpringConfigFile(springContextConfig);
                servlet.setUrl(urlPattern);
                servletUrlMap.put(urlPattern, servlet);
            }
        }

        return servletUrlMap;
    }
}
