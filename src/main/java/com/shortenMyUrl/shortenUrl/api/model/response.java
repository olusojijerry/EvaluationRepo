package com.shortenMyUrl.shortenUrl.api.model;

import java.util.List;

public class response {
    private String longUrl;
    private String description;
    private String errors;
    private String shortenUrl;


    public response(String longUrl, String description, String errors, String shortenUrl) {
        this.longUrl = longUrl;
        this.description = description;
        this.errors = errors;
        this.shortenUrl = shortenUrl;
    }

    public String getlongUrl() {
        return longUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getErrors() {
        return errors;
    }

    public String getShortenUrl() {
        return shortenUrl;
    }
}
