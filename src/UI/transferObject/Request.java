package UI.transferObject;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by DotinSchool2 on 9/13/2016.
 */
public class Request implements Serializable{
    private String id;
    private String transactionType;
    private String amount;
    private String deposit;

    public String getId() {
        return id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Integer getAmount() {
        return Integer.parseInt(amount);
    }

    public String getDeposit() {
        return deposit;
    }

    static public Request convertToRequest(Object object) {
        Request request = new Request();
        Field[] fields = Request.class.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                field.setAccessible(true);
                Field objectField = object.getClass().getDeclaredField(fieldName);
                objectField.setAccessible(true);
                field.set(request, objectField.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return request;
    }
}
