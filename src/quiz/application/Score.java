package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Score extends JFrame implements ActionListener {

    // ▼ store the player info so we can reuse it
    private final String studentName;
    private final String regNumber;

    public Score(String studentName, String regNumber, int score) {
        this.studentName = studentName;   // keep for “Try Again”
        this.regNumber   = regNumber;

        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon scoreImg = new ImageIcon(ClassLoader.getSystemResource("icons/score.png"));
        Image scaled       = scoreImg.getImage().getScaledInstance(300, 250, Image.SCALE_DEFAULT);
        JLabel image       = new JLabel(new ImageIcon(scaled));
        image.setBounds(570, 200, 300, 250);
        add(image);

        JLabel heading = new JLabel("Thank you " + studentName + ", " + regNumber, SwingConstants.CENTER);
        heading.setBounds(200, 50, 1040, 30);
        heading.setFont(new Font("SansSerif", Font.PLAIN, 26));
        heading.setForeground(Color.RED);
        add(heading);

        JLabel lblScore = new JLabel("Your score is " + score, SwingConstants.CENTER);
        lblScore.setBounds(200, 470, 1040, 30);
        lblScore.setFont(new Font("SansSerif", Font.PLAIN, 26));
        add(lblScore);

        JButton tryAgain = new JButton("Try Again");
        tryAgain.setBounds(660, 530, 120, 40);
        tryAgain.setBackground(new Color(30, 144, 255));
        tryAgain.setForeground(Color.WHITE);
        tryAgain.setFont(new Font("Tahoma", Font.PLAIN, 18));
        tryAgain.addActionListener(this);
        add(tryAgain);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);                 // close this window
        new Quiz(studentName, regNumber);  // start a fresh quiz with same user
    }

    public static void main(String[] args) {
        new Score("User", "Roll Number", 0);
    }
}
