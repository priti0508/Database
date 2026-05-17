package ui;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;
    
    private JTextField regNameField;
    private JTextField regEmailField;
    private JPasswordField regPasswordField;
    private JTextField regPhoneField;

    private UserDAO userDAO;
    private JPanel cards;
    private CardLayout cardLayout;

    public LoginUI() {
        userDAO = new UserDAO();
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        setTitle("OTT Platform - Portal");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createLoginPanel();
        createRegisterPanel();

        add(cards);
    }

    private void createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("STREAMING CENTRAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(229, 9, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel subTitleLabel = new JLabel("Sign In to access your account", SwingConstants.CENTER);
        subTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subTitleLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        panel.add(subTitleLabel, gbc);

        gbc.gridwidth = 1;

        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        emailLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        loginEmailField = new JTextField(20);
        styleTextField(loginEmailField);
        gbc.gridy = 3;
        panel.add(loginEmailField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridy = 4;
        panel.add(passwordLabel, gbc);

        loginPasswordField = new JPasswordField(20);
        styleTextField(loginPasswordField);
        gbc.gridy = 5;
        panel.add(loginPasswordField, gbc);

        JButton loginButton = new JButton("Sign In");
        styleButton(loginButton, new Color(229, 9, 20));
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 12, 10, 12);
        panel.add(loginButton, gbc);

        JButton switchRegButton = new JButton("New user? Register here");
        styleLinkButton(switchRegButton);
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 12, 10, 12);
        panel.add(switchRegButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginEmailField.getText().trim();
                String password = new String(loginPasswordField.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Fields cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (userDAO.loginUser(email, password)) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    dispose(); // Purani window band hogi
                    
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ui.DashboardUI dashboard = new ui.DashboardUI();
                                dashboard.setVisible(true);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Dashboard load hone me error: " + ex.getMessage());
                            }
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        switchRegButton.addActionListener(e -> cardLayout.show(cards, "Register"));

        cards.add(panel, "Login");
    }

    private void createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("CREATE ACCOUNT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(229, 9, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        regNameField = new JTextField(20);
        styleTextField(regNameField);
        gbc.gridy = 2;
        panel.add(regNameField, gbc);

        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        emailLabel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        panel.add(emailLabel, gbc);

        regEmailField = new JTextField(20);
        styleTextField(regEmailField);
        gbc.gridy = 4;
        panel.add(regEmailField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridy = 5;
        panel.add(passwordLabel, gbc);

        regPasswordField = new JPasswordField(20);
        styleTextField(regPasswordField);
        gbc.gridy = 6;
        panel.add(regPasswordField, gbc);

        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        phoneLabel.setForeground(Color.WHITE);
        gbc.gridy = 7;
        panel.add(phoneLabel, gbc);

        regPhoneField = new JTextField(20);
        styleTextField(regPhoneField);
        gbc.gridy = 8;
        panel.add(regPhoneField, gbc);

        JButton registerButton = new JButton("Register");
        styleButton(registerButton, new Color(46, 125, 50));
        gbc.gridy = 9;
        gbc.insets = new Insets(15, 12, 5, 12);
        panel.add(registerButton, gbc);

        JButton switchLoginButton = new JButton("Already have an account? Sign In");
        styleLinkButton(switchLoginButton);
        gbc.gridy = 10;
        gbc.insets = new Insets(5, 12, 5, 12);
        panel.add(switchLoginButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = regNameField.getText().trim();
                String email = regEmailField.getText().trim();
                String password = new String(regPasswordField.getPassword()).trim();
                String phone = regPhoneField.getText().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginUI.this, "All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean success = userDAO.registerUser(name, email, password, phone);
                if (success) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Registration Successful! Please Sign In.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    regNameField.setText("");
                    regEmailField.setText("");
                    regPasswordField.setText("");
                    regPhoneField.setText("");
                    cardLayout.show(cards, "Login");
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Registration Failed. Email might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        switchLoginButton.addActionListener(e -> cardLayout.show(cards, "Login"));

        cards.add(panel, "Register");
    }

    private void styleTextField(JTextField field) {
        field.setBackground(new Color(51, 51, 51));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleLinkButton(JButton button) {
        button.setForeground(Color.LIGHT_GRAY);
        button.setBackground(new Color(20, 20, 20));
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}