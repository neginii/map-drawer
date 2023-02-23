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
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContentPanel extends JPanel {

    private final JLabel serverStatus = new JLabel();
    private SomeMap someMap;
    private final transient ClientService clientService = new ClientService();
    private final transient ActionListener refresher = evt -> refresh();
    private final Timer timer = new Timer(30000, refresher);

    public ContentPanel() {

        List<Coordinate> coordinates = clientService.getCoordinates();
        SwingUtilities.invokeLater(() -> {
            someMap = new SomeMap();
            someMap.setCoordinates(coordinates);
            serverStatus.setText("Last Updated at " + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            this.setLayout(new BorderLayout());
            this.setBackground(Color.lightGray);
            this.add(serverStatus, BorderLayout.PAGE_START);
            this.add(someMap, BorderLayout.CENTER);
            this.add(serverStatus, BorderLayout.PAGE_START);
            this.setVisible(true);
        });
    }

    public void refresh() {
        SwingWorker<List<Coordinate>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Coordinate> doInBackground() {
                return clientService.getCoordinates();
            }

            @Override
            protected void done() {
                try {
                    List<Coordinate> coordinates = get();
                    someMap.setCoordinates(coordinates);
                    serverStatus.setText(String.format("Last Updated at %s", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
                } catch (ExecutionException | InterruptedException ex) {
                    log.error(ex.getMessage());
                }
            }
        };

        worker.execute();
    }


    public void enableAutoRefresh(boolean enabled) {

        if (enabled) {
            timer.start();
        } else {
            timer.stop();
        }
    }

}
