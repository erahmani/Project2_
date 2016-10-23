package UI.transferObject;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by DotinSchool2 on 9/13/2016.
 */
public class Response implements Serializable{
    @XmlAttribute(name = "transactionId")
    private String transactionId;
    @XmlAttribute(name = "transactionType")
    private String transactionType;
    @XmlAttribute(name = "depositId")
    private String depositId;
    @XmlAttribute(name = "message")
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


    @Override
    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        String res = "\n";
        try {
            for (Field field : fields) {
                res += field.getName() + " : " + field.get(this) + "  ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
