package payment;
public class Payment{
    public String transactionId;
    public int amont;
    public String userName;
    public int payOption;
    public String cardNumber;

    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public int getAmont() {
        return amont;
    }
    public void setAmont(int amont) {
        this.amont = amont;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getPayOption() {
        return payOption;
    }
    public void setPayOption(int payOption) {
        this.payOption = payOption;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}