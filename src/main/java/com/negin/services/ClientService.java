package com.negin.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.negin.dialogs.NetworkProblemDialog;
import com.negin.models.Coordinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientService {

    public List<Coordinate> getCoordinates() {
        List<Coordinate> data = new ArrayList<>();
        int retries = 10;
        while (retries > 0) {
            try {
                URL url = new URL("http://localhost:8080/map/coordinates");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    data = mapper.readValue(response.toString(), mapper.getTypeFactory().constructCollectionType(List.class, Coordinate.class));
                    break;
                }
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
