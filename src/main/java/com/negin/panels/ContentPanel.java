package com.negin.panels;

import com.negin.maps.SomeMap;
import com.negin.models.Coordinate;
import com.negin.services.ClientService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContentPanel extends JPanel {

    private final JLabel serverStatus = new JLabel();
    private final SomeMap someMap;
    private final transient ClientService clientService = new ClientService();
    private final transient ActionListener refresher = evt -> refresh();
    private final Timer timer = new Timer(30000, refresher);

    public ContentPanel() {

        List<Coordinate> coordinates = clientService.getCoordinates();
        serverStatus.setText("Last Updated at " + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        someMap = new SomeMap();
        someMap.setCoordinates(coordinates);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.lightGray);
        this.add(serverStatus, BorderLayout.PAGE_START);
        this.add(someMap, BorderLayout.CENTER);
        this.add(serverStatus, BorderLayout.PAGE_START);
        this.setVisible(true);
    }

    public void refresh() {
        List<Coordinate> coordinates = clientService.getCoordinates();
        someMap.setCoordinates(coordinates);
        serverStatus.setText(String.format("Last Updated at %s", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
    }

    public void enableAutoRefresh(boolean enabled) {

        if (enabled) {
            timer.start();
        } else {
            timer.stop();
        }
    }

}
