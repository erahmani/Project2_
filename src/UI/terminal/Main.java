package UI.terminal;

public class Main {
    public static void main(String[] args) {
        Thread terminal1 = new Thread(new UI.terminal.Terminal("src\\DB\\xmlFiles\\terminal1.xml","response1.xml"), "Terminal 1");
        Thread terminal2 = new Thread(new UI.terminal.Terminal("src\\DB\\xmlFiles\\terminal2.xml","response2.xml"), "Terminal 2");
        Thread terminal3 = new Thread(new UI.terminal.Terminal("src\\DB\\xmlFiles\\terminal3.xml","response3.xml"), "Terminal 3");

        terminal1.start();
        terminal2.start();
        terminal3.start();
    }
}
