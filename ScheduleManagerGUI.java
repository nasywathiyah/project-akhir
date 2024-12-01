// Nama : Nasywa Athiyah Rabbani
// NIM  : 235150707111003

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScheduleManagerGUI extends JFrame {
    private final DefaultTableModel tableModel;
    private final JTable scheduleTable;
    private final List<Schedule> scheduleList;

    public ScheduleManagerGUI() {
        scheduleList = new ArrayList<>();

        // Set up frame
        setTitle("Manajemen Jadwal Kuliah");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table setup
        String[] columnNames = {"Mata Kuliah", "Hari", "Waktu Mulai", "Waktu Selesai"};
        tableModel = new DefaultTableModel(columnNames, 0);
        scheduleTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(scheduleTable);

        // Buttons
        JButton addButton = new JButton("Tambah Jadwal");
        JButton deleteButton = new JButton("Hapus Jadwal");
        JButton sortByTimeButton = new JButton("Urutkan Berdasarkan Waktu Mulai");
        JButton sortByNameButton = new JButton("Urutkan Berdasarkan Nama Mata Kuliah");

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortByTimeButton);
        buttonPanel.add(sortByNameButton);

        // Add components to frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add button action listeners
        addButton.addActionListener(e -> addScheduleDialog());
        deleteButton.addActionListener(e -> deleteSelectedSchedule());
        sortByTimeButton.addActionListener(e -> sortSchedulesByTime());
        sortByNameButton.addActionListener(e -> sortSchedulesByName());
    }

    private void addScheduleDialog() {
        // Dialog input fields
        JTextField courseField = new JTextField(10);
        JTextField dayField = new JTextField(10);
        JTextField startTimeField = new JTextField(10);
        JTextField endTimeField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Mata Kuliah:"));
        panel.add(courseField);
        panel.add(new JLabel("Hari:"));
        panel.add(dayField);
        panel.add(new JLabel("Waktu Mulai (HH:MM):"));
        panel.add(startTimeField);
        panel.add(new JLabel("Waktu Selesai (HH:MM):"));
        panel.add(endTimeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Tambah Jadwal", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String courseName = courseField.getText();
            String day = dayField.getText();
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();

            // Add schedule to list and update table
            Schedule schedule = new Schedule(courseName, day, startTime, endTime);
            scheduleList.add(schedule);
            tableModel.addRow(new Object[]{courseName, day, startTime, endTime});
        }
    }

    private void deleteSelectedSchedule() {
        int selectedRow = scheduleTable.getSelectedRow();
        if (selectedRow >= 0) {
            scheduleList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih jadwal yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sortSchedulesByTime() {
        scheduleList.sort(Comparator.comparing(s -> s.startTime));
        updateTable();
    }

    private void sortSchedulesByName() {
        scheduleList.sort(Comparator.comparing(s -> s.courseName));
        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Clear table
        for (Schedule schedule : scheduleList) {
            tableModel.addRow(new Object[]{schedule.courseName, schedule.day, schedule.startTime, schedule.endTime});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScheduleManagerGUI gui = new ScheduleManagerGUI();
            gui.setVisible(true);
        });
    }
}

class Schedule {
    String courseName;
    String day;
    String startTime;
    String endTime;

    public Schedule(String courseName, String day, String startTime, String endTime) {
        this.courseName = courseName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}