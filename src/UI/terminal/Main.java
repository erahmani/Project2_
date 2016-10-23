package UI.terminal;

public class Main {
    public static void main(String[] args) {
        Thread terminal1 = new Thread(new UI.terminal.Terminal("src\\DB\\xmlFiles\\terminal1.xml","response1.xml"), "Terminal 1");
        Thread terminal2 = new Thread(new UI.terminal.Terminal("src\\DB\\xmlFiles\\terminal2.xml","response2.xml"), "Terminal 2");
        Thread terminal3 = new Thread(new UI.terminal.Terminal("src\\DB\\xmlFiles\\terminal3.xml","response3.xml"), "Terminal 3");
        Thread terminal4 = new Thread(new UI.terminal.Terminal("src\\DB\\xmlFiles\\terminal4.xml","response4.xml"), "Terminal 4");
       Thread terminal5 = new Thread(new UI.terminal.Terminal("src\\DB\\xmlFiles\\terminal5.xml","response5.xml"), "Terminal 5");

        terminal1.start();
        terminal2.start();
        terminal3.start();
        terminal4.start();
        terminal5.start();
    }
}