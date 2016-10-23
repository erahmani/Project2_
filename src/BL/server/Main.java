package BL.server;

public class Main {
    public static void main(String[] args) {
        Thread mainServer = new Thread(new MainServer("src\\DB\\jsonFiles\\core.json"), "Server");
        mainServer.start();
    }
}
