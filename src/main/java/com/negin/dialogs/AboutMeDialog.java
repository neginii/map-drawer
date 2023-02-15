package com.negin.dialogs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class AboutMeDialog extends JDialog {

    public AboutMeDialog(String name, String emailAddress, String mobileNumber) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        int x = (width - 300) / 2;
        int y = (height - 100) / 2;
        setLocation(x, y);

        setLayout(new FlowLayout());
        setSize(300, 100);
        setAlwaysOnTop(true);
        setVisible(false);

        JLabel nameLabel = new JLabel("Name: " + name);
        JLabel emailLabel = new JLabel("Email: " + emailAddress);
        JLabel mobileLabel = new JLabel("Mobile: " + mobileNumber);

        add(nameLabel);
        add(emailLabel);
        add(mobileLabel);
    }
}
