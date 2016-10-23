package BL.server;

import BL.exceptions.DepositNotFoundException;
import BL.exceptions.InitialBoundException;
import BL.exceptions.TransactionTypeException;
import BL.exceptions.UpperBoundException;
import BL.log.Logger;
import UI.transferObject.Request;
import UI.transferObject.Requests;
import UI.transferObject.Response;
import UI.transferObject.Responses;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

class Server extends MainServer implements Runnable {
    private Socket client;
    Server(Socket client) {
        this.client = client;
    }

    public void run() {
        Requests requests = getRequestsFromTerminal();
        Responses responses = responseToRequests(requests);
        sendResponseToTerminal(responses);
        eventLog(responses);
    }

    private Requests getRequestsFromTerminal() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(client.getInputStream());
            Requests requests = (Requests) objectInputStream.readObject();
            return requests;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Responses responseToRequests(Requests requests) {
        Responses responses = new Responses();
        responses.setTerminalId(requests.getTerminalId());
        for (Request request : requests.getRequests()) {
            Response response = new Response();
            try {
                response.setTransactionId(request.getId());
                response.setTransactionType(request.getTransactionType());
                response.setDepositId(request.getDeposit());
                responseToRequest(request);
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
            responses.addTransactionLog(response);
        }
        return responses;
    }

    private void responseToRequest(Request request) {
        try {
            int indexInDeposits = findIndexInDeposits(request.getDeposit());//change getDeposit to getDepositId//agar ezafe karan bashe inja khube
            Method method = Deposit.class.getDeclaredMethod(request.getTransactionType(), Integer.class);
            method.invoke(deposits.get(indexInDeposits), request.getAmount());
        }
        catch (NoSuchMethodException e) {
            throw new TransactionTypeException();
        } catch (TransactionTypeException e) {
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

    private int findIndexInDeposits(String deposit) {
        for (int dIndex = 0; dIndex < deposits.size(); dIndex++) {
            Deposit d = deposits.get(dIndex);
            if (d.getId().compareTo(deposit) == 0) {
                return dIndex;
            }
        }
        throw new DepositNotFoundException();
    }

    private void sendResponseToTerminal(Responses responses) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(responses);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eventLog(Responses responses){
        synchronized (outLog) {
            new Logger().log(responses, outLog);
            System.out.println(toString());
        }
    }
}