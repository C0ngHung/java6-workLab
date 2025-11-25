package org.conghung.lab01.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.conghung.lab01.model.Student;
import org.conghung.lab01.model.StudentMap;
import org.conghung.lab01.utils.HttpClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;

public class StudentFrame extends JFrame {
    private JTextField txtId, txtName, txtMark;
    private JRadioButton rdoMale, rdoFemale;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnCreate, btnUpdate, btnDelete, btnReset;

    private static final String BASE_URL = "https://poly-java-6-e08ab-default-rtdb.asia-southeast1.firebasedatabase.app/students";
    private final ObjectMapper mapper = new ObjectMapper();

    public StudentFrame() {
        setTitle("Quản lý sinh viên");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        // Row 1: Labels
        gbc.gridy = 0;
        gbc.gridx = 0;
        pnlForm.add(new JLabel("Id"), gbc);
        
        gbc.gridx = 1;
        pnlForm.add(new JLabel("Full Name"), gbc);

        // Row 2: Inputs
        gbc.gridy = 1;
        gbc.gridx = 0;
        txtId = new JTextField();
        pnlForm.add(txtId, gbc);
        
        gbc.gridx = 1;
        txtName = new JTextField();
        pnlForm.add(txtName, gbc);

        // Row 3: Labels
        gbc.gridy = 2;
        gbc.gridx = 0;
        pnlForm.add(new JLabel("Gender"), gbc);
        
        gbc.gridx = 1;
        pnlForm.add(new JLabel("Average Mark"), gbc);

        // Row 4: Inputs
        gbc.gridy = 3;
        gbc.gridx = 0;
        JPanel pnlGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rdoMale = new JRadioButton("Male");
        rdoFemale = new JRadioButton("Female");
        ButtonGroup grpGender = new ButtonGroup();
        grpGender.add(rdoMale);
        grpGender.add(rdoFemale);
        rdoFemale.setSelected(true);
        pnlGender.add(rdoMale);
        pnlGender.add(rdoFemale);
        pnlForm.add(pnlGender, gbc);

        gbc.gridx = 1;
        txtMark = new JTextField();
        pnlForm.add(txtMark, gbc);

        add(pnlForm, BorderLayout.NORTH);

        // Buttons Panel
        JPanel pnlButtons = new JPanel(new FlowLayout());
        btnCreate = new JButton("Create");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnReset = new JButton("Reset");

        pnlButtons.add(btnCreate);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnReset);

        // Group Form and Buttons
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlForm, BorderLayout.CENTER);
        pnlTop.add(pnlButtons, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.NORTH);

        // Table Panel
        String[] columns = {"Id", "Full Name", "Gender", "Mark"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Events
        initEvents();

        // Load Data
        loadData();
    }

    private void initEvents() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(tableModel.getValueAt(row, 0).toString());
                    txtName.setText(tableModel.getValueAt(row, 1).toString());
                    boolean gender = Boolean.parseBoolean(tableModel.getValueAt(row, 2).toString());
                    if (gender) rdoMale.setSelected(true); else rdoFemale.setSelected(true);
                    txtMark.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        btnCreate.addActionListener(e -> createStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnReset.addActionListener(e -> resetForm());
    }

    private void loadData() {
        new Thread(() -> {
            try {
                var connection = HttpClient.openConnection("GET", BASE_URL + ".json");
                var response = HttpClient.readData(connection);
                String json = new String(response);
                
                if (json.equals("null")) {
                    SwingUtilities.invokeLater(() -> tableModel.setRowCount(0));
                    return;
                }

                StudentMap map = mapper.readValue(json, StudentMap.class);
                
                SwingUtilities.invokeLater(() -> {
                    tableModel.setRowCount(0);
                    map.forEach((key, student) -> {
                        student.setId(key); // Set key as ID
                        tableModel.addRow(new Object[]{
                                key,
                                student.getName(),
                                student.isGender(),
                                student.getMark()
                        });
                    });
                });

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
            }
        }).start();
    }

    private void createStudent() {
        try {
            Student student = getStudentFromForm();
            String json = mapper.writeValueAsString(student);
            String id = txtId.getText().trim();
            
            new Thread(() -> {
                try {
                    if (id.isEmpty()) {
                        var connection = HttpClient.openConnection("POST", BASE_URL + ".json");
                        HttpClient.writeData(connection, json.getBytes());
                    } else {
                        var connection = HttpClient.openConnection("PUT", BASE_URL + "/" + id + ".json");
                        HttpClient.writeData(connection, json.getBytes());
                    }
                    loadData();
                    resetForm();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating student: " + e.getMessage());
        }
    }

    private void updateStudent() {
        String id = txtId.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student to update");
            return;
        }

        try {
            Student student = getStudentFromForm();
            String json = mapper.writeValueAsString(student);

            new Thread(() -> {
                try {
                    var connection = HttpClient.openConnection("PUT", BASE_URL + "/" + id + ".json");
                    HttpClient.writeData(connection, json.getBytes());
                    loadData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating student: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        String id = txtId.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        new Thread(() -> {
            try {
                var connection = HttpClient.openConnection("DELETE", BASE_URL + "/" + id + ".json");
                HttpClient.readData(connection);
                loadData();
                resetForm();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void resetForm() {
        SwingUtilities.invokeLater(() -> {
            txtId.setText("");
            txtName.setText("");
            txtMark.setText("");
            rdoFemale.setSelected(true);
            table.clearSelection();
        });
    }

    private Student getStudentFromForm() {
        return Student.builder()
                .name(txtName.getText())
                .mark(Double.parseDouble(txtMark.getText()))
                .gender(rdoMale.isSelected())
                .build();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentFrame().setVisible(true);
        });
    }
}
