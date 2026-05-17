package ui;

import db.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class DashboardUI extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public DashboardUI() {
        setTitle("STREAMING CENTRAL - ADMIN MANAGEMENT DASHBOARD");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(15, 15, 15));
        sidebar.setPreferredSize(new Dimension(250, 700));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(35, 35, 35)));

        JLabel brandLabel = new JLabel("STREAM ENGINE");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brandLabel.setForeground(new Color(229, 9, 20));
        brandLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        brandLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        sidebar.add(brandLabel);

        JButton view1Btn = createNavigationButton(">  High Rated Content");
        JButton view2Btn = createNavigationButton(">  User Subscriptions");
        JButton view3Btn = createNavigationButton(">  Revenue Summary");
        JButton view4Btn = createNavigationButton(">  Watch History");

        sidebar.add(view1Btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(view2Btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(view3Btn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(view4Btn);

        JPanel headerBar = new JPanel(new BorderLayout());
        headerBar.setBackground(Color.WHITE);
        headerBar.setPreferredSize(new Dimension(800, 60));
        headerBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel welcomeLabel = new JLabel("Welcome back, Administrator");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        welcomeLabel.setForeground(new Color(60, 60, 60));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        headerBar.add(welcomeLabel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 246, 248));

        JPanel panel1 = createPremiumTablePanel("SELECT * FROM HighRatedContentView", new String[]{"Content ID", "Title", "Release Year", "Content Type"});
        JPanel panel2 = createPremiumTablePanel("SELECT * FROM UserSubscriptionDetailsView", new String[]{"User ID", "Full Name", "Email Address", "Subscription ID", "Plan ID", "Status"});
        JPanel panel3 = createPremiumTablePanel("SELECT * FROM RevenueSummaryView", new String[]{"Payment Method", "Total Revenue (INR)", "Total Transactions"});
        
        JPanel panel4 = createPremiumTablePanel(
            "SELECT w.history_id, u.full_name, c.title, w.watch_time, w.watched_at FROM WATCH_HISTORY w JOIN Users u ON w.user_id = u.user_id JOIN CONTENT c ON w.content_id = c.content_id", 
            new String[]{"History ID", "User Name", "Movie/Series Title", "Watch Time (Min)", "Watched At"}
        );

        contentPanel.add(panel1, "HighRated");
        contentPanel.add(panel2, "Subscriptions");
        contentPanel.add(panel3, "Revenue");
        contentPanel.add(panel4, "WatchHistory");

        view1Btn.addActionListener(e -> cardLayout.show(contentPanel, "HighRated"));
        view2Btn.addActionListener(e -> cardLayout.show(contentPanel, "Subscriptions"));
        view3Btn.addActionListener(e -> cardLayout.show(contentPanel, "Revenue"));
        view4Btn.addActionListener(e -> cardLayout.show(contentPanel, "WatchHistory"));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(headerBar, BorderLayout.NORTH);
        rightPanel.add(contentPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private JPanel createPremiumTablePanel(String query, String[] columnNames) {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(245, 246, 248));
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel cardContainer = new JPanel(new BorderLayout());
        cardContainer.setBackground(Color.WHITE);
        cardContainer.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(38);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(254, 240, 240));
        table.setSelectionForeground(new Color(229, 9, 20));
        table.setGridColor(new Color(240, 240, 240));
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(250, 250, 250));
        header.setForeground(new Color(80, 80, 80));
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(100, 42));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        cardContainer.add(scrollPane, BorderLayout.CENTER);
        wrapperPanel.add(cardContainer, BorderLayout.CENTER);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int cols = columnNames.length;
            while (rs.next()) {
                Object[] row = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            JLabel errLabel = new JLabel("⚠️ Database sync issue. Check if required SQL Data is compiled.", SwingConstants.CENTER);
            errLabel.setForeground(new Color(229, 9, 20));
            errLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            wrapperPanel.add(errLabel, BorderLayout.NORTH);
            e.printStackTrace();
        }

        return wrapperPanel;
    }

    private JButton createNavigationButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.LIGHT_GRAY);
        btn.setBackground(new Color(15, 15, 15));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(30, 30, 30));
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(15, 15, 15));
                btn.setForeground(Color.LIGHT_GRAY);
            }
        });

        return btn;
    }
}