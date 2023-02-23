package com.negin.models;

import lombok.Data;

@Data
public class Coordinate {

    public Coordinate(String x, String y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    private String x;
    private String y;
    private String name;


}
