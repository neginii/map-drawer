package com.negin.frames;

import com.negin.menus.HelpMenu;
import com.negin.panels.ButtonPanel;
import com.negin.panels.ContentPanel;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class MainFrame extends JFrame {

    public MainFrame(HelpMenu helpMenu, ContentPanel contentPanel, ButtonPanel buttonPanel) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        this.setSize(new Dimension(width, height));
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Map");
        this.setMenuBar(helpMenu);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, contentPanel, buttonPanel);
        splitPane.setDividerLocation(height - 150);

        this.add(splitPane);
        this.setVisible(true);
    }
}
