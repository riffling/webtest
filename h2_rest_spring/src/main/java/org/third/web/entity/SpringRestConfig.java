package org.third.web.entity;

import java.lang.reflect.Method;

public class SpringRestConfig {
    String servletUrl;
    String controllerUrl;
    Method method;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    String methodUrl;
    String methodName;
    String methodHttpMethod;
    String controller;

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getServletUrl() {
        return servletUrl;
    }

    public void setServletUrl(String servletUrl) {
        this.servletUrl = servletUrl;
    }

    public String getControllerUrl() {
        return controllerUrl;
    }

    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }

    public String getMethodUrl() {
        return methodUrl;
    }

    public void setMethodUrl(String methodUrl) {
        this.methodUrl = methodUrl;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodHttpMethod() {
        return methodHttpMethod;
    }

    public void setMethodHttpMethod(String methodHttpMethod) {
        this.methodHttpMethod = methodHttpMethod;
    }

    @Override
    public String toString() {
        return "{" + methodName + "," + servletUrl + "," + controllerUrl + "," + methodUrl + "}";
    }

    public String getUrl() {
        return servletUrl + controllerUrl + "" + methodUrl;
    }
}
