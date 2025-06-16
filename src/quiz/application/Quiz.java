package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz extends JFrame implements ActionListener {

    /* ------------ DATA STRUCTURES ------------ */
    String[][] questions   = new String[50][5];  // [index][0‑4]
    String[][] answers     = new String[50][2];  // [index][1] = correct text
    String[][] userAnswers = new String[10][1];  // player choices
    int[]      selected    = new int[10];        // 10 random indices

    /* ------------ GUI COMPONENTS ------------ */
    JLabel       qNoLabel, qTextLabel, timerLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup  group;
    JButton      nextBtn, submitBtn;
    javax.swing.Timer swingTimer;

    /* ------------ STATE ------------ */
    int timer  = 15;   // seconds for countdown
    int count  = 0;    // question counter 0‑9
    int score  = 0;

    final String studentName;
    final String regNumber;

    /* ------------ CONSTRUCTOR ------------ */
    public Quiz(String studentName, String regNumber) {
        this.studentName = studentName;
        this.regNumber   = regNumber;

        /* ----- window & banner ----- */
        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        add(new JLabel(new ImageIcon(
                ClassLoader.getSystemResource("icons/quiz.jpg")))).setBounds(0, 0, 1440, 392);

        /* ----- question number & text ----- */
        qNoLabel   = new JLabel(); qNoLabel.setBounds(100, 450, 50, 30);
        qTextLabel = new JLabel(); qTextLabel.setBounds(150, 450, 900, 30);
        qNoLabel.setFont  (new Font("Tahoma", Font.PLAIN, 24));
        qTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(qNoLabel); add(qTextLabel);

        /* ----- timer label ----- */
        timerLabel = new JLabel("Time left - 15 seconds");
        timerLabel.setBounds(1100, 500, 300, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        /* ----- four radio‑button options ----- */
        opt1 = makeOption(520);
        opt2 = makeOption(560);
        opt3 = makeOption(600);
        opt4 = makeOption(640);

        group = new ButtonGroup();
        group.add(opt1); group.add(opt2);
        group.add(opt3); group.add(opt4);

        /* ----- navigation buttons ----- */
        nextBtn   = makeNavButton("Next",   550);
        submitBtn = makeNavButton("Submit", 620);
        submitBtn.setEnabled(false);

        /* ----- load data & start ----- */
        fillQuestions();          // 50 Qs
        pickRandomTen();          // fill selected[0‑9]
        display(count);           // show first question
        setVisible(true);
    }

    /* ------------ HELPERS ------------ */
    private JRadioButton makeOption(int y) {
        JRadioButton rb = new JRadioButton();
        rb.setBounds(170, y, 700, 30);
        rb.setBackground(Color.WHITE);
        rb.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rb);
        return rb;
    }

    private JButton makeNavButton(String txt, int y) {
        JButton b = new JButton(txt);
        b.setBounds(1100, y, 200, 40);
        b.setFont(new Font("Tahoma", Font.PLAIN, 22));
        b.setBackground(new Color(30, 144, 255));
        b.setForeground(Color.WHITE);
        b.addActionListener(this);
        add(b);
        return b;
    }

    /* ------------ LOAD 50 QUESTIONS ------------ */
    private void fillQuestions() {
        String[][] q = {
            /*  0 */ {"What does OOP stand for?", "Object-Oriented Programming", "Only Object Programs", "Open Office Protocol", "Order Of Processing"},
            /*  1 */ {"What is a class in Java?", "A blueprint for creating objects", "A method", "A function", "A loop"},
            /*  2 */ {"What is encapsulation?", "Hiding internal state and requiring all interaction through methods", "Inheritance", "Overloading", "Interface"},
            /*  3 */ {"Which OOP concept allows code reuse?", "Inheritance", "Polymorphism", "Abstraction", "Encapsulation"},
            /*  4 */ {"Which keyword is used to inherit a class?", "extends", "implements", "super", "import"},
            /*  5 */ {"What is method overloading?", "Same method name with different parameters", "Same method with same signature", "Different class with same method", "Multiple return types"},
            /*  6 */ {"What is method overriding?", "Same method name and signature in subclass", "Different parameters", "Static method change", "Changing class name"},
            /*  7 */ {"What is abstraction?", "Hiding implementation details", "Showing all data", "Inheritance", "Extending class"},
            /*  8 */ {"Which of these is not an OOP principle?", "Compilation", "Inheritance", "Encapsulation", "Abstraction"},
            /*  9 */ {"Which keyword is used to create object?", "new", "this", "extends", "final"},
            /* 10 */ {"Java supports multiple inheritance?", "No, only through interfaces", "Yes, through classes", "Yes, through constructors", "No, Java does not support inheritance"},
            /* 11 */ {"What is an interface?", "Abstract type used to specify behavior", "Class type", "Object type", "Primitive type"},
            /* 12 */ {"Can an abstract class have a constructor?", "Yes", "No", "Only if final", "Only if static"},
            /* 13 */ {"What is 'this' keyword?", "Refers to current object", "Refers to parent class", "Refers to static block", "Refers to main method"},
            /* 14 */ {"Can we instantiate abstract class?", "No", "Yes", "Only once", "Only in main method"},
            /* 15 */ {"What is polymorphism?", "Ability of object to take many forms", "Inheritance", "Compilation", "Abstraction"},
            /* 16 */ {"Which keyword is used to stop inheritance?", "final", "static", "super", "this"},
            /* 17 */ {"What is constructor?", "Special method to initialize object", "Static block", "Data field", "Return type method"},
            /* 18 */ {"Default constructor is provided by?", "Compiler", "User", "JVM", "Object"},
            /* 19 */ {"What is static method?", "Method that belongs to class", "Instance method", "Object method", "Virtual method"},
            /* 20 */ {"Super keyword refers to?", "Parent class object", "Child object", "Static method", "Interface"},
            /* 21 */ {"Which one is true for constructor?", "It has no return type", "It is static", "It is abstract", "It must be private"},
            /* 22 */ {"Can constructor be overloaded?", "Yes", "No", "Only in interfaces", "Only with static"},
            /* 23 */ {"Can static methods be overridden?", "No", "Yes", "Only in final class", "Only if abstract"},
            /* 24 */ {"Final class can be?", "Not inherited", "Overridden", "Static", "Abstract"},
            /* 25 */ {"Abstract class can have?", "Concrete methods", "Only abstract methods", "No methods", "Static main only"},
            /* 26 */ {"Which class is parent of all classes?", "Object", "Main", "Class", "Super"},
            /* 27 */ {"What is instanceof used for?", "Check object type", "Create object", "Delete object", "Copy object"},
            /* 28 */ {"Which allows runtime polymorphism?", "Method Overriding", "Method Overloading", "Constructor", "Interface"},
            /* 29 */ {"Interface can have?", "Abstract methods only (Java 7)", "Private methods", "Constructor", "Fields"},
            /* 30 */ {"Which is used to achieve abstraction?", "Interface", "Constructor", "Method", "Object"},
            /* 31 */ {"Multiple interfaces can be?", "Implemented", "Extended", "Inherited", "Composed"},
            /* 32 */ {"Which is not allowed with final class?", "Inheritance", "Instantiation", "Static method", "Constructor"},
            /* 33 */ {"Object is created with?", "new", "super", "this", "final"},
            /* 34 */ {"Which supports dynamic method dispatch?", "Method Overriding", "Method Overloading", "Interface call", "Static method"},
            /* 35 */ {"Which can’t be used to implement OOP?", "goto", "class", "object", "interface"},
            /* 36 */ {"Inheritance provides?", "Code Reusability", "Security", "Flexibility", "Abstraction"},
            /* 37 */ {"Object is?", "Instance of class", "Type of method", "Syntax", "Type of loop"},
            /* 38 */ {"All classes extend?", "Object", "Main", "Class", "Super"},
            /* 39 */ {"What is default access modifier?", "Package-private", "Public", "Protected", "Private"},
            /* 40 */ {"Private members are accessible?", "Within same class", "Subclass", "Package", "Everywhere"},
            /* 41 */ {"Getter and Setter are used for?", "Encapsulation", "Abstraction", "Inheritance", "Polymorphism"},
            /* 42 */ {"‘implements’ keyword is used for?", "Interface", "Class", "Abstract class", "Object"},
            /* 43 */ {"Can abstract class have main()?", "Yes", "No", "Only in subclass", "Only in interface"},
            /* 44 */ {"What is anonymous class?", "Class without name", "Static class", "Public class", "Main class"},
            /* 45 */ {"Constructor name should be?", "Same as class", "Different from class", "Random", "Capital only"},
            /* 46 */ {"What is object lifecycle?", "Creation to garbage collection", "Compilation only", "Execution only", "Declaration only"},
            /* 47 */ {"Can class extend multiple interfaces?", "Yes", "No", "Only abstract", "Only final"},
            /* 48 */ {"Is Java 100% OOP?", "No", "Yes", "Always", "Depends"},
            /* 49 */ {"Can interface have static method?", "Yes (since Java 8)", "No", "Only in abstract", "Only private"}
        };

        for (int i = 0; i < q.length; i++) {
            questions[i] = q[i];
            answers[i][1] = q[i][1];     // first option is correct
        }
    }

    /* ------------ PICK 10 RANDOM QUESTIONS ------------ */
    private void pickRandomTen() {
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i < 50; i++) idx.add(i);
        Collections.shuffle(idx);
        for (int i = 0; i < 10; i++) selected[i] = idx.get(i);
    }

    /* ------------ DISPLAY A QUESTION ------------ */
    private void display(int num) {
        if (swingTimer != null && swingTimer.isRunning()) swingTimer.stop();

        int qIndex = selected[num];
        qNoLabel  .setText((num + 1) + ". ");
        qTextLabel.setText(questions[qIndex][0]);

        opt1.setText(questions[qIndex][1]); opt1.setActionCommand(questions[qIndex][1]);
        opt2.setText(questions[qIndex][2]); opt2.setActionCommand(questions[qIndex][2]);
        opt3.setText(questions[qIndex][3]); opt3.setActionCommand(questions[qIndex][3]);
        opt4.setText(questions[qIndex][4]); opt4.setActionCommand(questions[qIndex][4]);

        group.clearSelection();
        startCountdown();
    }

    /* ------------ COUNTDOWN TIMER ------------ */
    private void startCountdown() {
        timer = 15;
        timerLabel.setText("Time left - 15 seconds");

        swingTimer = new javax.swing.Timer(1000, e -> {
            timerLabel.setText("Time left - " + timer + " seconds");
            timer--;

            if (timer < 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                recordAnswer();
                autoNavigate();
            }
        });
        swingTimer.start();
    }

    /* ------------ RECORD CURRENT ANSWER ------------ */
    private void recordAnswer() {
        if (group.getSelection() == null)
            userAnswers[count][0] = "";
        else
            userAnswers[count][0] = group.getSelection().getActionCommand();
    }

    /* ------------ AUTO NEXT / SUBMIT ------------ */
    private void autoNavigate() {
        if (count == 9) { submitBtn.doClick(); }
        else            { nextBtn  .doClick(); }
    }

    /* ------------ BUTTON CLICKS ------------ */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (swingTimer != null) swingTimer.stop();
        recordAnswer();

        if (ae.getSource() == nextBtn) {
            count++;
            if (count == 9) { nextBtn.setVisible(false); submitBtn.setEnabled(true); }
            display(count);

        } else if (ae.getSource() == submitBtn) {
            for (int i = 0; i < 10; i++)
                if (userAnswers[i][0].equals(answers[selected[i]][1]))
                    score += 10;

            setVisible(false);
            new Score(studentName, regNumber, score);  // your existing Score class
        }
    }

    /* ------------ MAIN FOR TEST ------------ */
    public static void main(String[] args) {
        new Quiz("User", "RollNumber");
    }
}
