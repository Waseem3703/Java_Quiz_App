package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.sound.sampled.*;

public class Quiz extends JFrame implements ActionListener {

    String[][] questions   = new String[50][5];
    String[][] answers     = new String[50][2];
    String[][] userAnswers = new String[10][1];
    int[] selected = new int[10];

    JLabel qNoLabel, qTextLabel, timerLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup group;
    JButton nextBtn, submitBtn;
    javax.swing.Timer swingTimer;

    int timer = 15, count = 0, score = 0;
    final String studentName;
    final String regNumber;

    private Clip tickClip;
    private Clip buzzerClip;

    public Quiz(String studentName, String regNumber) {
        this.studentName = studentName;
        this.regNumber   = regNumber;

        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        add(new JLabel(new ImageIcon(
             ClassLoader.getSystemResource("icons/quiz.jpg")))).setBounds(0, 0, 1440, 392);

        qNoLabel   = new JLabel(); qNoLabel  .setBounds(100, 450, 50, 30);
        qTextLabel = new JLabel(); qTextLabel.setBounds(150, 450, 900, 30);
        qNoLabel  .setFont(new Font("Tahoma", Font.PLAIN, 24));
        qTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(qNoLabel); add(qTextLabel);

        timerLabel = new JLabel("Time left - 15 seconds");
        timerLabel.setBounds(1100, 500, 300, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        opt1 = makeOption(520);
        opt2 = makeOption(560);
        opt3 = makeOption(600);
        opt4 = makeOption(640);

        group = new ButtonGroup();
        group.add(opt1); group.add(opt2); group.add(opt3); group.add(opt4);

        nextBtn   = makeButton("Next",   550);
        submitBtn = makeButton("Submit", 620);
        submitBtn.setEnabled(false);

        fillQuestions();
        pickRandomTen();
        display(count);

        setVisible(true);
    }

    private JRadioButton makeOption(int y) {
        JRadioButton rb = new JRadioButton();
        rb.setBounds(170, y, 700, 30);
        rb.setBackground(Color.WHITE);
        rb.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rb);
        return rb;
    }

    private JButton makeButton(String txt, int y) {
        JButton b = new JButton(txt);
        b.setBounds(1100, y, 200, 40);
        b.setFont(new Font("Tahoma", Font.PLAIN, 22));
        b.setBackground(new Color(30, 144, 255));
        b.setForeground(Color.WHITE);
        b.addActionListener(this);
        add(b);
        return b;
    }

    private void pickRandomTen() {
        java.util.List<Integer> idx = new java.util.ArrayList<>();
        for (int i = 0; i < 50; i++) idx.add(i);
        Collections.shuffle(idx);
        for (int i = 0; i < 10; i++) selected[i] = idx.get(i);
    }

    private void display(int num) {
        stopTickSound();
        if (swingTimer != null && swingTimer.isRunning()) swingTimer.stop();

        int q = selected[num];
        qNoLabel.setText((num + 1) + ". ");
        qTextLabel.setText(questions[q][0]);

        opt1.setText(questions[q][1]); opt1.setActionCommand(questions[q][1]);
        opt2.setText(questions[q][2]); opt2.setActionCommand(questions[q][2]);
        opt3.setText(questions[q][3]); opt3.setActionCommand(questions[q][3]);
        opt4.setText(questions[q][4]); opt4.setActionCommand(questions[q][4]);

        group.clearSelection();
        startCountdown();
    }

    private void startCountdown() {
        timer = 15;
        timerLabel.setText("Time left - 15 seconds");

        swingTimer = new javax.swing.Timer(1000, e -> {
            timerLabel.setText("Time left - " + timer + " seconds");

            if (timer == 5) playTickSound();
            if (timer == 0) playBuzzerSound();

            timer--;

            if (timer < 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                stopTickSound();
                recordAnswer();
                autoNavigate();
            }
        });
        swingTimer.start();
    }

