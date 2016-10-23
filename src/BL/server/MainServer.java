package BL.server;

import DA.parse.JSONParser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class MainServer implements Runnable {
    static protected ServerSocket serverSocket;
    static private Integer port;
    static protected List<Deposit> deposits;
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
        MainServer mainServer = (MainServer) (new JSONParser().parse(serverFileAddress, MainServer.class));
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


    @Override
    public String toString() {
        String s = "****************************************\n";
        for (Deposit deposit: deposits) {

            s += deposit.toString();
            s +="\n";
        }
        return s;
    }
}