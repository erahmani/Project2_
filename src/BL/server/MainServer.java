package BL.server;

import BL.log.Logger;
import DA.parse.MyJSONParser;
import UI.transferObject.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MainServer {
    static protected ServerSocket serverSocket;
    static private Integer port;
    static protected HashMap<String, Deposit> deposits;
    static protected String outLog;
    static private String serverFileAddress;

    public MainServer(){

    }

    public MainServer(String serverFileAddress) {
        this.serverFileAddress = serverFileAddress;
        initializeServer();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println(toString());
        while (true) {
            try {
                Socket client = serverSocket.accept();
                new Thread(new Server(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeServer() {
        MainServer mainServer = (MainServer) (new MyJSONParser().parse(serverFileAddress, MainServer.class));
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.get(this) == null) {
                    field.set(this, field.get(mainServer));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    protected void eventLog(Response response){
        synchronized (outLog) {
            new Logger().log(response, outLog);
        }
    }

    @Override
    public String toString() {
        String s = "****************************************\n";
        for (Deposit deposit: deposits.values()) {
            s += deposit.toString();
            s +="\n";
        }
        return s;
    }
}