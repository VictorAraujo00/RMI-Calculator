import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalculatorClient extends JFrame {
    private JTextField displayField;
    private Calculator calculator; // Objeto remoto RMI
    private double memory = 0; // Memória local para armazenar o valor

    public CalculatorClient() {
        setTitle("Calculadora");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Conectar ao servidor RMI
        try {
            calculator = (Calculator) java.rmi.Naming.lookup("rmi://192.168.165.206/CalculatorService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao servidor RMI", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Display estilizado
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 36));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(new Color(240, 240, 240));
        displayField.setForeground(Color.BLACK);
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(displayField, BorderLayout.NORTH);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(220, 220, 220));

        // Botões
        String[] buttons = {
                "MC", "MR", "M+", "M-", "C",
                "7", "8", "9", "/", "CE",
                "4", "5", "6", "*", "%",
                "1", "2", "3", "-", "√",
                "+/-", "0", ".", "+", "="
        };

        for (String text : buttons) {
            JButton button = createStyledButton(text);
            buttonPanel.add(button);
            button.addActionListener(e -> onButtonPressed(e));
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Ajustar margens e preenchimento
        getContentPane().setBackground(new Color(220, 220, 220));
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setBackground(new Color(245, 245, 245));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Botão "igual" com destaque especial
        if (text.equals("=")) {
            button.setBackground(new Color(66, 133, 244));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 22));
        }

        // Botão de limpar com cor diferenciada
        if (text.equals("C") || text.equals("CE")) {
            button.setBackground(new Color(255, 87, 87));
            button.setForeground(Color.WHITE);
        }

        return button;
    }

    private void onButtonPressed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            switch (command) {
                case "C":
                    displayField.setText("");
                    break;
                case "MC":
                    memory = 0; // Limpa a memória
                    break;
                case "MR":
                    displayField.setText(String.valueOf(memory)); // Exibe o valor da memória
                    break;
                case "M+":
                    memory += Double.parseDouble(displayField.getText()); // Adiciona ao valor da memória
                    break;
                case "M-":
                    memory -= Double.parseDouble(displayField.getText()); // Subtrai do valor da memória
                    break;
                case "CE":
                    displayField.setText(""); // Limpa a entrada sem afetar a memória
                    break;
                case "=":
                    String[] tokens = displayField.getText().split(" ");
                    if (tokens.length == 3) {
                        double num1 = Double.parseDouble(tokens[0]);
                        String operator = tokens[1];
                        double num2 = Double.parseDouble(tokens[2]);

                        double result = 0;
                        switch (operator) {
                            case "+":
                                result = calculator.add(num1, num2);
                                break;
                            case "-":
                                result = calculator.subtract(num1, num2);
                                break;
                            case "*":
                                result = calculator.multiply(num1, num2);
                                break;
                            case "/":
                                result = calculator.divide(num1, num2);
                                break;
                        }

                        displayField.setText(String.valueOf(result));
                    }
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    displayField.setText(displayField.getText() + " " + command + " ");
                    break;
                case "√":
                    double num = Double.parseDouble(displayField.getText());
                    double sqrtResult = calculator.squareRoot(num); // Chama a operação de raiz quadrada do servidor
                    displayField.setText(String.valueOf(sqrtResult));
                    break;
                case "+/-":
                    double numToNegate = Double.parseDouble(displayField.getText());
                    displayField.setText(String.valueOf(calculator.negate(numToNegate))); // Chama a operação de negação do servidor
                    break;
                default:
                    displayField.setText(displayField.getText() + command);
            }
        } catch (Exception ex) {
            displayField.setText("Erro");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorClient calculator = new CalculatorClient();
            calculator.setVisible(true);
        });
    }
}
