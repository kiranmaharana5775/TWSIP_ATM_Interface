import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ATMInterface {
    private JFrame frame;
    private Map<String, Account> accounts;

    public ATMInterface() {
        frame = new JFrame("ATM Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        accounts = new HashMap<>();
        accounts.put("123456", new Account("123456", "1234", 10000.0));
        accounts.put("987654", new Account("987654", "4321", 5000.0));

        createWelcomeScreen();
    }

    private void createWelcomeScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(0, 102, 204)); // Background color

        JLabel welcomeLabel = new JLabel("Welcome to ATM Machine");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE); // Text color
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(welcomeLabel);

        createButton(panel, "Deposit");
        createButton(panel, "Withdraw");
        createButton(panel, "Balance Inquiry");
        createButton(panel, "Money Transfer");

        frame.add(panel);
        frame.getContentPane().setBackground(new Color(204, 204, 255));

        frame.setVisible(true);
    }

    private void createButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Deposit")) {
                    handleDeposit();
                } else if (text.equals("Withdraw")) {
                    handleWithdraw();
                } else if (text.equals("Balance Inquiry")) {
                    handleBalanceInquiry();
                } else if (text.equals("Money Transfer")) {
                    handleMoneyTransfer();
                }
            }
        });
    }

    public void handleDeposit() {
        String accountNumber = getAccountNumber();
        String pin = getPIN();
        double amount = getAmount();

        Account account = accounts.get(accountNumber);
        if (account != null && account.getPin().equals(pin)) {
            if (account.deposit(amount)) {
                JOptionPane.showMessageDialog(frame, "Deposit successful. New balance: $" + account.getBalance());
            } else {
                showError("Invalid deposit amount.");
            }
        } else {
            showError("Invalid account number or PIN.");
        }
    }

    public void handleWithdraw() {
        String accountNumber = getAccountNumber();
        String pin = getPIN();
        double amount = getAmount();

        Account account = accounts.get(accountNumber);
        if (account != null && account.getPin().equals(pin)) {
            if (account.withdraw(amount)) {
                JOptionPane.showMessageDialog(frame, "Withdrawal successful. New balance: $" + account.getBalance());
            } else {
                showError("Invalid withdrawal amount or insufficient balance.");
            }
        } else {
            showError("Invalid account number or PIN.");
        }
    }

    private void handleBalanceInquiry() {
        String accountNumber = getAccountNumber();
        String pin = getPIN();

        Account account = accounts.get(accountNumber);
        if (account != null && account.getPin().equals(pin)) {
            JOptionPane.showMessageDialog(frame, "Your balance: $" + account.getBalance());
        } else {
            showError("Invalid account number or PIN.");
        }
    }

    private void handleMoneyTransfer() {
        String senderAccountNumber = getAccountNumber();
        String senderPin = getPIN();
        double amount = getAmount();

        Account senderAccount = accounts.get(senderAccountNumber);
        if (senderAccount != null && senderAccount.getPin().equals(senderPin)) {
            String recipientAccountNumber = JOptionPane.showInputDialog(frame, "Enter recipient's account number:");
            Account recipientAccount = accounts.get(recipientAccountNumber);
            if (recipientAccount != null) {
                if (senderAccount.transfer(recipientAccount, amount)) {
                    JOptionPane.showMessageDialog(frame, "Money transfer successful.");
                } else {
                    showError("Insufficient balance for transfer.");
                }
            } else {
                showError("Recipient account not found.");
            }
        } else {
            showError("Invalid account number or PIN.");
        }
    }

    private String getAccountNumber() {
        return JOptionPane.showInputDialog(frame, "Enter Account Number:");
    }

    private String getPIN() {
        return JOptionPane.showInputDialog(frame, "Enter PIN:");
    }

    private double getAmount() {
        String amountStr = JOptionPane.showInputDialog(frame, "Enter Amount:");
        return Double.parseDouble(amountStr);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATMInterface();
            }
        });
    }
}
