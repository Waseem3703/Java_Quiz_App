package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Quiz extends JFrame implements ActionListener {

    String questions[][] = new String[10][5];
    String answers[][] = new String[10][2];
    String useranswers[][] = new String[10][1];
    JLabel QuestionNo, question;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup groupoptions;
    JButton next, submit;
    JLabel timerLabel;
    Timer swingTimer;

    public static int timer = 15;
    public static int count = 0;
    public static int score = 0;

    String StudentName;
    String RNumber;

    Quiz(String StudentName, String RNumber) {
        this.StudentName = StudentName;
        this.RNumber = RNumber;

        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/quiz.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 1440, 392);
        add(image);

        QuestionNo = new JLabel();
        QuestionNo.setBounds(100, 450, 50, 30);
        QuestionNo.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(QuestionNo);

        question = new JLabel();
        question.setBounds(150, 450, 900, 30);
        question.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(question);

        timerLabel = new JLabel("Time left - 15 seconds");
        timerLabel.setBounds(1100, 500, 300, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        // Questions
        questions[0][0] = "What does OOP stand for?";
        questions[0][1] = "Object-Oriented Programming";
        questions[0][2] = "Only Object Programs";
        questions[0][3] = "Open Office Protocol";
        questions[0][4] = "Order Of Processing";

        questions[1][0] = "In Java, what is an object?";
        questions[1][1] = "A real-world entity with state and behavior";
        questions[1][2] = "A blueprint for creating classes";
        questions[1][3] = "A type of variable";
        questions[1][4] = "A method in a class";

        questions[2][0] = "Which of the following is a key feature of OOP?";
        questions[2][1] = "Encapsulation";
        questions[2][2] = "Compilation";
        questions[2][3] = "Execution";
        questions[2][4] = "Interpretation";

        questions[3][0] = "Which keyword is used to create an object in Java?";
        questions[3][1] = "new";
        questions[3][2] = "class";
        questions[3][3] = "void";
        questions[3][4] = "return";

        questions[4][0] = "What is a class in Java?";
        questions[4][1] = "A blueprint for creating objects";
        questions[4][2] = "A variable";
        questions[4][3] = "A method";
        questions[4][4] = "An interface";

        questions[5][0] = "What concept of OOP hides data?";
        questions[5][1] = "Encapsulation";
        questions[5][2] = "Inheritance";
        questions[5][3] = "Polymorphism";
        questions[5][4] = "Abstraction";

        questions[6][0] = "What is inheritance in Java?";
        questions[6][1] = "A class acquires properties of another class";
        questions[6][2] = "Data hiding";
        questions[6][3] = "Method overloading";
        questions[6][4] = "Interface implementation";

        questions[7][0] = "Which feature allows methods to have the same name but different parameters?";
        questions[7][1] = "Polymorphism";
        questions[7][2] = "Encapsulation";
        questions[7][3] = "Inheritance";
        questions[7][4] = "Abstraction";

        questions[8][0] = "What is a constructor in Java?";
        questions[8][1] = "A special method to initialize objects";
        questions[8][2] = "A class variable";
        questions[8][3] = "A return type method";
        questions[8][4] = "An interface method";

        questions[9][0] = "What keyword is used to inherit a class in Java?";
        questions[9][1] = "extends";
        questions[9][2] = "implements";
        questions[9][3] = "inherits";
        questions[9][4] = "super";

        // Answers
        answers[0][1] = "Object-Oriented Programming";
        answers[1][1] = "A real-world entity with state and behavior";
        answers[2][1] = "Encapsulation";
        answers[3][1] = "new";
        answers[4][1] = "A blueprint for creating objects";
        answers[5][1] = "Encapsulation";
        answers[6][1] = "A class acquires properties of another class";
        answers[7][1] = "Polymorphism";
        answers[8][1] = "A special method to initialize objects";
        answers[9][1] = "extends";

        opt1 = new JRadioButton();
        opt1.setBounds(170, 520, 700, 30);
        opt1.setBackground(Color.WHITE);
        opt1.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt1);

        opt2 = new JRadioButton();
        opt2.setBounds(170, 560, 700, 30);
        opt2.setBackground(Color.WHITE);
        opt2.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt2);

        opt3 = new JRadioButton();
        opt3.setBounds(170, 600, 700, 30);
        opt3.setBackground(Color.WHITE);
        opt3.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt3);

        opt4 = new JRadioButton();
        opt4.setBounds(170, 640, 700, 30);
        opt4.setBackground(Color.WHITE);
        opt4.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt4);

        groupoptions = new ButtonGroup();
        groupoptions.add(opt1);
        groupoptions.add(opt2);
        groupoptions.add(opt3);
        groupoptions.add(opt4);

        next = new JButton("Next");
        next.setBounds(1100, 550, 200, 40);
        next.setFont(new Font("Tahoma", Font.PLAIN, 22));
        next.setBackground(new Color(30, 144, 255));
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        add(next);

        submit = new JButton("Submit");
        submit.setBounds(1100, 620, 200, 40); // Changed Y from 710 to 620 for better visibility
        submit.setFont(new Font("Tahoma", Font.PLAIN, 22));
        submit.setBackground(new Color(30, 144, 255));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setEnabled(false);
        submit.setVisible(true); // Ensure it is visible
        add(submit);

        start(count);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (swingTimer != null) {
            swingTimer.stop();
        }

        if (ae.getSource() == next) {
            if (groupoptions.getSelection() == null) {
                useranswers[count][0] = "";
            } else {
                useranswers[count][0] = groupoptions.getSelection().getActionCommand();
            }

            count++;
            if (count == 9) {
                next.setVisible(false);   // Hide next button
                submit.setEnabled(true);  // Enable submit
                submit.setVisible(true);  // Ensure it shows
            }

            start(count);
        } else if (ae.getSource() == submit) {
            if (groupoptions.getSelection() == null) {
                useranswers[count][0] = "";
            } else {
                useranswers[count][0] = groupoptions.getSelection().getActionCommand();
            }

            for (int i = 0; i < useranswers.length; i++) {
                if (useranswers[i][0].equals(answers[i][1])) {
                    score += 10;
                }
            }

            setVisible(false);
            new Score(StudentName, RNumber, score);
        }
    }

    public void start(int count) {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop();
        }

        QuestionNo.setText("" + (count + 1) + ". ");
        question.setText(questions[count][0]);
        opt1.setText(questions[count][1]);
        opt1.setActionCommand(questions[count][1]);

        opt2.setText(questions[count][2]);
        opt2.setActionCommand(questions[count][2]);

        opt3.setText(questions[count][3]);
        opt3.setActionCommand(questions[count][3]);

        opt4.setText(questions[count][4]);
        opt4.setActionCommand(questions[count][4]);

        groupoptions.clearSelection();
        startTimer();
    }

    public void startTimer() {
        timer = 15;
        timerLabel.setText("Time left - " + timer + " seconds");

        swingTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerLabel.setText("Time left - " + timer + " seconds");
                timer--;

                if (timer < 0) {
                    ((Timer) e.getSource()).stop();

                    if (groupoptions.getSelection() == null) {
                        useranswers[count][0] = "";
                    } else {
                        useranswers[count][0] = groupoptions.getSelection().getActionCommand();
                    }

                    if (count == 9) {
                        for (int i = 0; i < useranswers.length; i++) {
                            if (useranswers[i][0].equals(answers[i][1])) {
                                score += 10;
                            }
                        }
                        setVisible(false);
                        new Score(StudentName, RNumber, score);
                    } else {
                        count++;
                        if (count == 9) {
                            next.setVisible(false);   
                            submit.setEnabled(true);  
                            submit.setVisible(true); 
                        }

                        start(count);
                    }
                }
            }
        });

        swingTimer.start();
    }

    public static void main(String[] args) {
        new Quiz("User", "Roll Number");
    }
}
