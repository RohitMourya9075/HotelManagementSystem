package com.hms.ui;

import com.hms.model.Guest;
import com.hms.util.FileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class HotelManagementUI extends JFrame {
    private JTextField tfName, tfAge, tfMobile, tfRoom, tfType, tfSearchRoom;
    private JTable table;
    private DefaultTableModel tableModel;

    public HotelManagementUI() {
        setTitle("Hotel Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        loadTable();
        setVisible(true);
    }

    private void initComponents() {
        JPanel pane = new JPanel(new BorderLayout(10,10));
        getContentPane().add(pane);

        // Top form panel
        JPanel form = new JPanel(new GridLayout(2,6,5,5));
        tfName = new JTextField(); tfAge = new JTextField();
        tfMobile = new JTextField(); tfRoom = new JTextField();
        tfType = new JTextField(); tfSearchRoom = new JTextField();

        form.add(new JLabel("Name")); form.add(tfName);
        form.add(new JLabel("Age")); form.add(tfAge);
        form.add(new JLabel("Mobile")); form.add(tfMobile);
        form.add(new JLabel("Room #")); form.add(tfRoom);
        form.add(new JLabel("Room Type")); form.add(tfType);
        form.add(new JLabel("Search Room #")); form.add(tfSearchRoom);

        pane.add(form, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttons = new JPanel();
        JButton btnAdd = new JButton("Add Guest");
        JButton btnUpdate = new JButton("Update Guest");
        JButton btnDelete = new JButton("Delete Guest");
        JButton btnSearch = new JButton("Search Guest");
        JButton btnCheck = new JButton("Check Availability");
        buttons.add(btnAdd); buttons.add(btnUpdate);
        buttons.add(btnDelete); buttons.add(btnSearch);
        buttons.add(btnCheck);
        pane.add(buttons, BorderLayout.CENTER);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Name","Age","Mobile","Room#","Type"},0);
        table = new JTable(tableModel);
        pane.add(new JScrollPane(table), BorderLayout.SOUTH);

        // Action listeners
        btnAdd.addActionListener(e -> addGuest());
        btnDelete.addActionListener(e -> deleteGuest());
        btnUpdate.addActionListener(e -> updateGuest());
        btnSearch.addActionListener(e -> searchGuest());
        btnCheck.addActionListener(e -> checkAvailability());
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Guest> list = FileHandler.readAllGuests();
        for (Guest g : list) {
            tableModel.addRow(new Object[]{
                g.getName(), g.getAge(), g.getMobile(),
                g.getRoomNumber(), g.getRoomType()
            });
        }
    }

    private void addGuest() {
        String rn = tfRoom.getText().trim();
        if (!FileHandler.isRoomAvailable(rn)) {
            JOptionPane.showMessageDialog(this, "Room already booked!");
            return;
        }
        Guest g = new Guest(
            tfName.getText().trim(),
            tfAge.getText().trim(),
            tfMobile.getText().trim(),
            rn,
            tfType.getText().trim()
        );
        FileHandler.addGuest(g);
        loadTable();
        clearFields();
    }

    private void deleteGuest() {
        String rn = tfRoom.getText().trim();
        if (FileHandler.deleteGuest(rn)) {
            JOptionPane.showMessageDialog(this, "Deleted!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "No such room to delete.");
        }
    }

    private void updateGuest() {
        String rn = tfRoom.getText().trim();
        Guest updated = new Guest(
            tfName.getText().trim(),
            tfAge.getText().trim(),
            tfMobile.getText().trim(),
            rn,
            tfType.getText().trim()
        );
        if (FileHandler.updateGuest(rn, updated)) {
            JOptionPane.showMessageDialog(this, "Updated!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "No such room to update.");
        }
    }

    private void searchGuest() {
        String rn = tfSearchRoom.getText().trim();
        Guest g = FileHandler.findGuest(rn);
        if (g != null) {
            tfName.setText(g.getName());
            tfAge.setText(g.getAge());
            tfMobile.setText(g.getMobile());
            tfRoom.setText(g.getRoomNumber());
            tfType.setText(g.getRoomType());
        } else {
            JOptionPane.showMessageDialog(this, "Guest not found.");
        }
    }

    private void checkAvailability() {
        String rn = tfSearchRoom.getText().trim();
        if (rn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter room # to check.");
        } else if (FileHandler.isRoomAvailable(rn)) {
            JOptionPane.showMessageDialog(this, "Room is available!");
        } else {
            JOptionPane.showMessageDialog(this, "Room is occupied.");
        }
    }

    private void clearFields() {
        tfName.setText(""); tfAge.setText("");
        tfMobile.setText(""); tfRoom.setText("");
        tfType.setText(""); tfSearchRoom.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelManagementUI::new);
    }
}
