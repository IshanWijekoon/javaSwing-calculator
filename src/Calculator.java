import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    // UI Components
    private JTextField display;
    private JPanel buttonPanel;
    private JButton[] numberButtons;
    private JButton[] operatorButtons;
    private JButton clearButton, equalsButton, decimalButton, backspaceButton;

    // Calculator logic variables
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private boolean isNewInput = true;
    private boolean operatorPressed = false;

    public Calculator() {
        super("Modern Calculator");

        // Initialize components
        initializeComponents();

        // Setup layout
        setupLayout();

        // Configure window
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setVisible(true);
    }

    private void initializeComponents() {
        // Initialize display
        display = new JTextField();
        display.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setText("0");
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        display.setPreferredSize(new Dimension(0, 80));

        // Initialize number buttons (0-9)
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFocusPainted(false);
            numberButtons[i].setPreferredSize(new Dimension(80, 60));
        }

        // Initialize operator buttons
        operatorButtons = new JButton[4];
        String[] operators = {"+", "-", "×", "÷"};
        for (int i = 0; i < 4; i++) {
            operatorButtons[i] = new JButton(operators[i]);
            operatorButtons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            operatorButtons[i].addActionListener(this);
            operatorButtons[i].setFocusPainted(false);
            operatorButtons[i].setBackground(new Color(255, 149, 0));
            operatorButtons[i].setForeground(Color.WHITE);
            operatorButtons[i].setPreferredSize(new Dimension(80, 60));
        }

        // Initialize special buttons
        clearButton = new JButton("C");
        clearButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        clearButton.addActionListener(this);
        clearButton.setFocusPainted(false);
        clearButton.setBackground(new Color(165, 165, 165));
        clearButton.setForeground(Color.BLACK);
        clearButton.setPreferredSize(new Dimension(80, 60));

        equalsButton = new JButton("=");
        equalsButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        equalsButton.addActionListener(this);
        equalsButton.setFocusPainted(false);
        equalsButton.setBackground(new Color(255, 149, 0));
        equalsButton.setForeground(Color.WHITE);
        equalsButton.setPreferredSize(new Dimension(80, 60));

        decimalButton = new JButton(".");
        decimalButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        decimalButton.addActionListener(this);
        decimalButton.setFocusPainted(false);
        decimalButton.setPreferredSize(new Dimension(80, 60));

        backspaceButton = new JButton("⌫");
        backspaceButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        backspaceButton.addActionListener(this);
        backspaceButton.setFocusPainted(false);
        backspaceButton.setBackground(new Color(165, 165, 165));
        backspaceButton.setForeground(Color.BLACK);
        backspaceButton.setPreferredSize(new Dimension(80, 60));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Add display to top
        add(display, BorderLayout.NORTH);

        // Create button panel with GridLayout
        buttonPanel = new JPanel(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add buttons in calculator layout
        buttonPanel.add(clearButton);
        buttonPanel.add(backspaceButton);
        buttonPanel.add(new JLabel());
        buttonPanel.add(operatorButtons[3]); // ÷

        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(operatorButtons[2]); // ×

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(operatorButtons[1]); // -

        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(operatorButtons[0]); // +

        buttonPanel.add(new JLabel());
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decimalButton);
        buttonPanel.add(equalsButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        for (int i = 0; i < 10; i++) {
            if (command.equals(String.valueOf(i))) {
                handleNumberInput(command);
                return;
            }
        }

        if (command.equals("+") || command.equals("-") || command.equals("×") || command.equals("÷")) {
            handleOperator(command);
            return;
        }

        switch (command) {
            case "C":
                handleClear();
                break;
            case "=":
                handleEquals();
                break;
            case ".":
                handleDecimal();
                break;
            case "⌫":
                handleBackspace();
                break;
        }
    }

    private void handleNumberInput(String number) {
        if (isNewInput) {
            display.setText(number);
            isNewInput = false;
        } else {
            String currentText = display.getText();
            display.setText(currentText.equals("0") ? number : currentText + number);
        }
        operatorPressed = false;
    }

    private void handleOperator(String op) {
        if (!operatorPressed) {
            if (!operator.isEmpty()) {
                handleEquals();
            }
            firstNumber = Double.parseDouble(display.getText());
            operator = op;
            isNewInput = true;
            operatorPressed = true;
        } else {
            operator = op;
        }
    }

    private void handleEquals() {
        if (!operator.isEmpty() && !operatorPressed) {
            secondNumber = Double.parseDouble(display.getText());
            if (operator.equals("÷") && secondNumber == 0) {
                display.setText("Error");
                resetCalculator();
                return;
            }

            double result = performCalculation();
            display.setText(result == (long) result ? String.valueOf((long) result) : String.valueOf(result));

            firstNumber = result;
            operator = "";
            isNewInput = true;
            operatorPressed = false;
        }
    }

    private double performCalculation() {
        return switch (operator) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "×" -> firstNumber * secondNumber;
            case "÷" -> firstNumber / secondNumber;
            default -> secondNumber;
        };
    }

    private void handleClear() {
        display.setText("0");
        resetCalculator();
    }

    private void resetCalculator() {
        firstNumber = 0;
        secondNumber = 0;
        operator = "";
        isNewInput = true;
        operatorPressed = false;
    }

    private void handleDecimal() {
        String currentText = display.getText();
        if (!currentText.contains(".")) {
            display.setText(isNewInput ? "0." : currentText + ".");
            isNewInput = false;
        }
    }

    private void handleBackspace() {
        String currentText = display.getText();
        if (currentText.length() > 1) {
            display.setText(currentText.substring(0, currentText.length() - 1));
        } else {
            display.setText("0");
            isNewInput = true;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        SwingUtilities.invokeLater(Calculator::new);
    }
}
