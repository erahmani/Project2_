package DA.parse;

import DA.parse.Parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

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
        try {
            File file = new File(address);
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
