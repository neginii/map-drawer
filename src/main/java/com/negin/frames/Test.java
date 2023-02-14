package com.negin.frames;

import com.negin.dialogs.NetworkProblemDialog;
import com.negin.models.Coordinate;
import com.negin.services.ClientService;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Test extends JFrame {

    private final List<Coordinate> coordinates;

    public Test() throws IOException {
        setTitle("Polygon with Symbols Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        // Initialize the list of points
        //  points = new ArrayList<>();
        // points.add(new Point(50, 100));
        // points.add(new Point(150, 300));
        //points.add(new Point(250, 50));
        //points.add(new Point(350, 200));
        coordinates = new ClientService().getCoordinates();


        // Create a new panel and add it to the frame
        MyPanel panel = new MyPanel();
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Test();
    }

    private class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Set the color and call drawShapes method
            g.setColor(Color.BLACK);
            drawShapes(this, coordinates);
        }
    }

    // Define drawShapes method here
    public static void drawShapes(JPanel panel, List<Coordinate> points) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

        Graphics graphics = panel.getGraphics();

        // Find the minimum and maximum latitude and longitude values
        double minLat = Double.MAX_VALUE, maxLat = Double.MIN_VALUE;
        double minLng = Double.MAX_VALUE, maxLng = Double.MIN_VALUE;
        for (Coordinate latLng : points) {
            double lat = Double.parseDouble(latLng.getX());
            double lng = Double.parseDouble(latLng.getY());
            if (lat < minLat) {
                minLat = lat;
            }
            if (lat > maxLat) {
                maxLat = lat;
            }
            if (lng < minLng) {
                minLng = lng;
            }
            if (lng > maxLng) {
                maxLng = lng;
            }
        }

        // Calculate the scaling factors to fit all the shapes in the JFrame
        double latScale = (double) frame.getHeight() / (maxLat - minLat);
        double lngScale = (double) frame.getWidth() / (maxLng - minLng);

        // Draw the polygon
        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Coordinate latLng = points.get(i);
            double lat = Double.parseDouble(latLng.getX());
            double lng = Double.parseDouble(latLng.getX());
            int x = (int) ((lng - minLng) * lngScale);
            int y = (int) ((lat - minLat) * latScale);
            xPoints[i] = x;
            yPoints[i] = y;
        }
        graphics.drawPolygon(xPoints, yPoints, points.size());

        // Draw a circle for each pair of coordinates
        int radius = 5;
        for (Coordinate latLng : points) {
            double lat = Double.parseDouble(latLng.getX());
            double lng = Double.parseDouble(latLng.getX());
            int x = (int) ((lng - minLng) * lngScale);
            int y = (int) ((lat - minLat) * latScale);
            graphics.drawOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }
}



// Define drawShapes method here
 /*   public void drawShapes(List<Coordinate> latLngList,JFrame frame) {
        Graphics graphics = frame.getGraphics();

        // Find the minimum and maximum latitude and longitude values
        double minLat = Double.MAX_VALUE, maxLat = Double.MIN_VALUE;
        double minLng = Double.MAX_VALUE, maxLng = Double.MIN_VALUE;
        for (Coordinate latLng : latLngList) {
            double lat = Double.parseDouble(latLng.getX());
            double lng =  Double.parseDouble(latLng.getY());
            if (lat < minLat) {
                minLat = lat;
            }
            if (lat > maxLat) {
                maxLat = lat;
            }
            if (lng < minLng) {
                minLng = lng;
            }
            if (lng > maxLng) {
                maxLng = lng;
            }
        }

        // Calculate the scaling factors to fit all the shapes in the JFrame
        double latScale = (double) frame.getHeight() / (maxLat - minLat);
        double lngScale = (double) frame.getWidth() / (maxLng - minLng);

        // Draw the polygon
        int[] xPoints = new int[latLngList.size()];
        int[] yPoints = new int[latLngList.size()];
        for (int i = 0; i < latLngList.size(); i++) {
            Coordinate latLng = latLngList.get(i);
            double lat =  Double.parseDouble(latLng.getX());
            double lng =  Double.parseDouble(latLng.getX());
            int x = (int) ((lng - minLng) * lngScale);
            int y = (int) ((lat - minLat) * latScale);
            xPoints[i] = x;
            yPoints[i] = y;
        }
        graphics.drawPolygon(xPoints, yPoints, latLngList.size());

        // Draw a circle for each pair of coordinates
        int radius = 5;
        for (Coordinate latLng : latLngList) {
            double lat = Double.parseDouble(latLng.getX());
            double lng =  Double.parseDouble(latLng.getX());
            int x = (int) ((lng - minLng) * lngScale);
            int y = (int) ((lat - minLat) * latScale);
            graphics.drawOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }*/
