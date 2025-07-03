import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

public class Calculator extends JFrame implements ActionListener, KeyListener {
    
    // UI Components
    private JTextField display;
    private JPanel buttonPanel;
    private JButton[] numberButtons;
    private JButton[] operatorButtons;
    private JButton clearButton, allClearButton, equalsButton, decimalButton, 
                   backspaceButton, percentButton, plusMinusButton;
    
    // Calculator logic variables
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private boolean isNewInput = true;
    private boolean operatorPressed = false;
    private DecimalFormat formatter = new DecimalFormat("#.##########");
    
    // UI Colors
    private final Color OPERATOR_COLOR = new Color(255, 149, 0);
    private final Color SPECIAL_COLOR = new Color(165, 165, 165);
    private final Color NUMBER_COLOR = new Color(51, 51, 51);
    private final Color DISPLAY_COLOR = new Color(248, 248, 248);
    
    public Calculator() {
        super("Modern Calculator - Day 3");
        
        // Initialize components
        initializeComponents();
        
        // Setup layout
        setupLayout();
        
        // Configure window
        setSize(400, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);
        
        setVisible(true);
    }
    
    private void initializeComponents() {
        // Initialize display with enhanced styling
        display = new JTextField();
        display.setFont(new Font("SF Pro Display", Font.PLAIN, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setText("0");
        display.setBackground(DISPLAY_COLOR);
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        display.setPreferredSize(new Dimension(0, 100));
        
        // Initialize number buttons (0-9) with hover effects
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createStyledButton(String.valueOf(i), NUMBER_COLOR, Color.WHITE);
        }
        
        // Initialize operator buttons with orange styling
        operatorButtons = new JButton[4];
        String[] operators = {"+", "-", "×", "÷"};
        for (int i = 0; i < 4; i++) {
            operatorButtons[i] = createStyledButton(operators[i], OPERATOR_COLOR, Color.WHITE);
        }
        
        // Initialize special function buttons
        clearButton = createStyledButton("C", SPECIAL_COLOR, Color.BLACK);
        allClearButton = createStyledButton("AC", SPECIAL_COLOR, Color.BLACK);
        equalsButton = createStyledButton("=", OPERATOR_COLOR, Color.WHITE);
        decimalButton = createStyledButton(".", NUMBER_COLOR, Color.WHITE);
        backspaceButton = createStyledButton("⌫", SPECIAL_COLOR, Color.BLACK);
        percentButton = createStyledButton("%", SPECIAL_COLOR, Color.BLACK);
        plusMinusButton = createStyledButton("±", SPECIAL_COLOR, Color.BLACK);
        
        // Make equals button wider by creating a custom component
        equalsButton.setPreferredSize(new Dimension(170, 70));
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SF Pro Display", Font.PLAIN, 20));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.addActionListener(this);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = button.getBackground();
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Add display to top
        add(display, BorderLayout.NORTH);
        
        // Create button panel with custom layout
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Row 0: AC, ±, %, ÷
        gbc.gridx = 0; gbc.gridy = 0; buttonPanel.add(allClearButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; buttonPanel.add(plusMinusButton, gbc);
        gbc.gridx = 2; gbc.gridy = 0; buttonPanel.add(percentButton, gbc);
        gbc.gridx = 3; gbc.gridy = 0; buttonPanel.add(operatorButtons[3], gbc); // ÷
        
        // Row 1: 7, 8, 9, ×
        gbc.gridx = 0; gbc.gridy = 1; buttonPanel.add(numberButtons[7], gbc);
        gbc.gridx = 1; gbc.gridy = 1; buttonPanel.add(numberButtons[8], gbc);
        gbc.gridx = 2; gbc.gridy = 1; buttonPanel.add(numberButtons[9], gbc);
        gbc.gridx = 3; gbc.gridy = 1; buttonPanel.add(operatorButtons[2], gbc); // ×
        
        // Row 2: 4, 5, 6, -
        gbc.gridx = 0; gbc.gridy = 2; buttonPanel.add(numberButtons[4], gbc);
        gbc.gridx = 1; gbc.gridy = 2; buttonPanel.add(numberButtons[5], gbc);
        gbc.gridx = 2; gbc.gridy = 2; buttonPanel.add(numberButtons[6], gbc);
        gbc.gridx = 3; gbc.gridy = 2; buttonPanel.add(operatorButtons[1], gbc); // -
        
        // Row 3: 1, 2, 3, +
        gbc.gridx = 0; gbc.gridy = 3; buttonPanel.add(numberButtons[1], gbc);
        gbc.gridx = 1; gbc.gridy = 3; buttonPanel.add(numberButtons[2], gbc);
        gbc.gridx = 2; gbc.gridy = 3; buttonPanel.add(numberButtons[3], gbc);
        gbc.gridx = 3; gbc.gridy = 3; buttonPanel.add(operatorButtons[0], gbc); // +
        
        // Row 4: 0 (spans 2 columns), ., =
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; 
        buttonPanel.add(numberButtons[0], gbc);
        gbc.gridwidth = 1; // Reset grid width
        gbc.gridx = 2; gbc.gridy = 4; buttonPanel.add(decimalButton, gbc);
        gbc.gridx = 3; gbc.gridy = 4; buttonPanel.add(equalsButton, gbc);
        
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        // Handle number buttons
        for (int i = 0; i < 10; i++) {
            if (command.equals(String.valueOf(i))) {
                handleNumberInput(command);
                return;
            }
        }
        
        // Handle operator buttons
        if (command.equals("+") || command.equals("-") || 
            command.equals("×") || command.equals("÷")) {
            handleOperator(command);
            return;
        }
        
        // Handle special buttons
        switch (command) {
            case "AC":
                handleAllClear();
                break;
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
            case "%":
                handlePercent();
                break;
            case "±":
                handlePlusMinus();
                break;
        }
    }
    
    // Enhanced number input with better formatting
    private void handleNumberInput(String number) {
        if (isNewInput) {
            display.setText(number);
            isNewInput = false;
        } else {
            String currentText = display.getText();
            if (!currentText.equals("0") && currentText.length() < 10) {
                display.setText(currentText + number);
            } else if (currentText.equals("0")) {
                display.setText(number);
            }
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
            operator = op; // Allow changing operator
        }
    }
    
    private void handleEquals() {
        if (!operator.isEmpty() && !operatorPressed) {
            secondNumber = Double.parseDouble(display.getText());
            double result = performCalculation();
            
            // Handle division by zero
            if (operator.equals("÷") && secondNumber == 0) {
                display.setText("Error");
                resetCalculator();
                return;
            }
            
            // Format and display result
            String formattedResult = formatter.format(result);
            if (formattedResult.length() > 10) {
                display.setText(String.format("%.3e", result));
            } else {
                display.setText(formattedResult);
            }
            
            firstNumber = result;
            operator = "";
            isNewInput = true;
            operatorPressed = false;
        }
    }
    
    private double performCalculation() {
        switch (operator) {
            case "+": return firstNumber + secondNumber;
            case "-": return firstNumber - secondNumber;
            case "×": return firstNumber * secondNumber;
            case "÷": return firstNumber / secondNumber;
            default: return secondNumber;
        }
    }
    
    private void handleAllClear() {
        display.setText("0");
        resetCalculator();
    }
    
    private void handleClear() {
        display.setText("0");
        isNewInput = true;
    }
    
    private void handlePercent() {
        double current = Double.parseDouble(display.getText());
        current = current / 100;
        display.setText(formatter.format(current));
        isNewInput = true;
    }
    
    private void handlePlusMinus() {
        double current = Double.parseDouble(display.getText());
        current = -current;
        display.setText(formatter.format(current));
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
            if (isNewInput) {
                display.setText("0.");
                isNewInput = false;
            } else {
                display.setText(currentText + ".");
            }
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
    
    // Keyboard support
    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        
        if (Character.isDigit(keyChar)) {
            handleNumberInput(String.valueOf(keyChar));
        } else {
            switch (keyChar) {
                case '+': handleOperator("+"); break;
                case '-': handleOperator("-"); break;
                case '*': handleOperator("×"); break;
                case '/': handleOperator("÷"); break;
                case '=': case '\n': handleEquals(); break;
                case '.': handleDecimal(); break;
                case '%': handlePercent(); break;
            }
        }
        
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            handleBackspace();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            handleAllClear();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    public static void main(String[] args) {
        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
        
        // Run calculator
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
