package UI.transferObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;


public class Request implements Serializable{
    private String terminalId;
    private String id;
    private String transactionType;
    private BigDecimal amount;
    private String deposit;

    public String getId() {
        return id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
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

                if(field.getGenericType() == java.math.BigDecimal.class){
                    BigDecimal bigDecimal = new BigDecimal((String)objectField.get(object));
                    field.set(request, bigDecimal);
                }else {
                    field.set(request, objectField.get(object));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {

            }
        }
        return request;
    }
}
