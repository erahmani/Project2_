package UI.terminal;

import javax.xml.bind.annotation.XmlAttribute;

class Transaction {

    private String id;
    private String transactionType;
    private String amount;
    private String deposit;

    Transaction() {

    }

    @XmlAttribute(name = "id")
    private void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "type")
    private void setTransactionType(String transactionType) {
        this.transactionType = transactionType;//Character.toUpperCase(transactionType.charAt(0)) + transactionType.substring(1);
    }

    @XmlAttribute(name = "amount")
    private void setAmount(String amount) {
        this.amount = amount;
    }

    @XmlAttribute(name = "deposit")
    private void setDeposit(String deposit) {
        this.deposit = deposit;
    }

}
