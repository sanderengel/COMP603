/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;

/**
 *
 * @author jasseldoong
 */

import javax.swing.*;
import java.awt.*;

public class PlayerNameDialog extends JDialog {
    private String playerName;
    private JTextField nameField;
    private JButton okButton;
    
    public PlayerNameDialog(Frame parent) {
        super(parent, "Enter Your Name", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(parent);

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter your name:"));
        nameField = new JTextField(15);
        inputPanel.add(nameField);
        add(inputPanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                setVisible(false); // Close the dialog
            } else {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getPlayerName() {
        return playerName;
    }
}

