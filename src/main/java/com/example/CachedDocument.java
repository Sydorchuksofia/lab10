package com.example;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CachedDocument implements Document {
    
    private Document document;

    @Override
    public String parse() {
        String cached = DBConnection.getInstance().getDocument(document.getGcsPath());
        if (cached != null) {
            return cached;
        }
        String parsed = document.parse();
        DBConnection.getInstance().createDocument(document.getGcsPath(), parsed);
        return parsed;
        
    }
    @Override
    public String getGcsPath() {
        return document.getGcsPath();
    }

}
