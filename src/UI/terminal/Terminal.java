package UI.terminal;

import BL.log.Logger;
import DA.parse.XMLParser;
import UI.transferObject.Request;
import UI.transferObject.Response;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.List;

@XmlRootElement(name = "terminal")
public class Terminal implements Runnable {
    private Socket client;
    @XmlElementWrapper(name = "transactions")
    @XmlElement(name = "transaction")
    private List<Transaction> transactions;
    private String terminalFileAddress;
    private String id;
    private String type;
    private Server server;
    private OutLog outLog;
    private String responseFile;

    public Terminal() {

    }

    public Terminal(String terminalFileAddress, String responseFile) {
        this.terminalFileAddress = terminalFileAddress;
        this.responseFile = responseFile;
        initializeTerminal();
    }

    @XmlAttribute(name = "id")
    private void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "type")
    private void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "server")
    private void setServer(Server server) {
        this.server = server;
    }

    @XmlElement(name = "outLog")
    private void setOutLog(OutLog outLog) {
        this.outLog = outLog;
    }

    public void run() {
        // age fail shod chi?
        for (Transaction transaction:transactions) {
            Request request = Request.convertToRequest(transaction);
            request.setTerminalId(id);
            sendRequestToServer(request);
            Response response = getResponseFromServer();
            writeResponseFile(response);
            eventLog(response);
        }
    }

    private void initializeTerminal() {
        Terminal terminal = (Terminal) new XMLParser().parse(terminalFileAddress, Terminal.class);
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.get(terminal) != null) {
                    field.set(this, field.get(terminal));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestToServer(Request request ) {
        ObjectOutputStream objectOutputStream = null;
        try {
            client = new Socket(this.server.getIp(), this.server.getPort());
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(request);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private Response getResponseFromServer(){
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(client.getInputStream());
            return (Response) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeResponseFile(Response response) {
        new XMLParser().map(response,responseFile);
    }

    private void eventLog(Response response){
        new Logger().log(response,outLog.getPath());
    }
}