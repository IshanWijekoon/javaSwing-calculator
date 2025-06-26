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
    private JButton clearButton, equalsButton, decimalButton;
    
    // Calculator state
    private String currentInput = "";
    private boolean isNewInput = true;
    
    public Calculator() {
        super("Calculator");
        
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
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Initialize number buttons (0-9)
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFocusPainted(false);
        }
        
        // Initialize operator buttons
        operatorButtons = new JButton[4];
        String[] operators = {"+", "-", "×", "÷"};
        for (int i = 0; i < 4; i++) {
            operatorButtons[i] = new JButton(operators[i]);
            operatorButtons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            operatorButtons[i].addActionListener(this);
            operatorButtons[i].setFocusPainted(false);
        }
        
        // Initialize special buttons
        clearButton = new JButton("C");
        clearButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        clearButton.addActionListener(this);
        clearButton.setFocusPainted(false);
        
        equalsButton = new JButton("=");
        equalsButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        equalsButton.addActionListener(this);
        equalsButton.setFocusPainted(false);
        
        decimalButton = new JButton(".");
        decimalButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        decimalButton.addActionListener(this);
        decimalButton.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Add display to top
        add(display, BorderLayout.NORTH);
        
        // Create button panel with GridLayout
        buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add buttons in calculator layout
        // Row 1: Clear and operators
        buttonPanel.add(clearButton);
        buttonPanel.add(new JLabel()); // Empty space
        buttonPanel.add(new JLabel()); // Empty space
        buttonPanel.add(operatorButtons[3]); // ÷
        
        // Row 2: 7, 8, 9, ×
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(operatorButtons[2]); // ×
        
        // Row 3: 4, 5, 6, -
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(operatorButtons[1]); // -
        
        // Row 4: 1, 2, 3, +
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(operatorButtons[0]); // +
        
        // Row 5: 0, ., =
        buttonPanel.add(new JLabel()); // Empty space
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decimalButton);
        buttonPanel.add(equalsButton);
        
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        // Handle number buttons
        for (int i = 0; i < 10; i++) {
            if (command.equals(String.valueOf(i))) {
                if (isNewInput) {
                    display.setText(command);
                    isNewInput = false;
                } else {
                    display.setText(display.getText() + command);
                }
                return;
            }
        }
        
        // Handle clear button
        if (command.equals("C")) {
            display.setText("0");
            currentInput = "";
            isNewInput = true;
            return;
        }
        
        // Handle decimal button
        if (command.equals(".")) {
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
            return;
        }
        
        // Placeholder for operator and equals handling (Day 2-3)
        if (command.equals("=") || command.equals("+") || 
            command.equals("-") || command.equals("×") || command.equals("÷")) {
            // Implementation coming in Day 2-3
            System.out.println("Operator pressed: " + command);
        }
        
        if (command.equals("-")) {
        	display.setText(display.getText() + " - ");
        }
        
        if (command.equals("+")) {
        	display.setText(display.getText() + " + ");
        }
        
        if (command.equals("×")) {
        	display.setText(display.getText() + " × ");
        }
        
        if (command.equals("÷")) {
        	display.setText(display.getText() + " ÷ ");
        }
    }
    
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
