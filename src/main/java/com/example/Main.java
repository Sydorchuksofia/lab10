package com.example;

public class Main {
    public static void main(String[] args) {
        MocketDocument mocketDocument = new MocketDocument();
        TimedDocument timedDocument = new TimedDocument(mocketDocument);
        System.out.print(timedDocument.parse());
        
    }
}