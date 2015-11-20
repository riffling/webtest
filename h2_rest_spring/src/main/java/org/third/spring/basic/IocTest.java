package org.third.spring.basic;

import org.springframework.beans.factory.BeanFactory;

public class IocTest {
    public static final String CONIG_FILE = "SpringIoc.xml";

    public static final BeanFactory getBeanFactory() {
        // // 从文件系统资源实例化BeanFactory;
        // // 默认从当前的工程目录下开始找。（工程目录/com/cao/文件目录）
        // org.springframework.core.io.Resource res = new
        // org.springframework.core.io.FileSystemResource(
        // "com/cao/bean.xml" + CONIG_FILE);
        // org.springframework.beans.factory.BeanFactory factory1 = new
        // org.springframework.beans.factory.xml.XmlBeanFactory(
        // res);
        // System.out.println("OK" + factory1);
        //
        // // 从classpath下的资源实例化BeanFactory (Eclipse 中的 src/data目录下)
        // org.springframework.core.io.Resource resClasspath = new
        // org.springframework.core.io.ClassPathResource(
        // "data/"+CONIG_FILE);
        // org.springframework.beans.factory.BeanFactory factory2 = new
        // XmlBeanFactory(resClasspath);

        // 使用ApplicationContext 从 classpath下的xml 文件实例化BeanFacotory
        String classPath = "org/third/spring/basic/";
        org.springframework.context.ApplicationContext appContext = new org.springframework.context.support.ClassPathXmlApplicationContext(
                new String[] { classPath + CONIG_FILE });
        // 直接生成类工厂的实例。
        org.springframework.beans.factory.BeanFactory factory3 = (BeanFactory) appContext;
        return factory3;
    }

    public static void main(String[] args) {
        BeanFactory bf = getBeanFactory();
        System.out.println(bf.getBean("complexBean"));

    }

}
