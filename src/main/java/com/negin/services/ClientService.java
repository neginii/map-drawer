package com.negin.services;

import com.negin.dialogs.NetworkProblemDialog;
import com.negin.models.Coordinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientService {

    public List<Coordinate> getCoordinates() {
        List<Coordinate> data = new ArrayList<>();

        String servletUrl = "http://daily.digpro.se/bios/servlet/bios.servlets.web.RecruitmentTestServlet"; // Replace with the URL of your servlet
        String targetLine = "# Data: (X, Y, Name)";

        int retries = 10;
        while (retries > 0) {
            try {
                URL url = new URL(servletUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.ISO_8859_1));
                boolean foundTargetLine = reader.lines()
                        .anyMatch(targetLine::equals);

                if (foundTargetLine) {
                    reader.lines()
                            .skip(1)
                            .forEach(line -> {
                                String[] parts = line.split(",\\s*");
                                if (parts.length == 3) {
                                    String x = parts[0];
                                    String y = parts[1];
                                    String name = parts[2];
                                    data.add(new Coordinate(x, y, name));
                                }
                            });

                }
                reader.close();
                conn.disconnect();
                retries = 0;
            } catch (IOException e) {
                retries--;
                log.warn("There seems to be a problem reaching the remote server due to '{}'", e.getMessage());
                if (retries != 0) {
                    log.info("Will retry '{}' more times!", retries);
                }
                if (retries == 0) {
                    log.error("Didn't succeed to connect the remote server after 10 attempts due to '{}'", e.getMessage());
                    NetworkProblemDialog networkProblemDialog = new NetworkProblemDialog(e.getMessage());
                    networkProblemDialog.setVisible(true);
                }
            }
        }
        return data;
    }
}
