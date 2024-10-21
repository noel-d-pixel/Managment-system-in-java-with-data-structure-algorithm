package App;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class UpdateStudent extends JFrame {
    private BinarySearchTree bst = new BinarySearchTree();

    private JPanel contentPane;
    private JTextField updateEntry;
    private JTextField nameU;
    private JTextField entryU;
    private JTextField emailU;
    private JTextField contactU;
    private JTextField homeU;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateStudent frame = new UpdateStudent();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateStudent() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 676, 656);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.GRAY);

        nameU = new JTextField();
        nameU.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Student Name");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel lblNewLabel_1_1 = new JLabel("Entry Number");
        lblNewLabel_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel lblNewLabel_1_2 = new JLabel("Email Address");
        lblNewLabel_1_2.setForeground(Color.BLACK);
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel lblNewLabel_1_3 = new JLabel("Contact Number");
        lblNewLabel_1_3.setForeground(Color.BLACK);
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel lblNewLabel_1_4 = new JLabel("Home City");
        lblNewLabel_1_4.setForeground(Color.BLACK);
        lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 16));

        entryU = new JTextField();
        entryU.setColumns(10);

        emailU = new JTextField();
        emailU.setColumns(10);

        contactU = new JTextField();
        contactU.setColumns(10);

        homeU = new JTextField();
        homeU.setColumns(10);

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> {
            String pid = updateEntry.getText();
            StudentData updatedStudent = new StudentData(
                    nameU.getText(),
                    entryU.getText(),
                    emailU.getText(),
                    contactU.getText(),
                    homeU.getText()
            );
            if (nameU.getText().isEmpty() || entryU.getText().isEmpty() || emailU.getText().isEmpty() || contactU.getText().isEmpty() || homeU.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill all the details :(");
            } else {
                bst.update(updatedStudent);
                updateDatabase(updatedStudent, pid);
                JOptionPane.showMessageDialog(null, "Updated Successfully :)");
                dispose();
                new Menu().setVisible(true);
            }
        });
        updateBtn.setForeground(Color.BLACK);
        updateBtn.setFont(new Font("Tahoma", Font.BOLD, 14));

        // Layout code omitted for brevity

        updateEntry = new JTextField();
        updateEntry.setBounds(190, 100, 237, 33);
        desktopPane.add(updateEntry);
        updateEntry.setColumns(10);

        JButton btnNewButton = new JButton("Search");
        btnNewButton.setForeground(Color.BLACK);
        btnNewButton.addActionListener(e -> {
            String str = updateEntry.getText();
            StudentData student = bst.search(str);
            if (student != null) {
                nameU.setText(student.name);
                entryU.setText(student.entryNumber);
                emailU.setText(student.email);
                contactU.setText(student.contactNumber);
                homeU.setText(student.homeCity);
            } else {
                JOptionPane.showMessageDialog(null, "Student not found :(");
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(334, 164, 149, 33);
        desktopPane.add(btnNewButton);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setForeground(Color.BLACK);
        btnCancel.addActionListener(e -> {
            new Menu().setVisible(true);
            dispose();
        });
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCancel.setBounds(143, 164, 149, 33);
        desktopPane.add(btnCancel);

        JLabel lblNewLabel = new JLabel("Search the \"Entry Number\"");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(180, 56, 283, 33);
        desktopPane.add(lblNewLabel);
        LayoutManager gl_contentPane = null;
        contentPane.setLayout(gl_contentPane);

        // Load existing data into BST
        loadStudentsIntoBST();
    }

    private void loadStudentsIntoBST() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://Noel/hr", "root", "");
            PreparedStatement pst = con.prepareStatement("SELECT * FROM student");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String entryNumber = rs.getString(2);
                String email = rs.getString(3);
                String contact = rs.getString(4);
                String home = rs.getString(5);
                StudentData student = new StudentData(name, entryNumber, email, contact, home);
                bst.insert(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateDatabase(StudentData student, String oldEntryNumber) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://Noel/hr", "root", "");
            PreparedStatement pst = con.prepareStatement(
                    "UPDATE student SET name=?, entry_number=?, email=?, contact=?, home_city=? WHERE entry_number=?");
            pst.setString(1, student.name);
            pst.setString(2, student.entryNumber);
            pst.setString(3, student.email);
            pst.setString(4, student.contactNumber);
            pst.setString(5, student.homeCity);
            pst.setString(6, oldEntryNumber);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
