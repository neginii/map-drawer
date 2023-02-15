package com.negin.dialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class NetworkProblemDialog extends JDialog {

    public NetworkProblemDialog(String errorMessage) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        int x = (width - 300) / 2;
        int y = (height - 100) / 2;
        setLocation(x, y);

        setLayout(new FlowLayout());
        setSize(300, 110);
        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel hint = new JLabel("There seems to be some network issue!");
        JLabel message = new JLabel(errorMessage);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> setVisible(false));

        add(hint);
        add(message);
        add(exitButton);
        add(continueButton);
    }
}
