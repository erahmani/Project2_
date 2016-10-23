package BL.server;

import BL.exceptions.InitialBoundException;
import BL.exceptions.UpperBoundException;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class Deposit {
    private String customer;
    private String id;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;

    public String getId() {
        return id;
    }

     public synchronized void withdraw(BigDecimal amount) {
   // public void withdraw(BigDecimal amount) {
        //  System.out.println("SSSSSSSSSSSSSSSSSSSS "+amount+" "+initialBalance+" "+Thread.activeCount());

        if (initialBalance.compareTo(amount) >= 0) {
            try {
                if (Thread.currentThread().getId() == 10) {
                    System.out.println(Thread.currentThread().getId());
                    Thread.currentThread().sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initialBalance = initialBalance.subtract(amount);
        } else {
            throw new InitialBoundException();
        }
    }

    public synchronized void deposit(BigDecimal amount) {//??
  //  public void deposit(BigDecimal amount) {
        if (initialBalance.add(amount).compareTo(upperBound) <= 0) {
            initialBalance = initialBalance.add(amount);
        } else {
            throw new UpperBoundException();
        }
    }

    @Override
    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        String res = "";
            for (Field field : fields) {
                try {
                    res += field.getName() + " : " + field.get(this) + " \n";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        return res;
    }
}
