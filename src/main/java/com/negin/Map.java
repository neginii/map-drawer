package com.negin;

import com.negin.dialogs.AboutMeDialog;
import com.negin.frames.MainFrame;
import com.negin.menus.HelpMenu;
import com.negin.panels.ButtonPanel;
import com.negin.panels.ContentPanel;

import javax.swing.SwingUtilities;

public class Map {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // About Me Dialog
            AboutMeDialog aboutMeDialog = new AboutMeDialog("Negin Malekzadeh", "negin.malekzadeh@gmail.com", "+46(0)709864626");

            // Help Menu
            HelpMenu helpMenu = new HelpMenu(aboutMeDialog);

            // Content Panel
            ContentPanel contentPanel = new ContentPanel();

            // Main Button Panel
            ButtonPanel buttonPanel = new ButtonPanel(contentPanel);

            // Main Frame
            new MainFrame(helpMenu, contentPanel, buttonPanel);
        });
    }
}
