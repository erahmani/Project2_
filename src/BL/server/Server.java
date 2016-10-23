package BL.server;

import BL.exceptions.DepositNotFoundException;
import BL.exceptions.InitialBoundException;
import BL.exceptions.TransactionTypeException;
import BL.exceptions.UpperBoundException;
import UI.transferObject.Request;
import UI.transferObject.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.Socket;

class Server extends MainServer implements Runnable {
    private Socket client;

    Server(Socket client) {
        this.client = client;
    }

    public void run() {
        Request request = getRequestFromTerminal();
        Response response = responseToRequest(request);
        sendResponseToTerminal(response);
        eventLog(response);
        System.out.println("ThreadID: " + Thread.currentThread().getId());
        System.out.println(toString());
    }

    private Request getRequestFromTerminal() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(client.getInputStream());
            Request request = (Request) objectInputStream.readObject();
            return request;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Response responseToRequest(Request request) {
        Response response = new Response();
        try {
            response.setTerminalId(request.getTerminalId());
            response.setTransactionId(request.getId());
            response.setTransactionType(request.getTransactionType());
            response.setDepositId(request.getDeposit());
            doTransaction(request);
            response.setMessage("OK");
        } catch (DepositNotFoundException e) {
            response.setMessage("Deposit Not Found Exception");
        } catch (TransactionTypeException e) {
            response.setMessage("Transaction Type Exception");
        } catch (UpperBoundException e) {
            response.setMessage("Upper Bound Exception");
        } catch (InitialBoundException e) {
            response.setMessage("Initial Bound Exception");
        }
        return response;
    }

    private void doTransaction(Request request) {
        try {
            Method method = Deposit.class.getDeclaredMethod(request.getTransactionType(), BigDecimal.class);
            Deposit deposit = deposits.get(request.getDeposit());
            if(deposit == null){
                throw new DepositNotFoundException();
            }
            method.invoke(deposit, request.getAmount()); //?!!!!!!!khode deposit ro befrest
        } catch (NoSuchMethodException e) {
            throw new TransactionTypeException();
        } catch (InvocationTargetException e) {
            try {
                throw e.getTargetException();
            } catch (UpperBoundException u) {
                throw new UpperBoundException();
            } catch (InitialBoundException i) {
                throw new InitialBoundException();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void sendResponseToTerminal(Response response) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(response);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}