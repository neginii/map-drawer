package com.negin.panels;

import com.negin.dialogs.NetworkProblemDialog;
import com.negin.maps.SomeMap;
import com.negin.models.Coordinate;
import com.negin.services.ClientService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContentPanel extends JPanel {

    private  SomeMap someMap;
    public static final String   AUTOMATIC_REFRESH="Coordinates reloaded from server automatically";
    private final transient ActionListener refresher = evt -> {
        try {
            String message = String.format("%s at %d.",AUTOMATIC_REFRESH, System.currentTimeMillis());
            System.out.println(message);
            refresh(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
    private final Timer timer = new Timer(5000, refresher);
    private ClientService clientService=new ClientService();
    JLabel serverStatus= new JLabel("getting coordinates from server");
    public ContentPanel() {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.lightGray);
        List<Coordinate> coordinates = new ArrayList<>();
        this.add(serverStatus, BorderLayout.PAGE_START);

        try {
            ClientService clientService = new ClientService();
            coordinates = clientService.getCoordinates();
            serverStatus.setText("Polygon has drawn by coordinates which is defined on a web server ");
        } catch (Exception e) {
            NetworkProblemDialog networkProblemDialog = new NetworkProblemDialog(e.getMessage());
            networkProblemDialog.setVisible(true);
        }

        someMap = new SomeMap(coordinates);
        this.add(someMap, BorderLayout.CENTER);
        this.add(serverStatus, BorderLayout.PAGE_START);
        this.setVisible(true);
    }

    public void refresh(String text) throws IOException {
        serverStatus.setText(text);
        this.add(serverStatus, BorderLayout.PAGE_START);
        List<Coordinate> coordinates= clientService.getCoordinates();
        removeAll();
        someMap=new SomeMap(coordinates);
        serverStatus.setText(text);
        this.add(someMap,BorderLayout.CENTER);
        this.add(serverStatus, BorderLayout.PAGE_START);
        revalidate();
        repaint();
    }

    public void enableAutoRefresh(boolean enabled) {

        if (enabled) {
            timer.start();
        } else {
            timer.stop();
        }
    }

}
