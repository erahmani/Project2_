package UI.transferObject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.lang.reflect.Field;

@XmlRootElement(name = "response")
public class Response implements Serializable{
    @XmlAttribute(name = "terminalId")
    private String terminalId;
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

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    @Override
    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        String res = "\n";

            for (Field field : fields) {
                try {
                    res += field.getName() + " : " + field.get(this) + "  ";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        return res;
    }
}
