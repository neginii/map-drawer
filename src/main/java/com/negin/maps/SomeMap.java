package com.negin.maps;

import com.negin.models.Coordinate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public class SomeMap extends JComponent implements MouseMotionListener, MouseWheelListener {
    private static final int PIN_SIZE = 10;
    private double scale = 1.0;
    private int translateX = 0;
    private int translateY = 0;
    private int prevMouseX;
    private int prevMouseY;
    private Polygon mapShape;
    private transient List<Pin> pins = new ArrayList<>();

    public SomeMap(List<Coordinate> coordinates) {

        this.setLayout(new BorderLayout());
        int[] xPoints = coordinates.stream().mapToInt(coordinate -> Integer.parseInt(coordinate.getX())).toArray();
        int[] yPoints = coordinates.stream().mapToInt(coordinate -> Integer.parseInt(coordinate.getY())).toArray();

        mapShape = new Polygon(xPoints, yPoints, xPoints.length);
        coordinates.forEach(coordinate -> addPin(Integer.parseInt(coordinate.getX()), Integer.parseInt(coordinate.getY()), coordinate.getName(), Color.magenta));

        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform transform = g2d.getTransform();
        transform.scale(scale, scale);
        transform.translate(translateX, translateY);
        g2d.setTransform(transform);

        g2d.setColor(Color.blue);
        g2d.drawPolygon(mapShape);
        for (Pin pin : pins) {
            int x = pin.getX();
            int y = pin.getY();
            g.fillOval(x - PIN_SIZE / 2, y - PIN_SIZE / 2, PIN_SIZE, PIN_SIZE);
            g.setColor(pin.getColor());
        }
    }


    public void addPin(int x, int y, String name, Color color) {
        Pin pin = new Pin(x, y, name, color);
        pins.add(pin);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        String label = "";

        boolean found = false;
        for (Pin pin : pins) {
            if (Math.abs(x - pin.x) <= 5 && Math.abs(y - pin.y) <= 5) {
                found = true;
                label = pin.getName();
                break;
            }
        }

        setToolTipText(found ? label : null);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // not implemented

        log.info("TranslateX: " + translateX + ", TranslateY: " + translateY);
        int currentMouseX = e.getX();
        int currentMouseY = e.getY();
        int deltaX = currentMouseX - prevMouseX;
        int deltaY = currentMouseY - prevMouseY;
        translateX += deltaX;
        translateY += deltaY;
        prevMouseX = currentMouseX;
        prevMouseY = currentMouseY;
        revalidate();
        repaint();

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches == 1) {
            scale *= 1.1;
        } else if (notches == -1) {
            scale *= 0.9;
        }
        repaint();
    }


    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    private static class Pin {
        int x;
        int y;
        String name;
        Color color;
    }
}
