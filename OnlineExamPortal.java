import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class OnlineExamPortal extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea questionArea;
    private ButtonGroup buttonGroup;
    private JButton loginButton;
    private JButton cancelButton;
    private JButton nextButton;
    private int currentQuestionIndex;
    private int correctAnswers;
    private String[] questions = {
            "1. Which keyword is used to define a constant in Java?\n"
                    + "a) final\n"
                    + "b) static\n"
                    + "c) const\n"
                    + "d) value\n",

            "2. What is the size of the boolean data type in Java?\n"
                    + "a) 8 bits\n"
                    + "b) 16 bits\n"
                    + "c) 32 bits\n"
                    + "d) It depends on the JVM\n",

            "3. Which method must be implemented by all threads?\n"
                    + "a) run()\n"
                    + "b) start()\n"
                    + "c) execute()\n"
                    + "d) main()\n",

            "4. What is the output of the following code?\n"
                    + "int x = 5;\n"
                    + "System.out.println(x++);\n"
                    + "a) 5\n"
                    + "b) 6\n"
                    + "c) 4\n"
                    + "d) Compilation error\n",

            "5. Which exception is thrown when an array is accessed with an invalid index?\n"
                    + "a) InvalidIndexException\n"
                    + "b) IndexOutOfBoundsException\n"
                    + "c) ArrayOutOfBoundsException\n"
                    + "d) ArrayIndexOutOfBoundsException\n"
    };
    private char[] answers = {'a', 'c', 'a', 'b', 'd'};

    public OnlineExamPortal() {
        setTitle("Online Exam Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(cancelButton);

        add(loginPanel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (validateLogin(username, password)) {
                    showQuestion();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials. Exiting...");
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showQuestion() {
        getContentPane().removeAll();

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());

        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("Arial", Font.PLAIN, 14));

        buttonGroup = new ButtonGroup();

        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new GridLayout(4, 1));

        for (char option = 'a'; option <= 'd'; option++) {
            JRadioButton radioButton = new JRadioButton(String.valueOf(option));
            radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
            buttonGroup.add(radioButton);
            answersPanel.add(radioButton);
        }

        nextButton = new JButton("Next");

        questionPanel.add(questionArea, BorderLayout.NORTH);
        questionPanel.add(answersPanel, BorderLayout.CENTER);
        questionPanel.add(nextButton, BorderLayout.SOUTH);

        add(questionPanel, BorderLayout.CENTER);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;

                if (currentQuestionIndex < questions.length) {
                    displayCurrentQuestion();
                } else {
                    showResult();
                }
            }
        });

        currentQuestionIndex = 0;
        correctAnswers = 0;
        displayCurrentQuestion();
    }

    private void displayCurrentQuestion() {
        questionArea.setText(questions[currentQuestionIndex]);
        buttonGroup.clearSelection();
    }

    private void checkAnswer() {
        char userAnswer = 0;

        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                userAnswer = button.getText().charAt(0);
                break;
            }
        }

        if (userAnswer == answers[currentQuestionIndex]) {
            correctAnswers++;
        }
    }

    private void showResult() {
        getContentPane().removeAll();

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());

        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel totalQuestionsLabel = new JLabel("Total questions: " + questions.length);
        JLabel correctAnswersLabel = new JLabel("Correct answers: " + correctAnswers);
        JLabel scoreLabel = new JLabel("Percentage: " + (correctAnswers * 100) / questions.length + "%");

        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(totalQuestionsLabel, BorderLayout.CENTER);
        resultPanel.add(correctAnswersLabel, BorderLayout.CENTER);
        resultPanel.add(scoreLabel, BorderLayout.AFTER_LAST_LINE);

        add(resultPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private boolean validateLogin(String username, String password) {
        return username.equals("admin") && password.equals("password");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OnlineExamPortal();
            }
        });
    }
}