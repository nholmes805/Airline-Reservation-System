import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AirlineReservationSystem extends JFrame implements ActionListener {
    private JLabel lblTitle;
    private JComboBox<String> cbFlightSource, cbFlightDestination;
    private JButton btnSearch;
    private Object[][] flightsData = {
        {"NYC101", "New York", "London", "2023-03-25", "07:00"},
        {"SFO102", "San Francisco", "Tokyo", "2023-03-26", "09:30"},
        {"LAX103", "Los Angeles", "Sydney", "2023-03-27", "12:45"},
        {"CHI104", "Chicago", "Paris", "2023-03-28", "17:30"}
    };
    
       
        private boolean processPayment(double totalPrice) {
            JTextField txtCardNumber = new JTextField(20);
            JTextField txtCardExpiry = new JTextField(5);
            JTextField txtCVV = new JTextField(4);
    
            JPanel panel = new JPanel(new GridLayout(4, 2));
            panel.add(new JLabel("Card Number:"));
            panel.add(txtCardNumber);
            panel.add(new JLabel("Expiry (MM/YY):"));
            panel.add(txtCardExpiry);
            panel.add(new JLabel("CVV:"));
            panel.add(txtCVV);
    
            int result = JOptionPane.showConfirmDialog(null, panel, "Payment", JOptionPane.OK_CANCEL_OPTION);
    
            if (result == JOptionPane.OK_OPTION) {
                String cardNumber = txtCardNumber.getText();
                String cardExpiry = txtCardExpiry.getText();
                String cvv = txtCVV.getText();
    
                if (cardNumber.length() == 16 && cardExpiry.matches("\\d{2}/\\d{2}") && cvv.length() == 3) {
                    JOptionPane.showMessageDialog(null, "Payment successful! Total amount: $" + totalPrice, "Payment", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid card information. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                return false;
            }
        }

    public AirlineReservationSystem() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Airline Reservation System");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblTitle = new JLabel("Airline Reservation System");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        JLabel lblSource = new JLabel("Source:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblSource, gbc);

        cbFlightSource = new JComboBox<>();
        cbFlightSource.setModel(new DefaultComboBoxModel<>(new String[]{"New York", "San Francisco", "Los Angeles", "Chicago"}));
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(cbFlightSource, gbc);

        JLabel lblDestination = new JLabel("Destination:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblDestination, gbc);

        cbFlightDestination = new JComboBox<>();
        cbFlightDestination.setModel(new DefaultComboBoxModel<>(new String[]{"London", "Paris", "Tokyo", "Sydney"}));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(cbFlightDestination, gbc);

        btnSearch = new JButton("Search");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnSearch, gbc);
        btnSearch.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        if (!showLoginDialog()) {
            System.exit(0);
        }
    }

    private boolean showLoginDialog() {
        JTextField txtUsername = new JTextField(20);
        JPasswordField txtPassword = new JPasswordField(20);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);

        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            if ("admin".equals(username) && "password".equals(password)) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AirlineReservationSystem frame = new AirlineReservationSystem();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            String source = cbFlightSource.getSelectedItem().toString();
            String destination = cbFlightDestination.getSelectedItem().toString();
            
            if (source.equals(destination)) {
                JOptionPane.showMessageDialog(this, "Source and destination cities must be different.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                displayDateTimeSelection(source, destination);
            }
        }
    }

    private void displayDateTimeSelection(String source, String destination) {
        JFrame dateTimeFrame = new JFrame("Select Date and Time");
        dateTimeFrame.setSize(350, 300);
        dateTimeFrame.setLocationRelativeTo(null);
        dateTimeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dateTimeFrame.getContentPane().setLayout(null);

        JLabel lblDate = new JLabel("Departure Date:");
        lblDate.setBounds(30, 30, 120, 25);
        dateTimeFrame.getContentPane().add(lblDate);

        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        dateSpinner.setBounds(150, 30, 150, 25);
        dateTimeFrame.getContentPane().add(dateSpinner);

        JLabel lblTime = new JLabel("Departure Time:");
        lblTime.setBounds(30, 80, 120, 25);
        dateTimeFrame.getContentPane().add(lblTime);

        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));
        timeSpinner.setBounds(150, 80, 150, 25);
        dateTimeFrame.getContentPane().add(timeSpinner);

        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener(e -> {
           
            JOptionPane.showMessageDialog(dateTimeFrame, "Date: " + dateSpinner.getValue() + "\nTime: " + timeSpinner.getValue());
            dateTimeFrame.dispose();
        });
        btnConfirm.setBounds(125, 150, 100, 30);
        dateTimeFrame.getContentPane().add(btnConfirm);

        dateTimeFrame.setVisible(true);
        btnConfirm.addActionListener(e -> {
            displayAvailableFlights(source, destination, dateSpinner.getValue().toString(), timeSpinner.getValue().toString());
            dateTimeFrame.dispose();
        });
    }
        private void displayAvailableFlights(String source, String destination, String date, String time) {
            JFrame flightFrame = new JFrame("Available Flights");
            flightFrame.setSize(600, 300);
            flightFrame.setLocationRelativeTo(null);
            flightFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            flightFrame.getContentPane().setLayout(new BorderLayout());
    
            String[] columnNames = {"Flight No", "Source", "Destination", "Date", "Time"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
            for (Object[] flight : flightsData) {
                if (flight[1].equals(source) && flight[2].equals(destination)) {
                    model.addRow(flight);
                }
            }
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            flightFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    
            JButton btnBook = new JButton("Book Flight");
            btnBook.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String flightNo = model.getValueAt(selectedRow, 0).toString();
                    displaySeatSelection(flightNo);
                } else {
                    JOptionPane.showMessageDialog(flightFrame, "Please select a flight to book.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });            
            flightFrame.getContentPane().add(btnBook, BorderLayout.SOUTH);
            flightFrame.setVisible(true);
        }

    private void displaySeatSelection(String flightNo) {
        JFrame seatFrame = new JFrame("Select Seat");
        seatFrame.setSize(400, 250);
        seatFrame.setLocationRelativeTo(null);
        seatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        seatFrame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSeat = new JLabel("Seat Class:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        seatFrame.getContentPane().add(lblSeat, gbc);

        JComboBox<String> cbSeatClass = new JComboBox<>();
        cbSeatClass.setModel(new DefaultComboBoxModel<>(new String[]{"Economy", "Business", "First Class"}));
        gbc.gridx = 1;
        gbc.gridy = 0;
        seatFrame.getContentPane().add(cbSeatClass, gbc);

        JLabel lblPrice = new JLabel("Price:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        seatFrame.getContentPane().add(lblPrice, gbc);

        JTextField txtPrice = new JTextField(20);
        txtPrice.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        seatFrame.getContentPane().add(txtPrice, gbc);

        cbSeatClass.addActionListener(e -> {
            String seatClass = cbSeatClass.getSelectedItem().toString();
            double price = calculatePrice(flightNo, seatClass);
            txtPrice.setText("$" + price);
        });

        JButton btnConfirmSeat = new JButton("Confirm Seat");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        seatFrame.getContentPane().add(btnConfirmSeat, gbc);
        btnConfirmSeat.addActionListener(e -> {
            double totalPrice = calculatePrice(flightNo, cbSeatClass.getSelectedItem().toString());
            if (processPayment(totalPrice)) {
                JOptionPane.showMessageDialog(seatFrame, "Seat confirmed for flight " + flightNo + ".\nTotal Price: $" + totalPrice);
                seatFrame.dispose();
            }
        });
        seatFrame.setVisible(true);
    }
    private double calculatePrice(String flightNo, String seatClass) {
        double basePrice = 200.0; // You can replace this with your own pricing logic
    
        switch (seatClass) {
            case "Economy":
                return basePrice;
            case "Business":
                return basePrice * 2;
            case "First Class":
                return basePrice * 3;
            default:
                return basePrice;
        }
    }
}