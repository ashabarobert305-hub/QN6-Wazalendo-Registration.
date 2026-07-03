import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WazalendoRegistration extends JFrame {
    // GUI Components
    private JTextField txtMemberID, txtFullName, txtNIN, txtPhone, txtDeposit;
    private JButton btnRegister, btnClear, btnExit;

    // Database Connection URL (MS Access via UCanAccess)
    private static final String DB_URL = "jdbc:ucanaccess://C:/WazalendoSACCO/WazalendoDB.accdb";

    public WazalendoRegistration() {
        // Window properties
        setTitle("Wazalendo SACCO - Member Registration");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(0, 102, 204));
        JLabel lblTitle = new JLabel("Member Registration Form");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // Form Fields Panel
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        pnlForm.add(new JLabel("Member ID:"));
        txtMemberID = new JTextField();
        pnlForm.add(txtMemberID);

        pnlForm.add(new JLabel("Full Name:"));
        txtFullName = new JTextField();
        pnlForm.add(txtFullName);

        pnlForm.add(new JLabel("National ID (NIN):"));
        txtNIN = new JTextField();
        pnlForm.add(txtNIN);

        pnlForm.add(new JLabel("Phone Number (10 Digits):"));
        txtPhone = new JTextField();
        pnlForm.add(txtPhone);

        pnlForm.add(new JLabel("Initial Deposit (UGX):"));
        txtDeposit = new JTextField();
        pnlForm.add(txtDeposit);

        add(pnlForm, BorderLayout.CENTER);

        // Buttons Panel
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnRegister = new JButton("Register");
        btnClear = new JButton("Clear");
        btnExit = new JButton("Exit");

        pnlButtons.add(btnRegister);
        pnlButtons.add(btnClear);
        pnlButtons.add(btnExit);
        add(pnlButtons, BorderLayout.SOUTH);

        // --- Action Handlers ---

        // Exit Button Action
        btnExit.addActionListener(e -> System.exit(0));

        // Clear Button Action
        btnClear.addActionListener(e -> clearFields());

        // Register Button Action
        btnRegister.addActionListener(e -> handleRegistration());
    }

    private void clearFields() {
        txtMemberID.setText("");
        txtFullName.setText("");
        txtNIN.setText("");
        txtPhone.setText("");
        txtDeposit.setText("");
        txtMemberID.requestFocus();
    }

    private void handleRegistration() {
        // 1. Capture Inputs
        String memberID = txtMemberID.getText().trim();
        String fullName = txtFullName.getText().trim();
        String nin = txtNIN.getText().trim();
        String phone = txtPhone.getText().trim();
        String depositStr = txtDeposit.getText().trim();

        // 2. Data Validation Checks
        if (memberID.isEmpty() || fullName.isEmpty() || nin.isEmpty() || phone.isEmpty() || depositStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nin.length() != 14) {
            JOptionPane.showMessageDialog(this, "NIN must be exactly 14 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Phone number must be numeric and exactly 10 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(depositStr);
            if (initialDeposit <= 0) {
                JOptionPane.showMessageDialog(this, "Initial Deposit must be a positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Initial Deposit must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Database Insertion (JDBC)
        String sql = "INSERT INTO Members (MemberID, FullName, NIN, PhoneNumber, InitialDeposit) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberID);
            pstmt.setString(2, fullName);
            pstmt.setString(3, nin);
            pstmt.setString(4, phone);
            pstmt.setDouble(5, initialDeposit);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Member successfully registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WazalendoRegistration().setVisible(true);
        });
    }
}