    private void playTickSound() {
        try {
            stopTickSound();
            AudioInputStream ais =
                AudioSystem.getAudioInputStream(getClass().getResource("/icons/tick.wav"));
            tickClip = AudioSystem.getClip();
            tickClip.open(ais);
            tickClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void stopTickSound() {
        if (tickClip != null && tickClip.isRunning()) {
            tickClip.stop(); tickClip.close();
        }
    }

    private void playBuzzerSound() {
        try {
            AudioInputStream ais =
                AudioSystem.getAudioInputStream(getClass().getResource("/icons/buzzer.wav"));
            buzzerClip = AudioSystem.getClip();
            buzzerClip.open(ais);
            buzzerClip.start();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void recordAnswer() {
        userAnswers[count][0] =
            (group.getSelection() == null) ? "" : group.getSelection().getActionCommand();
    }

    private void autoNavigate() {
        if (count == 9) submitBtn.doClick();
        else            nextBtn  .doClick();
    }

    @Override public void actionPerformed(ActionEvent ae) {
        if (swingTimer != null) swingTimer.stop();
        stopTickSound();
        recordAnswer();

        if (ae.getSource() == nextBtn) {
            count++;
            if (count == 9) { nextBtn.setVisible(false); submitBtn.setEnabled(true); }
            display(count);
        } else {
            for (int i = 0; i < 10; i++)
                if (userAnswers[i][0].equals(answers[selected[i]][1])) score += 10;
            setVisible(false);
            new Score(studentName, regNumber, score);
        }
    }

    private void fillQuestions() {
        String[][] q = {
            {"What does OOP stand for?", "Object-Oriented Programming", "Only Object Programs", "Open Office Protocol", "Order Of Processing"},
            {"What is a class in Java?", "A blueprint for creating objects", "A method", "A function", "A loop"},
            {"What is encapsulation?", "Hiding internal state and requiring all interaction through methods", "Inheritance", "Overloading", "Interface"},
            {"Which OOP concept allows code reuse?", "Inheritance", "Polymorphism", "Abstraction", "Encapsulation"},
            {"Which keyword is used to inherit a class?", "extends", "implements", "super", "import"},
            {"What is method overloading?", "Same method name with different parameters", "Same method with same signature", "Different class with same method", "Multiple return types"},
            {"What is method overriding?", "Same method name and signature in subclass", "Different parameters", "Static method change", "Changing class name"},
            {"What is abstraction?", "Hiding implementation details", "Showing all data", "Inheritance", "Extending class"},
            {"Which of these is not an OOP principle?", "Compilation", "Inheritance", "Encapsulation", "Abstraction"},
            {"Which keyword is used to create object?", "new", "this", "extends", "final"},
            {"Java supports multiple inheritance?", "No, only through interfaces", "Yes, through classes", "Yes, through constructors", "No, Java does not support inheritance"},
            {"What is an interface?", "Abstract type used to specify behavior", "Class type", "Object type", "Primitive type"},
            {"Can an abstract class have a constructor?", "Yes", "No", "Only if final", "Only if static"},
            {"What is 'this' keyword?", "Refers to current object", "Refers to parent class", "Refers to static block", "Refers to main method"},
            {"Can we instantiate abstract class?", "No", "Yes", "Only once", "Only in main method"},
            {"What is polymorphism?", "Ability of object to take many forms", "Inheritance", "Compilation", "Abstraction"},
            {"Which keyword is used to stop inheritance?", "final", "static", "super", "this"},
            {"What is constructor?", "Special method to initialize object", "Static block", "Data field", "Return type method"},
            {"Default constructor is provided by?", "Compiler", "User", "JVM", "Object"},
            {"What is static method?", "Method that belongs to class", "Instance method", "Object method", "Virtual method"},
            {"Super keyword refers to?", "Parent class object", "Child object", "Static method", "Interface"},
            {"Which one is true for constructor?", "It has no return type", "It is static", "It is abstract", "It must be private"},
            {"Can constructor be overloaded?", "Yes", "No", "Only in interfaces", "Only with static"},
            {"Can static methods be overridden?", "No", "Yes", "Only in final class", "Only if abstract"},
            {"Final class can be?", "Not inherited", "Overridden", "Static", "Abstract"},
            {"Abstract class can have?", "Concrete methods", "Only abstract methods", "No methods", "Static main only"},
            {"Which class is parent of all classes?", "Object", "Main", "Class", "Super"},
            {"What is instanceof used for?", "Check object type", "Create object", "Delete object", "Copy object"},
            {"Which allows runtime polymorphism?", "Method Overriding", "Method Overloading", "Constructor", "Interface"},
            {"Interface can have?", "Abstract methods only (Java 7)", "Private methods", "Constructor", "Fields"},
            {"Which is used to achieve abstraction?", "Interface", "Constructor", "Method", "Object"},
            {"Multiple interfaces can be?", "Implemented", "Extended", "Inherited", "Composed"},
            {"Which is not allowed with final class?", "Inheritance", "Instantiation", "Static method", "Constructor"},
            {"Object is created with?", "new", "super", "this", "final"},
            {"Which supports dynamic method dispatch?", "Method Overriding", "Method Overloading", "Interface call", "Static method"},
            {"Which can’t be used to implement OOP?", "goto", "class", "object", "interface"},
            {"Inheritance provides?", "Code Reusability", "Security", "Flexibility", "Abstraction"},
            {"Object is?", "Instance of class", "Type of method", "Syntax", "Type of loop"},
            {"All classes extend?", "Object", "Main", "Class", "Super"},
            {"What is default access modifier?", "Package-private", "Public", "Protected", "Private"},
            {"Private members are accessible?", "Within same class", "Subclass", "Package", "Everywhere"},
            {"Getter and Setter are used for?", "Encapsulation", "Abstraction", "Inheritance", "Polymorphism"},
            {"‘implements’ keyword is used for?", "Interface", "Class", "Abstract class", "Object"},
            {"Can abstract class have main()?", "Yes", "No", "Only in subclass", "Only in interface"},
            {"What is anonymous class?", "Class without name", "Static class", "Public class", "Main class"},
            {"Constructor name should be?", "Same as class", "Different from class", "Random", "Capital only"},
            {"What is object lifecycle?", "Creation to garbage collection", "Compilation only", "Execution only", "Declaration only"},
            {"Can class extend multiple interfaces?", "Yes", "No", "Only abstract", "Only final"},
            {"Is Java 100% OOP?", "No", "Yes", "Always", "Depends"},
            {"Can interface have static method?", "Yes (since Java 8)", "No", "Only in abstract", "Only private"},
        };

        for (int i = 0; i < q.length; i++) {
            questions[i] = q[i];
            answers[i][1] = q[i][1];
        }
    }

    public static void main(String[] args) {
        new Quiz("User", "RollNumber");
    }
}
