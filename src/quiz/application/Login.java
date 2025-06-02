package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    JButton rules, back;
    JTextField StudentName;
    JTextField RNumber;


    Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon LoginImage = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpg"));
        JLabel image = new JLabel(LoginImage);
        image.setBounds(0, 0, 700, 800);
        add(image);

        JLabel heading = new JLabel("COMSATS University Islamabad");
        heading.setBounds(750, 60,700, 50);
        heading.setFont(new Font("Algerian", Font.BOLD, 30));
        heading.setForeground(new Color(30, 144, 254));
        add(heading);

        JLabel name = new JLabel("Enter your name");
        name.setBounds(750, 150, 300, 20);
        name.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        name.setForeground(new Color(30, 144, 254));
        add(name);
        
        StudentName = new JTextField();
        StudentName.setBounds(750, 200, 300, 25);
        StudentName.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(StudentName);
        
        JLabel rollNumber = new JLabel("Enter your Regestration Number");
        rollNumber.setBounds(750, 250, 300, 20);
        rollNumber.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        rollNumber.setForeground(new Color(30, 144, 254));
        add(rollNumber);

        RNumber = new JTextField();
        RNumber.setBounds(750, 300, 300, 25);
        RNumber.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(RNumber);

        rules = new JButton("Rules");
        rules.setBounds(750, 400, 120, 25);
        rules.setBackground(new Color(30, 144, 254));
        rules.setForeground(Color.WHITE);
        rules.addActionListener(this);
        add(rules);

        back = new JButton("Back");
        back.setBounds(930, 400, 120, 25);
        back.setBackground(new Color(30, 144, 254));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(1200, 500);
        setLocation(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == rules) {
            String name = StudentName.getText();
            String rollNumber = RNumber.getText();
            setVisible(false);
            new Rules(name ,rollNumber);

        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
