package com.example;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import com.dreamsyssoft.demo.rest.model.User;

@javax.xml.bind.annotation.XmlRootElement(name = "##default")
@javax.xml.bind.annotation.XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    private long id;
    @javax.xml.bind.annotation.XmlElement
    private String name = "c";
    private Sex sex = Sex.MALE;

    private User user = new User();
    private int age = 12;
    @javax.xml.bind.annotation.XmlTransient
    private long tempId;

    static JAXBContext jaxbCtx = null;

    public static void init() throws JAXBException {
        jaxbCtx = JAXBContext.newInstance(Customer.class);
        javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
    }

    public static void main(String[] args) {
        try {
            init();
            testMarshaller();

            testSchemaOutput();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void testMarshaller() throws JAXBException {
        javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();

        Customer c = new Customer();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "utf-8");
        marshaller.setProperty("jaxb.formatted.output", true);
        marshaller.marshal(c, System.out);
    }

    static class WadlSchemaOutputResolver extends javax.xml.bind.SchemaOutputResolver {
        int counter = 0;
        final List<StreamResult> results = new ArrayList<>();

        public WadlSchemaOutputResolver() {
            
        }

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            final StreamResult result = new StreamResult(new CharArrayWriter());
            result.setSystemId("xsd" + (counter++) + ".xsd");
            results.add(result);
            return result;
        }

    }

    private static void testSchemaOutput() throws IOException {

        WadlSchemaOutputResolver schemaOutputResolver = new WadlSchemaOutputResolver();
        jaxbCtx.generateSchema(schemaOutputResolver);

        // Store the new files for later use
        //

        for (final StreamResult result : schemaOutputResolver.results) {
            final CharArrayWriter writer = (CharArrayWriter) result.getWriter();
            System.out.println(writer.toString());
            // final byte[] contents = writer.toString().getBytes("UTF8");
            // extraFiles.put(result.getSystemId(),
            // new
            // ApplicationDescription.ExternalGrammar(MediaType.APPLICATION_XML_TYPE,
            // // I
            // // don't
            // // think
            // // there
            // // is
            // // a
            // // specific
            // // media
            // // type
            // // for
            // // XML
            // // Schema
            // contents));
        }

    }
}
