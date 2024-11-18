package com.example;

import lombok.Getter;

@Getter
public class MocketDocument implements Document{
    public String gcsPath;
    
    @Override
    public String parse() {
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
        }
        return "MockedDocument";
    }

    @Override
    public String getGcsPath() {
        return gcsPath;
    }

}
