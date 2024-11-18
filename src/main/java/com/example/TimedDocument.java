package com.example;

import java.time.Duration;
import java.time.LocalTime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimedDocument implements Document {

    private Document document;
    
    @Override
    public String parse() {
        LocalTime starTime = LocalTime.now();
        String parsed = document.parse();
        LocalTime endTime = LocalTime.now();
        System.out.print("Time: " + Duration.between(starTime, endTime).getSeconds());
        return parsed;
    }
    
    @Override
    public String getGcsPath() {
        return document.getGcsPath();
    }

}
