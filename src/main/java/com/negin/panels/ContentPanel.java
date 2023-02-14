package com.negin.panels;

import com.negin.dialogs.NetworkProblemDialog;
import com.negin.maps.SomeMap;
import com.negin.models.Coordinate;
import com.negin.services.ClientService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContentPanel extends JPanel {

    private final SomeMap someMap;
    private final Random random = new Random();
    private final transient ActionListener refresher = evt -> refresh();
    private final Timer timer = new Timer(5000, refresher);

    public ContentPanel() {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.lightGray);
        List<Coordinate> coordinates = new ArrayList<>();

        try {
            ClientService clientService = new ClientService();
            coordinates = clientService.getCoordinates();
        } catch (Exception e) {
            NetworkProblemDialog networkProblemDialog = new NetworkProblemDialog(e.getMessage());
            networkProblemDialog.setVisible(true);
        }

        someMap = new SomeMap(coordinates);
        this.add(someMap, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void refresh() {

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        Color randomColor = new Color(r, g, b);

        someMap.addPin(random.nextInt(561) + 40, random.nextInt(401) + 40, "Pin " + random.nextInt(10000), randomColor);
    }

    public void enableAutoRefresh(boolean enabled) {

        if (enabled) {
            timer.start();
        } else {
            timer.stop();
        }
    }
}
