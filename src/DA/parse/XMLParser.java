package DA.parse;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;

public class XMLParser implements Parser {

    public Object parse(String address, Class classToBeBounded) {
        File xml = new File(address);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classToBeBounded);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void map(Object object, String address) {
        // JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        //  Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        //  jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        //jaxbMarshaller.marshal(object, file);
        File file = new File(address);
        try {
            if(!file.exists()){
                RandomAccessFile randomAccessFile = new RandomAccessFile(address, "rw");
                randomAccessFile.seek(file.length());
                randomAccessFile.writeBytes("<responses></responses>");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        append(file, object);

    }


    private void append(File docFile, Object object) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(docFile);
            DOMSource source = new DOMSource(doc);

            Element element = doc.createElement("response");
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                element.setAttribute(field.getName(), field.get(object).toString());
            }
            Element root = doc.getDocumentElement();
            root.appendChild(element);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, new StreamResult(docFile));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

