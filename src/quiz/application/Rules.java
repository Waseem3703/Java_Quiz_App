package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Rules extends JFrame implements ActionListener {

    String StudentName, RNumber;
    JButton start, back;

    Rules(String StudentName, String RNumber) {
        this.StudentName = StudentName;
        this.RNumber = RNumber;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Welcome " + StudentName + " " + RNumber + "  to OOP QUIZ");
        heading.setBounds(50, 20, 700, 40);
        heading.setFont(new Font("SansSerif", Font.BOLD, 28));
        heading.setForeground(new Color(30, 144, 254));
        add(heading);

        JTextPane rulesPane = new JTextPane();
        rulesPane.setText(
                "1. This quiz is designed to test your knowledge on Java OOP. Answer the questions to the best of your ability.\n\n"
                + "2. The quiz consists of 10 questions.\n\n"
                + "3. You will have 15 seconds for each question. A timer will be displayed on the screen to help you keep track.\n\n"
                + "4. Each correct answer will earn you 10 points.\n\n"
                + "5. Once you submit, you'll immediately receive your score. You can also view the correct answers."
        );
        rulesPane.setEditable(false);
        rulesPane.setBackground(Color.WHITE);
        rulesPane.setFont(new Font("SansSerif", Font.PLAIN, 18));
        rulesPane.setForeground(Color.RED); // Set text color

// Center text using Style
        StyledDocument doc = rulesPane.getStyledDocument();
        SimpleAttributeSet leftAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(leftAlign, StyleConstants.ALIGN_LEFT);
        doc.setParagraphAttributes(0, doc.getLength(), leftAlign, false);
        rulesPane.setBounds(200, 120, 900, 300); // Adjust size and position
        add(rulesPane); // Add directly without wrapping in scroll pane


        back = new JButton("Back");
        back.setBounds(250, 500, 100, 30);
        back.setBackground(new Color(30, 144, 254));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        start = new JButton("Start Quiz");
        start.setBounds(400, 500, 100, 30);
        start.setBackground(new Color(30, 144, 254));
        start.setForeground(Color.WHITE);
        start.addActionListener(this);
        add(start);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false); // set true if you want to hide title bar

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == start) {
            setVisible(false);
            new Quiz(StudentName, RNumber);
        } else {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Rules("User", "Roll Number");
    }
}
