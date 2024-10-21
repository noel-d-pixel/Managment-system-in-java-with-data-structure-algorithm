package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.border.EmptyBorder;

public class RemoveStudent extends JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs;

    private JPanel contentPane;
    private JTextField deleteEntry;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RemoveStudent frame = new RemoveStudent();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RemoveStudent() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 477, 526);
        contentPane = new JPanel();
        contentPane.setBackground(Color.GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.LIGHT_GRAY);

        JDesktopPane desktopPane_1 = new JDesktopPane();
        desktopPane_1.setBackground(Color.LIGHT_GRAY);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(desktopPane_1, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(desktopPane, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(desktopPane_1, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                .addContainerGap())
        );

        deleteEntry = new JTextField();
        deleteEntry.setBounds(111, 40, 206, 29);
        desktopPane_1.add(deleteEntry);
        deleteEntry.setColumns(10);

        JButton deleteData = new JButton("Delete");
        deleteData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String entryNumber = deleteEntry.getText();
                if (!entryNumber.isEmpty()) {
                    try {
                        // Search for the student with the given entry number in the database
                        String query = "SELECT * FROM student WHERE entrynumber=?";
                        con = DriverManager.getConnection("jdbc:mysql://Noel/hr", "root", "");
                        pst = con.prepareStatement(query);
                        pst.setString(1, entryNumber);
                        rs = pst.executeQuery();

                        if (rs.next()) {
                            // Student found, proceed with deletion
                            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.YES_OPTION) {
                                // Perform deletion from the database
                                String deleteQuery = "DELETE FROM student WHERE entrynumber=?";
                                pst = con.prepareStatement(deleteQuery);
                                pst.setString(1, entryNumber);
                                int rowsAffected = pst.executeUpdate();
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(null, "Student deleted successfully");
                                    dispose(); // Close the current window
                                    Menu menu = new Menu();
                                    menu.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to delete student");
                                }
                            }
                        } else {
                            // Student not found
                            JOptionPane.showMessageDialog(null, "Student not found with entry number: " + entryNumber);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (pst != null) pst.close();
                            if (con != null) con.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error closing resources: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter entry number to delete a student");
                }
            }
        });
        deleteData.setForeground(Color.BLACK);
        deleteData.setBounds(130, 111, 167, 37);
        desktopPane_1.add(deleteData);
        deleteData.setFont(new Font("Tahoma", Font.BOLD, 14));

        JButton btnNewButton_1 = new JButton("Cancel");
        btnNewButton_1.setForeground(Color.BLACK);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                menu.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton_1.setBounds(130, 171, 167, 37);
        desktopPane_1.add(btnNewButton_1);

        JLabel lblNewLabel = new JLabel("Enter the \"Entry Number\" of the student");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setBounds(10, 90, 408, 25);
        desktopPane.add(lblNewLabel);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        contentPane.setLayout(gl_contentPane);
    }
}
