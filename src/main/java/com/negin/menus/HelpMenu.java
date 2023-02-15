package com.negin.menus;

import com.negin.dialogs.AboutMeDialog;

import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpMenu extends MenuBar implements ActionListener {

    private final AboutMeDialog aboutMeDialog;

    public HelpMenu(AboutMeDialog aboutMeDialog) throws HeadlessException {

        this.aboutMeDialog = aboutMeDialog;

        Menu helpMenu = new Menu("Help");
        MenuItem contactMeMenuItem = new MenuItem("Contact Me");
        contactMeMenuItem.addActionListener(this);
        helpMenu.add(contactMeMenuItem);

        this.add(helpMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Contact Me")) {
            aboutMeDialog.setVisible(true);
        }
    }

}
