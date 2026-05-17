package main;

import dao.UserDAO;
import dao.PaymentDAO;
import dao.SubscriptionDAO;
import dao.WatchHistoryDAO;
import ui.LoginUI;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        System.out.println("====== OTT Streaming Platform System Initialization ======");

        UserDAO userDAO = new UserDAO();
        SubscriptionDAO subDAO = new SubscriptionDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        WatchHistoryDAO watchDAO = new WatchHistoryDAO();

        int totalUsers = userDAO.getTotalUsersCount();
        System.out.println("Current Registered Users in Database: " + totalUsers);

        double revenue = paymentDAO.getTotalRevenue();
        System.out.println("Live Revenue Dashboard Check: INR " + revenue);
        
        System.out.println("\n--- Advanced SRS Metrics Check ---");
        subDAO.printPopularPlans();
        subDAO.checkSubscriptionStatus(501);
        watchDAO.printTotalViewsPerContent();
        watchDAO.printMostWatchedContent();
        paymentDAO.printPremiumSpenders();
        
        System.out.println("\nLaunching Desktop Interface Engine...");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }
}