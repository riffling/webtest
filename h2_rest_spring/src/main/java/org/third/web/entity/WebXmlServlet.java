package org.third.web.entity;

public class WebXmlServlet {

    private String name;
    private String springConfigFile;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpringConfigFile() {
        return springConfigFile;
    }

    public void setSpringConfigFile(String springConfigFile) {
        this.springConfigFile = springConfigFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{" + name + "," + url + "," + springConfigFile + "}";
    }
}
