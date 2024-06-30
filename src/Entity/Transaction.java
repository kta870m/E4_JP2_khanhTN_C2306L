package Entity;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private String accountId;
    private double amount;
    private Type type;
    private LocalDateTime dateTime;
    private Status status;

    public Transaction() {;}
    public Transaction(int id, String accountId, double amount, Type type, LocalDateTime dateTime, Status status) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.dateTime = dateTime;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String transactionToLine(String separator) {
        StringBuilder builder = new StringBuilder();
        return builder.append(this.id)
                .append(separator)
                .append(this.accountId)
                .append(separator)
                .append(this.amount)
                .append(separator)
                .append(this.type)
                .append(separator)
                .append(this.dateTime)
                .append(separator)
                .append(this.status)
                .append(separator)
                .toString();

    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", dateTime=" + dateTime +
                ", status=" + status +
                '}';
    }
}
