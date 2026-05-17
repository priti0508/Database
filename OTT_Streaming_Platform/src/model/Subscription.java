package model;

import java.util.Date;

public class Subscription {
    private int subscriptionId;
    private int userId;
    private int planId;
    private Date startDate;
    private Date endDate;
    private String status;

    public Subscription(int subscriptionId, int userId, int planId, Date startDate, Date endDate, String status) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getSubscriptionId() { return subscriptionId; }
    public int getUserId() { return userId; }
    public int getPlanId() { return planId; }
    public String getStatus() { return status; }
}