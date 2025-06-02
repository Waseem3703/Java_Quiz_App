package quiz.application;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Score extends JFrame implements ActionListener {

    Score(String StudentName, String RNumber, int score) {
        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/score.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(570, 200, 300, 250);
        add(image);

        JLabel heading = new JLabel("Thank you " + StudentName + ",  " + RNumber, SwingConstants.CENTER);
        heading.setBounds(200, 50, 1040, 30); 
        heading.setFont(new Font("SansSerif", Font.PLAIN, 26));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.RED);
        add(heading);

        JLabel lblscore = new JLabel("Your score is " + score, SwingConstants.CENTER);
        lblscore.setBounds(200, 470, 1040, 30); 
        lblscore.setFont(new Font("SansSerif", Font.PLAIN, 26));
        lblscore.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblscore);

        JButton submit = new JButton("Try Again");
        submit.setBounds(660, 530, 120, 40); 
        submit.setBackground(new Color(30, 144, 255));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        submit.addActionListener(this);
        add(submit);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Login();
    }

    public static void main(String[] args) {
        new Score("User", "Roll Number", 0);
    }
}
