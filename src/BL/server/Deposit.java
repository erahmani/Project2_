package BL.server;

import BL.exceptions.InitialBoundException;
import BL.exceptions.UpperBoundException;

import java.lang.reflect.Field;

/**
 * Created by DotinSchool2 on 9/7/2016.
 */
class Deposit{
    private String customer;
    private String id;
    private String initialBalance;
    private String upperBound;

    public String getId() {
        return id;
    }

    private Integer getInitialBalance() {
        return Integer.parseInt(initialBalance.replace(",",""));
    }

    private void setInitialBalance(Integer initialBalance) {
        this.initialBalance = initialBalance.toString();
    }

    private Integer getUpperBound() {
        return Integer.parseInt(upperBound.replace(",",""));
    }

    public synchronized void withdraw(Integer amount) {
        int initialBalance = getInitialBalance();
        if ( initialBalance >= amount) {
            initialBalance -= amount;
        } else {
            throw new InitialBoundException();
        }
        setInitialBalance(initialBalance);
    }

    public synchronized void deposit(Integer amount) {//
        int initialBalance = getInitialBalance();
        if ( getUpperBound() >= amount + initialBalance) {
            initialBalance += amount ;
        } else {
            throw new UpperBoundException();
        }
        setInitialBalance(initialBalance);
    }

    @Override
    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        String res = "";
        try {
            for (Field field : fields) {
                res += field.getName() + " : " + field.get(this) + " \n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
