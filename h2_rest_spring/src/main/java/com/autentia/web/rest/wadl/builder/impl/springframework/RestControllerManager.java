package com.autentia.web.rest.wadl.builder.impl.springframework;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.util.xml.DomUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.third.web.entity.SpringRestConfig;
import org.third.web.entity.WebXmlServlet;
import org.third.web.wadl.spring.reconstruct.WadlUrlGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class RestControllerManager {
    static RestControllerManager instance;
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

    private RestControllerManager() {
//        initCache();
        String restBeanServletMapperString = "com.hp.ccue.identity.web.api.RoleAndGroupController=/api/scim, com.hp.ccue.identity.web.api.AbstractGroupRepresentationController=/api/scim, com.hp.ccue.identity.web.api.VersionInfoController=/api, com.hp.ccue.identity.web.api.KeystoneController=/api/scim, com.hp.ccue.identity.web.api.TokenController=/idm/v0, com.hp.ccue.identity.web.api.AbstractGroupController=/api/scim, com.hp.ccue.identity.web.api.AuthenticationController=/idm/v0, com.hp.ccue.identity.web.LoginPageController=/idm/v0, com.hp.ccue.identity.web.api.TenantController=/idm/v0, com.hp.ccue.identity.web.api.OrganizationInfoController=/api, com.hp.ccue.identity.web.api.SamlConfigController=/api/scim, com.hp.ccue.identity.web.api.AbstractUserRepresentationController=/api/scim, com.hp.ccue.identity.web.api.IdentityController=/v2.0, com.hp.ccue.identity.web.api.ImageProxyController=/idm/v0, com.hp.ccue.identity.web.api.OrganizationController=/api/scim, com.hp.ccue.identity.web.api.AbstractUserController=/api/scim,  com.hp.ccue.identity.web.api.AuthenticationConfigurationController=/api/scim,";
        String[] restBeanServletUrlMapperArray = restBeanServletMapperString.split(",");
        for (String entry : restBeanServletUrlMapperArray) {
            String[] keyValue = entry.trim().split("=");
            if (keyValue.length == 2) {
                restBeanServletUrlMapper.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public final Map<String, String> restBeanServletUrlMapper = new ConcurrentHashMap<String, String>();

    private static final Object LOCK = new Object();

    public static final RestControllerManager getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new RestControllerManager();
                }
            }
        }
        return instance;
    }

    final Map<String, WebXmlServlet> parseServletPrefix(String _webxml)
            throws SAXException, IOException, ClassNotFoundException {

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
                Map<String, SpringRestConfig> methodSpringRestConfig = new HashMap<String, SpringRestConfig>();

                parseSpringRestController(servlet, methodSpringRestConfig);
            }
        }

        return servletUrlMap;
    }

    void parseSpringRestController(WebXmlServlet servlet, Map<String, SpringRestConfig> methodSpringRestConfig)
            throws SAXException, IOException, ClassNotFoundException {
        System.out.println("Parsing: " + this.appDirectory + servlet.getSpringConfigFile());
        Document springConfigDoc = domBuilder.parse(this.appDirectory + servlet.getSpringConfigFile());
        Element springConfigRootElement = springConfigDoc.getDocumentElement();

        List<Element> beanList = DomUtils.getChildElementsByTagName(springConfigRootElement, "bean");
        for (Element bean : beanList) {
            String beanClassName = bean.getAttribute("class");
            Class beanClass = Class.forName(beanClassName);
            RequestMapping springRestClassRequestMapping = (RequestMapping) beanClass
                    .getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class);
            String classRequestMapping = "";
            boolean restController = false;
            if (null != springRestClassRequestMapping) {
                if (null != springRestClassRequestMapping) {
                    classRequestMapping = springRestClassRequestMapping.value()[0];
                }
                restController = true;
            } else {
                org.springframework.stereotype.Controller springRestClassController = (org.springframework.stereotype.Controller) beanClass
                        .getAnnotation(org.springframework.stereotype.Controller.class);
                if (null != springRestClassController) {
                    classRequestMapping = "";
                    restController = true;
                }
            }
            if (restController) {
                restBeanServletUrlMapper.put(beanClassName, servlet.getUrl());
                System.out.println("Rest Controller: " + beanClass + "," + classRequestMapping);
                Method[] methods = beanClass.getDeclaredMethods();
                for (Method method : methods) {
                    RequestMapping springRestMethodRequestMapping = method
                            .getAnnotation(org.springframework.web.bind.annotation.RequestMapping.class);

                    if (null != springRestMethodRequestMapping) {

                        System.out
                                .print("\t" + method.getName() + ", " + servlet.getUrl() + ", " + classRequestMapping);
                        System.out.println(springRestMethodRequestMapping.value()[0] + ", "
                                + springRestMethodRequestMapping.method()[0].name());

                        SpringRestConfig config = new SpringRestConfig();
                        config.setServletUrl(servlet.getUrl());
                        config.setControllerUrl(classRequestMapping);
                        config.setMethod(method);
                        config.setMethodUrl(springRestMethodRequestMapping.value()[0]);
                        config.setMethodName(method.getName());
                        config.setMethodHttpMethod(springRestMethodRequestMapping.method()[0].name());
                        config.setController(beanClass.getName());

                        // methodSpringRestConfig.put(getRestMethod(classRequestMapping
                        // + config.getMethodUrl(),
                        // config.getMethodName(),
                        // config.getMethodHttpMethod()), config);
                    }
                }

            }
        }

    }

    private String appDirectory;
    private static String classesDirectory;

    private void initCache() {
        if (appDirectory == null) {
            if (classesDirectory == null) {
                classesDirectory = getClass().getClassLoader().getResource("").getPath();
            }
            File f = new File(classesDirectory);
            appDirectory = f.getParentFile().getParent();

        }
        String _webXml = appDirectory + "/WEB-INF/web.xml";
        try {
            parseServletPrefix(_webXml);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getServletPrefix(String controllName) {
        return restBeanServletUrlMapper.get(controllName);
    }

    public static void main(String[] args) {
        RestControllerManager.classesDirectory = "C:/Users/gongyo/workspace/mvntest/spring_wadl/src/main/webapp/WEB-INF/classes";
        RestControllerManager rm = RestControllerManager.getInstance();

        
        rm.restBeanServletUrlMapper.clear();
        String restBeanServletMapperString = "com.hp.ccue.identity.web.api.RoleAndGroupController=/api/scim, com.hp.ccue.identity.web.api.AbstractGroupRepresentationController=/api/scim, com.hp.ccue.identity.web.api.VersionInfoController=/api, com.hp.ccue.identity.web.api.KeystoneController=/api/scim, com.hp.ccue.identity.web.api.TokenController=/idm/v0, com.hp.ccue.identity.web.api.AbstractGroupController=/api/scim, com.hp.ccue.identity.web.api.AuthenticationController=/idm/v0, com.hp.ccue.identity.web.LoginPageController=/idm/v0, com.hp.ccue.identity.web.api.TenantController=/idm/v0, com.hp.ccue.identity.web.api.OrganizationInfoController=/api, com.hp.ccue.identity.web.api.SamlConfigController=/api/scim, com.hp.ccue.identity.web.api.AbstractUserRepresentationController=/api/scim, com.hp.ccue.identity.web.api.IdentityController=/v2.0, com.hp.ccue.identity.web.api.ImageProxyController=/idm/v0, com.hp.ccue.identity.web.api.OrganizationController=/api/scim, com.hp.ccue.identity.web.api.AbstractUserController=/api/scim";
        String[] restBeanServletUrlMapperArray = restBeanServletMapperString.split(",");
        for (String entry : restBeanServletUrlMapperArray) {
            String[] keyValue = entry.trim().split("=");
            if (keyValue.length == 2) {
                rm.restBeanServletUrlMapper.put(keyValue[0], keyValue[1]);
            }
        }
        System.out.println(rm.restBeanServletUrlMapper);
    }
}
