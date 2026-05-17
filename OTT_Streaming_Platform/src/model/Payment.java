package model;

import java.util.Date;

public class Payment {
    private int paymentId;
    private int userId;
    private int subscriptionId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private Date paymentDate;

    public Payment(int paymentId, int userId, int subscriptionId, double amount, String paymentMethod, String paymentStatus, Date paymentDate) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public double getAmount() { return amount; }
    public int getUserId() { return userId; }
}